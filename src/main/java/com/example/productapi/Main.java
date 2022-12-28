package com.example.productapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class Main {

	private final ProductRepository productRepository;

	public Main(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@GetMapping("/products")
	public List<Product> getProducts(){
		return productRepository.findAll();
	}




	record ProductRequest(
			String name,
			String category,
			String description,
			Integer price
	){}

}
