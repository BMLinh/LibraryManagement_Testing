package com.ou.librarymanagement;

import com.ou.pojo.ReaderCard;
import com.ou.pojo.User;
import com.ou.services.ReaderCardService;
import com.ou.services.RoleService;
import com.ou.services.ReaderCardService;
import com.ou.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    private App app = new App();

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lbNotification;

    UserService userService = new UserService();
    ReaderCardService readerCardService = new ReaderCardService();

    private User user;
    private ReaderCard currentCard;

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

    private void redirect(int userRoleId) throws IOException, SQLException {
        String fxml = "";
        RoleService roleService = new RoleService();
        String roleName = roleService.getRoleById(userRoleId).getName();

        if (roleName.trim().compareToIgnoreCase("admin") == 0) {
            fxml = "FXMLLoginSuccessful.fxml";
        } else if (roleName.trim().compareToIgnoreCase("staff") == 0) {
            fxml = "FXMLHome-Ems.fxml";
        } else {
            fxml = "FXMLHome-Usrs.fxml";
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        Parent root = loader.load();
        Scene mainScene = new Scene(root);

        if (userRoleId == 1) {
            LoginSuccessfulController loginSuccessfulController = loader.getController();
            loginSuccessfulController.sendData(user);
        } else if (userRoleId == 2) {
            Home_EmsController home_emsController = loader.getController();
            home_emsController.setCurrentStaff(user);
        } else {
            try {
                setCurrentCard(readerCardService.findReaderCardsByUserId(user.getId()).get(0));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Home_UsrsController home_usrsController = loader.getController();
            home_usrsController.setCurrentUser(user);
            home_usrsController.setCurrentCard(currentCard);
        }

        Stage primaryStage = (Stage) btnLogin.getScene().getWindow();
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void checkLogin() throws SQLException, IOException {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        user = userService.getByUsername(username);

        lbNotification.setVisible(true);
        btnLogin.setLayoutX(78);
        btnLogin.setLayoutY(305);

        if (username.equalsIgnoreCase(user.getUsername()) && password.equals(user.getPassword())) {
            redirect(user.getRoleId());
        } else {
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
}
