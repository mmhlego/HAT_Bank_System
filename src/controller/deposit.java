package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class deposit implements Initializable{

    @FXML
    private AnchorPane MainPanel;

    @FXML
    private TextField cardTXF;

    @FXML
    private TextField AmountTXF;

    @FXML
    private TextField pinTXF;

    @FXML
    private TextField CVV2TXF;

    @FXML
    private TextField YearTXF;

    @FXML
    private TextField MonthTXF;

    @FXML
    private JFXButton submit;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

}
