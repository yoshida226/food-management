package com.example.foodmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.foodmanagement.model.entity.Inventory;
import com.example.foodmanagement.model.entity.Users;
import com.example.foodmanagement.repository.InventoryRepository;
import com.example.foodmanagement.util.AuthUtil;

@Controller
@RequestMapping("/inventory")
public class InventoryController {
	
	@Autowired
	InventoryRepository inventoryRepository;
	
	@Autowired
	AuthUtil authUtil;
	
	@GetMapping
	public String showList(Authentication auth, Model model) {
		
		Users user = authUtil.getLoginUser(auth);
		List<Inventory> inventories = inventoryRepository.findByUserAndStatus(user, "ACTIVE");
		
		model.addAttribute("inventories", inventories);
		
		return "inventory/list";
	}

}