package com.example.foodmanagement.controller;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.foodmanagement.model.entity.Users;
import com.example.foodmanagement.security.UserDetailsImpl;
import com.example.foodmanagement.service.HistoryService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HistoryController {
	
	private final HistoryService historyService;
	
	@GetMapping("/history")
	public String showHistoryPage() {
		return "history/list";
	}
	
	@GetMapping("/history-data")
	@ResponseBody
	public List<Map<String, Object>> getListData(@RequestParam(name = "option", required = false) String option,
																 @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
		
		Users user = userDetailsImpl.getUser();
		List<Map<String, Object>> data = historyService.getDataByOption(option == null ? "consumption" : option, user);
		
		return data;
	}
}