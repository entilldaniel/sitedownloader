package com.github.whalenut.sitedownloader;


import com.github.whalenut.sitedownloader.download.HttpDownloader;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.io.IOException;
import java.net.URI;

@Command(name = "SiteDownloader",
mixinStandardHelpOptions = true,
version = "0.0.1",
description = "Downloads a site recursively to a specified folder.")
public class SiteDownloader implements Runnable{

    @Option(names = {"-d", "--destination"}, description = "The destination of the downloaded site.", paramLabel = "DESTINATION")
    private File file;

    @Option(names = {"-u", "--url"}, description = "The URL to start downloading from.", required = true, paramLabel = "URL")
    private URI url;

    private final HttpDownloader downloader;

    public SiteDownloader(HttpDownloader downloader) {
        this.downloader = downloader;

    }

    @Override
    public void run() {
        try {
            downloader.download(url);
        } catch (IOException | InterruptedException e) {
            //TODO: clean up and do error handling.
            throw new RuntimeException(e);
        }
    }
}
