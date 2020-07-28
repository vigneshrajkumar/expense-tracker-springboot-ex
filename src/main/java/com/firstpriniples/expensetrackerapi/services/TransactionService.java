package com.firstpriniples.expensetrackerapi.services;

import java.util.List;

import com.firstpriniples.expensetrackerapi.domain.Transaction;
import com.firstpriniples.expensetrackerapi.exceptions.EtBadRequestException;
import com.firstpriniples.expensetrackerapi.exceptions.EtResourceNotFoundException;


public interface TransactionService {
    List<Transaction> fetchAllTransactions(Integer userID, Integer categoryID);

    Transaction fetchTransactionByID(Integer userID, Integer categoryID, Integer transactionID) throws EtResourceNotFoundException;

    Transaction addTransaction(Integer userID, Integer categoryID, Double amount, String note, Long transactionDate) throws EtBadRequestException;
    
    void updateTransaction(Integer userID, Integer categoryID, Integer transactionID, Transaction transaction) throws EtBadRequestException;

    void removeTransaction(Integer userID, Integer categoryID, Integer transactionID) throws EtResourceNotFoundException;
    
}