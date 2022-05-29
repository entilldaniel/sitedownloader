package com.github.whalenut.sitedownloader;


import picocli.CommandLine;

public class Application {

    public static void main(String[] args) {
        var downloader = new SiteDownloader();
        new CommandLine(downloader).execute(args);
    }
}
