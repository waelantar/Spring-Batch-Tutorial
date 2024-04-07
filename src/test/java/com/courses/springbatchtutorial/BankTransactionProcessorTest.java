package com.courses.springbatchtutorial;

import com.courses.springbatchtutorial.Config.BankTransactionProcessor;
import com.courses.springbatchtutorial.entity.BankTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankTransactionProcessorTest {

    private BankTransactionProcessor processor;

    @BeforeEach
    public void setUp() {
        processor = new BankTransactionProcessor();
    }

    @Test
    public void testProcess() {
        // Given
        BankTransaction transaction = new BankTransaction();
        transaction.setWithdrawalAmt("100");
        transaction.setDepositAmt("200");

        // When
        BankTransaction processedTransaction = processor.process(transaction);

        // Then
        assertEquals(100.0, processedTransaction.getNetAmount());
    }

    @Test
    public void testProcess_withEmptyValues() {
        // Given
        BankTransaction transaction = new BankTransaction();
        transaction.setWithdrawalAmt("");
        transaction.setDepositAmt("");

        // When
        BankTransaction processedTransaction = processor.process(transaction);

        // Then
        assertEquals(0.0, processedTransaction.getNetAmount());
    }
}

