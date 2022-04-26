/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * @author Admin
 */
public class Utils {
    public static Alert setAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        return alert;
    }

    public static final java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }

    public static final String convertDateToString(Date date) {
        SimpleDateFormat F = new SimpleDateFormat("dd/MM/yyyy");

        return F.format(date);
    }

    public static final String convertDateTimeToString(Date date) {
        SimpleDateFormat F = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return F.format(date);
    }

    public static final String convertDateToString(Date date, String pattern) {
        SimpleDateFormat F = new SimpleDateFormat(pattern);
        return F.format(date);
    }

    public static final boolean isContainNumber(String str) {
        char[] chars = str.toCharArray();
        for (char c : chars) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isContainSpecialCharacter(String str) {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    public static String capitalizeWord(String str) {
        String words[] = str.split(" ");
        String capitalizeWord = "";
        for (String w : words) {
            String first = w.substring(0, 1);
            String afterfirst = w.substring(1);
            capitalizeWord += first.toUpperCase() + afterfirst + " ";
        }
        return capitalizeWord.trim();
    }

    public static final String stringNormalization(String str) {
        String result = str;

        result = result.trim();
        result = result.replaceAll("\\s+", " ");
        result = capitalizeWord(result);

        return result;
    }

}
