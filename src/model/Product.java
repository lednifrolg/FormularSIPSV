/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nolofinwe
 */
public class Product {
    private String mName;
    private double mPrice;
    private int mID;
    private ArrayList<Variant> mVariants;

    public Product(String mName, double mPrice, int mID, ArrayList<Variant> mVariants) {
        this.mName = mName;
        this.mPrice = mPrice;
        this.mID = mID;
        this.mVariants = mVariants;
    }

    public Product() {
        }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double mPrice) {
        this.mPrice = mPrice;
    }

    public int getID() {
        return mID;
    }

    public void setID(int mID) {
        this.mID = mID;
    }

    public ArrayList<Variant> getVariants() {
        return mVariants;
    }

    public void setVariants(ArrayList<Variant> mVariants) {
        this.mVariants = mVariants;
    }

    @Override
    public String toString() {
        return "Product{" + "mName=" + mName + ", mPrice=" + mPrice + ", mID=" + mID + ", mVariants=" + mVariants + '}';
    }
    
    
    
}
