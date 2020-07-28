package com.firstpriniples.expensetrackerapi.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import com.firstpriniples.expensetrackerapi.domain.Transaction;
import com.firstpriniples.expensetrackerapi.exceptions.EtBadRequestException;
import com.firstpriniples.expensetrackerapi.exceptions.EtResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    private static final String SQL_CREATE = "INSERT INTO et_transactions (transaction_id, category_id, user_id, amount, note, transaction_date) VALUES (NEXTVAL('et_transactions_seq'), ?, ?, ?, ?, ?)";

    private static final String SQL_FIND_BY_ID = "SELECT transaction_id, category_id, user_id, amount, note, transaction_date FROM et_transactions WHERE user_id = ? AND category_id = ? AND transaction_id = ?";

    private static final String SQL_FIND_ALL = "SELECT transaction_id, category_id, user_id, amount, note, transaction_date FROM et_transactions WHERE user_id = ? AND category_id = ?";

    private static final String SQL_UPDATE = "UPDATE et_transactions SET amount = ?, note = ?, transaction_date = ? WHERE user_id = ? AND category_id = ? AND transaction_id = ?";

    private static final String SQL_DELETE = "DELETE FROM et_transactions WHERE user_id = ? AND category_id = ? AND transaction_id = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Transaction> findAll(Integer userID, Integer catgoryID) {
        try {
            return jdbcTemplate.query(SQL_FIND_ALL, new Object[] { userID, catgoryID }, transactionRowMapper);
        } catch (Exception e) {
            throw new EtBadRequestException("Bad Request");
        }
    }

    @Override
    public Transaction findByID(Integer userID, Integer categoryID, Integer transactionID)
            throws EtResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[] { userID, categoryID, transactionID },
                    transactionRowMapper);
        } catch (Exception e) {
            throw new EtResourceNotFoundException("Transaction not found");
        }
    }

    @Override
    public Integer create(Integer userID, Integer categoryID, Double amount, String note, Long transactionDate)
            throws EtBadRequestException {
        try {
            KeyHolder keyholder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, categoryID);
                ps.setInt(2, userID);
                ps.setDouble(3, amount);
                ps.setString(4, note);
                ps.setLong(5, transactionDate);
                return ps;
            }, keyholder);
            return (Integer) keyholder.getKeys().get("transaction_id");
        } catch (Exception e) {
            throw new EtBadRequestException("Invalid Request. " + e.getMessage());
        }
    }

    @Override
    public void update(Integer userID, Integer categoryID, Integer transactionID, Transaction transaction)
            throws EtBadRequestException {
        try {
            jdbcTemplate.update(SQL_UPDATE, new Object[] { transaction.getAmount(), transaction.getNote(),
                    transaction.getTransactionDate(), userID, categoryID, transactionID });

        } catch (Exception e) {
            throw new EtBadRequestException("Invalid Request");
        }
    }

    @Override
    public void removeByID(Integer userID, Integer categoryID, Integer transactionID)
            throws EtResourceNotFoundException {

        int count = jdbcTemplate.update(SQL_DELETE, new Object[] { userID, categoryID, transactionID });
        if (count == 0)
            throw new EtResourceNotFoundException("Resource Not Found");

    }

    private RowMapper<Transaction> transactionRowMapper = ((rs, rowNum) -> {
        return new Transaction(rs.getInt("transaction_id"), rs.getInt("category_id"), rs.getInt("user_id"),
                rs.getDouble("amount"), rs.getString("note"), rs.getLong("transaction_date"));
    });
}