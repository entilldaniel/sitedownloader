package com.github.whalenut.sitedownloader.persist;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class DiskWriter {

    //TODO: This is to simple.
    private static final List<String> READABLE_FILE_SUFFIXES = List.of("xml", "html", "js", "jsp", "aspx", "php");
    private final Path basePath;

    public DiskWriter(Path basePath) {
        this.basePath = basePath;

        basePath.toFile().mkdirs();
    }

    public Optional<String> write(Path path, InputStream stream) {
        File file = Paths.get(basePath.toString(), path.toString()).toFile();
        file.getParentFile().mkdirs();

        try {
            file.createNewFile();

            //TODO: This can surely be optimized.
            var fos = new FileOutputStream(file);
            var bis = new BufferedInputStream(stream);
            fos.write(bis.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }

        boolean isReadableFile = READABLE_FILE_SUFFIXES.stream()
                .anyMatch(s -> path.toString().endsWith(s));

        if (isReadableFile) {
            try {
                return Optional.of(Files.readString(file.toPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return Optional.empty();
    }


}
