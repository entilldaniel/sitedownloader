package com.github.whalenut.sitedownloader.download;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilePathCreatorTest {

    @Test
    void testMimeTypeMapForContentType() {
        FilePathCreator creator = new FilePathCreator();

        URI uri = URI.create("https://foo.com/a/b/c");
        String type = "application/xml";

        Path path = Path.of(uri.getPath());
        Path result = creator.build(path, type);

        assertEquals(Path.of("/a/b/c.xml"), result);
    }

    @Test
    void testSimpleFileName() {
        FilePathCreator creator = new FilePathCreator();

        URI uri = URI.create("https://foo.com/a/b/c.jpg");
        String type = "unknown";

        Path path = Path.of(uri.getPath());
        Path result = creator.build(path, type);

        assertEquals(Path.of("/a/b/c.jpg"), result);
    }

    @Test
    void testNoSuffixOrKnownType() {
        FilePathCreator creator = new FilePathCreator();

        URI uri = URI.create("https://foo.com/a/b/c");
        String type = "unknown";

        Path path = Path.of(uri.getPath());
        Path result = creator.build(path, type);

        assertEquals(Path.of("/a/b/c.html"), result);
    }

    @Test
    void testNoSuffixOrNameOrKnownType() {
        FilePathCreator creator = new FilePathCreator();

        URI uri = URI.create("https://foo.com/");
        String type = "unknown";

        Path path = Path.of(uri.getPath());
        Path result = creator.build(path, type);

        assertEquals(Path.of("index.html"), result);
    }

}
