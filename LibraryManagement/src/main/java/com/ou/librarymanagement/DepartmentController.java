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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtId.setEditable(false);
        this.loadColumns();
        this.loadData(null);

        this.txtKeyword.textProperty().addListener((evt) ->{
            this.loadData(this.txtKeyword.getText());
        });
    }
    private void loadData(String kw){
        try {
            this.tbDepartment.setItems(FXCollections.observableList(s.getDepartments(kw)));
        } catch (SQLException ex){
            Logger.getLogger(DepartmentController.class.getName()).log(Level.SEVERE,null, ex);
        }
    }

    private void loadColumns(){
        TableColumn col1 = new TableColumn("Id");
        col1.setCellValueFactory(new PropertyValueFactory("id"));
        col1.setPrefWidth(300);

        TableColumn col2 = new TableColumn(("Tên đối tượng"));
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

    public void addDepartment(ActionEvent evt) throws SQLException{
        Department d = new Department();
        d.setName(txtName.getText());
        if (s.addDepartment(d) == true){
            Utils.setAlert("Thêm thành công!!!", Alert.AlertType.INFORMATION).show();
            this.loadData(null);
        }
        else
            Utils.setAlert("Thêm thất bại!!!", Alert.AlertType.ERROR).show();
    }
}
