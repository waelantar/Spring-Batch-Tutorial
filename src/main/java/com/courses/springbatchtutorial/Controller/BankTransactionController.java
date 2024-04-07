package com.courses.springbatchtutorial.Controller;

import com.courses.springbatchtutorial.Service.BankTransactionService;
import org.springframework.batch.core.BatchStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// Controller class for managing bank transactions
public class BankTransactionController {
    private BankTransactionService bankTransactionService;  // Service for bank transactions

    // Constructor with dependencies
    public BankTransactionController(BankTransactionService bankTransactionService) {
        this.bankTransactionService = bankTransactionService;
    }

    // Endpoint to execute the bank transaction job
    @GetMapping("/bank-transactions")
    public ResponseEntity<String> executeBankTransactionJob() {
        try {
            BatchStatus status = bankTransactionService.executeBankTransactionJob();
            return ResponseEntity.ok("Batch job status: " + status);
        } catch (Exception e) {
            // Log the error
            e.printStackTrace();
            // Or return an appropriate error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }
}
