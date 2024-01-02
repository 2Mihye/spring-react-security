package com.kh.springChap1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kh.springChap1.model.Product;
import com.kh.springChap1.repository.ProductRepository;

// Service와 Controller 분리하여 작성하기



@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:3000", allowCredentials="true", allowedHeaders="*")
public class ProductController {
	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping("/item")
	public ResponseEntity<List <Product>> getAllProduct() {
		//return productRepository.findAll();
		List<Product> products = productRepository.findAll();
		return ResponseEntity.ok(products);
	}
	
	@PostMapping("/add")
	public ResponseEntity<Product> addProduct(@RequestBody Product product){ // RequestBody는 input에 넣은 값을 요청하는 것
		Product saveProduct = productRepository.save(product);
		return ResponseEntity.ok(saveProduct);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id){
		productRepository.deleteById(id);
		return ResponseEntity.ok("성공적으로 삭제했습니다."); // 개발자들간 전달하는 메세지
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
		Product existProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("아이디를 찾을 수 없습니다." + id));
		existProduct.setItem_name(updatedProduct.getItem_name());
		existProduct.setPrice(updatedProduct.getPrice());
		
		Product updateProduct = productRepository.save(existProduct);
		
		return ResponseEntity.ok(updateProduct);
	}
}
