package com.Inventory_Management_System.Verto_Project.service;


import com.Inventory_Management_System.Verto_Project.model.Product;
import com.Inventory_Management_System.Verto_Project.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repo;

    public ProductServiceImpl(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public Product createProduct(Product product) {
        return repo.save(product);
    }

    @Override
    public Product getProduct(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    @Override
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    @Override
    public Product updateProduct(String id, Product updated) {
        Product existing = getProduct(id);
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setStockQuantity(updated.getStockQuantity());
        existing.setLowStockThreshold(updated.getLowStockThreshold());
        return repo.save(existing);
    }

    @Override
    public void deleteProduct(String id) {
        repo.deleteById(id);
    }

    @Override
    public Product increaseStock(String id, int quantity) {
        Product product = getProduct(id);
        product.setStockQuantity(product.getStockQuantity() + quantity);
        return repo.save(product);
    }

    @Override
    public Product decreaseStock(String id, int quantity) {
        Product product = getProduct(id);
        if(quantity <= 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid stock operation");
        }
        if (product.getStockQuantity() < quantity) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient stock");
        }
        product.setStockQuantity(product.getStockQuantity() - quantity);
        return repo.save(product);
    }

    @Override
    public List<Product> getLowStockProducts() {
        return repo.findByStockQuantityLessThanEqual(5); // Example threshold
    }
}

