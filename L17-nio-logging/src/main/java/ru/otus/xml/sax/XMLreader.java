package ru.otus.xml.sax;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class XMLreader {

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        var file = new File(ClassLoader.getSystemResource("data.xml").getFile());

        List<Share> shareList = new XMLreader().parse(file);
        System.out.println(shareList);
    }

    private List<Share> parse(File file) throws ParserConfigurationException, SAXException, IOException {
        var factory = SAXParserFactory.newInstance();
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        var saxParser = factory.newSAXParser();

        var handler = new XMLhandler();
        saxParser.parse(file, handler);
        return handler.getResult();
    }
}


