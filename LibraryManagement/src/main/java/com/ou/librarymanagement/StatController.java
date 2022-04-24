package com.ou.librarymanagement;

import com.ou.services.StatService;
import com.ou.utils.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class StatController implements Initializable {

    @FXML
    private ComboBox cbCondition;
    @FXML
    private TextField txtConditionSearch;
    @FXML
    private TableView tbStat;

    private static final StatService statService = new StatService();
    private static final List<String> condition = new ArrayList<>();
    {
        this.condition.add("Quý");
        this.condition.add("Năm");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
        this.loadColumns();
        Map<String, String> paramUnit = new HashMap<>();
        paramUnit.put("year",this.txtConditionSearch.getText());
        this.loadData(paramUnit);
    }
    public void init(){
        this.cbCondition.setItems(FXCollections.observableList(condition));
        this.cbCondition.getSelectionModel().select(1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int currentYear = calendar.get(Calendar.YEAR);
        this.txtConditionSearch.setText(String.valueOf(currentYear));

        this.txtConditionSearch.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (!t1.matches("\\d*"))
                    txtConditionSearch.setText(t1.replaceAll("[^\\d]", ""));
                if (txtConditionSearch.getText().length() > 4) {
                    String s = txtConditionSearch.getText().substring(0, 4);
                    txtConditionSearch.setText(s);
                }
            }
        });

        this.txtConditionSearch.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().equals(" ")) {
                change.setText("");
            }
            return change;
        }));
    }

    private void loadData(Map<String,String> param){
        try {
            this.tbStat.setItems(FXCollections.observableList(statService.getStat(param)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadColumns(){
        TableColumn col1 = new TableColumn("Tháng");
        col1.setCellValueFactory(new PropertyValueFactory("month"));
        col1.setPrefWidth(246);

        TableColumn col2 = new TableColumn("Tên sách");
        col2.setCellValueFactory(new PropertyValueFactory("bookName"));
        col2.setPrefWidth(246);

        TableColumn col3 = new TableColumn("Số lượng mượn");
        col3.setCellValueFactory(new PropertyValueFactory("sum"));
        col3.setPrefWidth(246);

        this.tbStat.getColumns().addAll(col1, col2, col3);
    }

    public void loadTableWithCondition(){
        if (this.cbCondition.getSelectionModel().getSelectedIndex() ==0){
            if (txtConditionSearch.getText() == "")
                Utils.setAlert("Vui lòng nhập quý cần thống kê!", Alert.AlertType.ERROR).show();
            else if (Integer.parseInt(txtConditionSearch.getText()) == 0)
                Utils.setAlert("Quý không bằng 0!", Alert.AlertType.ERROR).show();
            else if (Integer.parseInt(txtConditionSearch.getText()) > 4)
                Utils.setAlert("Quý phải nhỏ hơn hoặc bằng 4!!", Alert.AlertType.ERROR).show();
            else
            {
                Map<String, String> param = new HashMap<>();
                param.put("quarter", txtConditionSearch.getText());
                loadData(param);
            }

        }
        else {
            if (txtConditionSearch.getText() == "")
                Utils.setAlert("Vui lòng nhập năm cần thống kê!", Alert.AlertType.ERROR).show();
            else if (Integer.parseInt(txtConditionSearch.getText()) == 0)
                Utils.setAlert("Năm không bằng 0!", Alert.AlertType.ERROR).show();
            else if (Integer.parseInt(txtConditionSearch.getText()) <= 1900)
                Utils.setAlert("Năm nhập phải lơn hơn 1900!", Alert.AlertType.ERROR).show();
            else {
                Map<String, String> param = new HashMap<>();
                param.put("year", txtConditionSearch.getText());
                loadData(param);
            }
        }
    }

}
