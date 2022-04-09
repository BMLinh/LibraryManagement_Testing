package com.ou.librarymanagement;

import com.ou.pojo.Department;
import com.ou.services.DepartmentService;
import com.ou.utils.Utils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
        this.tbDepartment.setRowFactory(et ->{
            TableRow row = new TableRow();
            row.setOnMouseClicked(r ->{
                this.btnUpdate.setVisible(true);
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

        TableColumn col3 = new TableColumn();
        col3.setCellFactory((p) -> {
            Button btn = new Button("Xóa");

            btn.setOnAction((evt) ->{
                TableCell c = (TableCell) ((Button)evt.getSource()).getParent();
                Department d = (Department) c.getTableRow().getItem();
                try{
                    if (s.deleteDepartment(String.valueOf(d.getId())) == true){
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
        this.tbDepartment.getColumns().addAll(col1, col2, col3);
    }

    public void reset() {
        this.txtId.setText("");
        this.txtName.setText("");
        this.btnUpdate.setVisible(false);
        this.btnInsert.setVisible(true);
        this.tbDepartment.getSelectionModel().select(null);
    }

    public void resetHandler(ActionEvent evt){
        reset();
    }

    public void addDepartment(ActionEvent evt) throws SQLException{
        Department d = new Department();
        d.setName(txtName.getText());
        if (s.addDepartment(d) == true){
            Utils.setAlert("Thêm thành công!!!", Alert.AlertType.INFORMATION).show();
            reset();
            this.loadData(null);
        }
        else
            Utils.setAlert("Thêm thất bại!!!", Alert.AlertType.ERROR).show();
    }

    public void updateDepartment(ActionEvent evt) throws SQLException{
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
