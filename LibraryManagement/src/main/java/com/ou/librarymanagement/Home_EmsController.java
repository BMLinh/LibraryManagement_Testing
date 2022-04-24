/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ou.librarymanagement;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.ou.pojo.User;
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

    @FXML
    private Button btnLogout;

    private User currentStaff;
    private LoginController loginController = new LoginController();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void switch1(ActionEvent evt) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLBorrowingBookDetail.fxml"));
        Parent node = loader.load();
        BorrowingBookDetailController controller = new BorrowingBookDetailController();
        
        controller.setCurrentStaff(this.currentStaff);
        
        Stage stage = new Stage();
        stage.setScene(new Scene(node));
        stage.show();
    }

    public void switch2() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLBorrowBook.fxml"));
        Stage window = (Stage) btn1.getScene().getWindow();
        window.setScene(new Scene(root, 1080, 802));
    }

    public void switch3() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLCheckingUser.fxml"));
        Parent node = loader.load();
        BorrowingBookDetailController controller = new BorrowingBookDetailController();
        
        controller.setCurrentStaff(this.currentStaff);
        
        Stage stage = new Stage();
        stage.setScene(new Scene(node));
        stage.show();
    }

    public void switch4() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLBorrowBook.fxml"));
        Stage window = (Stage) btn1.getScene().getWindow();
        window.setScene(new Scene(root, 1080, 802));
    }

    

    @FXML
    private void logout(ActionEvent event) throws IOException {
        logoutExecute();
    }

    private void logoutExecute() throws IOException {
        loginController.setUser(null);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXMLLogin.fxml"));
        Parent root = loader.load();
        Scene mainScene = new Scene(root);
        Stage primaryStage = (Stage) btnLogout.getScene().getWindow();
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    /**
     * @return the currentStaff
     */
    public User getCurrentStaff() {
        return currentStaff;
    }

    /**
     * @param currentStaff the currentStaff to set
     */
    public void setCurrentStaff(User currentStaff) {
        this.currentStaff = currentStaff;
    }
}
