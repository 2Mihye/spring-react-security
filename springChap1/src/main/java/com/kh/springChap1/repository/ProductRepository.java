package com.kh.springChap1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.springChap1.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
}
