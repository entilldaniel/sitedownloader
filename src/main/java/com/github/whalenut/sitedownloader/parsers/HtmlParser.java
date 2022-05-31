package com.github.whalenut.sitedownloader.parsers;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HtmlParser {

    private static final Pattern pattern = Pattern.compile("href=['\"](?<url>.*?)['\"]|src=['\"](?<src>.*?)['\"]");

    public static Set<URI> parseLinks(String protocol, String host, String data) {
        Matcher matcher = pattern.matcher(data);

        var result = new HashSet<String>();
        while(matcher.find()) {
            String group = matcher.group(1);
            result.add(group);
        }
        return result.stream()
                .filter(l -> !Objects.isNull(l))
                .filter(l -> !l.startsWith("#"))
                .filter(l -> !l.startsWith("//"))
                .map(link -> {
                    URI pageURL;
                    if (!link.startsWith("/")) {
                        try {
                            pageURL = new URL(link).toURI();
                        } catch (MalformedURLException | URISyntaxException e) {
                            System.out.println("Could not create URL, ignoring " + link);
                            return null;
                        }
                    } else {
                        try {
                            pageURL = new URI(protocol, host, link, null);
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                    System.out.println(pageURL);
                    if (pageURL.getHost().equals(host)) {
                        return pageURL;
                    }
                    return null;
                })
                .collect(Collectors.toSet());
    }

}
