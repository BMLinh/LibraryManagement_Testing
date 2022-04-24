/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.control.Alert;

/**
 *
 * @author Admin
 */
public class Utils {
    public static Alert setAlert(String message, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        return alert;
    }
    
    public static final java.sql.Date convertDateUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }
    
    public static final String dateToString(Date date){
        SimpleDateFormat F = new SimpleDateFormat("dd/MM/yyyy");
        
        return F.format(date);
    }

    public static final String convertDateTimeToString(Date date){
        SimpleDateFormat F = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return F.format(date);
    }

    public static final String dateToString(Date date, String pattern){
        SimpleDateFormat F = new SimpleDateFormat(pattern);
        return F.format(date);
    }
    
}
