/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou.librarymanagement;

import com.ou.pojo.PublishingCompany;
import com.ou.services.PublishingCompanyService;
import com.ou.utils.Utils;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Linh
 */
public class PublishingCompanyController implements Initializable{
    private static final PublishingCompanyService s = new PublishingCompanyService();
    @FXML
    private TextField txtKeyword;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TableView<PublishingCompany> tbPublishingCompany;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnInsert;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtId.setEditable(false);
        this.loadColumns();
        this.loadData(null);

        //Ẩn button sửa
        this.btnUpdate.setVisible(false);

        //Tìm kiếm đối tượng theo tên đối tượng
        this.txtKeyword.textProperty().addListener((evt) ->{
            this.loadData(this.txtKeyword.getText());
        });

        //Chọn 1 dòng trên TableView đổ dữ liệu lên các controls
        this.tbPublishingCompany.setRowFactory(et ->{
            TableRow row = new TableRow();
            row.setOnMouseClicked(r ->{
                this.btnUpdate.setVisible(true);
                this.btnInsert.setVisible(false);
                PublishingCompany p = (PublishingCompany) this.tbPublishingCompany.getSelectionModel().getSelectedItem();
                this.txtId.setText(String.valueOf(p.getId()));
                this.txtName.setText(p.getName());
            });
            return row;
        });
    }
    private void loadData(String kw){
        try {
            this.tbPublishingCompany.setItems(FXCollections.observableList(s.getPublishingCompanys()));
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void loadColumns(){
        TableColumn col1 = new TableColumn("Id");
        col1.setCellValueFactory(new PropertyValueFactory("id"));
        col1.setPrefWidth(300);

        TableColumn col2 = new TableColumn(("Tên nhà xuất bản"));
        col2.setCellValueFactory(new PropertyValueFactory("name"));
        col2.setPrefWidth(300);

        TableColumn col3 = new TableColumn();
        col3.setCellFactory((d) -> {
            Button btn = new Button("Xóa");

            btn.setOnAction((evt) ->{
                TableCell c = (TableCell) ((Button)evt.getSource()).getParent();
                PublishingCompany p = (PublishingCompany) c.getTableRow().getItem();
                try{
                    if (s.deletePublishingCompany(String.valueOf(p.getId())) == true){
                        Utils.setAlert("Xóa thành công!!!", Alert.AlertType.INFORMATION).show();
                        reset();
                        this.loadData(null);
                    }
                    else {
                        Utils.setAlert("Xóa thất bại!!!", Alert.AlertType.ERROR).show();
                    }
                }catch (SQLException ex){
                    Logger.getLogger(RoleController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            TableCell cell= new TableCell();
            cell.setGraphic(btn);
            return cell;
        });
        this.tbPublishingCompany.getColumns().addAll(col1, col2, col3);
    }

    public void reset() {
        this.txtId.setText("");
        this.txtName.setText("");
        this.btnUpdate.setVisible(false);
        this.btnInsert.setVisible(true);
        this.tbPublishingCompany.getSelectionModel().select(null);
    }

    public void resetHandler(ActionEvent evt){
        reset();
    }

    public void addPublishingCompany(ActionEvent evt) throws SQLException{
        PublishingCompany p = new PublishingCompany();
        p.setName(txtName.getText());
        if (s.addPublishingCompany(p) == true){
            Utils.setAlert("Thêm thành công!!!", Alert.AlertType.INFORMATION).show();
            reset();
            this.loadData(null);
        }
        else
            Utils.setAlert("Thêm thất bại!!!", Alert.AlertType.ERROR).show();
    }

    public void updatePublishingCompany(ActionEvent evt) throws SQLException{
        try {
            if (s.updateDepartment(this.txtId.getText(), this.txtName.getText()) == true){
                Utils.setAlert("Sửa thành công!!!", Alert.AlertType.INFORMATION).show();
                this.loadData(null);
            }
            else
                Utils.setAlert("Sửa thất bại!!!", Alert.AlertType.ERROR).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
