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
@Table(name = "inventory")
@Data
public class Inventory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Double quantity;
	
	@Column(name = "purchase_date")
	private LocalDate purchaseDate;
	
	@Column(name = "expiration_date")
	private LocalDate expirationDate;
	
	@Column(name = "storage_method")
	private String storageMethod;
	
	@ManyToOne
	@JoinColumn(name = "food_master_id")
	private Categories foodMaster;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;
	
	private String status;
}
