package ru.otus.xml.dom;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class XMLreader {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        var file = new File(ClassLoader.getSystemResource("data.xml").getFile());


        var dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        dbFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        var dBuilder = dbFactory.newDocumentBuilder();
        var xmlDocument = dBuilder.parse(file);

        //http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        xmlDocument.getDocumentElement().normalize();

        List<Share> shareList = new ArrayList<>();
        System.out.println("Root element :" + xmlDocument.getDocumentElement().getNodeName());

        NodeList nList = xmlDocument.getElementsByTagName("share");
        for (var idx = 0; idx < nList.getLength(); idx++) {
            var nNode = nList.item(idx);
            System.out.println("Element :" + nNode.getNodeName());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                var eElement = (Element) nNode;

                System.out.println("ticker: " + eElement.getElementsByTagName("ticker").item(0).getTextContent());
                System.out.println("last: " + eElement.getElementsByTagName("last").item(0).getTextContent());
                System.out.println("date : " + eElement.getElementsByTagName("date").item(0).getTextContent());


                shareList.add(new Share(
                        eElement.getElementsByTagName("ticker").item(0).getTextContent(),
                        Double.parseDouble(eElement.getElementsByTagName("last").item(0).getTextContent()),
                        eElement.getElementsByTagName("date").item(0).getTextContent()));
            }
        }
        System.out.println(shareList);

        //XPath
        var xPath = XPathFactory.newInstance().newXPath();
        var expression = "/shares/share[@id='1']";
        var nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);


        for (var idx = 0; idx < nodeList.getLength(); idx++) {
            var nNode = nodeList.item(idx);
            System.out.println("\nXPath Element :" + nNode.getNodeName());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                var eElement = (Element) nNode;
                System.out.println("ticker: " + eElement.getElementsByTagName("ticker").item(0).getTextContent());
                System.out.println("last: " + eElement.getElementsByTagName("last").item(0).getTextContent());
                System.out.println("date: " + eElement.getElementsByTagName("date").item(0).getTextContent());
            }
        }

    }
}
