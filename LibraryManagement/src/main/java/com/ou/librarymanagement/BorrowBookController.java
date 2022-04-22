/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ou.librarymanagement;

import com.ou.services.BookService;
import java.net.URL;
import java.sql.SQLException;
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
public class BorrowBookController implements Initializable {
    @FXML
    private TableView bookTableView;
    
    
    private static final BookService bookService = new BookService();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadTableView();
        loadData();
    }    
    
    private void loadData(){
        try {
            this.bookTableView.setItems(FXCollections.observableList(this.bookService.getBooks()));
        } catch (SQLException ex) {
            Logger.getLogger(BorrowBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadTableView(){
        TableColumn t1 = new TableColumn("ID");
        t1.setCellValueFactory(new PropertyValueFactory("id"));
        t1.setPrefWidth(40);
        
        TableColumn t2 = new TableColumn("Tên sách");
        t2.setCellValueFactory(new PropertyValueFactory("name"));
        t2.setPrefWidth(100);
        
        TableColumn t3 = new TableColumn("Mô tả");
        t3.setCellValueFactory(new PropertyValueFactory("description"));
        t3.setPrefWidth(200);
        
        TableColumn t4 = new TableColumn("Năm xuất bản");
        t4.setCellValueFactory(new PropertyValueFactory("publishing_year"));
        t4.setPrefWidth(100);
        
        TableColumn t5 = new TableColumn("Nhà xuất bản");
        t5.setCellValueFactory(new PropertyValueFactory("publishing_company_id"));
        t5.setPrefWidth(100);
        
        TableColumn t6 = new TableColumn("Danh mục");
        t6.setCellValueFactory(new PropertyValueFactory("category_id"));
        t6.setPrefWidth(100);
        
        this.bookTableView.getColumns().addAll(t1,t2,t3,t4,t5,t6);
    }
}
