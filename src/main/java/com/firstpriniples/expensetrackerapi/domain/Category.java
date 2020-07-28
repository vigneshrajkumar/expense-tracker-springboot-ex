package com.firstpriniples.expensetrackerapi.domain;

public class Category {
    private Integer categoryID;
    private Integer userID;
    private String title;
    private String description;
    private Double totalExpense;

    public Category(Integer categoryID, Integer userID, String title, String description, Double totalExpense) {
        this.setCategoryID(categoryID);
        this.setUserID(userID);
        this.setTitle(title);
        this.setDescription(description);
        this.setTotalExpense(totalExpense);
    }

    public Double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(Double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }
}