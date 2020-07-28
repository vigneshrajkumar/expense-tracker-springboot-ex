package com.firstpriniples.expensetrackerapi.repositories;

import java.util.List;

import com.firstpriniples.expensetrackerapi.domain.Transaction;
import com.firstpriniples.expensetrackerapi.exceptions.EtBadRequestException;
import com.firstpriniples.expensetrackerapi.exceptions.EtResourceNotFoundException;

public interface TransactionRepository {
    List<Transaction> findAll(Integer userID, Integer catgoryID);

    Transaction findByID(Integer userID, Integer categoryID, Integer transactionID) throws EtResourceNotFoundException;

    Integer create(Integer userID, Integer categoryID, Double amount, String note, Long transactionDate) throws EtBadRequestException;

    void update(Integer userID, Integer categoryID, Integer transactionID, Transaction transaction) throws EtBadRequestException;

    void removeByID(Integer userID, Integer categoryID, Integer tranasctionId) throws EtResourceNotFoundException;

}