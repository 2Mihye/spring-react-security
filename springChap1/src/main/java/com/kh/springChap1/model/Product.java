package com.kh.springChap1.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="product_seq")
	@SequenceGenerator(name="product_seq", sequenceName = "product_seq", allocationSize=1)
	private Long id;
	private String item_name;
	private int price;  
}
