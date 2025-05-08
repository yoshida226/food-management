package com.example.foodmanagement.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.foodmanagement.model.entity.Inventory;
import com.example.foodmanagement.model.entity.WasteRecords;
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
	
	public void discard(Long id, String reason) {
		Inventory inventory = inventoryRepository.findById(id).orElseThrow();
		inventory.setStatus("DISCARDED");
		
		WasteRecords wasteRecord = new WasteRecords();
		wasteRecord.setInventory(inventory);
		wasteRecord.setReason(reason);
		wasteRecord.setWastedDate(LocalDate.now());
		
		inventoryRepository.save(inventory);
		wasteRecordsRepository.save(wasteRecord);
	}
	
	public void update(Long id, Map<String, String> data) {
		Inventory inventory = inventoryRepository.findById(id).orElseThrow();
		inventory.setExpirationDate(LocalDate.parse(data.get("expirationDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		inventory.setPurchaseDate(LocalDate.parse(data.get("purchaseDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		inventory.setQuantity(Double.parseDouble(data.get("quantity")));
		inventory.setStorageMethod(data.get("storageMethod"));
		
		inventoryRepository.save(inventory);
		
	}
}
