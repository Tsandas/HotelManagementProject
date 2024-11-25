package org.example.hotelmanagementproject.Utils;

public class Staff {

    private String fullname;
    private double pay;
    private String field;
    private String phone;
    private int id;

    public Staff(int id, String fullname, double pay, String field, String phone){
        this.id = id;
        this.fullname = fullname;
        this.pay = pay;
        this.field = field;
        this.phone = phone;
    }

    public int getId(){
        return id;
    }

    public String getFullname(){
        return fullname;
    }

    public double getPay(){
        return pay;
    }

    public String getField(){
        return field;
    }

    public String getPhone(){
        return phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFullname(String fullname){
        this.fullname = fullname;
    }


    public void setPay(double pay){
        this.pay = pay;
    }

    public void setField(String field){
        this.field = field;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

}