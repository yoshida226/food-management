package com.example.foodmanagement.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.foodmanagement.model.entity.Inventory;

@Service
public class HomeService {
	
	public List<Inventory> getInventoriesWithExpirationDateBefore(List<Inventory> inventory, LocalDate date) {
		return inventory.stream()
				.filter(item -> item.getExpirationDate().isBefore(date))
				.toList();
	}
	
	public List<Inventory> getInventoriesIfExpirationDateMatches(List<Inventory> inventory, LocalDate date) {
		return inventory.stream()
				.filter(item -> item.getExpirationDate().isEqual(date))
				.toList();
	}
	
}
