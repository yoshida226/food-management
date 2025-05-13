package com.example.foodmanagement.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.foodmanagement.model.entity.FoodMaster;
import com.example.foodmanagement.model.entity.Inventory;
import com.example.foodmanagement.model.entity.Users;
import com.example.foodmanagement.repository.FoodMasterRepository;
import com.example.foodmanagement.repository.InventoryRepository;
import com.example.foodmanagement.service.HomeService;
import com.example.foodmanagement.util.AuthUtil;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Autowired
	private HomeService homeSeervice;

	@Autowired
	private AuthUtil authUtil;
	
	@Autowired
	private FoodMasterRepository foodMasterRepository;

	@GetMapping
	public String showHome (Model model, Authentication auth) {

		Users user = authUtil.getLoginUser(auth);
		/*
		 * 期限が近い食材
		 */
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalDate threeDaysAfter = today.plusDays(3);
        LocalDate fiveDaysAfter = today.plusDays(5);
		
        //今日を入れて過去5日までの賞味期限の食品を取得
		List<Inventory> inventoriesInFiveDays = inventoryRepository.findByUserAndExpirationDateLessThan(user, fiveDaysAfter);
		
		//期限切れ食品リストを作成
		List<Inventory> expired = homeSeervice.getInventoriesWithExpirationDateBefore(inventoriesInFiveDays, today);
		
		//今日が期限の食品リストを作成
		List<Inventory> expirationToday = homeSeervice.getInventoriesIfExpirationDateMatches(inventoriesInFiveDays, today);
		
		//明日が期限の食品リストを作成
		List<Inventory> expirationTomorrow = homeSeervice.getInventoriesIfExpirationDateMatches(inventoriesInFiveDays, tomorrow);
		
		//３日後が期限の食品リストを作成
		List<Inventory> expirationThreeDaysAfter = homeSeervice.getInventoriesIfExpirationDateMatches(inventoriesInFiveDays, threeDaysAfter);

		model.addAttribute("expired", expired);
		model.addAttribute("expirationToday", expirationToday);
		model.addAttribute("expirationTomorrow", expirationTomorrow);
		model.addAttribute("expiration3daysafter", expirationThreeDaysAfter);
		
		/*
		 * 必須購入アイテム
		 */
		LocalDate startDate = today.minusWeeks(4);
		
		List<Inventory> inventoriesInOneMonth = inventoryRepository.findByUserAndPurchaseDateAfter(user, startDate);
		
		Map<Long, Set<Integer>> inventoryToWeekSet = new HashMap<>();
		
		for(Inventory inv : inventoriesInOneMonth) {
			Long foodMasterId = inv.getFoodMaster().getId();
			int weekNumber = (int) ChronoUnit.WEEKS.between(startDate, inv.getPurchaseDate());
			
			inventoryToWeekSet.computeIfAbsent(foodMasterId, key -> new HashSet<>()).add(weekNumber);
		}
		
		List<Long> frequentlyBoughtItemIds = inventoryToWeekSet.entrySet().stream()
				.filter(entry -> entry.getValue().size() >= 3)
				.map(Map.Entry::getKey)
				.toList();
		
		List<Long> activeItemIds = inventoryRepository.findByStatus("ACTIVE").stream()
				.map(inv -> inv.getId()).toList();
		
		List<Long> mustBuyItemIds = frequentlyBoughtItemIds.stream()
				.filter(id -> !activeItemIds.contains(id))
				.toList();
		
		List<FoodMaster> mustBuyItems = foodMasterRepository.findByIdIn(mustBuyItemIds);
		
		model.addAttribute("mustBuyItems", mustBuyItems);
		
		/*
		 * ストック対象アイテム残量
		 */
		List<FoodMaster> stockFoods = foodMasterRepository.findByStockUsers(user);
		List<Inventory> stockFoodsSituation = inventoryRepository.findByFoodMasterIn(stockFoods);
		
		model.addAttribute("stockFoodsSituation", stockFoodsSituation);
		
		return "home";
	}
}
