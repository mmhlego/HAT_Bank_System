package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class AccountInformation implements Initializable{

    @FXML
    private AnchorPane MainPanel;

    @FXML
    private JFXTextField cardNumberTXF;

    @FXML
    private JFXTextField pinTXF;

    @FXML
    private JFXTextField cvvTXF;

    @FXML
    private JFXTextField expTXF;

    @FXML
    private JFXTextField cvv2TXF;

    @FXML
    private JFXButton changePinBTN;

    @FXML
    private JFXButton withdrawBTN;

    @FXML
    private JFXButton depositBTn;

    @FXML
    private JFXButton historyBTN;

    @FXML
    private JFXButton changeCvvBTN;

    @FXML
    private ImageView cardImage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
