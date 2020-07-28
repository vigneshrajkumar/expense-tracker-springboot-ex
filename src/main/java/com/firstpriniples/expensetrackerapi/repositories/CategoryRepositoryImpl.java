package com.firstpriniples.expensetrackerapi.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import com.firstpriniples.expensetrackerapi.domain.Category;
import com.firstpriniples.expensetrackerapi.exceptions.EtBadRequestException;
import com.firstpriniples.expensetrackerapi.exceptions.EtResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private static final String SQL_FIND_ALL = "SELECT C.category_id, C.user_id, C.title, C.description, "
            + "COALESCE(SUM(T.amount), 0) total_expense "
            + "FROM et_transactions T RIGHT JOIN et_categories C ON C.category_id = T.category_id "
            + "WHERE C.user_id = ? GROUP BY C.category_id";;

    private static final String SQL_FIND_BY_ID = "SELECT C.category_id, C.user_id, C.title, C.description, "
            + "COALESCE(SUM(T.amount), 0) total_expense "
            + "FROM et_transactions T RIGHT JOIN et_categories C ON C.category_id = T.category_id "
            + "WHERE C.user_id = ? AND C.category_id = ? GROUP BY C.category_id";;

    private static final String SQL_CREATE = "INSERT INTO et_categories (category_id, user_id, title, description) VALUES (NEXTVAL('et_categories_seq'), ?, ?, ?);";

    private static final String SQL_UPDATE = "UPDATE et_categories SET TITLE = ?, DESCRIPTION = ? WHERE user_id = ? AND category_id = ?";

    private static final String SQL_DELETE_CATEGORY = "DELETE FROM et_categories WHERE user_id = ? AND category_id = ?";

    private static final String SQL_DELETE_TRANSACTIONS = "DELETE FROM et_transactions WHERE category_id = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Category> findAll(Integer userID) throws EtResourceNotFoundException {
        return jdbcTemplate.query(SQL_FIND_ALL, new Object[] { userID }, categoryRowMapper);
    }

    @Override
    public Category findByID(Integer userID, Integer categoryID) throws EtResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[] { userID, categoryID }, categoryRowMapper);
        } catch (Exception e) {
            throw new EtResourceNotFoundException("Category not found");
        }
    }

    @Override
    public Integer create(Integer userID, String title, String descritption) throws EtBadRequestException {
        // keyholder is used to fetch the primary key generated in postgres from JDBC
        // interface

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userID);
                ps.setString(2, title);
                ps.setString(3, descritption);
                return ps;
            }, keyHolder);

            return (Integer) keyHolder.getKeys().get("category_id");

        } catch (Exception e) {
            throw new EtBadRequestException("Invalid Request" + e.getMessage());
        }
    }

    @Override
    public void update(Integer userID, Integer categoryID, Category category) throws EtBadRequestException {
        try {
            jdbcTemplate.update(SQL_UPDATE,
                    new Object[] { category.getTitle(), category.getDescription(), userID, categoryID });

        } catch (Exception e) {
            throw new EtBadRequestException("Invalid request");
        }

    }

    @Override
    public void removeByID(Integer userID, Integer categoryID) {
        this.removeAllCatTransactions(categoryID);
        jdbcTemplate.update(SQL_DELETE_CATEGORY, new Object[] { userID, categoryID });
    }

    private void removeAllCatTransactions(Integer categoryID) {
        jdbcTemplate.update(SQL_DELETE_TRANSACTIONS, new Object[] { categoryID });
    }

    private RowMapper<Category> categoryRowMapper = ((rs, rowNum) -> {
        return new Category(rs.getInt("category_id"), rs.getInt("user_id"), rs.getString("title"),
                rs.getString("description"), rs.getDouble("total_expense"));
    });

}