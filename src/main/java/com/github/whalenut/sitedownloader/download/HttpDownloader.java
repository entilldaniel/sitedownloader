package com.github.whalenut.sitedownloader.download;

import com.github.whalenut.sitedownloader.persist.DiskWriter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.List;

import static java.net.http.HttpResponse.BodyHandlers.ofInputStream;

public class HttpDownloader {

    private static final String CONTENT_TYPE = "Content-Type";

    private final HttpClient client;
    private final DiskWriter writer;

    public HttpDownloader(HttpClient client, DiskWriter writer) {
        this.client = client;
        this.writer = writer;
    }

    public InputStream download(URI uri) throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        HttpResponse<InputStream> response = client.send(request, ofInputStream());


        //TODO: handle types!
        String contentType = response.headers()
                .firstValue(CONTENT_TYPE)
                .orElse("unknown");

        String path = uri.getPath();
        if (path.isBlank()) {
            path = "index.html";
        }

        writer.write(path, response.body());

        return response.body();
    }




}
