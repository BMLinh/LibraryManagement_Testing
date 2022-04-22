/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou.librarymanagement;

import com.ou.pojo.BookCategory;
import com.ou.pojo.PublishingCompany;
import com.ou.services.BookCategoryService;
import com.ou.utils.Utils;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
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
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Linh
 */
public class BookCategoryController implements Initializable{
    private static final BookCategoryService s = new BookCategoryService();
    @FXML
    private TextField txtKeyword;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPosition;
    @FXML
    private TableView<BookCategory> tbBookCategory;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnDelete;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtId.setEditable(false);
        this.loadColumns();
        this.loadData(null);

        //Ẩn button sửa
        this.btnUpdate.setVisible(false);
        
        this.btnDelete.setVisible(false);

        //Tìm kiếm đối tượng theo tên đối tượng
        this.txtKeyword.textProperty().addListener((evt) ->{
            this.loadData(this.txtKeyword.getText());
        });

        //Chọn 1 dòng trên TableView đổ dữ liệu lên các controls
        this.tbBookCategory.setRowFactory(et ->{
            TableRow row = new TableRow();
            row.setOnMouseClicked(r ->{
                this.btnUpdate.setVisible(true);
                this.btnDelete.setVisible(true);
                this.btnInsert.setVisible(false);
                BookCategory b = (BookCategory) this.tbBookCategory.getSelectionModel().getSelectedItem();
                this.txtId.setText(String.valueOf(b.getId()));
                this.txtName.setText(b.getName());
                this.txtPosition.setText(b.getPosition());
            });
            return row;
        });
    }
    private void loadData(String kw){
        try {
            this.tbBookCategory.setItems(FXCollections.observableList(s.getBookCategory(kw)));
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void loadColumns(){
        TableColumn col1 = new TableColumn("Id");
        col1.setCellValueFactory(new PropertyValueFactory("id"));
        col1.setPrefWidth(300);

        TableColumn col2 = new TableColumn("Tên thể loại");
        col2.setCellValueFactory(new PropertyValueFactory("name"));
        col2.setPrefWidth(300);
        
        TableColumn col3 = new TableColumn("Vị trí");
        col3.setCellValueFactory(new PropertyValueFactory("position"));
        col3.setPrefWidth(300);
        
        this.tbBookCategory.getColumns().addAll(col1, col2, col3);
    }

    public void reset() {
        this.txtId.setText("");
        this.txtName.setText("");
        this.txtPosition.setText("");
        this.btnUpdate.setVisible(false);
        this.btnDelete.setVisible(false);
        this.btnInsert.setVisible(true);
        this.tbBookCategory.getSelectionModel().select(null);
    }

    public void resetHandler(ActionEvent evt){
        reset();
    }

    public void addBookCategory(ActionEvent evt) throws SQLException{
        BookCategory b = new BookCategory();
        b.setName(txtName.getText());
        b.setPosition(txtPosition.getText());
        if (s.addBookCategory(b) == true){
            Utils.setAlert("Thêm thành công!!!", Alert.AlertType.INFORMATION).show();
            reset();
            this.loadData(null);
        }
        else
            Utils.setAlert("Thêm thất bại!!!", Alert.AlertType.ERROR).show();
    }

    public void updateBookCategory(ActionEvent evt) throws SQLException{
        try {
            if (s.updateBookCategory(this.txtId.getText(), this.txtName.getText(), this.txtPosition.getText()) == true){
                Utils.setAlert("Sửa thành công!!!", Alert.AlertType.INFORMATION).show();
                this.loadData(null);
            }
            else
                Utils.setAlert("Sửa thất bại!!!", Alert.AlertType.ERROR).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteBookCategory(ActionEvent evt) throws SQLException{
        try{
            if (s.deleteBookCategory(this.txtId.getText()) == true){
                Utils.setAlert("Xóa thành công!!!", Alert.AlertType.INFORMATION).show();
                reset();
                this.loadData(null);
            }
            else
                Utils.setAlert("Xóa thất bại!!!", Alert.AlertType.ERROR).show();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
