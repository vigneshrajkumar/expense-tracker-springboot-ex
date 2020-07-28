package com.firstpriniples.expensetrackerapi.services;

import java.util.List;

import com.firstpriniples.expensetrackerapi.domain.Category;
import com.firstpriniples.expensetrackerapi.exceptions.EtBadRequestException;
import com.firstpriniples.expensetrackerapi.exceptions.EtResourceNotFoundException;

public interface CategoryService {

    List<Category> fetchAllCategories(Integer userID);

    Category fetchCategoryByID(Integer userID, Integer categoryID) throws EtResourceNotFoundException;

    Category addCategory(Integer userID, String title, String description) throws EtBadRequestException;

    void updateCategory(Integer userID, Integer categoryID, Category category) throws EtBadRequestException;

    void removeCategory(Integer userID, Integer categoryID) throws EtResourceNotFoundException;

}