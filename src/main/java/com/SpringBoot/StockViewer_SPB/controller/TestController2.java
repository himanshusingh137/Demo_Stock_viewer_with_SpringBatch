//package com.SpringBoot.StockViewer_SPB.controller;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.CompletableFuture;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//import com.SpringBoot.StockViewer_SPB.service.StockService;
//
//@Controller
//public class TestController2 {
//
//	@Autowired
//	StockService stockService2;
//
//	@PostMapping("/upload-csv")
//	public ResponseEntity<Map<String, Object>> uploadCSV(@RequestParam("file") MultipartFile file) {
//		
//		long startTime = System.currentTimeMillis();
//
//		Map<String, Object> response = new HashMap();
//		try {
//			if (file.isEmpty()) {
//				System.out.println("file is empty");
//				response.put("success", false);
//				response.put("message", "Please select a file to upload.");
//				return ResponseEntity.badRequest().body(response);
//			}
//			System.out.println("file is not empty");
//			
//			
//			CompletableFuture<Boolean> uploadResult = stockService2.uploadCSV(file);
//			
//			// Wait for the CompletableFuture to complete
//            Boolean success = uploadResult.join(); // This will block until the CompletableFuture completes
//            
//			System.out.println("uploadcsv complete");
//			if (success) {
//				System.out.println("succed");
//				response.put("success", true);
//				response.put("message", "File uploaded successfully!");
//			} else {
//				System.out.println("fail to upload");
//				response.put("success", false);
//				response.put("message", "Failed to process the file.");
//			}
//			
//			long endTime = System.currentTimeMillis();
//	        long elapsedTime = endTime - startTime;
//	        System.out.println("upload csv endpoint Execution time: " + elapsedTime + "ms");
//	        
//			return ResponseEntity.ok().body(response);
//			
//		} catch (Exception e) {
//			response.put("success", false);
//			response.put("message", "An error occurred while uploading the file.");
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//		}
//	}
//
//	@RequestMapping("/home")
//	public String home() {
//		long startTime = System.currentTimeMillis();
//		long endTime = System.currentTimeMillis();
//        long elapsedTime = endTime - startTime;
//		System.out.println("home endpoint Execution time: " + elapsedTime + "ms");
//		return "Upload";
//	}
//
//	@RequestMapping("fetchAllData")
//	public String fetchAllData(Model model) {
//
//		long startTime = System.currentTimeMillis();
//		List<Object[]> allOHLCDataWithStock = stockService2.getAllData();
//
//		model.addAttribute("allDataListOfObjectArray", allOHLCDataWithStock);
//
//		long endTime = System.currentTimeMillis();
//        long elapsedTime = endTime - startTime;
//        System.out.println("all data Search Execution time: " + elapsedTime + "ms");
//		return "Details";
//	}
//	
//	@RequestMapping("fetchLatestUnique")
//	public String fetchOnlyLatestUniqueStockRecords(Model model) {
//		
//		long startTime = System.currentTimeMillis();
//
//		List<Object[]> onlyLatestDetails = stockService2.fetchOnlyLatestUniqueStockRecords();
//
//		model.addAttribute("onlyLatestDetails", onlyLatestDetails);
//		
//		long endTime = System.currentTimeMillis();
//        long elapsedTime = endTime - startTime;
//        System.out.println("latest unique records Search Execution time: " + elapsedTime + "ms");
//		return "latestDetailsOnly";
//	}
//
//	@RequestMapping("fetchDataBySymbol")
//	public ResponseEntity<List<Object[]>> fetchDataBySymbol(@RequestParam String symbol) {
//		
//		long startTime = System.currentTimeMillis();
//
//		List<Object[]> allDataOfSpecificSymbol = stockService2.getAllDataOfSpecificSymbol(symbol);
//
//		long endTime = System.currentTimeMillis();
//        long elapsedTime = endTime - startTime;
//        System.out.println("symbol Search Execution time: " + elapsedTime + "ms");
//        
//		return ResponseEntity.ok(allDataOfSpecificSymbol);
//	}
//	
//	@RequestMapping("fetchDataBySymbolOnlyLatest")
//	public ResponseEntity<List<Object[]>> fetchDataBySymbolOnlyLatest(@RequestParam String symbol) {
//
//		long startTime = System.currentTimeMillis();
//		
//		List<Object[]> allLatestDataOfSpecificSymbol = stockService2.getAllDataOfSpecificSymbolOnlyLatest(symbol);
//
//		long endTime = System.currentTimeMillis();
//        long elapsedTime = endTime - startTime;
//        System.out.println("Symbol search Execution time: " + elapsedTime + "ms");
//        
//		return ResponseEntity.ok(allLatestDataOfSpecificSymbol);
//	}
//	
//	@RequestMapping("fetchDataByDateRange")
//	public ResponseEntity<List<Object[]>> fetchDataByDateRange(@RequestParam("startDate")String startDate,
//	                                                            @RequestParam("endDate") String endDate) {
//		
//		long startTime = System.currentTimeMillis();
//		
//	    try {
//	    	
//	    	System.out.println("start:-" +startDate);
//	    	System.out.println("end:-"+ endDate);
//	    	
//	    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//	        Date parsedStartDate = dateFormat.parse(startDate);
//	        Date parsedEndDate = dateFormat.parse(endDate);
//	    	
//	        List<Object[]> dataInRange = stockService2.getDataByDateRange(parsedStartDate, parsedEndDate);
//	        
//	        long endTime = System.currentTimeMillis();
//	        long elapsedTime = endTime - startTime;
//	        System.out.println("DateRange Search Execution time: " + elapsedTime + "ms");
//	        
//	        return ResponseEntity.ok(dataInRange);
//	        
//	    } catch (Exception e) {
//	        // Handle any exceptions or errors
//	    	System.out.println("not fetched betwn dates");
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//	    }
//	}
//	
//	@RequestMapping("view_chart")
//	public String viewChart(@RequestParam String stockSymbol , Model model) {
//		
//		long startTime = System.currentTimeMillis();
//		
//		List<Object[]> allDataOfSpecificSymbol = stockService2.getAllDataOfSpecificSymbol(stockSymbol);  
//		
//		System.out.println("view chart list of object:  "+allDataOfSpecificSymbol);
//		model.addAttribute("allDataOfSpecificSymbol", allDataOfSpecificSymbol);
//		
//		long endTime = System.currentTimeMillis();
//        long elapsedTime = endTime - startTime;
//        System.out.println("view chart Execution time: " + elapsedTime + "ms");
//		return "viewChart";      
//	}
//
//}
