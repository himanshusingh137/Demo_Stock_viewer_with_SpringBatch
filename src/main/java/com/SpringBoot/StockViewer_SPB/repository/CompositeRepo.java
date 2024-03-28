package com.SpringBoot.StockViewer_SPB.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SpringBoot.StockViewer_SPB.entity.CompositeRecord;

public interface CompositeRepo extends JpaRepository<CompositeRecord, String>{
	

}
