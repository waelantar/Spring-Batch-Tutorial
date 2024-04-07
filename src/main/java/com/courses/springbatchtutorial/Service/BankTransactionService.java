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
// Service class for managing bank transactions
public class BankTransactionService {
    private JobLauncher jobLauncher;   // Launcher for Spring Batch jobs
    private Job bankTransactionJob;   // Spring Batch job for processing bank transactions

    // Constructor with dependencies
    public BankTransactionService(JobLauncher jobLauncher, Job bankTransactionJob) {
        this.jobLauncher = jobLauncher;
        this.bankTransactionJob = bankTransactionJob;
    }

    // Method to execute the bank transaction job
    public BatchStatus executeBankTransactionJob() {
        // Method logic...
        try {
            // Prepare job parameters
            Map<String, JobParameters> jobParameterMap = new HashMap<>();
            jobParameterMap.put("time", new JobParameters());
            JobParameters jobParameters = new JobParameters();

            // Launch the Spring Batch job
            JobExecution jobExecution = jobLauncher.run(bankTransactionJob, jobParameters);

            // Wait for the job to complete
            while (jobExecution.isRunning()) {
                System.out.println(".........");
            }

            // Return the status of the job execution
            return jobExecution.getStatus();
        } catch (Exception e) {
            // Log any errors that occur during job execution
            e.printStackTrace();

            // Throw a custom runtime exception with an error message
            throw new RuntimeException("Failed to launch job: " + e.getMessage());
        }

    }
}

