package com.example.foodmanagement.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.foodmanagement.model.entity.Inventory;
import com.example.foodmanagement.repository.InventoryRepository;
import com.example.foodmanagement.service.HomeService;

@Controller
public class HomeController {
	
	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Autowired
	private HomeService homeSeervice;
	
	@GetMapping("/")
	public String showHome (Model model, Authentication auth) {

		/*
		 * 期限が近い食材
		 */
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalDate threeDaysAfter = today.plusDays(3);
        LocalDate fiveDaysAfter = today.plusDays(5);
		
        //今日を入れて過去5日までの賞味期限の食品を取得
		List<Inventory> inventories = inventoryRepository.findByExpirationDateLessThan(fiveDaysAfter);
		
		//期限切れ食品リストを作成
		List<Inventory> expired = homeSeervice.getInventoriesWithExpirationDateBefore(inventories, today);
		
		//今日が期限の食品リストを作成
		List<Inventory> expirationToday = homeSeervice.getInventoriesIfExpirationDateMatches(inventories, today);
		
		//明日が期限の食品リストを作成
		List<Inventory> expirationTomorrow = homeSeervice.getInventoriesIfExpirationDateMatches(inventories, tomorrow);
		
		//３日後が期限の食品リストを作成
		List<Inventory> expirationThreeDaysAfter = homeSeervice.getInventoriesIfExpirationDateMatches(inventories, threeDaysAfter);
		
		System.out.println(expired);
		model.addAttribute("expired", expired);
		model.addAttribute("expirationToday", expirationToday);
		model.addAttribute("expirationTomorrow", expirationTomorrow);
		model.addAttribute("expiration3daysafter", expirationThreeDaysAfter);
		
		/*
		 * ストック対象アイテム残量
		 */
		
		
		
		
		return "home";
	}
}
