package com.ou.librarymanagement;

import com.ou.pojo.Department;
import com.ou.services.DepartmentService;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DepartmentController implements Initializable {
    private static final DepartmentService s = new DepartmentService();
    @FXML
    private TextField txtKeyword;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TableView<Department> tbDepartment;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnBack;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtId.setEditable(false);
        this.loadColumns();
        this.loadData(null);

        //Ẩn button sửa
        this.btnUpdate.setVisible(false);

        //Ẩn button xóa
        this.btnDelete.setVisible(false);

        //Tìm kiếm đối tượng theo tên đối tượng
        this.txtKeyword.textProperty().addListener((evt) ->{
            this.loadData(this.txtKeyword.getText().trim());
        });

        //Chọn 1 dòng trên TableView đổ dữ liệu lên các controls
        this.tbDepartment.setRowFactory(et ->{
            TableRow row = new TableRow();
            row.setOnMouseClicked(r ->{
                this.btnUpdate.setVisible(true);
                this.btnDelete.setVisible(true);
                this.btnInsert.setVisible(false);
                Department d = (Department) this.tbDepartment.getSelectionModel().getSelectedItem();
                this.txtId.setText(String.valueOf(d.getId()));
                this.txtName.setText(d.getName());
            });
            return row;
        });
    }
    private void loadData(String kw){
        try {
            this.tbDepartment.setItems(FXCollections.observableList(s.getDepartments(kw)));
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void loadColumns(){
        TableColumn col1 = new TableColumn("Id");
        col1.setCellValueFactory(new PropertyValueFactory("id"));
        col1.setPrefWidth(300);

        TableColumn col2 = new TableColumn(("Tên bộ phận"));
        col2.setCellValueFactory(new PropertyValueFactory("name"));
        col2.setPrefWidth(300);
        this.tbDepartment.getColumns().addAll(col1, col2);
    }

    public void reset() {
        this.txtId.setText("");
        this.txtName.setText("");
        this.btnUpdate.setVisible(false);
        this.btnDelete.setVisible(false);
        this.btnInsert.setVisible(true);
        this.tbDepartment.getSelectionModel().select(null);
    }

    public void resetHandler(ActionEvent evt){
        reset();
    }

    public void addDepartment(ActionEvent evt) throws SQLException{
        Department d = new Department();
        d.setName(txtName.getText().trim());
        if (s.addDepartment(d)){
            Utils.setAlert("Thêm thành công!!!", Alert.AlertType.INFORMATION).show();
            reset();
            this.loadData(null);
        }
        else
            Utils.setAlert("Thêm thất bại!!!", Alert.AlertType.ERROR).show();
    }

    public void updateDepartment(ActionEvent evt) throws SQLException{
        try {
            if (s.updateDepartment(Integer.parseInt(this.txtId.getText()), this.txtName.getText().trim())){
                Utils.setAlert("Sửa thành công!!!", Alert.AlertType.INFORMATION).show();
                this.loadData(null);
            }
            else
                Utils.setAlert("Sửa thất bại!!!", Alert.AlertType.ERROR).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDepartment(ActionEvent evt) throws SQLException{
        try{
            if (s.deleteDepartment(Integer.parseInt(this.txtId.getText()))){
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
