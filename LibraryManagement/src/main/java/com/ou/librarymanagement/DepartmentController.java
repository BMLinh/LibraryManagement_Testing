package com.ou.librarymanagement;

import com.ou.pojo.Department;
import com.ou.services.DepartmentService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
    public void initialize(URL location, ResourceBundle resources) {

    }
    private void loadData(String kw){
        try {
            this.tbDepartment.setItems(FXCollections.observableList(s.getDepartments(kw)));
        } catch (SQLException ex){
            Logger.getLogger(DepartmentController.class.getName()).log(Level.SEVERE,null, ex);
        }


    }
}
