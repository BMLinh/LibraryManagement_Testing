package com.ou.librarymanagement;

import com.ou.pojo.User;
import com.ou.services.RoleService;
import com.ou.services.UserService;
import com.ou.utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lbNotification;

    UserService userService = new UserService();
    RoleService roleService = new RoleService();

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    private void login() throws SQLException, IOException, InterruptedException {
        checkLogin();
    }

    @FXML
    void pressEnter(KeyEvent event) throws SQLException, IOException, InterruptedException {
        if (event.getCode().equals(KeyCode.ENTER))
            login();
    }

    public void checkLogin() throws SQLException, IOException{
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        user = userService.getByUsername(username);

        System.out.println("txtUsername = " + username);
        System.out.println("User username = " + user.getUsername());

        System.out.println("txtPassword = " + password);
        System.out.println("User password = " + user.getPassword());

        lbNotification.setVisible(true);
        btnLogin.setLayoutX(78);
        btnLogin.setLayoutY(305);

        if (user == null){
            lbNotification.setTextFill(Color.RED);
            lbNotification.setText("Username không tồn tại!");
        }
        else if(user.getPassword().equals(password)){
            if (roleService.getRoleById(user.getRoleId()).getName().equals("User")){
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLHome-Usrs.fxml"));
                Home_UsrsController controller = fxmlLoader.getController();
                controller.setCurrentUser(this.getUser());
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Home User");
                stage.show();
            }
        }
        else {
            lbNotification.setTextFill(Color.RED);
            lbNotification.setText("Đăng nhập thất bại");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbNotification.setVisible(false);
        btnLogin.setLayoutX(78);
        btnLogin.setLayoutY(295);
    }
}
