package com.firstpriniples.expensetrackerapi.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.firstpriniples.expensetrackerapi.domain.Category;
import com.firstpriniples.expensetrackerapi.services.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class CategoryResource {

    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories(HttpServletRequest request) {
        int userID = (Integer) request.getAttribute("userID");
        List<Category> categories = categoryService.fetchAllCategories(userID);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{categoryID}")
    public ResponseEntity<Category> getCategoryByID(HttpServletRequest request,
            @PathVariable("categoryID") Integer categoryID) {
        int userID = (Integer) request.getAttribute("userID");
        Category category = categoryService.fetchCategoryByID(userID, categoryID);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Category> addCategory(HttpServletRequest request,
            @RequestBody Map<String, Object> categoryMap) {

        int userID = (Integer) request.getAttribute("userID");
        String title = (String) categoryMap.get("title");
        String description = (String) categoryMap.get("description");

        Category category = categoryService.addCategory(userID, title, description);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryID}")
    public ResponseEntity<Map<String, Boolean>> updateCategory(HttpServletRequest request,
            @PathVariable("categoryID") Integer categoryID, @RequestBody Category category) {
        int userID = (Integer) request.getAttribute("userID");
        categoryService.updateCategory(userID, categoryID, category);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/{categoryID}")
    public ResponseEntity<Map<String, Boolean>> deleteCategory(HttpServletRequest request,
            @PathVariable("categoryID") Integer categoryID) {

        int userID = (Integer) request.getAttribute("userID");
        categoryService.removeCategory(userID, categoryID);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}