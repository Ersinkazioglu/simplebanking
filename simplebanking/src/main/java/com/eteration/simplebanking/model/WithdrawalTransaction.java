package com.eteration.simplebanking.model;


import com.eteration.simplebanking.exceptions.InsufficientBalanceException;

import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDateTime;

// This class is a place holder you can change the complete implementation
@Entity
public class WithdrawalTransaction extends Transaction implements Serializable {
    public WithdrawalTransaction(double transactionAmount) {
            setAmount(transactionAmount);
            setDate(LocalDateTime.now());
        }

    public WithdrawalTransaction() {

    } @Override
    public void execute(Account account) throws InsufficientBalanceException {


        if (getAmount() > account.getBalance()) {
            throw new InsufficientBalanceException();
        }
        account.setBalance(account.getBalance() - getAmount());
    }

}


