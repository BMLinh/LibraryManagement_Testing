package com.ou.librarymanagement;

import com.ou.pojo.Author;
import com.ou.services.AuthorService;
import com.ou.utils.Utils;
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthorController implements Initializable {

    @FXML
    private TableView<Author> authorTabView;

    @FXML
    private Button btnBack;

    @FXML
    private TextField searchKw;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    private static final AuthorService authorService = new AuthorService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.loadTableView();
        this.loadData();
        this.txtId.setEditable(false);

        this.txtName.setTextFormatter(new TextFormatter<>(change -> {
            if(!change.getText().matches("[aA-zZ\\p{L} ]+$"))
                change.setText("");
            return change;
        }));

        this.authorTabView.setRowFactory(et -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(r -> {
                Author author = (Author) this.authorTabView.getSelectionModel().getSelectedItem();
                this.txtId.setText(String.valueOf(author.getId()));
                this.txtName.setText(String.valueOf(author.getName()));
            });
            return row;
        });
    }

    public void loadTableView() {
        TableColumn col1 = new TableColumn("Id");
        col1.setCellValueFactory(new PropertyValueFactory("id"));
        col1.setPrefWidth(this.authorTabView.getPrefWidth() * 0.1);
        col1.setStyle("-fx-alignment: CENTER;");

        TableColumn col2 = new TableColumn("Author name");
        col2.setCellValueFactory(new PropertyValueFactory("name"));
        col2.setPrefWidth(this.authorTabView.getPrefWidth() * 0.9);

        this.authorTabView.getColumns().addAll(col1, col2);
    }

    public void loadData() {
        try {
            this.authorTabView.setItems(FXCollections.observableList(authorService.getAuthors("")));
        } catch (SQLException ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void reset() throws SQLException {
        this.txtId.clear();
        this.txtName.clear();
        this.searchKw.clear();
    }

    private void reloadWindow() throws SQLException {
        reset();
        loadData();
    }

    private boolean checkValidForm() {
        if (txtName.getText() == null || txtName.getText().trim().isEmpty())
            return false;
        if (Utils.isContainNumber(txtName.getText().trim()))
            return false;

        return true;
    }

    private Author getAuthorFromForm() {
        Author author = new Author();

        if (!txtName.getText().trim().isEmpty()) {
            author.setName(Utils.stringNormalization(this.txtName.getText()));
        }

        return author;
    }

    @FXML
    void add(ActionEvent event) throws SQLException {
        Author author = this.getAuthorFromForm();
        if (checkValidForm()) {
            boolean addAuthorCheck = authorService.add(author);
            if (addAuthorCheck) {
                reloadWindow();
            } else {
                Utils.setAlert("Thêm thất bại!!!", Alert.AlertType.ERROR).show();
            }
        } else {
            Utils.setAlert("Thông tin tác giả không hợp lệ!!!", Alert.AlertType.ERROR).show();
        }
    }

    @FXML
    void delete(ActionEvent event) throws SQLException {
        if (this.authorTabView.getSelectionModel().getSelectedItem() != null) {
            authorService.delete(this.authorTabView.getSelectionModel().getSelectedItem().getId());
            reloadWindow();
        }
    }

    @FXML
    void refresh(ActionEvent event) throws SQLException {
        reloadWindow();
    }

    @FXML
    void searchAuthor(KeyEvent event) throws SQLException {
        if (authorService.getAuthors(this.searchKw.getText()).size() > 0) {
            this.authorTabView.setItems(FXCollections.observableList(authorService.getAuthors(this.searchKw.getText())));
        }
    }

    @FXML
    void update(ActionEvent event) throws ParseException, SQLException {
        if (authorTabView.getSelectionModel().getSelectedItem() != null) {
            Author author = this.getAuthorFromForm();
            if (checkValidForm()) {
                if (authorService.update(authorTabView.getSelectionModel().getSelectedItem().getId(), author.getName())) {
                    reloadWindow();
                } else {
                    Utils.setAlert("Sửa thất bại!!!", Alert.AlertType.ERROR).show();
                }
            } else {
                Utils.setAlert("Thông tin sách không hợp lệ!!!", Alert.AlertType.ERROR).show();
            }
        } else {
            Utils.setAlert("Chưa chọn sách cần chỉnh sửa!!!", Alert.AlertType.ERROR).show();
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
