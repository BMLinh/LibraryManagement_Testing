/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou.librarymanagement;

import com.ou.pojo.BorrowingBook;
import com.ou.services.BookService;
import com.ou.services.BorrowingBookService;
import com.ou.services.ReaderCardService;
import com.ou.utils.Utils;
import static java.lang.Math.abs;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import static java.util.concurrent.TimeUnit.DAYS;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author Linh
 */
public class ReturnBooksController implements Initializable{
    @FXML
    private TableView tbBorrowingBook;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtReaderName;
    @FXML
    private TextField txtBookName;
    @FXML
    private TextField txtAmount;
    @FXML
    private TextField txtReaderId;
    @FXML
    private TextField txtKeyword;
    @FXML
    private TextField txtFine;
    @FXML
    private DatePicker dpCreatedDate;
    @FXML
    private DatePicker dpReturnDate;
    @FXML
    private Button btnReturnBook;
    
    private static final BorrowingBookService borrowingBookService = new BorrowingBookService();
    private static final ReaderCardService readerCard = new ReaderCardService();
    private static final BookService book = new BookService();
    private int bookId;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadTableView();
        loadData(null);
        this.btnReturnBook.setVisible(false);
        
        this.txtKeyword.textProperty().addListener((evt) ->{
            this.loadData(this.txtKeyword.getText());
        });
        
        this.tbBorrowingBook.setRowFactory(et ->{
            TableRow row = new TableRow();
            row.setOnMouseClicked(r ->{
                BorrowingBook brw = (BorrowingBook)this.tbBorrowingBook.getSelectionModel().getSelectedItem();
                this.txtId.setText(String.valueOf(brw.getId()));
                this.txtBookName.setText(brw.getBookName());
                this.txtAmount.setText(String.valueOf(brw.getAmount()));
                this.txtReaderName.setText(brw.getUserName());
                this.txtReaderId.setText(String.valueOf(brw.getReaderCardId()));
                this.dpCreatedDate.setValue(Utils.convertUtilToSql(brw.getCreatedDate()).toLocalDate());
                this.dpReturnDate.setValue(Utils.convertUtilToSql(brw.getReturnDate()).toLocalDate());
                this.bookId = brw.getBookId();
                this.btnReturnBook.setVisible(true);
                int Days = (int)ChronoUnit.DAYS.between(LocalDate.now(), this.dpReturnDate.getValue());
                if ( Days < 0)
                    this.txtFine.setText(String.valueOf(abs(Days * 5000)));
                else
                    this.txtFine.setText("0");
            });
            return row;
        });
    }    
    
    private void loadData(String kw){
        try {
            this.tbBorrowingBook.setItems(FXCollections.observableList(borrowingBookService.getBorrowingBooksByActive(false, kw)));
        } catch (SQLException ex) {
            Logger.getLogger(BorrowBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadTableView(){
        TableColumn t1 = new TableColumn("ID");
        t1.setCellValueFactory(new PropertyValueFactory("id"));
        t1.setPrefWidth(40);
        
        TableColumn t2 = new TableColumn("Tên người mượn");
        t2.setCellValueFactory(new PropertyValueFactory("userName"));
        t2.setPrefWidth(200);
        
        TableColumn t3 = new TableColumn("Mã độc giả");
        t3.setCellValueFactory(new PropertyValueFactory("readerCardId"));
        t3.setPrefWidth(100);
        
        TableColumn t4 = new TableColumn("Tên sách");
        t4.setCellValueFactory(new PropertyValueFactory("bookName"));
        t4.setPrefWidth(200);       
        
        TableColumn t5 = new TableColumn("Số lượng");
        t5.setCellValueFactory(new PropertyValueFactory("amount"));
        t5.setPrefWidth(100);
        
        TableColumn t6 = new TableColumn("Ngày tạo");
        t6.setCellValueFactory(new PropertyValueFactory("createdDate"));
        t6.setPrefWidth(100);
        
        TableColumn t7 = new TableColumn("Ngày trả");
        t7.setCellValueFactory(new PropertyValueFactory("returnDate"));
        t7.setPrefWidth(100);
        
        this.tbBorrowingBook.getColumns().addAll(t1,t2,t3,t4,t5,t6,t7);
    }
    
    public void reset(){
        this.txtAmount.clear();
        this.txtBookName.clear();
        this.txtId.clear();
        this.txtReaderId.clear();
        this.txtReaderName.clear();
        this.txtFine.clear();
        this.dpCreatedDate.setValue(null);
        this.dpReturnDate.setValue(null);
        this.btnReturnBook.setVisible(false);
    }
    
    public void resetHandler(ActionEvent evt){
        reset();
    }
    
    public void returnBook(ActionEvent evt) throws SQLException {
        Alert alert = Utils.setAlert("Hoàn tất phiếu mượn sách Id = \"" + this.txtId.getText() + "\"?", Alert.AlertType.CONFIRMATION);
        alert.setTitle("TRẢ SÁCH");
	alert.setHeaderText("Xác nhận hoàn tất việc trả \"" + this.txtAmount.getText() +"\" cuốn sách: \"" + this.txtBookName.getText() +"\"?");
        if (alert.showAndWait().get() == ButtonType.OK){
            borrowingBookService.updateReturnBook(Integer.parseInt(this.txtId.getText()), 1, Integer.parseInt(this.txtFine.getText()), Date.valueOf(LocalDate.now()));
            readerCard.updateAmount(readerCard.findReaderCardById(Integer.parseInt(this.txtReaderId.getText())).get(0).getAmount() - Integer.parseInt(this.txtAmount.getText()), Integer.parseInt(this.txtReaderId.getText()));
            book.updateAmount((book.getById(bookId).getAmount() + Integer.parseInt(this.txtAmount.getText())), bookId);
            Utils.setAlert("Cập nhật trả sách thành công phiếu mượn sách Id = \"" + this.txtId.getText() + "\"", Alert.AlertType.INFORMATION).show();
            reset();
            loadData(this.txtKeyword.getText());
        }    
    }

}
