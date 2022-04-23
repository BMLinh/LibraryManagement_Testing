/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ou.librarymanagement;

import com.ou.pojo.Book;
import com.ou.pojo.ReaderCard;
import com.ou.pojo.User;
import com.ou.services.DepartmentService;
import com.ou.services.ReaderCardService;
import com.ou.services.UserService;
import com.ou.utils.Utils;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class BorrowingBookDetailController implements Initializable {
    @FXML
    private TextField readerCardIdTxtFld;
    @FXML
    private TextField nameTxtFld;
    @FXML
    private TextField departmentTxtFld;
    @FXML
    private TextField amountTxtFld;
    
    private ReaderCard readerCard;
    private User user;
    private User staff;
    
    
    private static final ReaderCardService readerCardService = new ReaderCardService();
    private static final UserService userService = new UserService();
    private static final DepartmentService departmentService = new DepartmentService();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void checkUser(ActionEvent evt) throws SQLException {
        if(this.readerCardService.findReaderCardById(Integer.parseInt(this.readerCardIdTxtFld.getText())).size() > 0){
            setReaderCard(this.readerCardService.findReaderCardById(Integer.parseInt(this.readerCardIdTxtFld.getText())).get(0));
            setUser(this.userService.findUserById(getReaderCard().getUserId()));
            this.nameTxtFld.setText(getUser().getFullname());
            this.amountTxtFld.setText(String.valueOf(getReaderCard().getAmount()));
            this.departmentTxtFld.setText(this.departmentService.getDepartmentById(getUser().getDepartmentId()).getName());
        }
        else Utils.setAlert("Không có thẻ độc giả!!!", Alert.AlertType.ERROR).show();
    }
    
    public void borrowBook(ActionEvent evt) throws IOException{
        if(getReaderCard().getEndDate().before(new Date())){
            Utils.setAlert("Thẻ hết hạn!!!", Alert.AlertType.ERROR).show();
        }
        else if(getReaderCard().getAmount() > 0)
            Utils.setAlert("Chưa trả hết sách!!!", Alert.AlertType.ERROR).show();
            
        else{   
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLBorrowBook.fxml"));
            Parent root = loader.load();
            
            BorrowBookController controller = loader.getController();
            controller.setUser(user);
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }       
    }

    /**
     * @return the readerCard
     */
    public ReaderCard getReaderCard() {
        return readerCard;
    }

    /**
     * @param readerCard the readerCard to set
     */
    public void setReaderCard(ReaderCard readerCard) {
        this.readerCard = readerCard;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the staff
     */
    public User getStaff() {
        return staff;
    }

    /**
     * @param staff the staff to set
     */
    public void setStaff(User staff) {
        this.staff = staff;
    }

}
