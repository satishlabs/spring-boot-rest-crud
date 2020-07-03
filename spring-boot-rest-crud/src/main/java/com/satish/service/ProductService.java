package com.satish.service;

import java.util.List;
import java.util.Optional;

import com.satish.model.Product;

public interface ProductService {
	Product save(Product product);
	
	public void deleteById(Long id);
	
	Optional<Product>findById(Long id);
	
	List<Product> findAll();
}
