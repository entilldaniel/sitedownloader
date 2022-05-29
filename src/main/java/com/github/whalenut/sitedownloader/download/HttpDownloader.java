package com.github.whalenut.sitedownloader.download;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static java.net.http.HttpResponse.BodyHandlers.ofInputStream;

public class HttpDownloader {

    private static final String CONTENT_TYPE = "Content-Type";

    private final HttpClient client;


    public HttpDownloader(HttpClient client) {
        this.client = client;
    }

    public InputStream download(URI uri) throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        HttpResponse<InputStream> response = client.send(request, ofInputStream());

        String contentType = response.headers()
                .firstValue(CONTENT_TYPE)
                .orElse("unknown");

        return response.body();
    }




}
