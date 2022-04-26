/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ou.librarymanagement;

import com.ou.pojo.Department;
import com.ou.pojo.Role;
import com.ou.pojo.User;
import com.ou.services.DepartmentService;
import com.ou.services.RoleService;
import com.ou.services.UserService;
import com.ou.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import javafx.stage.Stage;

/**
 * update
 * <p>
 * FXML Controller class
 *
 * @author Admin
 */
public class UserController implements Initializable {
    @FXML
    private TableView<User> userTabView;
    @FXML
    private TextField idTxtFld;
    @FXML
    private TextField usernameTxtFld;
    @FXML
    private TextField passwordTxtFld;
    @FXML
    private TextField fullnameTxtFld;
    @FXML
    private ComboBox genderCb;
    @FXML
    private DatePicker birthTxtFld;
    @FXML
    private TextField addressTxtFld;
    @FXML
    private TextField phoneTxtFld;
    @FXML
    private TextField createdDateTxtFld;
    @FXML
    private ComboBox<Role> roleCb;
    @FXML
    private ComboBox<Department> departmentCb;
    @FXML
    private TextField searchContentTxtFld;
    @FXML
    private Button btnBack;

    private static final UserService userService = new UserService();
    private static final RoleService roleService = new RoleService();
    private static final DepartmentService departmentService = new DepartmentService();
    private static final List<String> gender = new ArrayList<>();
    private static final List<String> searchOptions = new ArrayList<>();

    {
        this.gender.add("Nam");
        this.gender.add("Nữ");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            this.init();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.loadTableView();
        this.loadData();


        this.userTabView.setRowFactory(et -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(r -> {
                User user = (User) this.userTabView.getSelectionModel().getSelectedItem();
                this.idTxtFld.setText(String.valueOf(user.getId()));
                this.usernameTxtFld.setText(user.getUsername());
                this.passwordTxtFld.setText(user.getPassword());
                this.fullnameTxtFld.setText(user.getFullname());
                if (1 == user.getGender())
                    this.genderCb.getSelectionModel().select(1);
                else this.genderCb.getSelectionModel().select(0);
                this.birthTxtFld.setValue(Utils.convertUtilToSql(user.getBirth()).toLocalDate());
                this.addressTxtFld.setText(user.getAddress());
                this.phoneTxtFld.setText(user.getPhone());
                this.createdDateTxtFld.setText(Utils.convertDateToString(user.getCreatedDate()));
                try {
                    this.roleCb.getSelectionModel().select(this.roleService.getRoleById(user.getRoleId()));
                    this.departmentCb.getSelectionModel().select(this.departmentService.getDepartmentById(user.getDepartmentId()));
                } catch (SQLException ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            return row;
        });
    }

    public void init() throws SQLException {
        this.birthTxtFld.setEditable(false);

        this.genderCb.setItems(FXCollections.observableList(gender));

        this.roleCb.setItems(FXCollections.observableList(roleService.getRoles(null)));

        this.departmentCb.setItems(FXCollections.observableList(departmentService.getDepartments(null)));

        this.genderCb.getSelectionModel().select(0);

        this.birthTxtFld.setValue(null);
        
        this.roleCb.getSelectionModel().select(this.roleService.getRoleById(1));
        
        this.departmentCb.getSelectionModel().select(this.departmentService.getDepartmentById(1));

        this.phoneTxtFld.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (!t1.matches("\\d*"))
                    phoneTxtFld.setText(t1.replaceAll("[^\\d]", ""));
                if (phoneTxtFld.getText().length() > 12) {
                    String s = phoneTxtFld.getText().substring(0, 12);
                    phoneTxtFld.setText(s);
                }
            }
        });

        this.searchContentTxtFld.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (!t1.matches("\\d*"))
                    searchContentTxtFld.setText(t1.replaceAll("[^\\d]", ""));
                if (searchContentTxtFld.getText().length() > 12) {
                    String s = searchContentTxtFld.getText().substring(0, 12);
                    searchContentTxtFld.setText(s);
                }
            }
        });

        this.usernameTxtFld.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().equals(" ")) {
                change.setText("");
            }
            
            return change;
        }));

        this.passwordTxtFld.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().equals(" ")) {
                change.setText("");
            }
           
            return change;
        }));
        
        this.fullnameTxtFld.setTextFormatter(new TextFormatter<>(change -> {
            if(!change.getText().matches("[aA-zZ0-9\\p{L} ]+$"))
                change.setText("");
            return change;
        }));
        
        this.addressTxtFld.setTextFormatter(new TextFormatter<>(change -> {
            if(!change.getText().matches("[aA-zZ0-9\\p{L} ]+$"))
                change.setText("");
            return change;
        }));
        
        this.birthTxtFld.setDayCellFactory(param -> new DateCell(){
            @Override
            public void updateItem(LocalDate ld, boolean bln) {
                super.updateItem(ld, bln);
                setDisable(bln || ld.compareTo(LocalDate.now()) > 0);
            }
        });
    }

    public void loadData() {
        try {
            this.userTabView.setItems(FXCollections.observableList(this.userService.getUser()));
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadTableView() {
        TableColumn col1 = new TableColumn("Id");
        col1.setCellValueFactory(new PropertyValueFactory("id"));
        col1.setPrefWidth(40);

        TableColumn col2 = new TableColumn("Username");
        col2.setCellValueFactory(new PropertyValueFactory("username"));
        col2.setPrefWidth(100);

        TableColumn col3 = new TableColumn("Password");
        col3.setCellValueFactory(new PropertyValueFactory("password"));
        col3.setPrefWidth(100);

        TableColumn col4 = new TableColumn("Họ tên");
        col4.setCellValueFactory(new PropertyValueFactory("fullname"));
        col4.setPrefWidth(150);

        TableColumn col5 = new TableColumn("Giới tính");
        col5.setCellValueFactory(new PropertyValueFactory("gender"));
        col5.setPrefWidth(40);

        TableColumn col6 = new TableColumn("Ngày sinh");
        col6.setCellValueFactory(new PropertyValueFactory("birth"));
        col6.setPrefWidth(100);

        TableColumn col7 = new TableColumn("Địa chỉ");
        col7.setCellValueFactory(new PropertyValueFactory("address"));
        col7.setPrefWidth(230);

        TableColumn col8 = new TableColumn("SĐT");
        col8.setCellValueFactory(new PropertyValueFactory("phone"));
        col8.setPrefWidth(100);

        TableColumn col9 = new TableColumn("Role Id");
        col9.setCellValueFactory(new PropertyValueFactory("roleId"));
        col9.setPrefWidth(40);

        TableColumn col10 = new TableColumn("Department Id");
        col10.setCellValueFactory(new PropertyValueFactory("departmentId"));
        col10.setPrefWidth(40);

        TableColumn col11 = new TableColumn("Ngày tạo");
        col11.setCellValueFactory(new PropertyValueFactory("createdDate"));
        col11.setPrefWidth(100);

        this.userTabView.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7, col8, col9, col10, col11);
    }

    public void reset() throws SQLException {
        this.idTxtFld.clear();
        this.usernameTxtFld.clear();
        this.passwordTxtFld.clear();
        this.fullnameTxtFld.clear();
        this.birthTxtFld.setValue(null);
        this.addressTxtFld.clear();
        this.phoneTxtFld.clear();
        this.genderCb.getSelectionModel().select(0);
        this.roleCb.getSelectionModel().select(this.roleService.getRoleById(1));
        this.departmentCb.getSelectionModel().select(this.departmentService.getDepartmentById(1));
        this.createdDateTxtFld.clear();
        this.birthTxtFld.setValue(null);
        this.userTabView.getSelectionModel().clearSelection();
    }

    public void addUser(ActionEvent evt) throws SQLException {
        if(!this.usernameTxtFld.getText().isBlank() &&
            !this.fullnameTxtFld.getText().isBlank() && 
            !this.passwordTxtFld.getText().isBlank() && 
            !this.phoneTxtFld.getText().isBlank()){
            User user = this.getUserFromFx();
            user.setFullname(Utils.chuannHoa(this.fullnameTxtFld.getText()));
            user.setAddress(Utils.chuannHoa(this.addressTxtFld.getText()));
            if(this.userService.findUserByPhone(user.getPhone()).isEmpty() && this.userService.findUserByUsername(this.usernameTxtFld.getText()).isEmpty()){
                if(this.userService.addUser(user)){
                    Utils.setAlert("Thêm thành công!!!", Alert.AlertType.INFORMATION).show();
                    this.loadData();
                    reset();
                } else
                    Utils.setAlert("Thêm thất bại!!!", Alert.AlertType.ERROR).show();
            } else
                Utils.setAlert("Có User trùng!!!", Alert.AlertType.ERROR).show();
        }
        else Utils.setAlert("Mời bạn nhập đủ thông tin!!!", Alert.AlertType.ERROR).show();
    }

    public void updateUser(ActionEvent evt) throws SQLException {
        if(!this.idTxtFld.getText().isBlank()){
            if(!this.usernameTxtFld.getText().isBlank() &&
            !this.fullnameTxtFld.getText().isBlank() && 
            !this.passwordTxtFld.getText().isBlank() && 
            !this.phoneTxtFld.getText().isBlank()){
                User user = this.getUserFromFx();
                user.setFullname(Utils.chuannHoa(this.fullnameTxtFld.getText()));
                user.setAddress(Utils.chuannHoa(this.addressTxtFld.getText()));
                if(this.userService.findUserByPhone(this.phoneTxtFld.getText()).isEmpty() && this.userService.findUserByUsername(this.usernameTxtFld.getText()).isEmpty()){
                    if (userService.updateUser(userTabView.getSelectionModel().getSelectedItem().getId(), user)) {
                        Utils.setAlert("Sửa thành công!!!", Alert.AlertType.INFORMATION).show();
                        this.loadData();
                        reset();
                    } else
                        Utils.setAlert("Sửa thất bại!!!", Alert.AlertType.ERROR).show();
                } else Utils.setAlert("Có User trùng với thông tin đang sửa hoặc Không có thay đổi với User đang được sửa!!!", Alert.AlertType.ERROR).show();
            } else Utils.setAlert("Mời bạn nhập đủ thông tin!!!", Alert.AlertType.ERROR).show();
        }
        else Utils.setAlert("Chưa chọn User cần sửa!!!", Alert.AlertType.ERROR).show();
    }

    public void resetHandler(ActionEvent evt) throws SQLException {
        reset();
        loadData();
    }

    public void deleteUser(ActionEvent evt) throws SQLException {
        if(!this.idTxtFld.getText().isBlank()){
            if (userService.deleteUser(this.userTabView.getSelectionModel().getSelectedItem().getId())) {
                Utils.setAlert("Xoá thành công!!!", Alert.AlertType.INFORMATION).show();
                this.loadData();
                reset();
            } else
                Utils.setAlert("Xoá thất bại!!!", Alert.AlertType.ERROR).show();
        } else Utils.setAlert("Chưa chọn User cần xoá!!!", Alert.AlertType.ERROR).show();
    } 

    public void searchUser(ActionEvent evt) throws SQLException {
        if (userService.findUserByPhone(this.searchContentTxtFld.getText()).size() > 0) {
            this.userTabView.setItems(FXCollections.observableList(userService.findUserByPhone(this.searchContentTxtFld.getText())));
        } else
            Utils.setAlert("Không có dữ liệu!!!", Alert.AlertType.ERROR).show();

        this.searchContentTxtFld.clear();
    }

    private User getUserFromFx() {
        try{    
            User u = new User();
            u.setUsername(this.usernameTxtFld.getText());
            u.setPassword(this.passwordTxtFld.getText());
            u.setFullname(this.fullnameTxtFld.getText());
            u.setBirth(Utils.convertUtilToSql(Date.from(this.birthTxtFld.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())));
            if (1 == this.genderCb.getSelectionModel().getSelectedIndex())
                u.setGender(1);
            else u.setGender(0);
            u.setAddress(this.addressTxtFld.getText());
            u.setPhone(this.phoneTxtFld.getText());
            u.setRoleId(this.roleCb.getSelectionModel().getSelectedItem().getId());
            u.setDepartmentId(this.departmentCb.getSelectionModel().getSelectedItem().getId());
            u.setCreatedDate(new Date());

            return u;
        }catch(NullPointerException ex){
            Utils.setAlert("Mời bạn kiểm tra lại thông tin!!!", Alert.AlertType.ERROR).show();
        }
        return new User();
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
