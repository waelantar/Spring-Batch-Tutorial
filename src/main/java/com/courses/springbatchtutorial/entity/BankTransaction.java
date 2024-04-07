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
    private String date;
    private String transactionDetails;
    private String chequeNo;
    private String valueDate;
    private String withdrawalAmount;
    private String depositAmount;
    private String balanceAmount;
    private Double NetAmount;
}
