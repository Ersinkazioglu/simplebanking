package com.eteration.simplebanking.model;


import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDateTime;

// This class is a place holder you can change the complete implementation
@Entity
public class DepositTransaction  extends Transaction  implements Serializable {

    public DepositTransaction(double transactionAmount ) {
    setAmount(transactionAmount);
    setDate(LocalDateTime.now());
    }


    public DepositTransaction() {

    }

    @Override
    public void execute(Account account) {

       account.setBalance(account.getBalance()+ getAmount());

    }
}
