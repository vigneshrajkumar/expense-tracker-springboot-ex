package com.firstpriniples.expensetrackerapi.services;

import java.util.List;

import com.firstpriniples.expensetrackerapi.domain.Transaction;
import com.firstpriniples.expensetrackerapi.exceptions.EtBadRequestException;
import com.firstpriniples.expensetrackerapi.exceptions.EtResourceNotFoundException;
import com.firstpriniples.expensetrackerapi.repositories.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public List<Transaction> fetchAllTransactions(Integer userID, Integer categoryID) {
        return transactionRepository.findAll(userID, categoryID);
    }

    @Override
    public Transaction fetchTransactionByID(Integer userID, Integer categoryID, Integer transactionID)
            throws EtResourceNotFoundException {
        return transactionRepository.findByID(userID, categoryID, transactionID);
    }

    @Override
    public Transaction addTransaction(Integer userID, Integer categoryID, Double amount, String note,
            Long transactionDate) throws EtBadRequestException {
        int transactionID = transactionRepository.create(userID, categoryID, amount, note, transactionDate);
        return transactionRepository.findByID(userID, categoryID, transactionID);
    }

    @Override
    public void updateTransaction(Integer userID, Integer categoryID, Integer transactionID, Transaction transaction)
            throws EtBadRequestException {

        transactionRepository.update(userID, categoryID, transactionID, transaction);

    }

    @Override
    public void removeTransaction(Integer userID, Integer categoryID, Integer transactionID)
            throws EtResourceNotFoundException {
        transactionRepository.removeByID(userID, categoryID, transactionID);

    }

}