package com.example.foodmanagement.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.foodmanagement.model.entity.FoodItems;
import com.example.foodmanagement.repository.FoodItemsRepository;

@Controller
public class HomeController {
	
	@Autowired
	private FoodItemsRepository foodItemsRepository;
	
	@GetMapping("/")
	public String showHome (Model model) {

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalDate threeDaysAfter = today.plusDays(3);
        LocalDate fiveDaysAfter = today.plusDays(5);
		
        //今日を入れて過去5日までの賞味期限の食品を取得
		List<FoodItems> foodItems = foodItemsRepository.findByExpirationDateLessThan(fiveDaysAfter);
		
		//期限切れ食品リストを作成
		List<FoodItems> expired = foodItems.stream()
				.filter(item -> item.getExpirationDate().isBefore(today))
				.toList();
		
		//今日が期限の食品リストを作成
		List<FoodItems> expirationToday = foodItems.stream()
											.filter(item -> item.getExpirationDate().compareTo(today) != 0)
											.toList();
		
		//明日が期限の食品リストを作成
		List<FoodItems> expirationTomorrow = foodItems.stream()
												.filter(item -> item.getExpirationDate().compareTo(tomorrow) != 0)
												.toList();
		
		//３日後が期限の食品リストを作成
		List<FoodItems> expirationThreeDaysAfter = foodItems.stream()
													.filter(item -> item.getExpirationDate().compareTo(threeDaysAfter) != 0)
													.toList();
		
		System.out.println(expired);
		model.addAttribute("expired", expired);
		model.addAttribute("expirationToday", expirationToday);
		model.addAttribute("expirationTomorrow", expirationTomorrow);
		model.addAttribute("expiration3daysafter", expirationThreeDaysAfter);
		
		return "home";
	}
}
