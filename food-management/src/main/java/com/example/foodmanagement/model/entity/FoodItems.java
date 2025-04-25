package com.example.foodmanagement.model.entity;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "food_items")
@Data
public class FoodItems {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private Double quantity;
	
	private String unit;
	
	@Column(name = "purchase_date")
	private LocalDate purchaseDate;
	
	@Column(name = "expiration_date")
	private LocalDate expirationDate;
	
	@Column(name = "storage_method")
	private String storageMethod;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Categories categoryId;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users userId;
}
