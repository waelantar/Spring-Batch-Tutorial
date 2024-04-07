package com.courses.springbatchtutorial.Controller;

import com.courses.springbatchtutorial.Service.BankTransactionService;
import org.springframework.batch.core.BatchStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankTransactionController {
    BankTransactionService bankTransactionService;

    public BankTransactionController(BankTransactionService bankTransactionService) {
        this.bankTransactionService = bankTransactionService;
    }

    @GetMapping("/start")
    public ResponseEntity<String> load() {
        try {
            BatchStatus status = bankTransactionService.load();
            return ResponseEntity.ok("Batch job status: " + status);
        } catch (Exception e) {
            // Log the error
            e.printStackTrace();
            // Or return an appropriate error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }
}
