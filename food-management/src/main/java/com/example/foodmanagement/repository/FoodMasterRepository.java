package com.example.foodmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.foodmanagement.model.entity.FoodMaster;
import com.example.foodmanagement.model.entity.Users;

@Repository
public interface FoodMasterRepository extends JpaRepository<FoodMaster, Long> {
	public List<FoodMaster> findByUser(Users user);
	public List<FoodMaster> findByIdIn(List<Long> ids);
}
