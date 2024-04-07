package com.courses.springbatchtutorial.Config;

import com.courses.springbatchtutorial.entity.BankTransaction;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class BankTransactionProcessor implements ItemProcessor<BankTransaction, BankTransaction> {
    @Override
    public BankTransaction process(BankTransaction item) {
        try {
            String withdrawalAmt = item.getWithdrawalAmt().trim().replaceAll(",", "");
            String depositAmt = item.getDepositAmt().trim().replaceAll(",", "");

            double withdrawal = Double.parseDouble(withdrawalAmt.isEmpty() ? "0" : withdrawalAmt);
            double deposit = Double.parseDouble(depositAmt.isEmpty() ? "0" : depositAmt);

            double netAmount = deposit - withdrawal;

            item.setNetAmount(netAmount);

            return item;
        } catch (Exception e) {
            // Log the error
            e.printStackTrace();
            // Or throw a custom exception
            throw new RuntimeException("Failed to process bank transaction: " + e.getMessage());
        }
    }
}
