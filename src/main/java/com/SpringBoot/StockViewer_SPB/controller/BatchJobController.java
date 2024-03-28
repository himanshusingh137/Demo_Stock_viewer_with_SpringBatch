package com.SpringBoot.StockViewer_SPB.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.SpringBoot.StockViewer_SPB.repository.CompositeRepo;

@Controller
public class BatchJobController {

	
	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;

	@Autowired
	private CompositeRepo repository;
	@Autowired
	private JobRepository jobRepository;

	private final String TEMP_STORAGE = "C:/Users/HP/OneDrive/Desktop/temp_batch_spring/";

	@PostMapping("/upload-csv")
	public ResponseEntity<Map<String, Object>> uploadCSV(@RequestParam("file") MultipartFile file) {

		long startTime = System.currentTimeMillis();
		
		// file -> path we don't know
		// copy the file to some storage in your VM : get the file path
		// copy the file to DB : get the file path

		
		
		try {
			String originalFileName = file.getOriginalFilename();
			File fileToImport = new File(TEMP_STORAGE + originalFileName);
			file.transferTo(fileToImport);

			JobParameters jobParameters = new JobParametersBuilder()
					.addString("fullPathFileName", TEMP_STORAGE + originalFileName)
					.addLong("startAt", System.currentTimeMillis()).toJobParameters();

			System.out.println("hello ji");
			System.out.println(TEMP_STORAGE + originalFileName);
			
			JobExecution execution = jobLauncher.run(job, jobParameters);

//            if(execution.getExitStatus().getExitCode().equals(ExitStatus.COMPLETED)){
//                //delete the file from the TEMP_STORAGE
//                Files.deleteIfExists(Paths.get(TEMP_STORAGE + originalFileName));
//            }
			System.out.println("over ji");

		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException | IOException e) {

			e.printStackTrace();
		}

		Map<String, Object> response = new HashMap<String, Object>();
		try {
			if (file.isEmpty()) {
				System.out.println("file is empty");
				response.put("success", false);
				response.put("message", "Please select a file to upload.");
				return ResponseEntity.badRequest().body(response);
			}
			System.out.println("file is not empty");


			

			boolean success = true;

			System.out.println("uploadcsv complete");
			if (success) {
				System.out.println("succed");
				response.put("success", true);
				response.put("message", "File uploaded successfully!");
			} else {
				System.out.println("fail to upload");
				response.put("success", false);
				response.put("message", "Failed to process the file.");
			}

			long endTime = System.currentTimeMillis();
			long elapsedTime = endTime - startTime;
			System.out.println("upload csv endpoint Execution time: " + elapsedTime + "ms");

			return ResponseEntity.ok().body(response);

		} catch (Exception e) {
			System.out.println("problem occured");
			response.put("success", false);
			response.put("message", "An error occurred while uploading the file.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}
	
	@RequestMapping("/home")
	public String home() {
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
		System.out.println("home endpoint Execution time: " + elapsedTime + "ms");
		return "Upload";
	}

}
