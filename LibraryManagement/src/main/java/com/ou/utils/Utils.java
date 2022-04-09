/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
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
    
    public static final java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        
        return sDate;
    }
    
    public static final String convertDateToString(Date date){
        SimpleDateFormat F = new SimpleDateFormat("MM/dd/yyyy");
        
        return F.format(date);
    }
    
}
