package com.shincha.microservice.product.service;

import com.shincha.microservice.product.model.Product;
import com.shincha.microservice.product.dto.ProductDTO;
import com.shincha.microservice.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shincha.microservice.product.exceptions.ProductNotFoundException;
import com.shincha.microservice.product.exceptions.ProductAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    public ProductDTO getProductById(int id) {
        logger.info("Fetching product with ID: {}", id);
        Product product = productRepository.findById(id);
        if (product != null) {
            logger.debug("Product found: {}", product);
            return convertToDTO(product);
        } else {
            logger.error("Product not found with ID: {}", id);
            throw new ProductNotFoundException("Product not found with id " + id);
        }
    }

    public List<ProductDTO> getAllProducts() {
        logger.info("Fetching all products");
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            logger.warn("No products found in the database");
            return Collections.emptyList();
        }
        logger.debug("Total products found: {}", products.size());
        return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductByName(String name) {
        logger.info("Fetching product with name: {}", name);
        Product product = productRepository.findByName(name);
        if (product != null) {
            logger.debug("Product found: {}", product);
            return convertToDTO(product);
        } else {
            logger.error("Product not found with name: {}", name);
            throw new ProductNotFoundException("Product not found with name " + name);
        }
    }

    public ProductDTO updateProduct(ProductDTO productDTO) {
        logger.info("Updating product with ID: {}", productDTO.getProductID());
        Product existingProduct = productRepository.findById(productDTO.getProductID());
        if (existingProduct != null) {
            logger.debug("Existing product found: {}", existingProduct);
            existingProduct.setName(productDTO.getName());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setStock(productDTO.getStock());
            existingProduct.setCategory(productDTO.getCategory());
            existingProduct.setCreatedAt(productDTO.getCreatedAt());
            productRepository.save(existingProduct);
            logger.info("Product updated successfully with ID: {}", productDTO.getProductID());
            return convertToDTO(existingProduct);
        } else {
            logger.error("Product not found with ID: {}", productDTO.getProductID());
            throw new ProductNotFoundException("Product not found with ID " + productDTO.getProductID());
        }
    }

    public ProductDTO saveProduct(ProductDTO productDTO) {
        logger.info("Saving new product with ID: {}", productDTO.getProductID());
        Optional<Product> existingProduct = Optional.ofNullable(productRepository.findById(productDTO.getProductID()));
        if (existingProduct.isPresent()) {
            logger.error("Product already exists with ID: {}", productDTO.getProductID());
            throw new ProductAlreadyExistsException("Product already exists with ID " + productDTO.getProductID());
        }
        Product product = convertToEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        logger.info("Product saved successfully with ID: {}", savedProduct.getProductID());
        return convertToDTO(savedProduct);
    }

    public boolean deleteProduct(int id) {
        logger.info("Deleting product with ID: {}", id);
        Optional<Product> product = Optional.ofNullable(productRepository.findById(id));
        if (product.isPresent()) {
            productRepository.deleteById(id);
            logger.info("Product deleted successfully with ID: {}", id);
            return true;
        } else {
            logger.error("Product not found with ID: {}", id);
            throw new ProductNotFoundException("Product not found with id " + id);
        }
    }

    public void deleteAllProducts() {
        logger.info("Deleting all products");
        productRepository.deleteAll();
        logger.info("All products deleted successfully");
    }

    private ProductDTO convertToDTO(Product product) {
        logger.debug("Converting Product to ProductDTO: {}", product);
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

    private Product convertToEntity(ProductDTO productDTO) {
        logger.debug("Converting ProductDTO to Product: {}", productDTO);
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
