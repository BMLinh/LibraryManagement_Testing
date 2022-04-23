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
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;

/**
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
        
        
        this.userTabView.setRowFactory(et ->{
            TableRow row = new TableRow();
            row.setOnMouseClicked(r ->{
                User user = (User)this.userTabView.getSelectionModel().getSelectedItem();
                this.idTxtFld.setText(String.valueOf(user.getId()));
                this.usernameTxtFld.setText(user.getUsername());
                this.passwordTxtFld.setText(user.getPassword());
                this.fullnameTxtFld.setText(user.getFullname());
                if(1 == user.getGender())
                    this.genderCb.getSelectionModel().select(1);
                else this.genderCb.getSelectionModel().select(0);
                this.birthTxtFld.setValue(Utils.convertUtilToSql(user.getDob()).toLocalDate());
                this.addressTxtFld.setText(user.getAddress());
                this.phoneTxtFld.setText(user.getPhone());
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
    
    public void init() throws SQLException{
        this.birthTxtFld.setEditable(false);
        
        this.genderCb.setItems(FXCollections.observableList(gender));
        
        this.roleCb.setItems(FXCollections.observableList(roleService.getRoles(null)));

        this.departmentCb.setItems(FXCollections.observableList(departmentService.getDepartments(null)));
        
        this.genderCb.getSelectionModel().select(0);
        
        this.birthTxtFld.setValue(LocalDate.now(ZoneId.systemDefault()));
        
        this.phoneTxtFld.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(!t1.matches("\\d*"))
                    phoneTxtFld.setText(t1.replaceAll("[^\\d]", ""));
                if(phoneTxtFld.getText().length() > 12){
                    String s = phoneTxtFld.getText().substring(0, 12);
                    phoneTxtFld.setText(s);
                }
            }
        });
        
        this.searchContentTxtFld.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(!t1.matches("\\d*"))
                    searchContentTxtFld.setText(t1.replaceAll("[^\\d]", ""));
                if(searchContentTxtFld.getText().length() > 12){
                    String s = searchContentTxtFld.getText().substring(0, 12);
                    searchContentTxtFld.setText(s);
                }
            }
        });
        
        this.usernameTxtFld.setTextFormatter(new TextFormatter<>(change -> {
            if(change.getText().equals(" ")){
                change.setText("");
            }
            return change;
        }));
        
        this.passwordTxtFld.setTextFormatter(new TextFormatter<>(change -> {
            if(change.getText().equals(" ")){
                change.setText("");
            }
            return change;
        }));
        
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
        
        this.userTabView.getColumns().addAll(col1,col2,col3,col4,col5,col6,col7,col8,col9,col10,col11);
    }
    
    public void reset() throws SQLException {
        this.idTxtFld.clear();
        this.usernameTxtFld.clear();
        this.passwordTxtFld.clear();
        this.fullnameTxtFld.clear();
        this.birthTxtFld.setValue(LocalDate.now(ZoneId.systemDefault()));
        this.addressTxtFld.clear();
        this.phoneTxtFld.clear();
        this.genderCb.getSelectionModel().select(0);
        this.roleCb.getSelectionModel().select(null);
        this.departmentCb.getSelectionModel().select(null);
        this.createdDateTxtFld.clear();
        this.birthTxtFld.setValue(null);
    }
    
    public void addUser(ActionEvent evt) throws SQLException{
        User user = this.getUserFromFx();
        if(this.userService.findUserByPhone(user.getPhone()).isEmpty() && this.userService.findUserByUsername(user.getUsername()).isEmpty()){
            if(this.userService.addUser(this.getUserFromFx())){
                Utils.setAlert("Thêm thành công!!!", Alert.AlertType.INFORMATION).show();
                this.loadData();
                reset();
            }
            else 
                Utils.setAlert("Thêm thất bại!!!", Alert.AlertType.ERROR).show();
        }
            else 
                Utils.setAlert("Có User trùng!!!", Alert.AlertType.ERROR).show();
    }
    
    public void updateUser(ActionEvent evt) throws SQLException{
        if (userService.updateUser(this.userTabView.getSelectionModel().getSelectedItem().getId(),this.getUserFromFx()) == true){
            Utils.setAlert("Sửa thành công!!!", Alert.AlertType.INFORMATION).show();
            this.loadData();
            reset();
        }
        else
            Utils.setAlert("Sửa thất bại!!!", Alert.AlertType.ERROR).show();
    }
    
    public void resetHandler(ActionEvent evt) throws SQLException {
        reset();
        loadData();
    }
    
    public void deleteUser(ActionEvent evt) throws SQLException {
        if(this.userService.deleteUser(this.userTabView.getSelectionModel().getSelectedItem().getId())){
            Utils.setAlert("Xoá thành công!!!", Alert.AlertType.INFORMATION).show();
            this.loadData();
            reset();
        }
        else
            Utils.setAlert("Xoá thất bại!!!", Alert.AlertType.ERROR).show();
    }
    
    public void searchUser(ActionEvent evt) throws SQLException{
        if(this.userService.findUserByPhone(this.searchContentTxtFld.getText()).size() > 0){
            this.userTabView.setItems(FXCollections.observableList(this.userService.findUserByPhone(this.searchContentTxtFld.getText())));
        }
        else 
            Utils.setAlert("Không có dữ liệu!!!", Alert.AlertType.ERROR).show();
        
        this.searchContentTxtFld.clear();
    }
    
    private User getUserFromFx(){
        User u = new User();
        u.setUsername(this.usernameTxtFld.getText());
        u.setPassword(this.passwordTxtFld.getText());
        u.setFullname(this.fullnameTxtFld.getText());
        u.setDob(Utils.convertUtilToSql(Date.from(this.birthTxtFld.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())));
        // email textfield
        if(1 == this.genderCb.getSelectionModel().getSelectedIndex())
            u.setGender(1);
        else u.setGender(0);
        u.setAddress(this.addressTxtFld.getText());
        u.setPhone(this.phoneTxtFld.getText());
        u.setRoleId(this.roleCb.getSelectionModel().getSelectedItem().getId());
        u.setDepartmentId(this.departmentCb.getSelectionModel().getSelectedItem().getId());
        
        return u;
    }
    
}
