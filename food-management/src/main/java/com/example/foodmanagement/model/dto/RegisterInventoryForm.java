package com.example.foodmanagement.model.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class RegisterInventoryForm {
	
	private String categoryId;
	
	@NotEmpty(message = "選択してください")
	private String foodMasterId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate purchaseDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "入力してください")
	private LocalDate expirationDate;
	
	@NotNull(message = "1以上の値を入力してください")
	@Min(value = 1, message="1以上の値を入力してください")
	private Double quantity;
	
	@NotEmpty(message = "入力してください")
	private String storageMethod;
	
}
