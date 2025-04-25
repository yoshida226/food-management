package com.example.foodmanagement.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.foodmanagement.model.entity.FoodItems;

@Repository
public interface FoodItemsRepository extends JpaRepository<FoodItems, Long> {
	public List<FoodItems> findByExpirationDateLessThan(LocalDate expirationDate);
}
