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
    private Stage stage1;
    private Stage stage2;
    private Stage stage3;
    private Stage stage4;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void switch1(ActionEvent evt) throws IOException {
        if (stage1 != null && stage1.isShowing()) {
            stage1.toFront();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLBorrowingBookDetail.fxml"));
            Parent node = loader.load();
            BorrowingBookDetailController controller = new BorrowingBookDetailController();
            controller.setCurrentStaff(this.currentStaff);
            stage1 = new Stage();
            stage1.setScene(new Scene(node));
            stage1.show();
        }
    }

    public void switch2() throws IOException {
        if (stage2 != null && stage2.isShowing()) {
            stage2.toFront();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLReturnBook.fxml"));
            Parent node = loader.load();
            stage2 = new Stage();
            stage2.setScene(new Scene(node));
            stage2.show();
        }
    }

    public void switch3() throws IOException {
        if (stage3 != null && stage3.isShowing()) {
            stage3.toFront();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLCheckingUser.fxml"));
            Parent node = loader.load();
            BorrowingBookDetailController controller = new BorrowingBookDetailController();
            controller.setCurrentStaff(this.currentStaff);
            stage3 = new Stage();
            stage3.setScene(new Scene(node));
            stage3.show();
        }
    }

    public void switch4() throws IOException {
        if (stage4 != null && stage4.isShowing()) {
            stage4.toFront();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLStat.fxml"));
            Parent node = loader.load();
            stage4 = new Stage();
            stage4.setScene(new Scene(node));
            stage4.show();
        }
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
        if (stage1 != null) {
            stage1.close();
        }
        if (stage2 != null) {
            stage2.close();
        }
        if (stage3 != null) {
            stage3.close();
        }
        if (stage4 != null) {
            stage4.close();
        }
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
