package com.example.foodmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.foodmanagement.model.entity.ConsumptionRecords;

@Repository
public interface ConsumptionRecordsRepository extends JpaRepository<ConsumptionRecords, Long> {

}
