package com.cokimutai.mrec;

import java.io.Serializable;

/**
 * Created by cokimutai on 1/7/2017.
 */
public class SuppliaData implements Serializable {

    String fname, lname, resdnc, phone;
    public String suppliaId;
    int litres;
    String date;

    public String getSuppliaId(){
        return suppliaId;
    }

    public void setSuppliaId(String suppliaId){
        this.suppliaId = suppliaId;
    }

    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date = date;
    }
}
