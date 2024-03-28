//package com.SpringBoot.StockViewer_SPB.entity;
//
//import java.util.Date;
//
//import org.springframework.stereotype.Component;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Temporal;
//import jakarta.persistence.TemporalType;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//@Component
//@Entity
//@Getter
//@Setter
//@ToString
//public class OHLC {
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private long ohlc_id;
//	
//	private double open;
//	
//	private double high;
//	
//	private double low;
//	
//	private double close;
//	
//	private double last;
//	
//	private double prevclose;
//	
//	@Temporal(TemporalType.DATE)
//	private Date timestamp;
//	
//	private String isin;
//	
//	@ManyToOne
//	private Stock stock;
//
//}
