package com.eteration.simplebanking.model;

import com.eteration.simplebanking.exceptions.InsufficientBalanceException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// This class is a place holder you can change the complete implementation
@Entity
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String owner;
    private String accountNumber;
    private double balance;


    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)

    private List<Transaction> transactions;

    public Account(String owner, String accountNumber) {
        this.owner = owner;
        this.accountNumber = accountNumber;
        transactions = new ArrayList<>();
    }

    public Account() {

    }

    public double getBalance() {
        return balance;
    }


    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void deposit(double amount) throws InsufficientBalanceException {

        DepositTransaction depositTransaction = new DepositTransaction(amount);
        depositTransaction.setApprovalCode(java.util.UUID.randomUUID().toString());
        depositTransaction.setType("Deposit");
        depositTransaction.setStatus("OK");
        post(depositTransaction);

    }

    public void withdraw(double amount) throws InsufficientBalanceException {

        WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction(amount);
        withdrawalTransaction.setApprovalCode(java.util.UUID.randomUUID().toString());
        withdrawalTransaction.setType("Withdrawal");
        withdrawalTransaction.setStatus("OK");
        post(withdrawalTransaction);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Transactional
    public void post(Transaction transaction) throws InsufficientBalanceException {
        synchronized (this) {
            try {
                transaction.execute(this);
                transactions.add(transaction);
            } catch (InsufficientBalanceException e) {
                throw e;
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
