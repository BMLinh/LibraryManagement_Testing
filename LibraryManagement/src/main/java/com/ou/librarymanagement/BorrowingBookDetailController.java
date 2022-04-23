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
import javafx.scene.input.MouseEvent;
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
            readerCard = (this.readerCardService.findReaderCardById(Integer.parseInt(this.readerCardIdTxtFld.getText())).get(0));
            user = (this.userService.findUserById(readerCard.getUserId()));
            this.nameTxtFld.setText(user.getFullname());
            this.amountTxtFld.setText(String.valueOf(readerCard.getAmount()));
            this.departmentTxtFld.setText(this.departmentService.getDepartmentById(user.getDepartmentId()).getName());
        }
        else Utils.setAlert("Không có thẻ độc giả!!!", Alert.AlertType.ERROR).show();
    }
    
    public void borrowBook(ActionEvent event) throws IOException{
        if(readerCard.getEndDate().before(new Date())){
            Utils.setAlert("Thẻ hết hạn!!!", Alert.AlertType.ERROR).show();
        }
        else if(readerCard.getAmount() > 0)
            Utils.setAlert("Chưa trả hết sách!!!", Alert.AlertType.ERROR).show();
            
        else{   
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLBorrowBook.fxml"));
            Parent root = loader.load();
            
            BorrowBookController controller = loader.getController();
            controller.display(user.getId(), readerCard.getId());
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }       
    }
}
