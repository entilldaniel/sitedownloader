package com.github.whalenut.sitedownloader;


import com.github.whalenut.sitedownloader.download.HttpDownloader;
import picocli.CommandLine;

import java.net.http.HttpClient;

public class Application {

    public static void main(String[] args) {
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
        var downloader = new HttpDownloader(client);
        var siteDownloader = new SiteDownloader(downloader);
        new CommandLine(siteDownloader).execute(args);
    }
}
