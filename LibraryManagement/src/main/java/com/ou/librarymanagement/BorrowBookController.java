/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ou.librarymanagement;

import com.ou.pojo.Book;
import com.ou.pojo.BorrowingBook;
import com.ou.pojo.ReaderCard;
import com.ou.pojo.User;
import com.ou.services.AuthorService;
import com.ou.services.BookCategoryService;
import com.ou.services.BookService;
import com.ou.services.BorrowingBookService;
import com.ou.services.PublishingCompanyService;
import com.ou.services.ReaderCardService;
import com.ou.services.UserService;
import com.ou.utils.Utils;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

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
    private TextField readerCardIdTxtFld;
    @FXML
    private TextField userIdTxtFld;

    private static User currentUser;
    private static ReaderCard currentCard;
    private static User currentStaff;
    
    private int amount;

    private static final BookService bookService = new BookService();
    private static final UserService userService = new UserService();
    private static final ReaderCardService readerCardService = new ReaderCardService();
    private static final BookCategoryService bookCategoryService = new BookCategoryService();
    private static final PublishingCompanyService publishingCompanyService = new PublishingCompanyService();
    private static final AuthorService authorService = new AuthorService();
    private static final BorrowingBookService borrowingBookService = new BorrowingBookService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadTableView();
        loadData(null);
        this.userIdTxtFld.setText(String.valueOf(getCurrentUser().getId()));
        this.readerCardIdTxtFld.setText(String.valueOf(getCurrentCard().getId()));

        this.bookTableView.setRowFactory(et -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(r -> {
                Book book = (Book) this.bookTableView.getSelectionModel().getSelectedItem();
                this.bookIdTxtFld.setText(String.valueOf(book.getId()));
                this.bookNameTxtFld.setText(book.getName());
                this.bookDescriptionTxtFld.setText(book.getDescription());
                this.bookPublishedDateTxtFld.setText(Utils.convertDateToString(book.getPublishingYear()));
                try {
                    this.bookCateTxtFld.setText(this.bookCategoryService.getBookCategoryById(book.getCategoryId()).getName());
                    this.bookPublisherTxtFld.setText(this.publishingCompanyService.getPublishingCompanyById(book.getPublishingCompanyId()).getName());
                    this.bookAuthorTxtFld.setText(this.authorService.getById(book.getAuthorId()).getName());
                } catch (SQLException ex) {
                    Logger.getLogger(BorrowBookController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            return row;
        });
    }

    private void loadData(String kw) {
        try {
            this.bookTableView.setItems(FXCollections.observableList(this.bookService.getBooks(kw)));
        } catch (SQLException ex) {
            Logger.getLogger(BorrowBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadTableView() {
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

        this.bookTableView.getColumns().addAll(t1, t2, t3, t4, t5, t6, t7);

    }
    
    public void reset() {
        this.bookIdTxtFld.clear();
        this.bookNameTxtFld.clear();
        this.bookDescriptionTxtFld.clear();
        this.bookPublishedDateTxtFld.clear();
        this.bookCateTxtFld.clear();
        this.bookPublisherTxtFld.clear();
        this.bookAuthorTxtFld.clear();
        loadData(null);
    }

    public void findBook(ActionEvent evt) throws SQLException {
        if (this.bookService.getBooks(this.searchContentTxtFld.getText().trim()).size() > 0) {
            reset();
            loadData(this.searchContentTxtFld.getText().trim());
        } else {
            Utils.setAlert("Không có dữ liệu!!!", Alert.AlertType.ERROR).show();
        }
    }

     public void borrowBook(ActionEvent evt) throws IOException {
        int tmp = 0;
        if(null != this.bookTableView.getSelectionModel().getSelectedItem()){
            if(0 == this.bookTableView.getSelectionModel().getSelectedItem().getAmount())
                Utils.setAlert("Hết sách!!!", Alert.AlertType.ERROR).show();
            else if(this.userIdTxtFld.getText().isBlank())
                Utils.setAlert("Chưa kiểm tra thẻ!!!", Alert.AlertType.ERROR).show();
            else {
                TextInputDialog inp = new TextInputDialog();
                inp.setHeaderText("Số lượng sách");
                Optional<String> num = inp.showAndWait();
                if (num.isPresent()){
                    try{
                        tmp = amount + Integer.parseInt(num.get());
                    } catch (Exception ex){
                        Utils.setAlert("Sai định dạng. Mời bạn nhập số!", Alert.AlertType.ERROR).show();
                    }
                }
                if (Integer.parseInt(num.get()) < 1){
                    Utils.setAlert("Số lượng sách đặt phải lớn hơn 0!", Alert.AlertType.ERROR).show();
                }
                else if (Integer.parseInt(num.get()) > 5){
                    Utils.setAlert("Không được mượn hơn 5 quyển sách!", Alert.AlertType.ERROR).show();
                }
                else if(Integer.parseInt(num.get()) > this.bookTableView.getSelectionModel().getSelectedItem().getAmount()){
                    Utils.setAlert("Không được mượn quá số lượng sách hiện tại!",Alert.AlertType.ERROR).show();
                }
                else if(tmp > 5){
                    Utils.setAlert("Không được đặt quá 5 cuốn sách!!!", Alert.AlertType.ERROR).show();
                }
                else {
                    try {
                        Date currentDate = new Date();
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(currentDate);
                        cal.add(Calendar.DATE, 30);
                        Date returnDate = cal.getTime();
                        amount += Integer.parseInt(num.get());
                        Map<String, String> param = new HashMap<>();
                        Book book = this.bookTableView.getSelectionModel().getSelectedItem();
                        ReaderCard readerCard = this.currentCard;
                        readerCard.setAmount(amount);
                        User staff = this.currentStaff;

                        BorrowingBook bw = new BorrowingBook(0, staff.getId(), book.getId(),
                                readerCard.getId(), Integer.parseInt(num.get()), currentDate, returnDate, 0, new BigDecimal(0));
                        this.borrowingBookService.addBorrowingBook(bw);

                        param.put("amount", String.valueOf(book.getAmount() - Integer.parseInt(num.get())));
                        this.bookService.update(this.bookTableView.getSelectionModel().getSelectedItem().getId(), param);

                        this.readerCardService.updateReaderCard(readerCard.getId(), readerCard);
                    } catch (SQLException ex) {
                        Logger.getLogger(BorrowBookController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Utils.setAlert("Đặt thành công!!!", Alert.AlertType.CONFIRMATION).show();
                    loadData(null);
                }
            }
        }
        else
            Utils.setAlert("Mời bạn chọn sách muốn mượn!!!", Alert.AlertType.ERROR).show();
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
