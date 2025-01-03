package com.shincha.microservice.product.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private int productID;
    private String name;
    private String description;
    private double price;
    private int stock;
    private String category;
    private LocalDateTime createdAt;

}
