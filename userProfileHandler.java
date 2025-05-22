import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.FactoryConfigurationError;
import org.xml.sax.InputSource;

public class XMLParser {

    public static void main(String[] args) {
        String xmlData = "<?xml version=\"1.0\" ?>\n" +
                "<!DOCTYPE foo [<!ELEMENT foo ANY >\n" +
                "<!ENTITY xxe SYSTEM \"file:///etc/passwd\" >]>\n" +
                "<foo>&xxe;</foo>";

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            
            // Vulnerable: External entities are not disabled, risking an XXE attack
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setXIncludeAware(false);
            factory.setExpandEntityReferences(false);
            DocumentBuilder builder = factory.newDocumentBuilder();

            InputSource is = new InputSource(new StringReader(xmlData));
            Document document = builder.parse(is);

            System.out.println("Root element :" + document.getDocumentElement().getNodeName());

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}