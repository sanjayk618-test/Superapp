package com.example.superapp;

public class DataHolder {
    String idt;
    String at;

    String emailid;

    String address;

    public void setidt(String value)
    {
        this.idt=value;
    }
    public String getidt()
    {
        return this.idt;
    }

    public void setemail(String value)
    {
        this.emailid=value;
    }
    public String getemail()
    {
        return this.emailid;
    }

    public void setaddress(String value)
    {
        this.address=value;
    }
    public String getaddress()
    {
        return this.address;
    }
}
