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

    private ReaderCard currentCard = null;
    private User currentUser = null;
    

    //Để ở đây test tí
    private static final ReaderCardService readerCardService = new ReaderCardService();
    //Để ở đây test tí
    private static final UserService userService = new UserService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            //Để ở đây test tí
            setCurrentUser(userService.findUserById(1));
            if (readerCardService.findReaderCardsByUserId(getCurrentUser().getId()).isEmpty())
                currentCard = null;
            else setCurrentCard(readerCardService.findReaderCardsByUserId(getCurrentUser().getId()).get(0));
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }    
    
    public void switch1(ActionEvent evt) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLOrderBook.fxml"));
        OrderingBookController controller = fxmlLoader.getController();
        controller.setCurrentCard(this.getCurrentCard());
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Đặt sách");
        stage.show();
    }
    
    public void switch2(ActionEvent evt) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("FXMLBorrowBook.fxml"));
        Stage window = (Stage) btn2.getScene().getWindow();
        window.setScene(new Scene(root, 1080, 802));
    }
    
    public void switch3(ActionEvent evt) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("FXMLBorrowBook.fxml"));
        Stage window = (Stage) btn3.getScene().getWindow();
        window.setScene(new Scene(root, 1080, 802));
    }
    
    public void switch4(ActionEvent evt) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLUserInfo.fxml"));
        UserInfoController controller = fxmlLoader.getController();
        controller.setCurrentUser(this.getCurrentUser());
        controller.setCurrentCard(this.getCurrentCard());
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Xem thông tin");
        stage.show();
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
}
