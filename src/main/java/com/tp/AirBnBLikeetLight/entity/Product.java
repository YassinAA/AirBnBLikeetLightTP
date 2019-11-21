package com.tp.AirBnBLikeetLight.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "Product")
public class Product implements Serializable {

    private static final long serialVersionUID = -1000119078147252957L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_product")
    private long id;

    @Column(name = "code", length = 20, nullable = false)
    private String code;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "type", length = 255, nullable = false)
    private String type;

    @Column(name = "adress", length = 255, nullable = false)
    private String adress;

    @Column(name = "persons", length = 255, nullable = false)
    private Integer persons;

    @Column(name = "reserve", length = 255, nullable = false)
    private Boolean reserve;
    @Lob
    @Column(name = "image", length = Integer.MAX_VALUE, nullable = true)
    private byte[] image;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_Date", nullable = false)
    private Date createDate;

    @ManyToOne
    @JoinColumn
    private AppUser user;

    public Product() {
    }

    public Product(String code, String name, double price, String type, String adress, Integer persons, Boolean reserve, byte[] image, Date createDate, AppUser user) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.type = type;
        this.adress = adress;
        this.persons = persons;
        this.reserve = reserve;
        this.image = image;
        this.createDate = createDate;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
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

    public Boolean getReserve() {
        return reserve;
    }

    public void setReserve(Boolean reserve) {
        this.reserve = reserve;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", adress='" + adress + '\'' +
                ", persons=" + persons +
                ", reserve=" + reserve +
                ", image=" + Arrays.toString(image) +
                ", createDate=" + createDate +
                ", user=" + user +
                '}';
    }
}
