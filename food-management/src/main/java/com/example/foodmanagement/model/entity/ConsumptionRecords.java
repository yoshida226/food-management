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
@Table(name = "consumption_records")
@Data
public class ConsumptionRecords {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "food_item_id")
	private FoodItems foodItemId;
	
	@Column(name = "consumed_date")
	private LocalDate consumedDate;
	
	private Double amount;

	private String note;
}
