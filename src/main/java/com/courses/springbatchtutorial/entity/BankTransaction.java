package com.courses.springbatchtutorial.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
// Represents a bank transaction entity
public class BankTransaction {
    @Id
    private String accountNo;  // Unique identifier for the account
    private String date;       // Date of the transaction
    private String transactionDetails;  // Details of the transaction
    private String chequeNo;   // Cheque number (if applicable)
    private String valueDate;  // Date when the transaction takes effect
    private String withdrawalAmount;   // Amount withdrawn
    private String depositAmount;      // Amount deposited
    private String balanceAmount;      // Current balance after the transaction
    private Double netAmount;          // Net amount after transaction
}
