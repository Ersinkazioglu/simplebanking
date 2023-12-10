package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.exceptions.AccountNotFoundException;
import com.eteration.simplebanking.exceptions.InsufficientBalanceException;
import com.eteration.simplebanking.model.*;
import com.eteration.simplebanking.services.AccountService;
import com.eteration.simplebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

// This class is a place holder you can change the complete implementation
@RestController
@RequestMapping("/account/v1")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;
    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) throws AccountNotFoundException {


        Account account= accountService.findAccount(accountNumber);
        return ResponseEntity.ok().body(account);

    }
    @PostMapping("/save-account")
    public ResponseEntity<Account> credit(@RequestBody Account account) {

        if(account.getAccountNumber()!=null){
            try {
                Account existAccount= accountService.findAccount(account.getAccountNumber());
                return ResponseEntity.badRequest().body(account);

            }catch (AccountNotFoundException ex){
                accountService.save(account);
                return ResponseEntity.ok().body(account);
            }
        }
        return ResponseEntity.badRequest().body(account);
    }

    @PostMapping("/credit/{accountNumber}")
    public ResponseEntity<TransactionStatus> credit(@PathVariable String accountNumber, @RequestBody DepositTransaction transaction) throws InsufficientBalanceException, AccountNotFoundException {

            Account account= accountService.findAccount(accountNumber);
            DepositTransaction depositTransaction = new DepositTransaction(transaction.getAmount());
            depositTransaction.setApprovalCode(java.util.UUID.randomUUID().toString());
            depositTransaction.setType("Deposit");
            depositTransaction.setStatus("OK");
            depositTransaction.setAccount(account);
            account.post(depositTransaction);
            accountService.save(account);
            TransactionStatus status= new  TransactionStatus(depositTransaction.getStatus(),depositTransaction.getApprovalCode());
            return ResponseEntity.ok().body(status);

    }

    @PostMapping("/debit/{accountNumber}")
    public ResponseEntity<TransactionStatus> debit(@PathVariable String accountNumber, @RequestBody WithdrawalTransaction transaction) throws InsufficientBalanceException, AccountNotFoundException {
            Account account= accountService.findAccount(accountNumber);

            WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction();
            withdrawalTransaction.setAmount(transaction.getAmount());
            withdrawalTransaction.setDate(LocalDateTime.now());
            withdrawalTransaction.setApprovalCode(java.util.UUID.randomUUID().toString());
            withdrawalTransaction.setType("Withdrawal");
            withdrawalTransaction.setAccount(account);
            withdrawalTransaction.setStatus("OK");
            account.post(withdrawalTransaction);
            accountService.save(account);
            TransactionStatus status= new  TransactionStatus(withdrawalTransaction.getStatus(),withdrawalTransaction.getApprovalCode());
            return ResponseEntity.ok().body(status);

    }
}