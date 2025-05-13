package com.example.foodmanagement.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.foodmanagement.repository.CategoriesRepository;
import com.example.foodmanagement.repository.FoodMasterRepository;
import com.example.foodmanagement.service.FoodMasterService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/food-master")
public class FoodMasterController {
	
	private final FoodMasterRepository foodMasterRepository;
	private final CategoriesRepository categoriesRepository;
	private final FoodMasterService foodMasterService;
	
	@GetMapping
	public String showList(Model model) {
		
		model.addAttribute("foodMasters", foodMasterRepository.findAll());
		model.addAttribute("categories", categoriesRepository.findAll());
		return "food/list";
	}
	
	@PostMapping("/update/{id}")
	@ResponseBody
	public ResponseEntity<Void> updateFoodMaster(@PathVariable Long id,
												 @RequestBody Map<String, String> payload) {
		
		foodMasterService.updateFoodMaster(id, payload);
		
		return ResponseEntity.ok().build();
	}
}
