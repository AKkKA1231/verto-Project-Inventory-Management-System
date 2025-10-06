package com.Inventory_Management_System.Verto_Project.controller;

import com.Inventory_Management_System.Verto_Project.model.Product;
import com.Inventory_Management_System.Verto_Project.dto.StockUpdateRequest;
import com.Inventory_Management_System.Verto_Project.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // CRUD
    @PostMapping
    public Product createProduct(@Valid @RequestBody Product product) {
        return service.createProduct(product);
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable String id) {
        return service.getProduct(id);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable String id, @RequestBody Product product) {
        return service.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        service.deleteProduct(id);
    }

    // Stock Management
    @PostMapping("/{id}/increase")
    public Product increaseStock(@PathVariable String id, @RequestBody StockUpdateRequest request) {
        return service.increaseStock(id, request.getQuantity());
    }

    @PostMapping("/{id}/decrease")
    public Product decreaseStock(@PathVariable String id, @RequestBody StockUpdateRequest request) {
        return service.decreaseStock(id, request.getQuantity());
    }




    // Bonus Feature: Low stock products
    @GetMapping("/low-stock")
    public List<Product> getLowStockProducts() {
        return service.getLowStockProducts();
    }


}
