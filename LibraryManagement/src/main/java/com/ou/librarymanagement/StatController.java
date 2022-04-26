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
    private ComboBox cbQuarterChoose;
    @FXML
    private TextField txtYear;
    @FXML
    private TableView tbStat;

    private static final StatService statService = new StatService();
    private static final List<String> condition = new ArrayList<>();
    {
        this.condition.add("1");
        this.condition.add("2");
        this.condition.add("3");
        this.condition.add("4");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
        this.loadColumns();
    }
    public void init(){
        this.cbQuarterChoose.setItems(FXCollections.observableList(condition));
        this.cbQuarterChoose.getSelectionModel().select(0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int currentYear = calendar.get(Calendar.YEAR);
        this.txtYear.setText(String.valueOf(currentYear));
        loadData(Integer.parseInt(cbQuarterChoose.getValue().toString()), currentYear);

        this.txtYear.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (!t1.matches("\\d*"))
                    txtYear.setText(t1.replaceAll("[^\\d]", ""));
                if (txtYear.getText().length() > 4) {
                    String s = txtYear.getText().substring(0, 4);
                    txtYear.setText(s);
                }
            }
        });

        this.txtYear.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().equals(" ")) {
                change.setText("");
            }
            return change;
        }));
    }

    private void loadData(int quarter, int year){
        try {
            this.tbStat.setItems(FXCollections.observableList(statService.getStat(quarter, year)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadColumns(){
        TableColumn col1 = new TableColumn("Tháng");
        col1.setCellValueFactory(new PropertyValueFactory("month"));
        col1.setPrefWidth(186);

        TableColumn col2 = new TableColumn("Năm");
        col2.setCellValueFactory(new PropertyValueFactory("year"));
        col2.setPrefWidth(186);

        TableColumn col3 = new TableColumn("Tên sách");
        col3.setCellValueFactory(new PropertyValueFactory("bookName"));
        col3.setPrefWidth(186);

        TableColumn col4 = new TableColumn("Số lượng mượn");
        col4.setCellValueFactory(new PropertyValueFactory("sum"));
        col4.setPrefWidth(186);

        this.tbStat.getColumns().addAll(col1, col2, col3, col4);
    }

    public void loadTableWithCondition(){
        if (txtYear.getText() == "")
            Utils.setAlert("Vui lòng nhập năm cần thống kê!", Alert.AlertType.ERROR).show();
        else if (Integer.parseInt(txtYear.getText()) == 0)
            Utils.setAlert("Năm không bằng 0!", Alert.AlertType.ERROR).show();
        else if (Integer.parseInt(txtYear.getText()) <= 1900)
            Utils.setAlert("Năm nhập phải lơn hơn 1900!", Alert.AlertType.ERROR).show();
        else {
            loadData(Integer.parseInt(cbQuarterChoose.getValue().toString()), Integer.parseInt(this.txtYear.getText()));
        }
    }

}
