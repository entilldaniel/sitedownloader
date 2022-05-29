package com.github.whalenut.sitedownloader.persist;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiskWriterTest {

    private static final Path BASE_PATH = Paths.get("./", UUID.randomUUID().toString());

    @AfterEach
    void cleanUp() throws IOException {
        Files.walkFileTree(BASE_PATH, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    @Test
    void writeToFile() throws IOException {
        var contents = "hello, World!";
        var test = new ByteArrayInputStream(contents.getBytes(StandardCharsets.UTF_8));
        var dw = new DiskWriter(BASE_PATH);
        dw.write(Path.of("index.html"), test);

        String s = Files.readString(BASE_PATH.resolve("index.html"));
        assertEquals(contents, s);
    }


    @Test
    void writeToFileInDepth() throws IOException {
        var contents = "hello, World!";
        var test = new ByteArrayInputStream(contents.getBytes(StandardCharsets.UTF_8));
        Path path = Path.of("a/b/c/index.html");

        var dw = new DiskWriter(BASE_PATH);
        dw.write(path, test);

        String s = Files.readString(BASE_PATH.resolve(path));
        assertEquals(contents, s);
    }

}

