package ru.otus.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class DemoIO {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("current dir: " + System.getProperty("user.dir"));
         zipFile("textFile.txt");
        // writeObject("person.bin");
        //  readObject("person.bin");
        //  writeTextFile("textFile.txt");
        //  readTextFile("textFile.txt");

    }

    //Обратите внимание на цепочку декораторов
    private static void zipFile(String textFile) throws IOException {
        try (var bufferedInputStream = new BufferedInputStream(new FileInputStream(textFile));
             var zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(textFile + "_copy.zip")))) {

            var zipEntry = new ZipEntry(textFile);
            zipOut.putNextEntry(zipEntry);
            var buffer = new byte[2];
            int size;
            while ((size = bufferedInputStream.read(buffer, 0, buffer.length)) > 0) {
                zipOut.write(buffer, 0, size);
            }
            zipOut.closeEntry();
        }
    }

    private static void writeObject(String personFile) throws IOException {
        try (var objectOutputStream = new ObjectOutputStream(new FileOutputStream(personFile))) {

            var person = new Person(92, "SerialPerson", "hidden");
            System.out.println("serializing:" + person);
            objectOutputStream.writeObject(person);
        }
    }

    private static void readObject(String personFile) throws IOException, ClassNotFoundException {
        try (var objectInputStream = new ObjectInputStream(new FileInputStream(personFile))) {

            var person = (Person) objectInputStream.readObject();
            System.out.println("read object:" + person);
        }
    }

    private static void writeTextFile(String textFile) throws IOException {
        var line1 = "Hello Java, str1";
        var line2 = "Hello Java, str2";
        try (var bufferedWriter = new BufferedWriter(new FileWriter(textFile))) {
            bufferedWriter.write(line1);
            bufferedWriter.newLine();
            bufferedWriter.write(line2);
        }
    }

    private static void readTextFile(String textFile) throws IOException {
        try (var bufferedReader = new BufferedReader(new FileReader(textFile))) {
            System.out.println("text from the file:");
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
