package com.firstpriniples.expensetrackerapi.repositories;

import com.firstpriniples.expensetrackerapi.domain.Category;
import com.firstpriniples.expensetrackerapi.exceptions.EtBadRequestException;
import com.firstpriniples.expensetrackerapi.exceptions.EtResourceNotFoundException;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll(Integer userID) throws EtResourceNotFoundException;

    Category findByID(Integer userID, Integer categoryID) throws EtResourceNotFoundException;

    Integer create(Integer userID, String title, String descritption) throws EtBadRequestException;

    void update(Integer userID, Integer categoryID, Category category) throws EtBadRequestException;

    void removeByID(Integer userID, Integer categoryID);

}