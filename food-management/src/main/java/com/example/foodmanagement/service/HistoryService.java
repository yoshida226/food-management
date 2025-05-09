package com.example.foodmanagement.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.foodmanagement.model.entity.Inventory;
import com.example.foodmanagement.model.entity.Users;
import com.example.foodmanagement.model.entity.WasteRecords;
import com.example.foodmanagement.repository.InventoryRepository;
import com.example.foodmanagement.repository.WasteRecordsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryService {
	
	private final InventoryRepository inventoryRepository;
	private final WasteRecordsRepository wasteRecordsRepository;

	public List<Map<String, Object>> getDataByOption(String option, Users user) {
		
		List<Map<String, Object>> data = new ArrayList<>();
		
		if(option.equals("consumption")) {
			
			List<Inventory> consumedRecords = inventoryRepository.findByUserAndStatus(user, "CONSUMED");
			
			for(Inventory inv : consumedRecords) {
				
				Map<String, Object> item = new LinkedHashMap<>();
				item.put("食品名", inv.getFoodMaster().getName());
				item.put("カテゴリー", inv.getFoodMaster().getCategory().getName());
				item.put("購入日", inv.getPurchaseDate());
				
				data.add(item);
			}
			
		} else if (option.equals("waste")) {
			List<Inventory> discardedRecords = inventoryRepository.findByUserAndStatus(user, "DISCARDED");
			
			for(Inventory inv : discardedRecords) {
				
				WasteRecords wasteRecord = wasteRecordsRepository.findByInventoryId(inv.getId());
				
				Map<String, Object> item = new LinkedHashMap<>();
				item.put("食品名", inv.getFoodMaster().getName());
				item.put("カテゴリー", inv.getFoodMaster().getCategory().getName());
				item.put("購入日", inv.getPurchaseDate());
				item.put("廃棄日", wasteRecord.getWastedDate());
				item.put("廃棄理由", wasteRecord.getReason());
				
				data.add(item);
			}
		}
		
		return data;
	}
}