package com.eteration.simplebanking.model;


import com.eteration.simplebanking.exceptions.InsufficientBalanceException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

// This class is a place holder you can change the complete implementation
@Entity
public abstract class Transaction implements Serializable{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date ;
    private double amount;

    private String approvalCode;

    private String type;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "account_id")
    private Account account;


    private String status;


    public abstract void execute(Account account) throws InsufficientBalanceException;

    public Transaction() {
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double transactionAmount) {
        this.amount = transactionAmount;
    }
    public String getApprovalCode() {
        return approvalCode;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
