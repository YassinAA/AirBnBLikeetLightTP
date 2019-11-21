package com.tp.AirBnBLikeetLight.model;

public class OrderDetailInfo {
    private String id;

    private String productCode;
    private String productName;
    private String type;
    private String adress;

    private int persons;
    private double price;
    private double amount;

    public OrderDetailInfo() {

    }

    public OrderDetailInfo(String id, String productCode, String productName, String type, String adress, int persons, double price, double amount) {
        this.id = id;
        this.productCode = productCode;
        this.productName = productName;
        this.type = type;
        this.adress = adress;
        this.persons = persons;
        this.price = price;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPersons() {
        return persons;
    }

    public void setPersons(int persons) {
        this.persons = persons;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
