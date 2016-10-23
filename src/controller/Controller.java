/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Product;
import model.Variant;
import util.WriteXMLFile;
import util.XMLSigner;
import view.FormularView;

import javax.swing.*;
import javax.xml.XMLConstants;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
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

    public void setting(JTextField field) {
        System.out.println("TESTIK");
        field.setText("DSA");
    }

    public void addVariant() {
        System.out.println(mView.getAvailTextField().getText());

        JList<Variant> mVariantsList = mView.getVariantsList();
        DefaultListModel<Variant> m = (DefaultListModel<Variant>) mVariantsList.getModel();

        Variant variant = new Variant(mView.getSizeTextField().getText(),
                Integer.parseInt(mView.getAvailTextField().getText()));

        m.addElement(variant);


        mVariants.add(variant);
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(xmlFile.getSystemId() + " is valid");

    }

    // TODO : 
    public void saveAsXML() {
        updateProduct();
        System.out.println(mProduct);
        WriteXMLFile.save(mProduct);
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
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        System.out.println("XSLT transformation completed.");
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

    public void SignDoc() {
        XMLSigner signer = new XMLSigner("src/formular/file.xml", "src/formular/schema2.xsd", "src/formular/transformSchema.xslt", "http://schemas.fiit.sk/form");
        signer.sign();
    }
}
