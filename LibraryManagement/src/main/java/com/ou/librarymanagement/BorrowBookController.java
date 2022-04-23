/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ou.librarymanagement;

import com.ou.pojo.Book;
import com.ou.services.AuthorService;
import com.ou.services.BookCategoryService;
import com.ou.services.BookService;
import com.ou.services.PublishingCompanyService;
import com.ou.utils.Utils;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class BorrowBookController implements Initializable {
    @FXML
    private TableView bookTableView;
    @FXML
    private TextField bookIdTxtFld;
    @FXML
    private TextField bookNameTxtFld;
    @FXML
    private TextField bookDescriptionTxtFld;
    @FXML
    private TextField bookPublishedDateTxtFld;
    @FXML
    private TextField bookPublisherTxtFld;
    @FXML
    private TextField bookCateTxtFld;
    @FXML
    private TextField bookAuthorTxtFld;
    @FXML
    private TextField searchContentTxtFld;
    
    
    private static final BookService bookService = new BookService();
    private static final BookCategoryService bookCategoryService = new BookCategoryService();
    private static final PublishingCompanyService publishingCompanyService = new PublishingCompanyService();
    private static final AuthorService authorService = new AuthorService();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadTableView();
        loadData(null);
        
        this.bookTableView.setRowFactory(et ->{
            TableRow row = new TableRow();
            row.setOnMouseClicked(r ->{
                Book book = (Book)this.bookTableView.getSelectionModel().getSelectedItem();
                this.bookIdTxtFld.setText(String.valueOf(book.getId()));
                this.bookNameTxtFld.setText(book.getName());
                this.bookDescriptionTxtFld.setText(book.getDescription());
                this.bookPublishedDateTxtFld.setText(Utils.convertDateToString(book.getPublishingYear()));
                try {
                    this.bookCateTxtFld.setText(this.bookCategoryService.getBookCategoryById(String.valueOf(book.getCategoryId())).getName());
                    this.bookPublisherTxtFld.setText(this.publishingCompanyService.getPublishingCompanyById(String.valueOf(book.getPublishingCompanyId())).getName());
                    this.bookAuthorTxtFld.setText(this.authorService.getById(book.getAuthorId()).getName());
                } catch (SQLException ex) {
                    Logger.getLogger(BorrowBookController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            return row;
        });
    }    
    
    private void loadData(String kw){
        try {
            this.bookTableView.setItems(FXCollections.observableList(this.bookService.getBooks(kw)));
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
        t2.setPrefWidth(200);
        
        TableColumn t3 = new TableColumn("Mô tả");
        t3.setCellValueFactory(new PropertyValueFactory("description"));
        t3.setPrefWidth(350);
        
        TableColumn t4 = new TableColumn("Năm xuất bản");
        t4.setCellValueFactory(new PropertyValueFactory("publishingYear"));
        t4.setPrefWidth(100);
        
        TableColumn t5 = new TableColumn("Nhà xuất bản");
        t5.setCellValueFactory(new PropertyValueFactory("publishingCompanyId"));
        t5.setPrefWidth(100);
        
        TableColumn t6 = new TableColumn("Tác giả");
        t6.setCellValueFactory(new PropertyValueFactory("authorId"));
        t6.setPrefWidth(100);
        
        TableColumn t7 = new TableColumn("Danh mục");
        t7.setCellValueFactory(new PropertyValueFactory("categoryId"));
        t7.setPrefWidth(100);
        
        this.bookTableView.getColumns().addAll(t1,t2,t3,t4,t5,t6,t7);
    }
    
    public void reset(){
        this.bookIdTxtFld.clear();
        this.bookNameTxtFld.clear();
        this.bookDescriptionTxtFld.clear();
        this.bookPublishedDateTxtFld.clear();
        this.bookCateTxtFld.clear();
        this.bookPublisherTxtFld.clear();
        this.bookAuthorTxtFld.clear();
        loadData(null);
    }
    
    public void findBook(ActionEvent evt) throws SQLException{
        if(this.bookService.getBooks(this.searchContentTxtFld.getText()).size() > 0){
            reset();
            loadData(this.searchContentTxtFld.getText());
        }
        else Utils.setAlert("Không có dữ liệu!!!", Alert.AlertType.ERROR).show();
    }
    
    public void borrowBook(ActionEvent evt) throws IOException {
        int i = Integer.parseInt(this.bookIdTxtFld.getText());
    }
}
