package com.courses.springbatchtutorial.Config;

import com.courses.springbatchtutorial.entity.BankTransaction;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
// Processor class for processing bank transactions
public class BankTransactionProcessor implements ItemProcessor<BankTransaction, BankTransaction> {
    @Override
    // Method to process a bank transaction
    public BankTransaction process(BankTransaction transaction) throws Exception {

        try {
            // Extract withdrawal and deposit amounts from the transaction and clean them
            String withdrawalAmt = transaction.getWithdrawalAmount().trim().replaceAll(",", "");
            String depositAmt = transaction.getDepositAmount().trim().replaceAll(",", "");

            // Parse withdrawal and deposit amounts to double, handling empty values gracefully
            double withdrawal = Double.parseDouble(withdrawalAmt.isEmpty() ? "0" : withdrawalAmt);
            double deposit = Double.parseDouble(depositAmt.isEmpty() ? "0" : depositAmt);

            // Calculate net amount
            double netAmount = deposit - withdrawal;

            // Set the net amount to the transaction
            transaction.setNetAmount(netAmount);

            // Return the transaction with the calculated net amount
            return transaction;
        } catch (Exception e) {
            // Log any errors that occur during processing
            e.printStackTrace();

            // Throw a custom runtime exception with an error message
            throw new RuntimeException("Failed to process bank transaction: " + e.getMessage());
        }
    }
    }
