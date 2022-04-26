/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ou.librarymanagement;

import com.ou.pojo.*;
import com.ou.services.BookService;
import com.ou.services.BorrowingBookService;
import com.ou.services.OrderingBookService;
import com.ou.services.ReaderCardService;
import com.ou.utils.Utils;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    private TextField txtNameBook;
    @FXML
    private TextField txtAmout;
    @FXML
    private TextField txtCreatedDate;
    @FXML
    private TextField txtExpiredDate;

    private static User currentStaff;
    private static User currentUser;
    private static ReaderCard currentCard;
    
    private static final OrderingBookService orderingBookService = new OrderingBookService();
    private static final ReaderCardService readerCardService = new ReaderCardService();
    private static final BookService bookService = new BookService();
    private static final BorrowingBookService borrowingBookService = new BorrowingBookService();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadTableView();
        loadData();
        this.txtNameBook.setEditable(false);
        this.txtAmout.setEditable(false);
        this.txtCreatedDate.setEditable(false);
        this.txtExpiredDate.setEditable(false);

        //Chọn 1 dòng trên TableView đổ dữ liệu lên các controls
        this.orderingBookTb.setRowFactory(et -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(r -> {
                OrderingBook orderB = (OrderingBook) this.orderingBookTb.getSelectionModel().getSelectedItem();
                try {
                    this.txtNameBook.setText(bookService.getById(orderB.getBookId()).getName());
                    this.txtAmout.setText(String.valueOf(orderB.getAmount()));
                    this.txtCreatedDate.setText(String.valueOf(orderB.getCreatedDate()));
                    this.txtExpiredDate.setText(String.valueOf(orderB.getExpiredDate()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            return row;
        });
    }
    
    public void loadData(){
        try {
            this.orderingBookTb.setItems(FXCollections.observableList(this.orderingBookService.findByActive(false, currentCard.getId())));
        } catch (SQLException ex) {
            Logger.getLogger(CheckingUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void reset(){
        this.txtNameBook.setText("");
        this.txtAmout.setText("");
        this.txtCreatedDate.setText("");
        this.txtExpiredDate.setText("");
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
                                Book book = bookService.getById(order.getBookId());

                                //Lấy tên event update order đã được định nghĩa trước, cú pháp order + id phiếu đặt ví dụ: order30
                                String nameEvent = "order" + order.getId();

                            if (orderingBookService.dropEventAutoUpdateOrder(nameEvent) == true){
                                    Date currentDate = new Date();
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(currentDate);
                                    cal.add(Calendar.DATE, 30);
                                    Date returnDate = cal.getTime();
                                    currentCard.setAmount(order.getAmount());
                                    BorrowingBook bw = new BorrowingBook(0, currentStaff.getId(), book.getId(),
                                            currentCard.getId(), order.getAmount(), currentDate, returnDate, 0, new BigDecimal(0));
                                    borrowingBookService.addBorrowingBook(bw);
                                    readerCardService.updateReaderCard(currentCard.getId(), currentCard);
                                    orderingBookService.updateActiveOrderBook(true, order.getId());
                                    Utils.setAlert("Đã xác nhận đặt sách. Đã tạo phiếu mượn!!!", Alert.AlertType.INFORMATION).show();
                                }
                                else
                                    Utils.setAlert("Xác nhận không thành công!!!", Alert.AlertType.ERROR).show();
                            } catch (SQLException ex) {
                                Logger.getLogger(CheckingUserController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            loadData();
                            reset();
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
}

// Sửa lại logic xác nhận đặt sách
