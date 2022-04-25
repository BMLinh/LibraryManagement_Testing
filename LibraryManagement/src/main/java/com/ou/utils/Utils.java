/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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
        SimpleDateFormat F = new SimpleDateFormat("dd/MM/yyyy");
        
        return F.format(date);
    }

    public static final String convertDateTimeToString(Date date){
        SimpleDateFormat F = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return F.format(date);
    }

    public static final String convertDateToString(Date date, String pattern){
        SimpleDateFormat F = new SimpleDateFormat(pattern);
        return F.format(date);
    }
    
}
