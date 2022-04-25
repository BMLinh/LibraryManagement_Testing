package com.ou.librarymanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Home_AdmController {

    @FXML
    private Button btnAuthorManagement;

    @FXML
    private Button btnBookManagement;

    @FXML
    private Button btnCategoryManagement;

    @FXML
    private Button btnDepartmentManagement;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnPubComManagement;

    @FXML
    private Button btnReaderCardManagement;

    @FXML
    private Button btnRoleManagement;

    @FXML
    private Button btnUserManagement;

    private void changeScene(Button btn, String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        Parent root = loader.load();
        Scene mainScene = new Scene(root);
        Stage primaryStage = (Stage) btn.getScene().getWindow();
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @FXML
    void authorManagement(ActionEvent event) throws IOException {
        changeScene(btnAuthorManagement, "FXMLAuthor.fxml");
    }

    @FXML
    void bookManagement(ActionEvent event) throws IOException {
        changeScene(btnBookManagement, "FXMLBook.fxml");
    }

    @FXML
    void categoryManagement(ActionEvent event) throws IOException {
        changeScene(btnCategoryManagement, "FXMLBookCategory.fxml");
    }

    @FXML
    void departmentManagement(ActionEvent event) throws IOException {
        changeScene(btnDepartmentManagement, "FXMLDepartment.fxml");
    }

    @FXML
    void pubComManagement(ActionEvent event) throws IOException {
        changeScene(btnPubComManagement, "FXMLPublishingcompany.fxml");
    }

    @FXML
    void readerCardManagement(ActionEvent event) throws IOException {
        changeScene(btnReaderCardManagement, "FXMLReaderCard.fxml");
    }

    @FXML
    void roleManagement(ActionEvent event) throws IOException {
        changeScene(btnRoleManagement, "FXMLRole.fxml");
    }

    @FXML
    void userManagement(ActionEvent event) throws IOException {
        changeScene(btnUserManagement, "FXMLUser.fxml");
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        logoutExecute();
    }

    private void logoutExecute() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXMLLogin.fxml"));
        Parent root = loader.load();
        Scene mainScene = new Scene(root);
        Stage primaryStage = (Stage) btnLogout.getScene().getWindow();
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

}
