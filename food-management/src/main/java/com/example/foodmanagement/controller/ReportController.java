package com.example.foodmanagement.controller;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.foodmanagement.service.ReportService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {
	
	private final ReportService reportService;
	
	@GetMapping("/summaryOfWastedFoodByMonth")
	public String showSummaryOfWastedFoodByMonth (@RequestParam(name = "year", required = false) Integer year, Model model) {
		
		int currentYear = LocalDate.now().getYear();
		int selectedYear = (year != null) ? year : currentYear;
		
		// 過去5年分を選択肢に出す
        List<Integer> years = IntStream.rangeClosed(currentYear - 4, currentYear)
                               .boxed()
                               .sorted(Comparator.reverseOrder())
                               .collect(Collectors.toList());
        
		model.addAttribute("monthlyData", reportService.countWasteRecordsByMonth(selectedYear));
		model.addAttribute("years", years);
		model.addAttribute("selectedYear", selectedYear);
		
		return "report/summaryOfWastedFoodByMonth";
	}
}
