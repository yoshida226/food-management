package com.example.foodmanagement.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.foodmanagement.model.entity.Categories;
import com.example.foodmanagement.model.entity.FoodMaster;
import com.example.foodmanagement.repository.CategoriesRepository;
import com.example.foodmanagement.repository.FoodMasterRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FoodMasterService {
	
	private final FoodMasterRepository foodMasterRepository;
	private final CategoriesRepository categoriesRepositoty;
	
	public void updateFoodMaster(Long id, Map<String, String> payload) {
		FoodMaster foodMaster = foodMasterRepository.findById(id).orElseThrow();
		foodMaster.setName(payload.get("name"));
		
		Categories category = categoriesRepositoty.findById(Long.parseLong(payload.get("categoryId"))).orElseThrow();
		foodMaster.setCategory(category);
		foodMaster.setUnit(payload.get("unit"));
		
		foodMasterRepository.save(foodMaster);
		
	}
}
