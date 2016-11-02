/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Product;
import model.Variant;
import org.bouncycastle.tsp.*;
import org.bouncycastle.util.encoders.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import util.WriteXMLFile;
import util.XMLSigner;
import view.FormularView;
import webservice.TS;
import webservice.TSSoap;

import javax.swing.*;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.ws.WebServiceException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * @author nolofinwe
 */
public class Controller {

    private FormularView mView;
    private Product mProduct;
    private ArrayList<Variant> mVariants;

    public Controller(FormularView view) {
        this.mView = view;
        mVariants = new ArrayList();
        mProduct = new Product();
    }

    public void addVariant() {
        System.out.println(mView.getAvailTextField().getText());

        JList<Variant> mVariantsList = mView.getVariantsList();
        DefaultListModel<Variant> m = (DefaultListModel<Variant>) mVariantsList.getModel();

        Variant variant = new Variant(mView.getSizeTextField().getText(),
                Integer.parseInt(mView.getAvailTextField().getText()));

        m.addElement(variant);

        mVariants.add(variant);
        try {
            getap();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TSPException e) {
            e.printStackTrace();
        }
    }

    public void removeVariant() {
        JList<Variant> mVariantsList = mView.getVariantsList();
        DefaultListModel<Variant> m = (DefaultListModel<Variant>) mVariantsList.getModel();

        Variant variant = m.remove(mVariantsList.getSelectedIndex());
        System.out.println(mVariants.size());
        mVariants.remove(variant);
        System.out.println(mVariants.size());

    }

    public void validateXML() {

        File schemaFile = new File("src/formular/schema2.xsd");
        Source xmlFile = new StreamSource(new File("src/formular/file.xml"));
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = null;
        try {
            schema = schemaFactory.newSchema(schemaFile);

            Validator validator = schema.newValidator();


            validator.validate(xmlFile);
        } catch (org.xml.sax.SAXException e) {
            showDialog(xmlFile.getSystemId() + " is NOT valid");
            e.printStackTrace();
            return;
        } catch (IOException e) {
            showDialog(xmlFile.getSystemId() + " is NOT valid");
            e.printStackTrace();
            return;
        }
        showDialog(xmlFile.getSystemId() + " is valid");
        System.out.println(xmlFile.getSystemId() + " is valid");

    }


    // TODO :
    public void saveAsXML() {
        updateProduct();
        System.out.println(mProduct);
        boolean result = WriteXMLFile.save(mProduct);

        if (result) {
            showDialog("XML File created");
            openFile("src/formular/file.xml");
        } else {
            showDialog("XML File creation failed");
        }
    }


    public void saveAsXSLT() {
        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File("src/formular/transformSchema.xslt"));
        Transformer transformer = null;
        try {
            transformer = factory.newTransformer(xslt);


            Source text = new StreamSource(new File("src/formular/file.xml"));
            transformer.transform(text, new StreamResult(new File("src/formular/xsltOutput.html")));
        } catch (TransformerConfigurationException e) {
            showDialog("XSLT transformation ERROR");
            e.printStackTrace();
            return;
        } catch (TransformerException e) {
            showDialog("XSLT transformation ERROR");
            e.printStackTrace();
            return;
        }
        showDialog("XSLT transformation completed.");
        System.out.println("XSLT transformation completed.");

        openFile("src/formular/xsltOutput.html");
    }

    public void updateProduct() {
        if (!mView.getNameTextField().getText().isEmpty()) {
            System.out.println(mView.getNameTextField().getText());
            mProduct.setName(mView.getNameTextField().getText());
        }

        if (!mView.getPriceTextField().getText().isEmpty()) {
            System.out.println(mView.getPriceTextField().getText());
            mProduct.setPrice(Double.parseDouble(mView.getPriceTextField().getText()));
        }

        if (!mView.getIDTextField().getText().isEmpty()) {
            System.out.println(mView.getIDTextField().getText());
            mProduct.setID(Integer.parseInt(mView.getIDTextField().getText()));
        }

        if (mVariants != null)
            mProduct.setVariants(mVariants);
    }

    public void signDoc() {
        XMLSigner signer = new XMLSigner("src/formular/file.xml", "src/formular/schema2.xsd", "src/formular/transformSchema.xslt", "http://schemas.fiit.sk/form");
        signer.sign();
    }

    public void signDocTS() {
        XMLSigner signer = new XMLSigner("src/formular/file.xml", "src/formular/schema2.xsd", "src/formular/transformSchema.xslt", "http://schemas.fiit.sk/form");
        String result = signer.signWithTimeStamp();
        showDialog("Signing with TimeStamp : " + result);
    }

    private void getap() throws IOException, ParserConfigurationException, SAXException, TSPException {
        String xmlFilePath = "sign.xml";

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        Document document = docBuilder.parse(new File(xmlFilePath));

        Node signatureValueElement =
                document.getElementsByTagName("ds:SignatureValue").item(0);

        String out = signatureValueElement.getChildNodes().item(0).getNodeValue();

        byte[] signatureValue = out.getBytes();

        TimeStampRequestGenerator reqGen = new TimeStampRequestGenerator();
        reqGen.setCertReq(true);
        TimeStampRequest tsReq = reqGen.generate(TSPAlgorithms.SHA1, signatureValue);

        byte[] tsData = tsReq.getEncoded();

        String base64data = Base64.toBase64String(tsData);

        TS ts = new TS();
        TSSoap soap = ts.getTSSoap();
        String timestamp = soap.getTimestamp(base64data);
        if(timestamp == null)
            throw new WebServiceException("Webová služba nedostupná");

        byte[] responseB64 = timestamp.getBytes();


        TimeStampResponse tsRes =
                new TimeStampResponse(Base64.decode(responseB64));

        String decodedTimestamp = Base64.toBase64String(tsRes.getTimeStampToken().getEncoded());
        System.out.println(decodedTimestamp);

    }

    private void showDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    /**
     * Open file in default application
     * @param path Path to file
     */
    private void openFile(String path) {
        System.out.println("Opening " + path + " in default browser");
        try {
            Desktop.getDesktop().open(new File(path));
            System.out.println("File opened");
        } catch (IOException e) {
            System.out.println("File opening failed");
            e.printStackTrace();
        }
    }
}
