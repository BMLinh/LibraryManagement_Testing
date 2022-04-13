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
    private String descriptions;
    private int amount;
    private Date publishingYear;
    private int categoryId;
    private int publishingCompanyId;
    private Date dateOfEntering;

    public Book() {
    }

    public Book(int id, String name, String description, int amount, Date publishingYear,
                int publishingCompanyId, int categoryId, Date dateOfEntering) {
        this.id = id;
        this.name = name;
        this.descriptions = description;
        this.amount = amount;
        this.publishingYear = publishingYear;
        this.publishingCompanyId = publishingCompanyId;
        this.categoryId = categoryId;
        this.dateOfEntering = dateOfEntering;
    }

    @Override
    public String toString() {
        return String.format("%d %s %s", id, name, descriptions);
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

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(Date publishingYear) {
        this.publishingYear = publishingYear;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getPublishingCompanyId() {
        return publishingCompanyId;
    }

    public void setPublishingCompanyId(int publishingCompanyId) {
        this.publishingCompanyId = publishingCompanyId;
    }

    public Date getDateOfEntering() {
        return dateOfEntering;
    }

    public void setDateOfEntering(Date dateOfEntering) {
        this.dateOfEntering = dateOfEntering;
    }
}
