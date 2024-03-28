package com.SpringBoot.StockViewer_SPB.config;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import org.springframework.batch.core.Job;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.SpringBoot.StockViewer_SPB.entity.CompositeRecord;
import com.SpringBoot.StockViewer_SPB.repository.CompositeRepo;


@Configuration
public class BatchConfig {

	
	@Autowired
	private  JobRepository jobRepository ;
	
	@Autowired
	private  PlatformTransactionManager platformTransactionManager;
	
	@Autowired
	private CompositeRepo compositeRepo;
	@Autowired
	private StockItemWriter stockItemWriter;
	
	// Define CSV reader, processor, and writer beans here
	
//	@Bean
//	@StepScope
//	public FlatFileItemReader<CompositeRecord> itemReader(@Value("#{jobParameters[fullPathFileName]}") String pathToFIle) {
//	
//		System.out.println("enter in reader");
//		FlatFileItemReader<CompositeRecord> flatFileItemReader = new FlatFileItemReader<>();
//		flatFileItemReader.setResource(new FileSystemResource(new File(pathToFIle)));
//		flatFileItemReader.setName("CSV-Reader");
//		flatFileItemReader.setLinesToSkip(1);
//		flatFileItemReader.setFieldSetMapper(dynamicFieldSetMapper());
//		return flatFileItemReader;
//	}
	

//	private LineMapper<CompositeRecord> lineMapper() {
//		DefaultLineMapper<CompositeRecord> lineMapper = new DefaultLineMapper<>();
//
//		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
//		lineTokenizer.setDelimiter(",");
//		lineTokenizer.setStrict(false);
//		lineTokenizer.setNames("SYMBOL","SERIES","OPEN","HIGH","LOW","CLOSE","LAST","PREVCLOSE","TIMESTAMP");
//		
//
//		BeanWrapperFieldSetMapper<CompositeRecord> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
//		fieldSetMapper.setTargetType(CompositeRecord.class);
//		
//		   // Specify the date format for parsing the timestamp field
//	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
//	    CustomDateEditor dateEditor = new CustomDateEditor(dateFormat, true);
//	    fieldSetMapper.setCustomEditors(Collections.singletonMap(Date.class, dateEditor));
//	    
//		lineMapper.setLineTokenizer(lineTokenizer);
//		lineMapper.setFieldSetMapper(fieldSetMapper);
//     
//		return lineMapper;
//	}
	
	@Bean
	@StepScope
	public FlatFileItemReader<CompositeRecord> itemReader(@Value("#{jobParameters[fullPathFileName]}") String pathToFile) {
	    
		System.out.println("enter reader");
		
		FlatFileItemReader<CompositeRecord> flatFileItemReader = new FlatFileItemReader<>();
	    flatFileItemReader.setResource(new FileSystemResource(new File(pathToFile)));
	    flatFileItemReader.setName("CSV-Reader");
	    flatFileItemReader.setLinesToSkip(1);

	    // Set LineMapper with custom FieldSetMapper
	    DefaultLineMapper<CompositeRecord> lineMapper = new DefaultLineMapper<>();
	    
	    lineMapper.setLineTokenizer(new DelimitedLineTokenizer() {
	        {
	            setDelimiter(",");
	            setStrict(false);
	            setNames(new String[]{"SYMBOL", "SERIES", "OPEN", "HIGH", "LOW", "CLOSE", "LAST", "PREVCLOSE", "TOTTRDQTY", "TOTTRDVAL", "TIMESTAMP", "TOTALTRADES", "ISIN"});
	        }
	    });
	    lineMapper.setFieldSetMapper(dynamicFieldSetMapper()); // Set the custom FieldSetMapper
	    flatFileItemReader.setLineMapper(lineMapper);

	    return flatFileItemReader;
	}

	private FieldSetMapper<CompositeRecord> dynamicFieldSetMapper() {
	    return fieldSet -> {
	        CompositeRecord record = new CompositeRecord();
	        record.setSymbol(fieldSet.readString("SYMBOL"));
	        record.setSeries(fieldSet.readString("SERIES"));
	        record.setOpen(fieldSet.readDouble("OPEN"));
	        record.setHigh(fieldSet.readDouble("HIGH"));
	        record.setLow(fieldSet.readDouble("LOW"));
	        record.setClose(fieldSet.readDouble("CLOSE"));
	        record.setLast(fieldSet.readDouble("LAST"));
	        record.setPrevclose(fieldSet.readDouble("PREVCLOSE"));

	        // Parse the timestamp string into a Date object
	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
	        Date timestamp = null;
	        try {
	            timestamp = dateFormat.parse(fieldSet.readString("TIMESTAMP"));
	        } catch (ParseException e) {
	            e.printStackTrace();
	            // Handle parsing exception
	        }
	        record.setTimestamp(timestamp);

	        return record;
	    };
	}
	


	  @Bean
	    public StockProcessor processor() {
	        return new StockProcessor();
	    }

	  @Bean
	    public RepositoryItemWriter<CompositeRecord> writer() {
	        RepositoryItemWriter<CompositeRecord> writer = new RepositoryItemWriter<>();
	        writer.setRepository(compositeRepo);
	        writer.setMethodName("save");
	        return writer;
	    }
	  
	 
	   
	  @Bean
	    public Step step1(FlatFileItemReader<CompositeRecord> itemReader) {
	        return new StepBuilder("step",jobRepository)
	        		.<CompositeRecord, CompositeRecord>chunk(10, platformTransactionManager)
	                .reader(itemReader)
	                .processor(processor())
	                .writer(stockItemWriter)
	                .taskExecutor(taskExecutor())
	                .build();
	    }


	    
	  @Bean
	    public Job runJob(FlatFileItemReader<CompositeRecord> itemReader) {
	        return new JobBuilder("job", jobRepository)
	        		.start(step1(itemReader)).build();
	    }

	    @Bean
	    public TaskExecutor taskExecutor() {
	        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
	        taskExecutor.setConcurrencyLimit(10);
	        return taskExecutor;
	    }


	
}