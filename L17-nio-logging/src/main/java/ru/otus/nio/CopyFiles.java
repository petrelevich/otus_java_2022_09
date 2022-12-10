package ru.otus.nio;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class CopyFiles {
    public static void main(String[] args) throws IOException {
        copyFiles("textFile.txt", "textFile.bak");
    }

    private static void copyFiles(String srcFile, String destFile) throws IOException {
        try (var channelSrc = FileChannel.open(Path.of(srcFile), StandardOpenOption.READ);
             var channelDest = FileChannel.open(Path.of(destFile), StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {

            channelSrc.transferTo(0, channelSrc.size(), channelDest);
        }
    }
}
