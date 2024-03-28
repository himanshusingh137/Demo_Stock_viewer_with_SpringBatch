package com.SpringBoot.StockViewer_SPB.config;


import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.SpringBoot.StockViewer_SPB.entity.CompositeRecord;
import com.SpringBoot.StockViewer_SPB.repository.CompositeRepo;

@Component
public class StockItemWriter implements ItemWriter<CompositeRecord> {

    @Autowired
    private CompositeRepo repository;


	@Override
	public void write(Chunk<? extends CompositeRecord> chunk) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Writer Thread "+Thread.currentThread().getName());
        repository.saveAll(chunk);
	}
}
