package com.ou.librarymanagement;

import com.ou.pojo.Author;
import com.ou.pojo.Book;
import com.ou.pojo.BookCategory;
import com.ou.pojo.PublishingCompany;
import com.ou.services.AuthorService;
import com.ou.services.BookCategoryService;
import com.ou.services.BookService;
import com.ou.services.PublishingCompanyService;
import com.ou.utils.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookController implements Initializable {
    @FXML
    private TableView<Book> bookTabView;
    @FXML
    private ComboBox<Author> cbAuthor;
    @FXML
    private ComboBox<BookCategory> cbCategory;
    @FXML
    private ComboBox<PublishingCompany> cbPublishingCompany;
    @FXML
    private DatePicker datepickerDateOfEntering;
    @FXML
    private TextField searchKw;
    @FXML
    private TextField txtAmount;
    @FXML
    private TextField txtBookName;
    @FXML
    private TextArea txtDescription;
    @FXML
    private DatePicker datepickerPublishingYear;
    @FXML
    private Button btnBack;

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private static final BookService bookService = new BookService();
    private static final AuthorService authorService = new AuthorService();
    private static final BookCategoryService bookCategoryService = new BookCategoryService();
    private static final PublishingCompanyService publishingCompanyService = new PublishingCompanyService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtDescription.setWrapText(true);
        try {
            // TODO
            this.init();
        } catch (SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.loadTableView();
        this.loadData();
        Calendar calendar = Calendar.getInstance();

        this.bookTabView.setRowFactory(et -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(r -> {
                Book book = (Book) this.bookTabView.getSelectionModel().getSelectedItem();
                this.txtBookName.setText(String.valueOf(book.getName()));
                this.txtAmount.setText(String.valueOf(book.getAmount()));
                this.txtDescription.setText(book.getDescription());

                if (book.getPublishingYear() != null) {
                    calendar.setTime(book.getPublishingYear());
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    this.datepickerPublishingYear.setValue(LocalDate.of(year, month + 1, day));
                }

                if (book.getDateOfEntering() != null) {
                     calendar.setTime(book.getDateOfEntering());
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH) + 1;
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    this.datepickerDateOfEntering.setValue(LocalDate.of(year, month, day));
                }

                try {
                    this.cbAuthor.getSelectionModel().select(authorService.getById(book.getAuthorId()));
                    this.cbCategory.getSelectionModel().select(bookCategoryService.getBookCategoryById(book.getCategoryId()));
                    this.cbPublishingCompany.getSelectionModel().select(publishingCompanyService.getPublishingCompanyById(book.getPublishingCompanyId()));
                } catch (SQLException ex) {
                    Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            return row;
        });
    }

    public void loadTableView() {
        TableColumn col1 = new TableColumn("Id");
        col1.setCellValueFactory(new PropertyValueFactory("id"));
        col1.setPrefWidth(40);
        col1.setStyle("-fx-alignment: CENTER;");

        TableColumn col2 = new TableColumn("Book name");
        col2.setCellValueFactory(new PropertyValueFactory("name"));
        col2.setPrefWidth(200);

        TableColumn col3 = new TableColumn("Amount");
        col3.setCellValueFactory(new PropertyValueFactory("amount"));
        col3.setPrefWidth(80);
        col3.setStyle("-fx-alignment: CENTER;");

        TableColumn col4 = new TableColumn("Description");
        col4.setCellValueFactory(new PropertyValueFactory("description"));
        col4.setPrefWidth(210);

        TableColumn col5 = new TableColumn("Ngày xuất bản");
        col5.setCellValueFactory(new PropertyValueFactory("publishingYear"));
        col5.setPrefWidth(100);
        col5.setStyle("-fx-alignment: CENTER;");

        TableColumn col6 = new TableColumn("Ngày nhập");
        col6.setCellValueFactory(new PropertyValueFactory("dateOfEntering"));
        col6.setPrefWidth(100);
        col6.setStyle("-fx-alignment: CENTER;");

        TableColumn col7 = new TableColumn("Category Id");
        col7.setCellValueFactory(new PropertyValueFactory("categoryId"));
        col7.setPrefWidth(80);
        col7.setStyle("-fx-alignment: CENTER;");

        TableColumn col8 = new TableColumn("Publishing Company Id");
        col8.setCellValueFactory(new PropertyValueFactory("publishingCompanyId"));
        col8.setPrefWidth(160);
        col8.setStyle("-fx-alignment: CENTER;");

        TableColumn col9 = new TableColumn("Author Id");
        col9.setCellValueFactory(new PropertyValueFactory("authorId"));
        col9.setPrefWidth(70);
        col9.setStyle("-fx-alignment: CENTER;");

        this.bookTabView.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7, col8, col9);
    }

    public void loadData() {
        try {
            this.bookTabView.setItems(FXCollections.observableList(bookService.getBooks("")));
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void init() throws SQLException {
        this.txtAmount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (!t1.matches("\\d*"))
                    txtAmount.setText(t1.replaceAll("[^\\d]", ""));
                if (txtAmount.getText().length() > 12) {
                    String s = txtAmount.getText().substring(0, 12);
                    txtAmount.setText(s);
                }
            }
        });
        this.cbCategory.setItems(FXCollections.observableList(bookCategoryService.getBookCategory(null)));
        this.cbAuthor.setItems(FXCollections.observableList(authorService.getAuthors(null)));
        this.cbPublishingCompany.setItems(FXCollections.observableList(publishingCompanyService.getPublishingCompanys(null)));
    }

    public void reset() throws SQLException {
        this.txtBookName.clear();
        this.txtAmount.clear();
        this.txtDescription.clear();
        this.datepickerPublishingYear.setValue(null);
        this.datepickerDateOfEntering.setValue(null);
        this.cbCategory.getSelectionModel().select(null);
        this.cbPublishingCompany.getSelectionModel().select(null);
        this.cbAuthor.getSelectionModel().select(null);
        this.searchKw.clear();
    }

    private void reloadWindow() throws SQLException {
        reset();
        loadData();
    }

    private boolean checkValidForm() {
        if (txtBookName.getText() == null || txtBookName.getText().trim().equals(""))
            return false;

        if (cbCategory.getSelectionModel().getSelectedItem() == null)
            return false;
        if (cbPublishingCompany.getSelectionModel().getSelectedItem() == null)
            return false;
        if (cbAuthor.getSelectionModel().getSelectedItem() == null)
            return false;

        return true;
    }

    private Book getBookFromForm() throws ParseException {
        Book book = new Book();

        if (!txtBookName.getText().trim().isEmpty())
            book.setName(this.txtBookName.getText());

        if (!txtAmount.getText().trim().isEmpty())
            book.setAmount(Integer.parseInt(this.txtAmount.getText()));
        else
            book.setAmount(0);

        book.setDescription(this.txtDescription.getText());

        if (this.datepickerPublishingYear.getValue() != null)
            book.setPublishingYear(format.parse(String.valueOf(this.datepickerPublishingYear.getValue())));

        if (this.datepickerDateOfEntering.getValue() != null)
            book.setDateOfEntering(format.parse(String.valueOf(this.datepickerDateOfEntering.getValue())));

        if (this.cbCategory.getSelectionModel().getSelectedItem() != null)
            book.setCategoryId(this.cbCategory.getSelectionModel().getSelectedItem().getId());

        if (this.cbPublishingCompany.getSelectionModel().getSelectedItem() != null)
            book.setPublishingCompanyId(this.cbPublishingCompany.getSelectionModel().getSelectedItem().getId());

        if (this.cbAuthor.getSelectionModel().getSelectedItem() != null)
            book.setAuthorId(this.cbAuthor.getSelectionModel().getSelectedItem().getId());

        return book;
    }

    @FXML
    void add(ActionEvent event) throws SQLException, ParseException {
        Book book = this.getBookFromForm();
        if (checkValidForm()) {
            if (bookService.add(book)) {
                reloadWindow();
            } else {
                Utils.setAlert("Thêm thất bại!!!", Alert.AlertType.ERROR).show();
            }
        } else {
            Utils.setAlert("Thông tin sách không hợp lệ!!!", Alert.AlertType.ERROR).show();
        }
    }

    @FXML
    void update(ActionEvent event) throws ParseException, SQLException {
        if (bookTabView.getSelectionModel().getSelectedItem() != null) {
            Book book = this.getBookFromForm();
            if (checkValidForm()) {
                if (bookService.update(bookTabView.getSelectionModel().getSelectedItem().getId(), book)) {
                    reloadWindow();
                } else {
                    Utils.setAlert("Sửa thất bại!!!", Alert.AlertType.ERROR).show();
                }
            } else {
                Utils.setAlert("Thông tin sách không hợp lệ!!!", Alert.AlertType.ERROR).show();
            }
        }
    }

    @FXML
    void delete(ActionEvent event) throws SQLException {
        if (bookTabView.getSelectionModel().getSelectedItem() != null) {
            bookService.delete(this.bookTabView.getSelectionModel().getSelectedItem().getId());
            reloadWindow();
        }
    }

    @FXML
    void refresh(ActionEvent event) throws SQLException {
        reloadWindow();
    }

    @FXML
    void searchBooks(KeyEvent event) throws SQLException {
        System.out.println(searchKw.getText());
        if (bookService.getBooks(this.searchKw.getText().trim().toLowerCase()).size() > 0) {
            this.bookTabView.setItems(FXCollections.observableList(bookService.getBooks(this.searchKw.getText())));
        }
    }

    @FXML
    void backToAdmin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXMLHome-Adm.fxml"));
        Parent root = loader.load();
        Scene mainScene = new Scene(root);
        Stage primaryStage = (Stage) btnBack.getScene().getWindow();
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

}
