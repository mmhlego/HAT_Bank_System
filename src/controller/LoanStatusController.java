package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class LoanStatusController implements Initializable {

    ObservableList<String> ComboValues = FXCollections.observableArrayList("Date", "Account ID", "Value");

    @FXML
    private AnchorPane MainPanel;

    @FXML
    private Group pendingGroup;

    @FXML
    private Label pendingLoanValue;

    @FXML
    private Label pendingLoanID;

    @FXML
    private Group acceptedGroup;

    @FXML
    private Label acceptedLoanValue;

    @FXML
    private Label acceptedLoanID;

    @FXML
    private Group finishedGroup;

    @FXML
    private Label finishedLoanValue;

    @FXML
    private Label finishedLoanID;

    @FXML
    private Group rejectedGroup;

    @FXML
    private Label rejectedLoanValue;

    @FXML
    private Label rejectedLoanID;

    @FXML
    private TextField SearchTF;

    @FXML
    private ImageView Searchbtn;

    @FXML
    private RadioButton ASCRB;

    @FXML
    private RadioButton DESCRB;

    @FXML
    private JFXComboBox<String> SortOptionCB;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SortOptionCB.setItems(ComboValues);
        Searchbtn.setOnMouseClicked((e) -> {
            String Type = SortOptionCB.getValue();
            if (ASCRB.isSelected()) {

            }
            if (DESCRB.isSelected()) {
                
            }
            // String ladder = Radio button Value;
            switch (Type) {
            case "Date": {
                // SortForDate(ladder);
                break;
            }
            case "Account ID": {
                // SortForAccountID(ladder);
                break;
            }
            case "Value": {
                // SortForValue(ladder);
                break;
            }
            default:
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("Error");
                alert.setContentText("Choose A Sort Type !");
                alert.show();
                break;
            }
        });
    }

    public static void SortForDate(String ladder) {
        
    }
    
    public static void SortForAccountID(String ladder) {
        
    }

    public static void SortForValue(String ladder) {
        
    }

}
