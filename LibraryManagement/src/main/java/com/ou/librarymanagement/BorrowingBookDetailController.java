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
import javafx.scene.control.Button;
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
    @FXML
    private Button submit;
    
    
    private static ReaderCard currentCard;
    private static User currentUser;
    private static User currentStaff;
    
    
    private static final ReaderCardService readerCardService = new ReaderCardService();
    private static final UserService userService = new UserService();
    private static final DepartmentService departmentService = new DepartmentService();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
        // TODO
    }    
    
    private void init(){
        this.readerCardIdTxtFld.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (!t1.matches("\\d*"))
                    readerCardIdTxtFld.setText(t1.replaceAll("[^\\d]", ""));
            }
        });
        
        this.readerCardIdTxtFld.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().equals(" ")) {
                change.setText("");
            }
            return change;
        }));
    }

    public void checkUser(ActionEvent evt) throws SQLException {
        try{
            if (this.readerCardIdTxtFld.getText().isBlank()){
                Utils.setAlert("Mời bạn nhập ID thẻ độc giả!!!", Alert.AlertType.ERROR).show();
            }
            else if (this.readerCardService.findReaderCardById(Integer.parseInt(this.readerCardIdTxtFld.getText())).isEmpty()){
                Utils.setAlert("Không có thẻ độc giả!!!", Alert.AlertType.ERROR).show();
            }
            else {
                this.setCurrentCard(readerCardService.findReaderCardById(Integer.parseInt(this.readerCardIdTxtFld.getText())).get(0));
                this.setCurrentUser(userService.findUserById(getCurrentCard().getUserId()));
                this.nameTxtFld.setText(getCurrentUser().getFullname());
                this.amountTxtFld.setText(String.valueOf(getCurrentCard().getAmount()));
                this.departmentTxtFld.setText(this.departmentService.getDepartmentById(getCurrentUser().getDepartmentId()).getName());
                this.submit.setDisable(false);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }

    }
    
    public void borrowBook(ActionEvent event) throws IOException{
        if(this.nameTxtFld.getText().isBlank())
            Utils.setAlert("Chưa có thẻ độc giả. Mời bạn kiểm tra thẻ độc giả!!!", Alert.AlertType.ERROR).show();
        else if(this.currentCard.getId() != Integer.parseInt(this.readerCardIdTxtFld.getText()))
            Utils.setAlert("Có phải bạn muốn kiểm tra thẻ độc giả khác!!!", Alert.AlertType.WARNING).show();
        else{
            System.out.println(getCurrentUser().getId());
            if(getCurrentCard().getEndDate().before(new Date())){
                Utils.setAlert("Thẻ hết hạn!!!", Alert.AlertType.ERROR).show();
            }
            else if(getCurrentCard().getAmount() > 0)
                Utils.setAlert("Chưa trả hết sách!!!", Alert.AlertType.ERROR).show();

            else{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLBorrowBook.fxml"));
                BorrowBookController controller = fxmlLoader.getController();
                controller.setCurrentUser(this.getCurrentUser());
                controller.setCurrentCard(this.getCurrentCard());
                controller.setCurrentStaff(this.getCurrentStaff());
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("tesst sách");
                stage.show();
            }       
        }
    }
    
    public void reset(ActionEvent evt){
        this.amountTxtFld.clear();
        this.departmentTxtFld.clear();
        this.nameTxtFld.clear();
        this.readerCardIdTxtFld.clear();
        setCurrentCard(null);
        setCurrentUser(null);
    }

    /**
     * @return the currentCard
     */
    public static ReaderCard getCurrentCard() {
        return currentCard;
    }

    /**
     * @param aCurrentCard the currentCard to set
     */
    public static void setCurrentCard(ReaderCard aCurrentCard) {
        currentCard = aCurrentCard;
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

    /**
     * @return the currentStaff
     */
    public static User getCurrentStaff() {
        return currentStaff;
    }

    /**
     * @param aCurrentStaff the currentStaff to set
     */
    public static void setCurrentStaff(User aCurrentStaff) {
        currentStaff = aCurrentStaff;
    }

}
