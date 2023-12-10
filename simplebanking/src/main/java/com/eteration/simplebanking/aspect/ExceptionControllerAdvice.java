package com.eteration.simplebanking.aspect;

import com.eteration.simplebanking.exceptions.AccountNotFoundException;
import com.eteration.simplebanking.exceptions.InsufficientBalanceException;
import org.apache.juli.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(LoggerAspect.class);
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<String> handleInsufficientBalanceException(InsufficientBalanceException ex) {
        System.out.println("Hesap bakiyesi yetersizdir. Özel mesaj: " + ex.getMessage());

        logger.error(String.format("Hesap Bakiyesi Yetersiz-> %s",ex.getStackTrace()));

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleAccountNotFoundException(AccountNotFoundException ex) {

        logger.error(String.format("Hesap Bulunamadı-> %s",ex.getStackTrace()));
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
