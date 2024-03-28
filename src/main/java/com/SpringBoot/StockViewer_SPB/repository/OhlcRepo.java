//package com.SpringBoot.StockViewer_SPB.repository;
//
//import java.util.Date;
//import java.util.List;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import com.SpringBoot.StockViewer_SPB.entity.OHLC;
//
//@Repository
//public interface OhlcRepo extends JpaRepository<OHLC, Long> {
//	
//	@Query("SELECT o, s FROM OHLC o JOIN o.stock s")
//	List<Object[]> getAllOHLCDataWithStock();
//	
//	
//	@Query("SELECT o FROM OHLC o WHERE o.stock.id = ?1 AND o.timestamp = ?2 ")
//	OHLC findOHLCByStockIdAndTimestamp(Long id , Date timestamp);
//	
//	@Query("SELECT o, s " +
//		       "FROM OHLC o " +
//		       "JOIN Stock s ON s.id = o.stock.id " +
//		       "WHERE (o.stock.id, o.timestamp) IN (" +
//		       "    SELECT o2.stock.id, MAX(o2.timestamp) " +
//		       "    FROM OHLC o2 " +
//		       "    GROUP BY o2.stock.id" +
//		       ")")
//	List<Object[]> findLatestUniqueStockRecords();
//	
//	@Query("SELECT o, s FROM OHLC o JOIN o.stock s WHERE LOWER(s.symbol) LIKE LOWER(concat('%', ?1 , '%'))")
//	List<Object[]> getAllDataOfSpecificSymbol(String symbol);
//	
//	@Query("SELECT o, s " +
//		       "FROM OHLC o " +
//		       "JOIN Stock s ON s.id = o.stock.id " +
//		       "WHERE  LOWER(s.symbol) LIKE LOWER(concat('%', ?1 , '%')) AND (o.stock.id, o.timestamp) IN (" +
//		       "    SELECT o2.stock.id, MAX(o2.timestamp) " +
//		       "    FROM OHLC o2 " +
//		       "    GROUP BY o2.stock.id" +
//		       ")")
//	List<Object[]> getAllDataOfSpecificSymbolOnlyLatest(String symbol);
//	
//	
//	 @Query("SELECT o, s " +
//	           "FROM OHLC o " +
//	           "JOIN FETCH o.stock s " +
//	           "WHERE o.timestamp >= :startDate AND o.timestamp <= :endDate " +
//	           "AND (o.stock.id, o.timestamp) IN (" +
//	           "    SELECT o2.stock.id, MAX(o2.timestamp) " +
//	           "    FROM OHLC o2 " +
//	           "    WHERE o2.timestamp >= :startDate AND o2.timestamp <= :endDate " +
//	           "    GROUP BY o2.stock.id" +
//	           ")")
//	    List<Object[]> getDataByDateRange(
//	            @Param("startDate") Date startDate, @Param("endDate") Date endDate);
//
//
//}
