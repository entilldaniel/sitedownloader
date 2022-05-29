package com.github.whalenut.sitedownloader;


import picocli.CommandLine;

public class Application {

    public static void main(String[] args) {
        var siteDownloader = new SiteDownloader();
        new CommandLine(siteDownloader).execute(args);
    }
}
