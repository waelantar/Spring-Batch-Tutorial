package com.courses.springbatchtutorial.Config;

import com.courses.springbatchtutorial.entity.BankTransaction;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class BankTransactionProcessor implements ItemProcessor<BankTransaction,BankTransaction> {
    @Override
    public BankTransaction process(BankTransaction item) throws Exception {
        // Get withdrawal and deposit amounts from the transaction
        String withdrawalAmt = item.getWithdrawalAmt().trim().replaceAll(",", "");
        String depositAmt = item.getDepositAmt().trim().replaceAll(",", "");

        // Parse withdrawal and deposit amounts to double
        double withdrawal = Double.parseDouble(withdrawalAmt.isEmpty() ? "0" : withdrawalAmt);
        double deposit = Double.parseDouble(depositAmt.isEmpty() ? "0" : depositAmt);

        // Calculate net amount
        double netAmount = deposit - withdrawal;

        // Set the net amount to the transaction
        item.setNetAmount(netAmount);

        return item;
    }
}
