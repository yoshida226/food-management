package com.example.foodmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.foodmanagement.repository.CategoriesRepository;
import com.example.foodmanagement.repository.FoodMasterRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/food-master")
public class FoodMasterController {
	
	private final FoodMasterRepository foodMasterRepository;
	private final CategoriesRepository categoriesRepository;
	
	@GetMapping
	public String showList(Model model) {
		
		model.addAttribute("foodMasters", foodMasterRepository.findAll());
		model.addAttribute("categories", categoriesRepository.findAll());
		return "food/list";
	}
}
