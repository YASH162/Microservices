package com.shincha.microservice.product.service;

import com.shincha.microservice.product.model.Product;
import com.shincha.microservice.product.dto.ProductDTO;
import com.shincha.microservice.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shincha.microservice.product.exceptions.ProductNotFoundException;
import com.shincha.microservice.product.exceptions.ProductAlreadyExistsException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductDTO getProductById(int id) {
        Product product = productRepository.findById(id);
        if (product != null) {
            return convertToDTO(product);
        } else {
            throw new ProductNotFoundException("Product not found with id " + id);
        }
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll(); // Get all products from DB
        if (((List<?>) products).isEmpty()) {
            throw new ProductNotFoundException("No products found.");
        }
        return products.stream() // Convert list of Product to list of ProductDTO
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductByName(String name) {
        Product product = productRepository.findByName(name);
        if (product != null) {
            return convertToDTO(product);
        } else {
            throw new ProductNotFoundException("Product not found with name " + name);
        }
    }

    public ProductDTO updateProduct(ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(productDTO.getProductID());
        if (existingProduct != null) {
            existingProduct.setName(productDTO.getName());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setStock(productDTO.getStock());
            existingProduct.setCategory(productDTO.getCategory());
            existingProduct.setCreatedAt(productDTO.getCreatedAt());
            productRepository.save(existingProduct);
            return convertToDTO(existingProduct);
        } else {
            throw new ProductNotFoundException("Product not found with ID " + productDTO.getProductID());
        }
    }

    public ProductDTO saveProduct(ProductDTO productDTO) {
        Optional<Product> existingProduct = Optional.ofNullable(productRepository.findById(productDTO.getProductID()));
        if (existingProduct.isPresent()) {
            throw new ProductAlreadyExistsException("Product already exists with ID " + productDTO.getProductID());
        }
        Product product = convertToEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    public boolean deleteProduct(int id) {
        Optional<Product> product = Optional.ofNullable(productRepository.findById(id));
        if (product.isPresent()) {
            productRepository.deleteById(id);
            return true;
        } else {
            throw new ProductNotFoundException("Product not found with id " + id);
        }
    }


    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

    // Convert Product entity to ProductDTO
    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductID(product.getProductID());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setStock(product.getStock());
        productDTO.setCategory(product.getCategory());
        productDTO.setCreatedAt(product.getCreatedAt());
        return productDTO;
    }

    // Convert ProductDTO to Product entity
    private Product convertToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setProductID(productDTO.getProductID());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setCategory(productDTO.getCategory());
        product.setCreatedAt(productDTO.getCreatedAt());
        return product;
    }
}
