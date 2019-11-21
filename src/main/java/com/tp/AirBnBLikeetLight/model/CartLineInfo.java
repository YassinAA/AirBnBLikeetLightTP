package com.tp.AirBnBLikeetLight.model;

public class CartLineInfo {

    private ProductInfo productInfo;
    private String type;
    private String adress;
    private int persons;

    public CartLineInfo() {
        this.persons = 0;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
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

    public int getPersons() {
        return persons;
    }

    public void setPersons(int persons) {
        this.persons = persons;
    }

    public double getAmount() {
        return this.productInfo.getPrice() * this.persons;
    }

}