/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ou.librarymanagement;

import com.ou.pojo.ReaderCard;
import com.ou.services.ReaderCardService;
import com.ou.services.UserService;
import com.ou.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
    private TextField startDateTxtFld;
    @FXML
    private DatePicker endDateDP;
    @FXML
    private TextField amountTxtFld;
    @FXML
    private TextField userIdTxtFld;
    @FXML
    private TextField searchContentTxtFld;
    @FXML
    private Button btnBack;
    
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
                    this.startDateTxtFld.setText(Utils.convertDateToString(readerCard.getStartDate()));
                    this.endDateDP.setValue(Utils.convertUtilToSql(readerCard.getEndDate()).toLocalDate());
                    this.amountTxtFld.setText(String.valueOf(readerCard.getAmount()));
                    this.userIdTxtFld.setText(String.valueOf(readerCard.getUserId()));
                    this.amountTxtFld.setDisable(false);
                } catch (SQLException ex) {
                    Logger.getLogger(ReaderCardController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            return row;
        });
    }    
    
    public void init(){
        this.amountTxtFld.setDisable(true);
        
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
        
        this.endDateDP.setDayCellFactory(param -> new DateCell(){
            @Override
            public void updateItem(LocalDate ld, boolean bln) {
                super.updateItem(ld, bln);
                setDisable(bln || ld.compareTo(LocalDate.now()) < 1);
            }
        });
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
        if(!this.userIdTxtFld.getText().isBlank()){
            ReaderCard readerCard = this.getReaderCardFromFx();
            if(this.readerCardService.findReaderCardsByUserId(readerCard.getUserId()).isEmpty()){
                if(this.readerCardService.addReaderCard(readerCard)){
                    Utils.setAlert("Thêm thành công!!!", Alert.AlertType.INFORMATION).show();
                    loadData();
                    reset();
                    
                }
                else Utils.setAlert("Thêm thất bại!!!", Alert.AlertType.ERROR).show();
            }
            else Utils.setAlert("Đã có thẻ!!!", Alert.AlertType.ERROR).show();
        } else Utils.setAlert("Chưa nhập đủ thông tin!!!", Alert.AlertType.ERROR).show();
    }
    
    public void deleteReaderCard(ActionEvent evt) throws SQLException{
        if(!this.userIdTxtFld.getText().isBlank()){
            if(this.readerCardService.deleteReaderCard(this.readerCardTabView.getSelectionModel().getSelectedItem().getId())){
                Utils.setAlert("Xoá thành công!!!", Alert.AlertType.INFORMATION).show();
                loadData();
                reset();
            }
            else Utils.setAlert("Xoá thất bại!!!", Alert.AlertType.ERROR).show();
        } else Utils.setAlert("Chưa chọn Thẻ cần xoá!!!", Alert.AlertType.ERROR).show();
    }
    
    public void updateReaderCard(ActionEvent evt) throws SQLException{
        if(!this.readerCardIdTxtFld.getText().isBlank()){
            if(!this.amountTxtFld.getText().isBlank() && !this.userIdTxtFld.getText().isBlank()){
                ReaderCard reader = this.getReaderCardFromFx();
                if(0 == this.readerCardService.findReaderCardsByUserId(reader.getUserId()).size()){
                    if(this.readerCardService.updateReaderCard(this.readerCardTabView.getSelectionModel().getSelectedItem().getId(), reader)){
                        Utils.setAlert("Sửa thành công!!!", Alert.AlertType.INFORMATION).show();
                        loadData();
                        reset();
                    } else Utils.setAlert("Sửa thất bại!!!", Alert.AlertType.ERROR).show();
                } else Utils.setAlert("Có Thẻ trùng với thông tin đang sửa hoặc Không có thay đổi với User đang được sửa!!!", Alert.AlertType.ERROR).show();
            } else Utils.setAlert("Mời bạn nhập đủ thông tin!!!", Alert.AlertType.ERROR).show();
        } else Utils.setAlert("Chưa chọn User cần sửa!!!", Alert.AlertType.ERROR).show();
    }
    
    public void reset(){
        this.readerCardIdTxtFld.clear();
        this.userNameTxtFld.clear();
        this.startDateTxtFld.clear();
        this.endDateDP.setValue(null);
        this.amountTxtFld.clear();
        this.userIdTxtFld.clear();
        this.amountTxtFld.setDisable(true);
        this.readerCardTabView.getSelectionModel().clearSelection();
    }
    
    public void resetHandler(ActionEvent evt){
        reset();
    }
    
    public ReaderCard getReaderCardFromFx(){
        try{
            ReaderCard readerCard = new ReaderCard();

            readerCard.setStartDate(new Date());
            readerCard.setEndDate(Utils.convertUtilToSql(Date.from(this.endDateDP.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())));
            readerCard.setUserId(Integer.parseInt(this.userIdTxtFld.getText()));

            return readerCard;
        }catch(NullPointerException ex){
            Utils.setAlert("Mời bạn kiểm tra lại thông tin!!!", Alert.AlertType.ERROR).show();
        }
        return new ReaderCard();
    }
    
    public void searchReaderCard(ActionEvent evt) throws SQLException{
        if(this.searchContentTxtFld.getText().isBlank())
            loadData();
        if(this.readerCardService.findReaderCardsByUserId(Integer.parseInt(this.searchContentTxtFld.getText())).size() > 0 && !this.searchContentTxtFld.getText().isBlank()){
            this.readerCardTabView.setItems(FXCollections.observableList(this.readerCardService.findReaderCardsByUserId(Integer.parseInt(this.searchContentTxtFld.getText()))));
        }
        else Utils.setAlert("Không có dữ liệu!!!", Alert.AlertType.ERROR).show();
        
        reset();
        this.searchContentTxtFld.clear();
    }

    @FXML
    void backToAdmin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXMLHome-Adm.fxml"));
        Parent root = loader.load();
        Scene mainScene = new Scene(root);
        Stage primaryStage = (Stage) btnBack.getScene().getWindow();
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Trang chủ");
        primaryStage.show();
    }

}
