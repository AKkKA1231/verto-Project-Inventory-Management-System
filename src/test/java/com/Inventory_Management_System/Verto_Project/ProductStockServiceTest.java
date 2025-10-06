package com.Inventory_Management_System.Verto_Project;


import com.Inventory_Management_System.Verto_Project.model.Product;
import com.Inventory_Management_System.Verto_Project.repository.ProductRepository;
import com.Inventory_Management_System.Verto_Project.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductStockServiceTest {

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductServiceImpl productService;

	private Product product;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);

		product = Product.builder()
				.id("P001")
				.name("Monitor")
				.description("24-inch Full HD")
				.stockQuantity(10)
				.lowStockThreshold(3)
				.build();
	}

	// ✅ Test case: Increase stock normally
	@Test
	void increaseStock_ShouldIncreaseQuantity() {
		when(productRepository.findById("P001")).thenReturn(Optional.of(product));
		when(productRepository.save(any(Product.class))).thenAnswer(i -> i.getArgument(0));

		Product updated = productService.increaseStock("P001", 5);

		assertEquals(15, updated.getStockQuantity());
		verify(productRepository, times(1)).save(product);
	}

	// ✅ Test case: Increase stock with 0 amount
	@Test
	void increaseStock_WithZero_ShouldKeepSameQuantity() {
		when(productRepository.findById("P001")).thenReturn(Optional.of(product));
		when(productRepository.save(any(Product.class))).thenAnswer(i -> i.getArgument(0));

		Product updated = productService.increaseStock("P001", 0);

		assertEquals(10, updated.getStockQuantity());
		verify(productRepository, times(1)).save(product);
	}

	// ✅ Test case: Decrease stock normally
	@Test
	void decreaseStock_ShouldDecreaseQuantity() {
		when(productRepository.findById("P001")).thenReturn(Optional.of(product));
		when(productRepository.save(any(Product.class))).thenAnswer(i -> i.getArgument(0));

		Product updated = productService.decreaseStock("P001", 4);

		assertEquals(6, updated.getStockQuantity());
		verify(productRepository, times(1)).save(product);
	}

	// ❌ Edge Case: Decrease more than available stock
	@Test
	void decreaseStock_ShouldThrowWhenInsufficientStock() {
		when(productRepository.findById("P001")).thenReturn(Optional.of(product));

		ResponseStatusException exception = assertThrows(ResponseStatusException.class,
				() -> productService.decreaseStock("P001", 15));

		assertEquals("400 BAD_REQUEST \"Insufficient stock\"", exception.getMessage());
		verify(productRepository, never()).save(any(Product.class));
	}

	// ❌ Edge Case: Decrease negative value (invalid input)
	@Test
	void decreaseStock_ShouldThrowWhenNegativeValue() {
		when(productRepository.findById("P001")).thenReturn(Optional.of(product));

		ResponseStatusException exception = assertThrows(ResponseStatusException.class,
				() -> productService.decreaseStock("P001", -5));

		assertTrue(exception.getMessage().contains("Invalid stock operation"));
		verify(productRepository, never()).save(any(Product.class));
	}
}
