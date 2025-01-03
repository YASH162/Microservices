package com.shincha.microservice.product.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
}
