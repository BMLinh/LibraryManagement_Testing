/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ou.librarymanagement;

import com.ou.pojo.ReaderCard;
import com.ou.services.ReaderCardService;
import com.ou.services.UserService;
import com.ou.utils.Utils;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class ReaderCardController implements Initializable {
    @FXML
    private TableView<ReaderCard> readerCardTabView;
    @FXML
    private TextField readerCardIdTxtFld;
    @FXML
    private TextField userNameTxtFld;
    @FXML
    private DatePicker startDateDP;
    @FXML
    private DatePicker endDateDP;
    @FXML
    private TextField amountTxtFld;
    @FXML
    private TextField userIdTxtFld;
    @FXML
    private TextField searchContentTxtFld;
    
    private static final ReaderCardService readerCardService = new ReaderCardService();
    private static final UserService userService = new UserService();
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        init();
        loadTableView();
        loadData();
        
        this.readerCardTabView.setRowFactory(et -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(r -> {
                try {
                    ReaderCard readerCard = (ReaderCard)this.readerCardTabView.getSelectionModel().getSelectedItem();
                    this.readerCardIdTxtFld.setText(String.valueOf(readerCard.getId()));
                    this.userNameTxtFld.setText(this.userService.findUserById(readerCard.getUserId()).getFullname());
                    this.startDateDP.setValue(Utils.convertUtilToSql(readerCard.getStartDate()).toLocalDate());
                    this.endDateDP.setValue(Utils.convertUtilToSql(readerCard.getEndDate()).toLocalDate());
                    this.amountTxtFld.setText(String.valueOf(readerCard.getAmount()));
                    this.userIdTxtFld.setText(String.valueOf(readerCard.getUserId()));
                } catch (SQLException ex) {
                    Logger.getLogger(ReaderCardController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            return row;
        });
    }    
    
    public void init(){
        this.amountTxtFld.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(!t1.matches("\\d*"))
                    amountTxtFld.setText(t1.replaceAll("[^\\d]", ""));
                }
        });

        
        this.userIdTxtFld.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(!t1.matches("\\d*"))
                    userIdTxtFld.setText(t1.replaceAll("[^\\d]", ""));
                }
        });
        
        this.searchContentTxtFld.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(!t1.matches("\\d*"))
                    searchContentTxtFld.setText(t1.replaceAll("[^\\d]", ""));
                }
        });
        
        this.amountTxtFld.setTextFormatter(new TextFormatter<>(change -> {
            if(change.getText().equals(" ")){
                change.setText("");
            }
            return change;
        }));
        
        this.userIdTxtFld.setTextFormatter(new TextFormatter<>(change -> {
            if(change.getText().equals(" ")){
                change.setText("");
            }
            return change;
        }));
        
        this.searchContentTxtFld.setTextFormatter(new TextFormatter<>(change -> {
            if(change.getText().equals(" ")){
                change.setText("");
            }
            return change;
        }));
    }
    
    public void loadData(){
        try {
            this.readerCardTabView.setItems(FXCollections.observableList(this.readerCardService.getReaderCards()));
        } catch (SQLException ex) {
            Logger.getLogger(ReaderCardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadTableView(){
        TableColumn col1 = new TableColumn("ID");
        col1.setCellValueFactory(new PropertyValueFactory("id"));
        col1.setPrefWidth(40);
        
        TableColumn col2 = new TableColumn("Ngày mở");
        col2.setCellValueFactory(new PropertyValueFactory("startDate"));
        col2.setPrefWidth(150);
        
        TableColumn col3 = new TableColumn("Ngày hết hạn");
        col3.setCellValueFactory(new PropertyValueFactory("endDate"));
        col3.setPrefWidth(150);
        
        TableColumn col4 = new TableColumn("Số lượng");
        col4.setCellValueFactory(new PropertyValueFactory("amount"));
        col4.setPrefWidth(100);
        
        TableColumn col5 = new TableColumn("User Id");
        col5.setCellValueFactory(new PropertyValueFactory("userId"));
        col5.setPrefWidth(100);
        
        this.readerCardTabView.getColumns().setAll(col1,col2,col3,col4,col5);
    }
    
    public void addReaderCard(ActionEvent evt) throws SQLException{
        ReaderCard readerCard = this.getReaderCardFromFx();
        if(this.readerCardService.findReaderCardsByUserId(readerCard.getUserId()).isEmpty()){
            if(this.readerCardService.addReaderCard(getReaderCardFromFx())){
                Utils.setAlert("Thêm thành công!!!", Alert.AlertType.INFORMATION).show();
                reset();
            }
            else Utils.setAlert("Thêm thất bại!!!", Alert.AlertType.ERROR).show();
        }
        else Utils.setAlert("Đã có thẻ!!!", Alert.AlertType.ERROR).show();
    }
    
    public void deleteReaderCard(ActionEvent evt) throws SQLException{
        if(this.readerCardService.deleteReaderCard(this.readerCardTabView.getSelectionModel().getSelectedItem().getId())){
            Utils.setAlert("Xoá thành công!!!", Alert.AlertType.INFORMATION).show();
            reset();
        }
        else Utils.setAlert("Xoá thất bại!!!", Alert.AlertType.ERROR).show();
    }
    
    public void updateReaderCard(ActionEvent evt) throws SQLException{
        if(this.readerCardService.updateReaderCard(this.readerCardTabView.getSelectionModel().getSelectedItem().getId(), this.getReaderCardFromFx())){
            Utils.setAlert("Sửa thành công!!!", Alert.AlertType.INFORMATION).show();
            reset();
        }
        else Utils.setAlert("Sửa thất bại!!!", Alert.AlertType.ERROR).show();
    }
    
    public void reset(){
        this.readerCardIdTxtFld.clear();
        this.userNameTxtFld.clear();
        this.startDateDP.setValue(LocalDate.now(ZoneId.systemDefault()));
        this.endDateDP.setValue(LocalDate.now(ZoneId.systemDefault()));
        this.amountTxtFld.clear();
        this.userIdTxtFld.clear();
        loadData();
    }
    
    public void resetHandler(ActionEvent evt){
        reset();
    }
    
    public ReaderCard getReaderCardFromFx(){
        ReaderCard readerCard = new ReaderCard();
        
        readerCard.setStartDate(Utils.convertUtilToSql(Date.from(this.startDateDP.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())));
        readerCard.setEndDate(Utils.convertUtilToSql(Date.from(this.endDateDP.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())));
        readerCard.setAmount(Integer.parseInt(this.amountTxtFld.getText()));
        readerCard.setUserId(Integer.parseInt(this.userIdTxtFld.getText()));
        
        return readerCard;
    }
    
    public void searchReaderCard(ActionEvent evt) throws SQLException{
        if(this.readerCardService.findReaderCardsByUserId(Integer.parseInt(this.searchContentTxtFld.getText())).size() > 0){
            this.readerCardTabView.setItems(FXCollections.observableList(this.readerCardService.findReaderCardsByUserId(Integer.parseInt(this.searchContentTxtFld.getText()))));
        }
        else Utils.setAlert("Không có dữ liệu!!!", Alert.AlertType.ERROR).show();
        
        this.searchContentTxtFld.clear();
    }
}
