package ru.otus.nio;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;


public class PathExample {
    public static void main(String[] args) throws IOException, URISyntaxException {
        new PathExample().go();
    }

    private void go() throws IOException, URISyntaxException {
        var fileUrl = ClassLoader.getSystemResource("share.xml");
        if (fileUrl == null) {
            throw new NoSuchElementException("Resource file share.xml not found");
        }
        Path shareXml = Paths.get(fileUrl.toURI());
        System.out.println("FileName:" + shareXml.getFileName());
        System.out.println("FileSystem:" + shareXml.getFileSystem());
        System.out.println("Parent:" + shareXml.getParent());
        System.out.println("isAbsolute:" + shareXml.isAbsolute());
        System.out.println("realPath:" + shareXml.toRealPath());

        var notExists = Paths.get("NotExists.xml");
        try {
            System.out.println("realPath:" + notExists.toRealPath());
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
