package com.github.whalenut.sitedownloader;


import com.github.whalenut.sitedownloader.download.FilePathCreator;
import com.github.whalenut.sitedownloader.download.HttpDownloader;
import com.github.whalenut.sitedownloader.persist.DiskWriter;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.nio.file.Path;
import java.util.concurrent.Executors;

@Command(name = "SiteDownloader",
        mixinStandardHelpOptions = true,
        version = "0.0.1",
        description = "Downloads a site recursively to a specified folder.")
public class SiteDownloader implements Runnable {

    @Option(names = {"-d", "--destination"}, description = "The destination of the downloaded site.", paramLabel = "DESTINATION")
    private Path path;

    @Option(names = {"-u", "--url"}, description = "The URL to start downloading from.", required = true, paramLabel = "URL")
    private URI url;

    @Override
    public void run() {
        var clientExecutor = Executors.newFixedThreadPool(10);
        var downloadExecutor = Executors.newFixedThreadPool(10);

        var client = HttpClient.newBuilder()
                .executor(clientExecutor)
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
        var writer = new DiskWriter(path);
        var pathCreator = new FilePathCreator();
        var downloader = new HttpDownloader(client, writer, pathCreator, downloadExecutor);

        downloader.init(url);
    }
}
