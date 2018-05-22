package com.pg.sboot.batch.xml.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class XmlOraRestController {

	@Autowired
	JobLauncher jobLauncher;
	
	@Autowired
	Job xmlBatchJob;
	
	@GetMapping("/xmljob")
	public String runJob() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException{
		
		JobParameters jobParameters = new JobParametersBuilder()
											.addLong("time", System.currentTimeMillis())
											.addString("param1", "Pradhap sends it")
											.toJobParameters();
		jobLauncher.run(xmlBatchJob, jobParameters);
		
		return "Job Initiated";
	}
	
}
