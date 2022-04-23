package com.ou.librarymanagement;

import com.ou.pojo.Book;
import com.ou.pojo.OrderingBook;
import com.ou.pojo.ReaderCard;
import com.ou.pojo.User;
import com.ou.services.*;
import com.ou.utils.Utils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
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

    private static User currentUser;
    private static ReaderCard currentCard;


    private static final BookService bookService = new BookService();
    private static final BookCategoryService bookCategoryService = new BookCategoryService();
    private static final PublishingCompanyService publishingCompanyService = new PublishingCompanyService();
    private static final AuthorService authorService = new AuthorService();
    private static final OrderingBookService orderingBookService = new OrderingBookService();
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

    public void orderBook(ActionEvent evt) throws SQLException {
        int amountOfBook = 0;
        Date currentDate = new Date();
        if (getCurrentCard() == null){
            Utils.setAlert("Bạn chưa có thẻ độc giả. Vui lòng làm thẻ để có thể đặt sách!", Alert.AlertType.ERROR).show();
        }
        else if (Integer.parseInt(this.txtAmount.getText()) == 0){
            Utils.setAlert("Sách đã được mượn hết!", Alert.AlertType.ERROR).show();
        }
        else if (Utils.convertDateToString(currentDate).compareTo(Utils.convertDateToString(getCurrentCard().getEndDate())) > 0){
            Utils.setAlert("Thẻ độc giả đã hết hạn!", Alert.AlertType.ERROR).show();
        }
        else if (getCurrentCard().getAmount() != 0){
            Utils.setAlert("Người dùng chưa trả hết sách. Vui lòng trả hết sách trước khi đặt!", Alert.AlertType.ERROR).show();
        }
        else {
            TextInputDialog inp = new TextInputDialog();
            inp.setHeaderText("Nhập số lượng sách cần đặt");
            Optional<String> num = inp.showAndWait();
            if (num.isPresent()){
                try{
                    amountOfBook = Integer.parseInt(num.get());
                } catch (Exception ex){
                    Utils.setAlert("Sai định dạng. Mời bạn nhập số!", Alert.AlertType.ERROR).show();
                }
            }
            if (amountOfBook > 5){
                Utils.setAlert("Bạn không được mướn hơn 5 quyển sách!", Alert.AlertType.ERROR).show();
            }
            else {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                calendar.roll(Calendar.DATE, 2);
                Date expiredDate = calendar.getTime();
                OrderingBook oderBook = new OrderingBook();
                oderBook.setBookId(Integer.parseInt(txtBookId.getText()));
                oderBook.setReaderCardId(this.getCurrentCard().getId());
                oderBook.setAmount(amountOfBook);
                oderBook.setCreatedDate(currentDate);
                oderBook.setExpiredDate(expiredDate);
                try {
                    if (orderingBookService.addOrderBook(oderBook) == true){
                        Utils.setAlert("Đặt sách thành công!", Alert.AlertType.INFORMATION).show();
                    }
                    else
                        Utils.setAlert("Đặt sách thất bại!", Alert.AlertType.ERROR).show();
                }
                catch (SQLException ex){
                    ex.printStackTrace();
                }
            }

        }

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
