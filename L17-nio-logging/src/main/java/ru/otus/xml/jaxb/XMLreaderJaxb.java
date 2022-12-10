package ru.otus.xml.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * @author sergey
 * created on 25.09.18.
 */

/* for java 9,10
https://stackoverflow.com/questions/43574426/how-to-resolve-java-lang-noclassdeffounderror-javax-xml-bind-jaxbexception-in-j
--add-modules java.xml.bind

removed from java 11
*/
public class XMLreaderJaxb {
    public static void main(String[] args) throws JAXBException {
        ClassLoader classLoader = ru.otus.xml.sax.XMLreader.class.getClassLoader();

        File fileShare = new File(classLoader.getResource("share.xml").getFile());

        JAXBContext jaxbContext = JAXBContext.newInstance(Share.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Share share = (Share) unmarshaller.unmarshal(fileShare);
        System.out.println(share);

        File fileData = new File(classLoader.getResource("data.xml").getFile());

        JAXBContext jaxbContext2 = JAXBContext.newInstance(Shares.class);
        Unmarshaller unmarshaller2 = jaxbContext2.createUnmarshaller();
        Shares shareList = (Shares) unmarshaller2.unmarshal(fileData);
        System.out.println(shareList);
    }
}
