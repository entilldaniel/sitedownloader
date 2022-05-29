package com.github.whalenut.sitedownloader.download;

import com.github.whalenut.sitedownloader.persist.DiskWriter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HttpDownloaderTest {

    @Test
    void testDownload() throws IOException, InterruptedException {
        var client = mock(HttpClient.class);
        var writer = mock(DiskWriter.class);
        var pathCreator = new FilePathCreator();

        var response = mock(HttpResponse.class);
        when(client.send(any(), any())).thenReturn(response);
        InputStream resource = this.getClass().getClassLoader().getResourceAsStream("index.html");
        when(response.body()).thenReturn(resource);
        HttpHeaders headers = HttpHeaders.of(
                Map.of("Content-Type", List.of("text/html")),
                (a, b) -> true);
        when(response.headers()).thenReturn(headers);

        var downloader = new HttpDownloader(client, writer, pathCreator);

        InputStream download = downloader.download(URI.create("https://foo.com"));

        verify(writer).write(Path.of("index.html"), download);
    }


}
