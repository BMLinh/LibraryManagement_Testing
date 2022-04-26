/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ou.librarymanagement;

import com.ou.pojo.Book;
import com.ou.pojo.OrderingBook;
import com.ou.pojo.ReaderCard;
import com.ou.pojo.User;
import com.ou.services.BookService;
import com.ou.services.OrderingBookService;
import com.ou.services.ReaderCardService;
import com.ou.utils.Utils;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class CheckingUserController implements Initializable {
    @FXML
    private TableView<OrderingBook> orderingBookTb;
    @FXML
    private TextField readerCardIdTxtFld;
    
    private static User currentStaff;
    
    private static final OrderingBookService orderingBookService = new OrderingBookService();
    private static final ReaderCardService readerCardService = new ReaderCardService();
    private static final BookService bookService = new BookService();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            init();
        } catch (SQLException ex) {
            Logger.getLogger(CheckingUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO
        loadTableView();
        loadData();
    }    
    
    private void init() throws SQLException{
        List<OrderingBook> list = this.orderingBookService.getOrderingBooks();
        list.forEach(r -> {
            if(Timestamp.valueOf(r.getExpiredDate()).before(new Timestamp(new Date().getTime())))
                try {
                    this.orderingBookService.updateActiveOrderBook(true, r.getId());
            } catch (SQLException ex) {
                Logger.getLogger(CheckingUserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
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
    
    public void loadData(){
        try {
            this.orderingBookTb.setItems(FXCollections.observableList(this.orderingBookService.findByActive(false)));
        } catch (SQLException ex) {
            Logger.getLogger(CheckingUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadTableView(){
        TableColumn t1 = new TableColumn("ID");
        t1.setCellValueFactory(new PropertyValueFactory("id"));
        t1.setPrefWidth(40);
        
        TableColumn t2 = new TableColumn("Book ID");
        t2.setCellValueFactory(new PropertyValueFactory("bookId"));
        t2.setPrefWidth(100);
        
        TableColumn t3 = new TableColumn("Reader Card ID");
        t3.setCellValueFactory(new PropertyValueFactory("readerCardId"));
        t3.setPrefWidth(100);
        
        TableColumn t4 = new TableColumn("Số lượng");
        t4.setCellValueFactory(new PropertyValueFactory("amount"));
        t4.setPrefWidth(100);
        
        TableColumn t5 = new TableColumn("Ngày tạo");
        t5.setCellValueFactory(new PropertyValueFactory("createdDate"));
        t5.setPrefWidth(200);
        
        TableColumn t6 = new TableColumn("Ngày đến hạn");
        t6.setCellValueFactory(new PropertyValueFactory("expiredDate"));
        t6.setPrefWidth(200);
        
        TableColumn t7 = new TableColumn("Active");
        t7.setCellValueFactory(new PropertyValueFactory("active"));
        t7.setPrefWidth(50);
        
        TableColumn<OrderingBook, Void> t8 = new TableColumn();
        t8.setSortable(true);
        Callback<TableColumn<OrderingBook, Void>,TableCell<OrderingBook, Void>> cellFactory = new Callback<TableColumn<OrderingBook, Void>, TableCell<OrderingBook, Void>>() {
            @Override
            public TableCell<OrderingBook, Void> call(TableColumn<OrderingBook, Void> p) {
                final TableCell<OrderingBook, Void> cell = new TableCell<>(){
                    private final Button btn = new Button("Xác nhận");
                    
                    {
                        btn.setOnAction((evt) -> {
                            TableCell c = (TableCell)((Button)evt.getSource()).getParent();
                            OrderingBook order = (OrderingBook) c.getTableRow().getItem();
                            try {
                                ReaderCard reader = readerCardService.findReaderCardById(order.getReaderCardId()).get(0);
                                Book book = bookService.getById(order.getBookId());

                                //Lấy tên event update order đã được định nghĩa trước, cú pháp order + id phiếu đặt ví dụ: order30
                                String nameEvent = "order" + order.getId();

                            if (orderingBookService.dropEventAutoUpdateOrder(nameEvent) == true){
                                    Map<String, String> param = new HashMap<>();
                                    param.put("amount", String.valueOf(book.getAmount() + order.getAmount()));
                                    orderingBookService.updateActiveOrderBook(true, order.getId());
                                    bookService.update(book.getId(), param);
                                    Utils.setAlert("Đã xác nhận đặt sách!!!", Alert.AlertType.INFORMATION).show();
                                }
                                else
                                    Utils.setAlert("Xác nhận không thành công!!!", Alert.AlertType.ERROR).show();
                            } catch (SQLException ex) {
                                Logger.getLogger(CheckingUserController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            loadData();
                        });
                    }

                    @Override
                    protected void updateItem(Void t, boolean bln) {
                        super.updateItem(t, bln);
                        if(bln)
                            setGraphic(null);
                        else
                            setGraphic(btn);
                    }
                };
                return cell;
            }
        };
        t8.setCellFactory(cellFactory);
        
        this.orderingBookTb.getColumns().addAll(t1,t2,t3,t4,t5,t6,t7,t8);
    }
    
    public void findOrderingBooks() throws SQLException{
        if(!this.orderingBookService.findByReaderCardId(Integer.parseInt(this.readerCardIdTxtFld.getText())).isEmpty()){
            this.orderingBookTb.setItems(FXCollections.observableList(this.orderingBookService.findByReaderCardId(Integer.parseInt(this.readerCardIdTxtFld.getText()))));
            this.readerCardIdTxtFld.clear();
        }
        else{
            Utils.setAlert("Không có dữ liệu!!!", Alert.AlertType.ERROR).show();
            loadData();
        }
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

// Sửa lại logic xác nhận đặt sách
