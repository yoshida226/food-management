package com.example.foodmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.foodmanagement.model.entity.FoodMaster;
import com.example.foodmanagement.model.entity.Users;

@Repository
public interface FoodMasterRepository extends JpaRepository<FoodMaster, Long> {
	public List<FoodMaster> findByStockUsers(Users user);
	public List<FoodMaster> findByIdIn(List<Long> ids);
	
	@Query(value = "SELECT fm FROM FoodMaster AS fm WHERE fm.category.id = :categoryId")
	public List<FoodMaster> findByCategoryId(@Param("categoryId")Long categoryId);
}
