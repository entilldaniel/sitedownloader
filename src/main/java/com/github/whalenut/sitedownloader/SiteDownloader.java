package com.github.whalenut.sitedownloader;


import com.github.whalenut.sitedownloader.download.FilePathCreator;
import com.github.whalenut.sitedownloader.download.HttpDownloader;
import com.github.whalenut.sitedownloader.persist.DiskWriter;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.nio.file.Path;

@Command(name = "SiteDownloader",
mixinStandardHelpOptions = true,
version = "0.0.1",
description = "Downloads a site recursively to a specified folder.")
public class SiteDownloader implements Runnable{

    @Option(names = {"-d", "--destination"}, description = "The destination of the downloaded site.", paramLabel = "DESTINATION")
    private Path path;

    @Option(names = {"-u", "--url"}, description = "The URL to start downloading from.", required = true, paramLabel = "URL")
    private URI url;

    @Override
    public void run() {
        try {
            var client = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.ALWAYS)
                    .build();
            var writer = new DiskWriter(path);
            var pathCreator = new FilePathCreator();
            var downloader = new HttpDownloader(client, writer, pathCreator);

            downloader.download(url);
        } catch (IOException | InterruptedException e) {
            //TODO: clean up and do error handling.
            throw new RuntimeException(e);
        }
    }
}
