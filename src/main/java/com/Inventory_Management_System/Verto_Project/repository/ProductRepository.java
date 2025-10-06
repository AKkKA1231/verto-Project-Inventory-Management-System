package com.Inventory_Management_System.Verto_Project.repository;



import com.Inventory_Management_System.Verto_Project.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByStockQuantityLessThanEqual(Integer threshold);
}

