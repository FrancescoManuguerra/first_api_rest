package com.example.first_project.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "products")
public class Product implements Serializable {

    public Product(){}
    public Product(int id, int quantity, float unitPrice, String name, int type) {
        this.id= id;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.name = name;
        this.type = type;
    }

    //primary key & auto-increment
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "quantity")
    private int quantity;

    @Column(name="unitPrice")
    private float unitPrice;

    @Column(name = "name")
    private String name;

    @Column(name="type")
    private int type;

    //getter & setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
