package com.github.whalenut.sitedownloader;


import com.github.whalenut.sitedownloader.download.HttpDownloader;
import com.github.whalenut.sitedownloader.persist.DiskWriter;
import picocli.CommandLine;

import java.net.http.HttpClient;

public class Application {

    public static void main(String[] args) {
        var siteDownloader = new SiteDownloader();
        new CommandLine(siteDownloader).execute(args);
    }
}
