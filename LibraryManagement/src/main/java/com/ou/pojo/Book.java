/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou.pojo;

import java.util.Date;

/**
 *
 * @author Lightning
 */
public class Book {
    private int id;
    private String name;
    private String description;
    private int amount;
    private int publishingYear;
    private int publishingCompanyId;
    private int categoryId;
    private Date dateOfEntering;
    private String position;

    public Book() {
    }

    public Book(int id, String name, String description, int amount, int publishingYear, int publishingCompanyId, int categoryId, Date dateOfEntering, String position) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.publishingYear = publishingYear;
        this.publishingCompanyId = publishingCompanyId;
        this.categoryId = categoryId;
        this.dateOfEntering = dateOfEntering;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(int publishingYear) {
        this.publishingYear = publishingYear;
    }

    public int getPublishingCompanyId() {
        return publishingCompanyId;
    }

    public void setPublishingCompanyId(int publishingCompanyId) {
        this.publishingCompanyId = publishingCompanyId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Date getDateOfEntering() {
        return dateOfEntering;
    }

    public void setDateOfEntering(Date dateOfEntering) {
        this.dateOfEntering = dateOfEntering;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    
    
    
}
