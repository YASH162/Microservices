package com.shincha.microservice.product.controller;

import com.shincha.microservice.product.dto.ProductDTO;
import com.shincha.microservice.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        if (products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.NO_CONTENT); // 204 No Content if no products found
        }
        return new ResponseEntity<>(products, HttpStatus.OK); // 200 OK with the list of products
    }

    @PutMapping("/update")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO updatedProduct = productService.updateProduct(productDTO);
        if (updatedProduct == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found if product not found
        }
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK); // 200 OK with updated product
    }

    @PostMapping("/add")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO savedProduct = productService.saveProduct(productDTO);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED); // 201 Created with the saved product
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        boolean isDeleted = productService.deleteProduct(id);
        if (isDeleted) {
            return new ResponseEntity<>("Product Deleted Successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Product not exist", HttpStatus.NOT_FOUND); // 404 Not Found if product to delete does not exist
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllProducts() {
        productService.deleteAllProducts();
        return new ResponseEntity<>("All products deleted", HttpStatus.NO_CONTENT); // 204 No Content
    }
}
