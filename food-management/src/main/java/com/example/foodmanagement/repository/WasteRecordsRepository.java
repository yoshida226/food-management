package com.example.foodmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.foodmanagement.model.dto.WasteStats;
import com.example.foodmanagement.model.entity.WasteRecords;

@Repository
public interface WasteRecordsRepository extends JpaRepository<WasteRecords, Long> {

	@Query(value = "SELECT MONTH(wr.wastedDate) AS month, count(wr) AS count FROM WasteRecords AS wr WHERE YEAR(wr.wastedDate) = :year GROUP BY MONTH(wr.wastedDate) ORDER BY MONTH(wr.wastedDate)")
	public List<WasteStats> countWasteRecords(@Param("year")int year);
	
	public WasteRecords findByInventoryId(Long inventoryId);
}
