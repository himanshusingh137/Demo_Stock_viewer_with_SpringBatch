//package com.SpringBoot.StockViewer_SPB.repository;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import com.SpringBoot.StockViewer_SPB.entity.Stock;
//
//@Repository
//public interface StockRepo extends JpaRepository<Stock, Long>{
//
//	@Query("select s from Stock s where s.symbol =?1")
//	public Stock getStockBySymbol(String symbol);
//	
//}
