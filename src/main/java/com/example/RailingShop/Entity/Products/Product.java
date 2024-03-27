package com.example.RailingShop.Entity.Products;

import com.example.RailingShop.Enums.ProductCategory;
import jakarta.persistence.*;

/*
* Данни за стоките:
* product_id,
* name,
* price,
* quantity,
* type,
* color,
* expires_in*/
@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String name;
    private double price;
    private int quantity;
    private ProductCategory categoryType;
    private String color;
    private String expires_in;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductCategory getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(ProductCategory categoryType) {
        this.categoryType = categoryType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }
}
