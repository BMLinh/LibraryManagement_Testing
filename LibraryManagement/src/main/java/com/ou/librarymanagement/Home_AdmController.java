package com.ou.librarymanagement;

import com.ou.pojo.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Home_AdmController implements Initializable {

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
    
    private static User currentUser; 
    private static String title;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
  
    private void changeScene(Button btn, String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        Parent root = loader.load();
        Scene mainScene = new Scene(root);
        Stage primaryStage = (Stage) btn.getScene().getWindow();
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.setTitle(title);
        primaryStage.show();
    }

    @FXML
    void authorManagement(ActionEvent event) throws IOException {
        title = "Quản lý tác giả";
        changeScene(btnAuthorManagement, "FXMLAuthor.fxml");
    }

    @FXML
    void bookManagement(ActionEvent event) throws IOException {
        title = "Quản lý sách";
        changeScene(btnBookManagement, "FXMLBook.fxml");
    }

    @FXML
    void categoryManagement(ActionEvent event) throws IOException {
        title = "Quản lý danh mục";
        changeScene(btnCategoryManagement, "FXMLBookCategory.fxml");
    }

    @FXML
    void departmentManagement(ActionEvent event) throws IOException {
        title = "Quản lý khoa";
        changeScene(btnDepartmentManagement, "FXMLDepartment.fxml");
    }

    @FXML
    void pubComManagement(ActionEvent event) throws IOException {
        title = "Quản lý nhà xuất bản";
        changeScene(btnPubComManagement, "FXMLPublishingcompany.fxml");
    }

    @FXML
    void readerCardManagement(ActionEvent event) throws IOException {
        title = "Quản lý thẻ độc giả";
        changeScene(btnReaderCardManagement, "FXMLReaderCard.fxml");
    }

    @FXML
    void roleManagement(ActionEvent event) throws IOException {
        title = "Quản lý chức vụ";
        changeScene(btnRoleManagement, "FXMLRole.fxml");
    }

    @FXML
    void userManagement(ActionEvent event) throws IOException {
        title = "Quản lý người dùng";
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
        primaryStage.setTitle("Đăng nhập");
        primaryStage.show();
    }

    /**
     * @return the currentUser
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * @param aCurrentUser the currentUser to set
     */
    public static void setCurrentUser(User aCurrentUser) {
        currentUser = aCurrentUser;
    }

}
