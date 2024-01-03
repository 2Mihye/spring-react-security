package com.kh.springChap2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.springChap2.mapper.ProductMapper;
import com.kh.springChap2.model.Product;

@Service
public class ProductService {
	@Autowired
	private ProductMapper productMapper;
	
	public List<Product> getAllProduct() {
		return productMapper.getAllProduct();
	}
}
