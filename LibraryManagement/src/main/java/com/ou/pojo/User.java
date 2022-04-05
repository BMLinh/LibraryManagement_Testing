/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.pojo;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class User {
    private static int autoinc; 
    private int id = ++autoinc;
    private String username;
    private String password;
    private String fullname;
    private byte gender;
    private Date birth;
    private String address;
    private String phone;
    private int role_id;
    private int department_id;
    private Date created_date  = new Date();

    public User() {}
    
    public User(int id, String username, String password, String fullname, byte gender, Date birth, String address, String phone, int role_id, int department_id) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.gender = gender;
        this.birth = birth;
        this.address = address;
        this.phone = phone;
        this.role_id = role_id;
        this.department_id = department_id;
    }

    /**
     * @return the autoinc
     */
    public static int getAutoinc() {
        return autoinc;
    }

    /**
     * @param aAutoinc the autoinc to set
     */
    public static void setAutoinc(int aAutoinc) {
        autoinc = aAutoinc;
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
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the fullname
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * @param fullname the fullname to set
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * @return the gender
     */
    public byte getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(byte gender) {
        this.gender = gender;
    }

    /**
     * @return the birth
     */
    public Date getBirth() {
        return birth;
    }

    /**
     * @param birth the birth to set
     */
    public void setBirth(Date birth) {
        this.birth = birth;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the role_id
     */
    public int getRole_id() {
        return role_id;
    }

    /**
     * @param role_id the role_id to set
     */
    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    /**
     * @return the department_id
     */
    public int getDepartment_id() {
        return department_id;
    }

    /**
     * @param department_id the department_id to set
     */
    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

}
