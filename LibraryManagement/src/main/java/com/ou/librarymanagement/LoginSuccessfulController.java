package com.ou.librarymanagement;

import com.ou.pojo.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginSuccessfulController {

    private LoginController loginController = new LoginController();

    @FXML
    private Label lbUserId;

    @FXML
    private Label lbUsername;

    @FXML
    private Button btnLogout;

    public void sendData(User user) {
        lbUserId.setText(String.valueOf(user.getId()));
        lbUsername.setText(user.getUsername());
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

}