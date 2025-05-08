package com.example.foodmanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.foodmanagement.model.dto.WasteStats;
import com.example.foodmanagement.repository.WasteRecordsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
	private final WasteRecordsRepository wasteRecordsRepository;
	
	public List<Long> countWasteRecordsByMonth(int year) {
		//廃棄データの月別行数を取得
		List<WasteStats> wasteStatses = wasteRecordsRepository.countWasteRecords(year);
		
		//12ヶ月分のcountデータのリストを作成（その月にデータがない場合は0を設定）
		List<Long> counts = new ArrayList<>();
		
		for(int i = 0; i < 12; i++) {
			for(WasteStats ws : wasteStatses) {
				if(ws.getMonth() == (i + 1)) {
					counts.add(ws.getCount());
				} else {
					counts.add(0L);
				}
			}
		}
		
		return counts;
	}
}
