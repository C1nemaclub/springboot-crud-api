package com.example.productapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping("/api/v1/products")
public class Main {

	private final ProductRepository productRepository;

	public Main(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@GetMapping
	public List<Product> getProducts(){
		return productRepository.findAll();
	}

	@GetMapping("/{id}")
	public Product getProduct(@PathVariable Integer id){
		Product product = productRepository.findById(id).get();
		return product;
	}
	@PostMapping
	public ProductRequest addProduct(@RequestBody ProductRequest request){
		Product product = new Product();
		product.setName(request.name());
		product.setDescription(request.description());
		product.setCategory(request.category());
		product.setPrice(request.price());
		productRepository.save(product);
		return new ProductRequest(request.name(), request.description(), request.category(), request.price);
	}

	@DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Integer id){
		System.out.println(id);
		try {
		productRepository.deleteById(id);// Code that may have error
			return "Product deleted";
		} catch(Exception e){
			System.out.println(e);// Another code
			return "There was an error";
		}
	}


	@PutMapping("/{id}")
	public String updateProduct(@PathVariable Integer id, @RequestBody ProductRequest request) {
		Optional<Product> product = productRepository.findById(id);
		if(product.isPresent()){
			product.get().setName(request.name());
            product.get().setDescription(request.description());
            product.get().setCategory(request.category());
            product.get().setPrice(request.price());
			productRepository.save(product.get());
			return "Product " + product.get().getName() + " Updated";
		} else{
			return "Product " + "Width Id: " + id + " Was not found";
		}
	}




	record ProductRequest(
			String name,
			String category,
			String description,
			Integer price
	){}

}
