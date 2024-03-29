/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ou.librarymanagement;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.ou.pojo.ReaderCard;
import com.ou.pojo.User;
import com.ou.services.ReaderCardService;
import com.ou.services.UserService;
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
public class Home_UsrsController implements Initializable { 
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
    private LoginController loginController = new LoginController();
    private ReaderCard currentCard = null;
    private User currentUser = null;
    private Stage stage1;
    private Stage stage2;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO


    }
    
    public void switch1(ActionEvent evt) throws IOException{
        if (stage1 != null && stage1.isShowing()) {
            stage1.toFront();
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLOrderBook.fxml"));
            OrderingBookController controller = fxmlLoader.getController();
            controller.setCurrentCard(this.getCurrentCard());
            stage1 = new Stage();
            stage1.setScene(new Scene(fxmlLoader.load()));
            stage1.setTitle("Đặt sách");            
            stage1.show();
        }
    }
    
    public void switch4(ActionEvent evt) throws IOException{
        if (stage2 != null && stage2.isShowing()) {
            stage2.toFront();
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLUserInfo.fxml"));
            UserInfoController controller = fxmlLoader.getController();
            controller.setCurrentUser(this.getCurrentUser());
            controller.setCurrentCard(this.getCurrentCard());
            stage2 = new Stage();
            stage2.setScene(new Scene(fxmlLoader.load()));
            stage2.setTitle("Xem thông tin");         
            stage2.show();
        }
    }

    /**
     * @return the currentCard
     */
    public ReaderCard getCurrentCard() {
        return currentCard;
    }

    /**
     * @param currentCard the currentCard to set
     */
    public void setCurrentCard(ReaderCard currentCard) {
        this.currentCard = currentCard;
    }

    /**
     * @return the currentUser
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * @param currentUser the currentUser to set
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
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
        primaryStage.setTitle("Đăng nhập");
        primaryStage.show();
        if (stage1 != null)
            stage1.close();
        if (stage2 != null)
            stage2.close();
    }

}
