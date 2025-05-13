package com.example.foodmanagement.model.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "food_master")
@Getter
@Setter
@EqualsAndHashCode(exclude = "users")
public class FoodMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String unit;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Categories category;
	
//	@ManyToOne
//	@JoinColumn(name = "user_id")
//	private Users user;
	
	@ManyToMany(mappedBy = "stockFoods")
    private Set<Users> stockUsers = new HashSet<>();

}
