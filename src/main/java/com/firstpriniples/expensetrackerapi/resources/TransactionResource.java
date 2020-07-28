package com.firstpriniples.expensetrackerapi.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleBinaryOperator;

import javax.servlet.http.HttpServletRequest;

import com.firstpriniples.expensetrackerapi.domain.Transaction;
import com.firstpriniples.expensetrackerapi.services.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/categories/{categoryID}/transactions")
public class TransactionResource {

    @Autowired
    TransactionService transactionService;

    @GetMapping("{transactionID}")
    public ResponseEntity<Transaction> getTransactionByID(HttpServletRequest request,
            @PathVariable("categoryID") Integer categoryID, @PathVariable("transactionID") Integer transactionID) {

        int userID = (Integer) request.getAttribute("userID");
        Transaction transaction = transactionService.fetchTransactionByID(userID, categoryID, transactionID);

        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Transaction>> getAllTransactions(HttpServletRequest request,
            @PathVariable("categoryID") Integer categoryID) {
        int userID = (Integer) request.getAttribute("userID");
        List<Transaction> transactions = transactionService.fetchAllTransactions(userID, categoryID);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Transaction> addTransaction(HttpServletRequest request,
            @PathVariable("categoryID") Integer categoryID, @RequestBody Map<String, Object> transactionMap) {

        int userID = (Integer) request.getAttribute("userID");
        Double amount = Double.valueOf(transactionMap.get("amount").toString());
        String note = (String) transactionMap.get("note");
        Long transactionDate = Long.valueOf(transactionMap.get("transactionDate").toString());
        Transaction transaction = transactionService.addTransaction(userID, categoryID, amount, note, transactionDate);

        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @PutMapping("/{transactionID}")
    public ResponseEntity<Map<String, Boolean>> updateTransaction(HttpServletRequest request,
            @PathVariable("categoryID") Integer categoryID, @PathVariable("transactionID") Integer transactionID,
            @RequestBody Transaction transaction) {

        int userID = (Integer) request.getAttribute("userID");
        transactionService.updateTransaction(userID, categoryID, transactionID, transaction);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/{transactionID}")
    public ResponseEntity<Map<String, Boolean>> deleteTransaction(HttpServletRequest request,
            @PathVariable("categoryID") Integer categoryID, @PathVariable("transactionID") Integer transactionID) {

        int userID = (Integer) request.getAttribute("userID");
        transactionService.removeTransaction(userID, categoryID, transactionID);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);

    }

}