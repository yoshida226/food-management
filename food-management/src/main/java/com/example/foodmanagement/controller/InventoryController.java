package com.example.foodmanagement.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.foodmanagement.model.dto.RegisterInventoryForm;
import com.example.foodmanagement.model.entity.Categories;
import com.example.foodmanagement.model.entity.FoodMaster;
import com.example.foodmanagement.model.entity.Inventory;
import com.example.foodmanagement.model.entity.Users;
import com.example.foodmanagement.repository.CategoriesRepository;
import com.example.foodmanagement.repository.FoodMasterRepository;
import com.example.foodmanagement.repository.InventoryRepository;
import com.example.foodmanagement.security.UserDetailsImpl;
import com.example.foodmanagement.service.InventoryService;
import com.example.foodmanagement.util.AuthUtil;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/inventory")
public class InventoryController {

	private final InventoryRepository inventoryRepository;
	private final AuthUtil authUtil;
	private final InventoryService inventoryService;
	private final CategoriesRepository categoriesRepository;
	private final FoodMasterRepository foodMasterRepository;
	
	//リスト表示
	@GetMapping
	public String showList(Authentication auth, Model model) {
		
		Users user = authUtil.getLoginUser(auth);
		List<Inventory> inventories = inventoryRepository.findByUserAndStatus(user, "ACTIVE");
		
		model.addAttribute("inventories", inventories);
		
		return "inventory/list";
	}
	
	//更新
	
	
	//消費
	@PostMapping("/consume/{id}")
	@ResponseBody
	public ResponseEntity<Void> consume(@PathVariable Long id) {
	    inventoryService.consumeAll(id);
	    return ResponseEntity.ok().build();
	}

	@PostMapping("/discard/{id}")
	@ResponseBody
	public ResponseEntity<Void> discard(@PathVariable Long id, @RequestBody Map<String, String> payload) {
	    inventoryService.discard(id, payload.get("reason"));
	    return ResponseEntity.ok().build();
	}

	@PostMapping("/update/{id}")
	@ResponseBody
	public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Map<String, String> payload) {
	    inventoryService.update(id, payload);
	    return ResponseEntity.ok().build();
	}
	
	//登録
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		
		model.addAttribute("categories", categoriesRepository.findAll());
		model.addAttribute("registerInventoryForm", new RegisterInventoryForm());
		return "inventory/register";
	}
	
	//登録
	@GetMapping("/food")
	@ResponseBody
	public List<FoodMaster> getFood(@RequestParam(name = "categoryId", required = false) Long categoryId) {

		 List<FoodMaster> fm = foodMasterRepository.findByCategoryId(categoryId);

		return foodMasterRepository.findByCategoryId(categoryId);
	}
	
	@PostMapping("/register")
	public String registerInventory(@ModelAttribute @Validated RegisterInventoryForm registerInventoryForm,
									BindingResult bindingResult,
									@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
									Model model,
									RedirectAttributes redirectAttributes) {
		
		List<Categories> categories = categoriesRepository.findAll();

		if(bindingResult.hasErrors()) {
			model.addAttribute("categories", categories);
			model.addAttribute("registerInventoryForm", registerInventoryForm);
			return "inventory/register";
			
		}
		
		if(registerInventoryForm.getPurchaseDate() != null
		   && registerInventoryForm.getPurchaseDate().isAfter(registerInventoryForm.getExpirationDate())) {
			
			bindingResult.rejectValue("expirationDate", "expirationDate.beforePurchaseDate", "賞味・消費期限は購入日より後の日付にしてください");
			model.addAttribute("registerInventoryForm", registerInventoryForm);
			model.addAttribute("categories", categories);

			return "inventory/register";
		}
		
		inventoryService.createInventory(registerInventoryForm, userDetailsImpl.getUser());
		redirectAttributes.addFlashAttribute("successMessage", "購入食品登録が成功しました");
		
		return "redirect:/inventory";
	}

}