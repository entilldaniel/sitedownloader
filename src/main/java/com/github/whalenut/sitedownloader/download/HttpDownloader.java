package com.github.whalenut.sitedownloader.download;

import com.github.whalenut.sitedownloader.parsers.HtmlParser;
import com.github.whalenut.sitedownloader.persist.DiskWriter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;

import static java.net.http.HttpResponse.BodyHandlers.ofInputStream;

public class HttpDownloader {

    private static final String CONTENT_TYPE = "Content-Type";

    private final HttpClient client;
    private final DiskWriter writer;
    private final FilePathCreator filePathCreator;
    private final Executor executor;

    private final Set<URI> pages;


    public HttpDownloader(HttpClient client, DiskWriter writer, FilePathCreator filePathCreator, Executor executor) {
        this.client = client;
        this.writer = writer;
        this.filePathCreator = filePathCreator;
        this.executor = executor;

        this.pages = Collections.synchronizedSet(new HashSet<>());
    }

    public void init(URI uri) {
        pages.add(uri);
        executor.execute(() -> {
            this.download(uri);
        });
    }

    public void download(URI uri) {
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        HttpResponse<InputStream> response = null;
        try {
            response = client.send(request, ofInputStream());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return;
        }

        String contentType = response.headers()
                .firstValue(CONTENT_TYPE)
                .orElse("unknown");

        Path path = filePathCreator.build(Path.of(uri.getPath()), contentType);

        Set<URI> foundUris = writer.write(path, response.body())
                .map(body -> HtmlParser.parseLinks(uri.getScheme(), uri.getHost(), body))
                .orElse(Collections.emptySet());

        Set<URI> filtered = removeDuplicated(foundUris);
        pages.addAll(filtered);

        filtered.forEach(url -> executor.execute(() -> {
            this.download(url);
        }));

    }

    private synchronized Set<URI> removeDuplicated(Set<URI> foundUris) {
        Set<URI> filtered = new HashSet<>();
        Iterator<URI> iterator = foundUris.iterator();

        while (iterator.hasNext()) {
            URI current = iterator.next();
            if (!pages.contains(current)) {
                filtered.add(current);
            }
        }

        return filtered;
    }


}
