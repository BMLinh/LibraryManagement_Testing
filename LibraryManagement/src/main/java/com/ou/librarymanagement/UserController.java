/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ou.librarymanagement;

import com.ou.services.UserService;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class UserController implements Initializable {
    private static final UserService s = new UserService();
    @FXML
    private TableView userTabView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.loadTableView();
        this.loadData();
        
    }    
    
    public void loadData() {
        try {
            this.userTabView.setItems(FXCollections.observableList(s.getUsers()));
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadTableView() {
        TableColumn col1 = new TableColumn("Id");
        col1.setCellValueFactory(new PropertyValueFactory("id"));
        col1.setPrefWidth(40);
        
        TableColumn col2 = new TableColumn("Username");
        col2.setCellValueFactory(new PropertyValueFactory("username"));
        col2.setPrefWidth(100);
        
        TableColumn col3 = new TableColumn("Password");
        col3.setCellValueFactory(new PropertyValueFactory("password"));
        col3.setPrefWidth(100);
        
        TableColumn col4 = new TableColumn("Họ tên");
        col4.setCellValueFactory(new PropertyValueFactory("fullname"));
        col4.setPrefWidth(150);
        
        TableColumn col5 = new TableColumn("Giới tính");
        col5.setCellValueFactory(new PropertyValueFactory("gender"));
        col5.setPrefWidth(40);
        
        TableColumn col6 = new TableColumn("Ngày sinh");
        col6.setCellValueFactory(new PropertyValueFactory("birth"));
        col6.setPrefWidth(100);
       
        TableColumn col7 = new TableColumn("Địa chỉ");
        col7.setCellValueFactory(new PropertyValueFactory("address"));
        col7.setPrefWidth(230);
        
        TableColumn col8 = new TableColumn("SĐT");
        col8.setCellValueFactory(new PropertyValueFactory("phone"));
        col8.setPrefWidth(100);
        
        TableColumn col9 = new TableColumn("Role Id");
        col9.setCellValueFactory(new PropertyValueFactory("roleId"));
        col9.setPrefWidth(40);
        
        TableColumn col10 = new TableColumn("Department Id");
        col10.setCellValueFactory(new PropertyValueFactory("departmentId"));
        col10.setPrefWidth(40);
        
        TableColumn col11 = new TableColumn("Ngày tạo");
        col11.setCellValueFactory(new PropertyValueFactory("createdDate"));
        col11.setPrefWidth(100);
        
        this.userTabView.getColumns().addAll(col1,col2,col3,col4,col5,col6,col7,col8,col9,col10,col11);
    }
}
