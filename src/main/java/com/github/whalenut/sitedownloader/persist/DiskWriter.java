package com.github.whalenut.sitedownloader.persist;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DiskWriter {

    private final Path basePath;

    public DiskWriter(Path basePath) {
        this.basePath = basePath;

        basePath.toFile().mkdirs();
    }

    public void write(Path path, InputStream stream) throws IOException {
        File file = Paths.get(basePath.toString(), path.toString()).toFile();
        file.getParentFile().mkdirs();
        file.createNewFile();


        //TODO: This can surely be optimized.
        var fos = new FileOutputStream(file);
        var bis = new BufferedInputStream(stream);
        fos.write(bis.readAllBytes());
    }


}
