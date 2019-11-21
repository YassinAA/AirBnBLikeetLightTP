package com.tp.AirBnBLikeetLight.model;

import com.tp.AirBnBLikeetLight.entity.Product;

public class ProductInfo {
    private String code;
    private String name;
    private double price;
    private String type;
    private String adress;
    private Integer persons;

    public ProductInfo() {
    }

    public ProductInfo(Product product) {
        this.code = product.getCode();
        this.name = product.getName();
        this.price = product.getPrice();
        this.type = product.getType();
        this.adress = product.getAdress();
        this.persons = product.getPersons();
    }

    // Using in JPA/Hibernate query


    public ProductInfo(String code, String name, double price, String type, String adress, Integer persons) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.type = type;
        this.adress = adress;
        this.persons = persons;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public Integer getPersons() {
        return persons;
    }

    public void setPersons(Integer persons) {
        this.persons = persons;
    }
}