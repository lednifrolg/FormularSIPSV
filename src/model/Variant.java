/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author nolofinwe
 */
public class Variant {
    private String mSize;
    private int mAvail;

    public Variant(String mSize, int mAvail) {
        this.mSize = mSize;
        this.mAvail = mAvail;
    }

    public String getmSize() {
        return mSize;
    }

    public void setmSize(String mSize) {
        this.mSize = mSize;
    }

    public int getmAvail() {
        return mAvail;
    }

    public void setmAvail(int mAvail) {
        this.mAvail = mAvail;
    }

    @Override
    public String toString() {
        return "Size : " + mSize + ", Available=" + mAvail;
    }
    
    
    
}
