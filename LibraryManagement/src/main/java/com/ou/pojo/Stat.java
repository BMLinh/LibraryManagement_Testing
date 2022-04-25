package com.ou.pojo;

public class Stat {
    private int month;
    private String bookName;
    private int sum;

    public Stat() {
    }

    public Stat(int month, String bookName, int sum) {
        this.month = month;
        this.bookName = bookName;
        this.sum = sum;
    }
    
    
    
    /**
     * @return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * @return the bookName
     */
    public String getBookName() {
        return bookName;
    }

    /**
     * @param bookName the bookName to set
     */
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    /**
     * @return the sum
     */
    public int getSum() {
        return sum;
    }

    /**
     * @param sum the sum to set
     */
    public void setSum(int sum) {
        this.sum = sum;
    }
}
