package com.shincha.microservice.product.repository;

import com.shincha.microservice.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findById(int id);
    @Query("SELECT p FROM Product p WHERE p.name = ?1")
    Product findByName(String name);
    List<Product> findByCategory(String category);
    List<Product> findByPriceLessThan(double price);
    List<Product> findAll();
}
