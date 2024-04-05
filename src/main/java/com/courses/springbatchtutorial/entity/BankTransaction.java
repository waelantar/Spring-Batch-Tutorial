package com.courses.springbatchtutorial.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class BankTransaction {
    @Id
    private String accountNo;
    private Date transactionDate;
    private String transactionDetails;
    private String chequeNo;
    private Date valueDate;
    private double withdrawalAmount;
    private double depositAmount;
    private double balanceAmount;
}
