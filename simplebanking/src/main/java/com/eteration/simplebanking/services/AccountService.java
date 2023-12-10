package com.eteration.simplebanking.services;


import com.eteration.simplebanking.exceptions.AccountNotFoundException;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.repository.AccountRepository;
import com.eteration.simplebanking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// This class is a place holder you can change the complete implementation
@Service
public class AccountService {


    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public Account findAccount(String accountNumber) throws AccountNotFoundException {
        Account account = accountRepository.findByAccountNumber(accountNumber);

        if (account == null) {
            throw new AccountNotFoundException();

        } else {

            account.setTransactions(transactionRepository.findByAccount_id(account.getId()));
            return account;
        }
    }

    public Account save(Account account)
    {
        return accountRepository.save(account);

    }

}
