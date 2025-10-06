package com.Inventory_Management_System.Verto_Project.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;

@Document(collection = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    private String id;

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    private String name;

    @Size(max = 300, message = "Description cannot exceed 300 characters")
    private String description;

    @Min(value = 0, message = "Stock quantity cannot be negative")
    private int stockQuantity;

    @Min(value = 1, message = "Low stock threshold must be at least 1")
    private Integer lowStockThreshold;
}
//import lombok.*;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//@Document(collection = "products")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class Product {
//    @Id
//    private String id;
//
//    private String name;
//    private String description;
//    private int stockQuantity;
//
//    private Integer lowStockThreshold; // optional
//}
