/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class BorrowingBook {
    private int id;
    private int staffId;
    private int bookId;
    private int readerCardId;
    private int amount;
    private Date createdDate;
    private Date returnDate;
    private int active;
    private BigDecimal fine;
    
    public BorrowingBook(){}
    
    public BorrowingBook(int id, int staffId, int bookId, int readerCardId, int amount, Date createdDate, Date returnDate, int active, BigDecimal fine){
        this.id = id;
        this.staffId = staffId;
        this.bookId = bookId;
        this.readerCardId = readerCardId;
        this.amount = amount;
        this.createdDate = createdDate;
        this.returnDate = returnDate;
        this.active = active;
        this.fine = fine;
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the staffId
     */
    public int getStaffId() {
        return staffId;
    }

    /**
     * @param staffId the staffId to set
     */
    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    /**
     * @return the bookId
     */
    public int getBookId() {
        return bookId;
    }

    /**
     * @param bookId the bookId to set
     */
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    /**
     * @return the readerCardId
     */
    public int getReaderCardId() {
        return readerCardId;
    }

    /**
     * @param readerCardId the readerCardId to set
     */
    public void setReaderCardId(int readerCardId) {
        this.readerCardId = readerCardId;
    }

    /**
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the returnDate
     */
    public Date getReturnDate() {
        return returnDate;
    }

    /**
     * @param returnDate the returnDate to set
     */
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * @return the active
     */
    public int getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(int active) {
        this.active = active;
    }

    /**
     * @return the fine
     */
    public BigDecimal getFine() {
        return fine;
    }

    /**
     * @param fine the fine to set
     */
    public void setFine(BigDecimal fine) {
        this.fine = fine;
    }
}
