package com.ou.pojo;

import java.time.LocalDateTime;
import java.util.Date;

public class OrderingBook {
    private int id;
    private int bookId;
    private int readerCardId;
    private int amount;
    private String createdDate;
    private String expiredDate;
    private boolean active;

    public OrderingBook() {
    }

    public OrderingBook(int id, int bookId, int readerCardId, int amount, String createdDate, String expiredDate, boolean active) {
        this.id = id;
        this.bookId = bookId;
        this.readerCardId = readerCardId;
        this.amount = amount;
        this.createdDate = createdDate;
        this.expiredDate = expiredDate;
        this.active = active;
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
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the expiredDate
     */
    public String getExpiredDate() {
        return expiredDate;
    }

    /**
     * @param expiredDate the expiredDate to set
     */
    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }
    
}
