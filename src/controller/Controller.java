/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextField;
import model.Product;
import model.Variant;
import view.FormularView;

/**
 *
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

    // TODO : 
    public void validateXML() {
        
    }
    
    // TODO : 
    public void saveAsXML() {
        updateProduct();
        System.out.println(mProduct);
    }
    
    // TODO : 
    public void saveAsXSLT() {
        
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
}
