/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.utils;

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
    
    public static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        
        return sDate;
    }
}
