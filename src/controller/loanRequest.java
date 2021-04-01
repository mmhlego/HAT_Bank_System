package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class loanRequest implements Initializable{
@FXML
    private AnchorPane MainPanel;

    @FXML
    private TextField cardNumber;

    @FXML
    private TextField cvv;

    @FXML
    private TextField nationalCode;

    @FXML
    private TextField guarantorID;

    @FXML
    private TextField guarantorPassword;

    @FXML
    private TextField securityCode;

    @FXML
    private JFXButton requestBTN;

    @FXML
    private JFXButton confirmBTN;

    @FXML
    private TextField amount;

    @FXML
    private ComboBox<?> mode;

    @FXML
    private Label maxValue;

    @FXML
    private Label status;

    @FXML
    private Label installment;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
