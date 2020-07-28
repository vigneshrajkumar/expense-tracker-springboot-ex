package com.firstpriniples.expensetrackerapi.services;

import java.util.List;

import com.firstpriniples.expensetrackerapi.domain.Category;
import com.firstpriniples.expensetrackerapi.exceptions.EtBadRequestException;
import com.firstpriniples.expensetrackerapi.exceptions.EtResourceNotFoundException;
import com.firstpriniples.expensetrackerapi.repositories.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public List<Category> fetchAllCategories(Integer userID) {
		return categoryRepository.findAll(userID);
	}

	@Override
	public Category fetchCategoryByID(Integer userID, Integer categoryID) throws EtResourceNotFoundException {
		return categoryRepository.findByID(userID, categoryID);
	}

	@Override
	public Category addCategory(Integer userID, String title, String description) throws EtBadRequestException {
		Integer categoryID = categoryRepository.create(userID, title, description);
		return categoryRepository.findByID(userID, categoryID);
	}

	@Override
	public void updateCategory(Integer userID, Integer categoryID, Category category) throws EtBadRequestException {
		categoryRepository.update(userID, categoryID, category);
	}

	@Override
	public void removeCategory(Integer userID, Integer categoryID) throws EtResourceNotFoundException {
		this.fetchCategoryByID(userID, categoryID);
		categoryRepository.removeByID(userID, categoryID);
	}

}