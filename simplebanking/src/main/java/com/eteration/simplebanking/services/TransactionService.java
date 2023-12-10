

package com.eteration.simplebanking.services;


        import com.eteration.simplebanking.controller.TransactionStatus;
        import com.eteration.simplebanking.model.*;
        import com.eteration.simplebanking.repository.AccountRepository;
        import com.eteration.simplebanking.repository.TransactionRepository;


        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.time.LocalDateTime;

@Service
public class TransactionService {


    @Autowired
    private TransactionRepository transactionRepository;


    public Transaction save(Transaction transaction)
    {
        return transactionRepository.save(transaction);

    }

}
