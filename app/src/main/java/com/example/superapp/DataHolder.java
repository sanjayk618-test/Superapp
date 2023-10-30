package com.example.superapp;

import android.util.Log;

public class DataHolder {
    String idt;

    String emailid;

    String address;

    public void setidt(String value)
    {
        this.idt=value;
    }
    public String getidt()
    {
        return idt;

    }

    public void setemail(String value)
    {
        this.emailid=value;
    }
    public String getemail()
    {
        return emailid;
    }

    public void setaddress(String value)
    {
        this.address=value;
    }
    public String getaddress()
    {
        return address;
    }
}
