package com.satish.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.satish.exception.ResourceNotFoundException;
import com.satish.model.Product;
import com.satish.service.ProductService;

@RestController
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	private static final Logger log = LoggerFactory.getLogger(ProductController.class); 
	
	@GetMapping("/products")
	public List<Product> getProductList(@RequestParam String consumerKey){
		log.info("Consumer: {}", consumerKey);
		return productService.findAll();
	}
	
	@GetMapping("/products/{productId}")
	public Product getProduct(@PathVariable(value ="productId")Long productId) {
		return  productService.findById(productId).orElseThrow(()-> new ResourceNotFoundException("This ProductId is not existing: "+productId));	
	}
	
	@PostMapping("/products")
    public String createProduct(@RequestBody Product product) {
		productService.save(product);
		return "Product added";
    }
	
	@PutMapping("/products/{productId}")
	public String updateProduct(@PathVariable(value = "productId")Long id,@RequestBody Product product) {
		return productService.findById(id).map(p ->{
			p.setName(product.getName());
			p.setPrice(product.getPrice());
			productService.save(p);
			
			return "Product updated..";
		}).orElseThrow(() -> new ResourceNotFoundException("productId "+id+" not found"));
	}
	
	@DeleteMapping("/products/{productId}")
	public String deleteProduct(@PathVariable(value = "productId")Long id) {
		return productService.findById(id).map(p ->{
			productService.deleteById(id);
			return "Product deleted";
		}).orElseThrow(() -> new ResourceNotFoundException("productId "+id+" not found"));
	}
}
