package com.courses.springbatchtutorial.Controller;

import com.courses.springbatchtutorial.Service.BankTransactionService;
import org.springframework.batch.core.BatchStatus;
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
    public BatchStatus load() throws Exception {return bankTransactionService.load();

    }
}
