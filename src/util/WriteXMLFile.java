package util;

import model.Product;
import model.Variant;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * Created by Nolofinwe on 10/22/2016.
 */
public class WriteXMLFile {

    public static boolean save(Product mProduct) {
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("catalog");
            doc.appendChild(rootElement);

            Attr xmlns = doc.createAttribute("xmlns");
            xmlns.setValue("http://schemas.fiit.sk/form");
            rootElement.setAttributeNode(xmlns);

            // staff elements
            Element product = doc.createElement("product");
            rootElement.appendChild(product);

            // set attribute to staff element
            Attr attr = doc.createAttribute("id");
            attr.setValue(Integer.toString(mProduct.getID()));
            product.setAttributeNode(attr);

            // shorten way
            // staff.setAttribute("id", "1");

            // firstname elements
            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(mProduct.getName()));
            product.appendChild(name);

            // lastname elements
            Element price = doc.createElement("price");
            price.appendChild(doc.createTextNode(Double.toString(mProduct.getPrice())));
            product.appendChild(price);

            for (Variant v : mProduct.getVariants()) {
                Element variant = doc.createElement("variant");
                product.appendChild(variant);

                Element size = doc.createElement("size");
                size.appendChild(doc.createTextNode(v.getmSize()));
                variant.appendChild(size);

                Element avail = doc.createElement("available");
                avail.appendChild(doc.createTextNode(Integer.toString(v.getmAvail())));
                variant.appendChild(avail);
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("src/formular/file.xml"));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            System.out.println("File saved!");
//
//            StreamResult res =  new StreamResult(System.out);
//            transformer.transform(source, res);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }

        return true;
    }
}

