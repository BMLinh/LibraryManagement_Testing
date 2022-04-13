package com.ou.librarymanagement;

import com.ou.pojo.Role;
import com.ou.services.RoleService;
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

public class RoleController implements Initializable {
    private static final RoleService s = new RoleService();
    @FXML
    private TextField txtKeyword;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TableView<Role> tbRoles;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnDelete;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.txtId.setEditable(false);
        this.loadColumns();
        this.loadData(null);

        //Ẩn button sửa
        this.btnUpdate.setVisible(false);

        //Ẩn button xóa
        this.btnDelete.setVisible(false);

        //Tìm kiếm loại quyền theo tên quyền
        this.txtKeyword.textProperty().addListener((evt) ->{
            this.loadData(this.txtKeyword.getText());
        });

        //Chọn 1 dòng trên TableView đổ dữ liệu lên các controls
        this.tbRoles.setRowFactory(et ->{
            TableRow row = new TableRow();
            row.setOnMouseClicked(r ->{
                this.btnDelete.setVisible(true);
                this.btnUpdate.setVisible(true);
                this.btnInsert.setVisible(false);
                Role role = (Role) this.tbRoles.getSelectionModel().getSelectedItem();
                this.txtId.setText(String.valueOf(role.getId()));
                this.txtName.setText(role.getName());
            });
            return row;
        });
    }

    private void loadData(String kw){
        try {
            this.tbRoles.setItems(FXCollections.observableList(s.getRoles(kw)));
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void loadColumns(){
        TableColumn col1 = new TableColumn("Id");
        col1.setCellValueFactory(new PropertyValueFactory("id"));
        col1.setPrefWidth(300);

        TableColumn col2 = new TableColumn(("Tên quyền"));
        col2.setCellValueFactory(new PropertyValueFactory("name"));
        col2.setPrefWidth(300);

//        TableColumn col3 = new TableColumn();
//        col3.setCellFactory((p) -> {
//            Button btn = new Button("Xóa");
//
//            btn.setOnAction((evt) ->{
//                TableCell c = (TableCell) ((Button)evt.getSource()).getParent();
//                Role r = (Role) c.getTableRow().getItem();
//                try{
//                    if (s.deleteRole(String.valueOf(r.getId())) == true){
//                        Utils.setAlert("Xóa thành công!!!", Alert.AlertType.INFORMATION).show();
//                        reset();
//                        this.loadData(null);
//                    }
//                    else
//                        Utils.setAlert("Xóa thất bại!!!", Alert.AlertType.ERROR).show();
//                }catch (SQLException e){
//                    e.printStackTrace();
//                }
//            });
//            TableCell cell= new TableCell();
//            cell.setGraphic(btn);
//            return cell;
//        });
        this.tbRoles.getColumns().addAll(col1, col2);
    }

    public void reset(){
        this.txtId.setText("");
        this.txtName.setText("");
        this.btnUpdate.setVisible(false);
        this.btnInsert.setVisible(true);
        this.btnDelete.setVisible(false);
        this.tbRoles.getSelectionModel().select(null);
    }
    public void resetHandler(ActionEvent evt){
        reset();
    }
    public void addRole(ActionEvent evt) throws SQLException{
        Role r = new Role();
        r.setName(txtName.getText());
        if (s.addRole(r) == true){
            Utils.setAlert("Thêm thành công!!!", Alert.AlertType.INFORMATION).show();
            reset();
            this.loadData(null);
        }
        else
            Utils.setAlert("Thêm thất bại!!!", Alert.AlertType.ERROR).show();
    }

    public void updateRole(ActionEvent evt) throws SQLException{
        try {
            if (s.updateRole(this.txtId.getText(), this.txtName.getText()) == true){
                Utils.setAlert("Sửa thành công!!!", Alert.AlertType.INFORMATION).show();
                this.loadData(null);
            }
            else
                Utils.setAlert("Sửa thất bại!!!", Alert.AlertType.ERROR).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRole(ActionEvent evt) throws SQLException{
            try{
                if (s.deleteRole(this.txtId.getText()) == true){
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
