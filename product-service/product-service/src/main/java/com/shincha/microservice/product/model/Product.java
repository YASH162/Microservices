package com.shincha.microservice.product.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.*;
import java.time.LocalDateTime;

@Entity
public class Product {

    @Id
    private int productID;  // Make field private
    @Column(nullable = false, length = 100)  // Customize column name and constraints
    private String name;  // Make field private
    @Column(length = 500)  // Limit description length
    private String description;  // Make field private
    @Column(nullable = false)
    private double price;  // Make field private
    @Column(nullable = false)
    private int stock;  // Make field private
    @Column(length = 50)
    private String category;  // Make field private
    @Column(nullable = false)
    private LocalDateTime createdAt;  // Make field private

    public Product() {
    }

    public Product(int productID, String name, String description, double price, int stock, String category, LocalDateTime createdAt) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.createdAt = createdAt;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
