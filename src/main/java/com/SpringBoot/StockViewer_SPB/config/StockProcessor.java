package com.SpringBoot.StockViewer_SPB.config;

import org.springframework.batch.item.ItemProcessor;

import com.SpringBoot.StockViewer_SPB.entity.CompositeRecord;

public class StockProcessor implements ItemProcessor<CompositeRecord, CompositeRecord> {
    @Override
    public CompositeRecord process(CompositeRecord compositeRecord) {
       
    	if (compositeRecord.getSeries().equals("EQ")) { // Check if series equals "EQ"

    		 return compositeRecord;

		} else {
            return null; // Filter out the record if series is not "EQ"
        }
    	
       
    }
}