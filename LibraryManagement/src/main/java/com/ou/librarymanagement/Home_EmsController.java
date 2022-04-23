/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ou.librarymanagement;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Home_EmsController implements Initializable {
    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    @FXML
    private Button btn3;
    @FXML
    private Button btn4;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void switch1(ActionEvent evt) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("FXMLBorrowBook.fxml"));
        Stage window = (Stage) btn1.getScene().getWindow();
        window.setScene(new Scene(root, 1080, 802));
    }
    
    public void switch2() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("FXMLBorrowBook.fxml"));
        Stage window = (Stage) btn1.getScene().getWindow();
        window.setScene(new Scene(root, 1080, 802));
    }
    
    public void switch3() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("FXMLBorrowBook.fxml"));
        Stage window = (Stage) btn1.getScene().getWindow();
        window.setScene(new Scene(root, 1080, 802));
    }
    
    public void switch4() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("FXMLBorrowBook.fxml"));
        Stage window = (Stage) btn1.getScene().getWindow();
        window.setScene(new Scene(root, 1080, 802));
    }
}
