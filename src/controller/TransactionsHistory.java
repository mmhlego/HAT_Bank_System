package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class TransactionsHistory implements Initializable {

    @FXML
    private AnchorPane MainPanel;

    @FXML
    private VBox successVbox;

    @FXML
    private VBox failedVbox;

    @FXML
    private ChoiceBox<?> sortBox;

    @FXML
    private RadioButton desRadioBTN;

    @FXML
    private ToggleGroup A;

    @FXML
    private RadioButton ascRadioBTN;

    @FXML
    private TextField searchTXF;

    @FXML
    private ImageView searchBTN;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
