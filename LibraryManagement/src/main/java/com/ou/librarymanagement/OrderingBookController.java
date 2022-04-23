package com.ou.librarymanagement;

import com.ou.pojo.Book;
import com.ou.services.AuthorService;
import com.ou.services.BookCategoryService;
import com.ou.services.BookService;
import com.ou.services.PublishingCompanyService;
import com.ou.utils.Utils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderingBookController implements Initializable {
    @FXML
    private TableView tbBook;
    @FXML
    private TextField txtBookId;
    @FXML
    private TextField txtBookName;
    @FXML
    private TextField txtBookDescription;
    @FXML
    private TextField txtAmount;
    @FXML
    private TextField txtBookPublisher;
    @FXML
    private TextField txtBookCate;
    @FXML
    private TextField txtBookAuthor;
    @FXML
    private TextField txtSearchContent;

    private static int userId;
    private static int cardId;


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

        this.tbBook.setRowFactory(et ->{
            TableRow row = new TableRow();
            row.setOnMouseClicked(r ->{
                Book book = (Book)this.tbBook.getSelectionModel().getSelectedItem();
                this.txtBookId.setText(String.valueOf(book.getId()));
                this.txtBookName.setText(book.getName());
                this.txtBookDescription.setText(book.getDescription());
                this.txtAmount.setText(String.valueOf(book.getAmount()));
                try {
                    this.txtBookCate.setText(this.bookCategoryService.getBookCategoryById(book.getCategoryId()).getName());
                    this.txtBookPublisher.setText(this.publishingCompanyService.getPublishingCompanyById(book.getPublishingCompanyId()).getName());
                    this.txtBookAuthor.setText(this.authorService.getById(book.getAuthorId()).getName());
                } catch (SQLException ex) {
                    Logger.getLogger(BorrowBookController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            return row;
        });
    }

    private void loadData(String kw){
        try {
            this.tbBook.setItems(FXCollections.observableList(this.bookService.getBooks(kw)));
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

        this.tbBook.getColumns().addAll(t1,t2,t3,t4,t5,t6,t7);
    }

    public void reset(){
        this.txtBookId.clear();
        this.txtBookName.clear();
        this.txtBookDescription.clear();
        this.txtAmount.clear();
        this.txtBookCate.clear();
        this.txtBookPublisher.clear();
        this.txtBookAuthor.clear();
        loadData(null);
    }

    public void findBook(ActionEvent evt) throws SQLException{
        if(this.bookService.getBooks(this.txtSearchContent.getText()).size() > 0){
            reset();
            loadData(this.txtSearchContent.getText());
        }
        else Utils.setAlert("Không có dữ liệu!!!", Alert.AlertType.ERROR).show();
    }

    public void orderBook(ActionEvent evt) throws IOException {
        int i = Integer.parseInt(this.txtBookId.getText());
    }
}
