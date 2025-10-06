package com.Inventory_Management_System.Verto_Project.service;



import com.Inventory_Management_System.Verto_Project.model.Product;
import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    Product getProduct(String id);
    List<Product> getAllProducts();
    Product updateProduct(String id, Product product);
    void deleteProduct(String id);

    Product increaseStock(String id, int quantity);
    Product decreaseStock(String id, int quantity);

    List<Product> getLowStockProducts();
}

