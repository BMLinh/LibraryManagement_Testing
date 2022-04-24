package com.ou.librarymanagement;

import com.ou.pojo.ReaderCard;
import com.ou.pojo.User;
import com.ou.utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class UserInfoController implements Initializable {

    @FXML
    private TextField txtUserId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtGender;
    @FXML
    private TextField txtDOD;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtIsCreatedCard;
    @FXML
    private Text lblCardId;
    @FXML
    private TextField txtCardID;
    @FXML
    private Text lblStartDate;
    @FXML
    private TextField txtStartDate;
    @FXML
    private Text lblEndDate;
    @FXML
    private TextField txtEndDate;
    @FXML
    private Text lblAmount;
    @FXML
    private TextField txtAmount;

    private static User currentUser = null;
    private static ReaderCard currentCard = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
        loadInfoUser();
    }
    public void init(){
        this.txtUserId.setEditable(false);
        this.txtName.setEditable(false);
        this.txtGender.setEditable(false);
        this.txtDOD.setEditable(false);
        this.txtAddress.setEditable(false);
        this.txtPhone.setEditable(false);
        this.txtIsCreatedCard.setEditable(false);
        this.txtCardID.setEditable(false);
        this.txtStartDate.setEditable(false);
        this.txtEndDate.setEditable(false);
        this.txtAmount.setEditable(false);
    }

    public void loadInfoUser(){
        this.txtUserId.setText(String.valueOf(currentUser.getId()));
        this.txtName.setText(currentUser.getFullname());
        if(currentUser.getGender() == 0)
            this.txtGender.setText("Nam");
        else txtGender.setText("Nữ");
        this.txtDOD.setText(Utils.convertDateToString(currentUser.getBirth()));
        this.txtAddress.setText(currentUser.getAddress());
        this.txtPhone.setText(currentUser.getPhone());
        if (currentCard == null){
            this.txtIsCreatedCard.setText("Chưa tạo thẻ");
            this.lblCardId.setVisible(false);
            this.lblStartDate.setVisible(false);
            this.lblEndDate.setVisible(false);
            this.lblAmount.setVisible(false);
            this.txtCardID.setVisible(false);
            this.txtStartDate.setVisible(false);
            this.txtEndDate.setVisible(false);
            this.txtAmount.setVisible(false);
        }
        else {
            this.txtIsCreatedCard.setText("Đã tạo thẻ");
            this.txtCardID.setText(String.valueOf(currentCard.getId()));
            this.txtStartDate.setText(Utils.convertDateToString(currentCard.getStartDate()));
            this.txtEndDate.setText(Utils.convertDateToString(currentCard.getEndDate()));
            this.txtAmount.setText(String.valueOf(currentCard.getAmount()));
        }
    }

    /**
     * @return the currentUser
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * @param aCurrentUser the currentUser to set
     */
    public static void setCurrentUser(User aCurrentUser) {
        currentUser = aCurrentUser;
    }

    /**
     * @return the currentCard
     */
    public static ReaderCard getCurrentCard() {
        return currentCard;
    }

    /**
     * @param aCurrentCard the currentCard to set
     */
    public static void setCurrentCard(ReaderCard aCurrentCard) {
        currentCard = aCurrentCard;
    }
}
