package com.example.foodmanagement.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.foodmanagement.model.entity.FoodMaster;
import com.example.foodmanagement.model.entity.Inventory;
import com.example.foodmanagement.model.entity.Users;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
	public List<Inventory> findByUserAndExpirationDateLessThan(Users user, LocalDate expirationDate);
	
	public List<Inventory> findByUserAndPurchaseDateAfter(Users user, LocalDate purchaseDate);

	public List<Inventory> findByFoodMasterIn(List<FoodMaster> foods);
	
	public List<Inventory> findByStatus(String status);
	
	public List<Inventory> findByUserAndStatus(Users user, String status);
}
