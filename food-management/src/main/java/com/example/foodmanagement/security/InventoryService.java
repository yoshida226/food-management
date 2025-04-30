package com.example.foodmanagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.foodmanagement.model.entity.Inventory;
import com.example.foodmanagement.repository.InventoryRepository;
import com.example.foodmanagement.repository.WasteRecordsRepository;

@Service
public class InventoryService {
	@Autowired
	InventoryRepository inventoryRepository;
	
	@Autowired
	WasteRecordsRepository wasteRecordsRepository;
	
	public void consumeAll(Long id) {
		Inventory inventory = inventoryRepository.findById(id).orElseThrow();
		inventory.setStatus("CONSUMED");
		
		inventoryRepository.save(inventory);
		
	}
	
	
}
