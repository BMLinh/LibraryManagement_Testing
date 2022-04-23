/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ou.librarymanagement;

import com.ou.pojo.Book;
import com.ou.pojo.ReaderCard;
import com.ou.pojo.User;
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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class BorrowBookController implements Initializable {
    @FXML
    private TableView<Book> bookTableView;
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
    @FXML
    private TextField userNameTxtFld;
    @FXML
    private TextField userIdTxtFld;
    @FXML
    private Button submitBtn;
    
    private User staff;
    private User user;
    private ReaderCard readerCard;
     
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
            this.userNameTxtFld.setText(user.getFullname());
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
        t2.setPrefWidth(400);
        
        TableColumn t3 = new TableColumn("Số lượng");
        t3.setCellValueFactory(new PropertyValueFactory("amount"));
        t3.setPrefWidth(100);
        
        TableColumn t4 = new TableColumn("Năm xuất bản");
        t4.setCellValueFactory(new PropertyValueFactory("publishingYear"));
        t4.setPrefWidth(200);
        
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
        if(0 == this.bookTableView.getSelectionModel().getSelectedItem().getAmount())
            Utils.setAlert("Hết sách!!!", Alert.AlertType.ERROR).show();
        else{            
            
        }
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
}
