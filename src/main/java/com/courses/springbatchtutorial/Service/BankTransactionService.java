package com.courses.springbatchtutorial.Service;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BankTransactionService {
private JobLauncher jobLauncher;
private Job job;
public BankTransactionService(JobLauncher jobLauncher, Job job){
    this.jobLauncher=jobLauncher;
    this.job=job;
}
public BatchStatus load() throws Exception {
    Map<String,JobParameters> jobParameterMap=new HashMap<>();
    jobParameterMap.put("time", new JobParameters());
    JobParameters jobParameters=new JobParameters();
    JobExecution jobExecution = jobLauncher.run(job, jobParameters);
    while (jobExecution.isRunning()) {
        System.out.println(".........");
    }
    return jobExecution.getStatus();
}

}

