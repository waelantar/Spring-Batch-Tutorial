package com.courses.springbatchtutorial.Config;

import com.courses.springbatchtutorial.Repository.BankTransactionRepository;
import com.courses.springbatchtutorial.entity.BankTransaction;
import jakarta.transaction.Transaction;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class BankTransactionWriter implements ItemWriter<BankTransaction> {
    @Autowired
    private BankTransactionRepository bankTransactionRepository;


    @Override
    public void write(Chunk<? extends BankTransaction> chunk) throws Exception {
        bankTransactionRepository.saveAll(chunk);

    }
}
