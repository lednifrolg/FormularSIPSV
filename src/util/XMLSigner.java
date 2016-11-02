package util;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import org.bouncycastle.tsp.TSPAlgorithms;
import org.bouncycastle.tsp.TimeStampRequest;
import org.bouncycastle.tsp.TimeStampRequestGenerator;
import org.bouncycastle.tsp.TimeStampResponse;
import org.bouncycastle.util.encoders.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import webservice.TS;
import webservice.TSSoap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.WebServiceException;
import java.io.*;
import java.util.Scanner;

/**
 * Created by Nolofinwe on 10/22/2016.
 */
public class XMLSigner {

    private String mOriginalXMLFile;
    private String mSchemaFile;
    private String mXSLFile;

    private String mOriginalXMLFileContent;
    private String mSchemaFileContent;
    private String mXSLFileContent;

    private String mNamespace;

    private ActiveXComponent mXadesSigner;
    private ActiveXComponent mXMLPlugin;

    public XMLSigner(String originalXMLFile, String schemaFile, String XSLFile, String namespace) {
        this.mOriginalXMLFile = new File(originalXMLFile).getName();
        this.mSchemaFile = new File(schemaFile).getName();
        this.mXSLFile = new File(XSLFile).getName();

        this.mNamespace = namespace;

        try {
            this.mOriginalXMLFileContent = new Scanner(new File(originalXMLFile)).useDelimiter("\\Z").next();
            this.mSchemaFileContent = new Scanner(new File(schemaFile)).useDelimiter("\\Z").next();
            this.mXSLFileContent = new Scanner(new File(XSLFile)).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        mXadesSigner = new ActiveXComponent("DSig.XadesSig");
        mXMLPlugin = new ActiveXComponent("DSig.XmlPlugin");
    }


    public String sign() {
        System.out.println(mOriginalXMLFileContent);

        Object xmlObj = Dispatch.call(mXMLPlugin, "CreateObject", "ID1", "catalog",
                mOriginalXMLFileContent, mSchemaFileContent, "http://schemas.fiit.sk/form", mNamespace, mXSLFileContent, "http://schemas.fiit.sk/form.xsd");
        System.out.println(mXMLPlugin.getProperty("ErrorMessage").getString());


        Dispatch.call(mXadesSigner, "AddObject", xmlObj);
        System.out.println(mXadesSigner.getProperty("ErrorMessage").getString());

        Dispatch.call(mXadesSigner, "Sign", "signatureId", "sha256", "urn:oid:1.3.158.36061701.1.2.1");
        String sign = mXadesSigner.getProperty("SignedXmlWithEnvelope").toString();

        System.out.println(mXadesSigner.getProperty("ErrorMessage").getString());

        String fileName = null;
        try {
            fileName = "sign.xml";
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
            out.write(sign);
            out.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return fileName;
    }

    public String signWithTimeStamp() {
        String xmlFilePath = sign();

        if (xmlFilePath.isEmpty() || xmlFilePath == null) {
            return "ERROR: Signed file not found";
        }


        try {
            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document document = docBuilder.parse(new File(xmlFilePath));

            Node signatureValueElement =
                    document.getElementsByTagName("ds:SignatureValue").item(0);

            String out = signatureValueElement.getTextContent();

            byte[] signatureValue = out.getBytes();

            TimeStampRequestGenerator reqGen = new TimeStampRequestGenerator();
            reqGen.setCertReq(true);
            TimeStampRequest tsReq = reqGen.generate(TSPAlgorithms.SHA1, signatureValue);

            byte[] tsData = tsReq.getEncoded();

            String base64data = Base64.toBase64String(tsData);

            TS ts = new TS();
            TSSoap soap = ts.getTSSoap();
            String timestamp = soap.getTimestamp(base64data);
            if (timestamp == null)
                throw new WebServiceException("Timestamp error: empty");

            byte[] responseB64 = timestamp.getBytes();

            TimeStampResponse tsRes =
                    new TimeStampResponse(Base64.decode(responseB64));

            String decodedTimestamp = Base64.toBase64String(tsRes.getTimeStampToken().getEncoded());
            System.out.println(decodedTimestamp);

            insertTimeStamp(decodedTimestamp, xmlFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            return "failed";
        }

        return "done";
    }

    public boolean insertTimeStamp(String timestamp, String xmlFilePath) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        Document document = docBuilder.parse(new File(xmlFilePath));

        Text timestampNode = document.createTextNode(timestamp);

        Node qp = document.getElementsByTagName("xades:QualifyingProperties").item(0);

        if (qp == null)
            return false;

        Element up = document.createElement("xades:UnsignedProperties");
        Element usp = document.createElement("xades:UnsignedSignatureProperties");
        Element sts = document.createElement("xades:SignatureTimeStamp");
        Element ets = document.createElement("xades:EncapsulatedTimeStamp");
        ets.appendChild(timestampNode);

        up.appendChild(usp);
        usp.appendChild(sts);
        sts.appendChild(ets);

        qp.appendChild(up);

        Transformer tf = null;
        try {
            tf = TransformerFactory.newInstance().newTransformer();

            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.setOutputProperty(OutputKeys.METHOD, "xml");
            tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource domSource = new DOMSource(document);
            StreamResult sr = new StreamResult(new File("signTS.xml"));
            tf.transform(domSource, sr);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            return false;
        } catch (TransformerException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}


