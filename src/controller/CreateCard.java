package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CreateCard implements Initializable{

    @FXML
    private AnchorPane MainPanel;

    @FXML
    private TextField firstNameTXF;

    @FXML
    private TextField lastNameTXF;

    @FXML
    private TextField nationalCodeTXF;

    @FXML
    private TextField pinTXF;

    @FXML
    private TextField ZipTXF;

    @FXML
    private ComboBox<String> typeCombo;

    @FXML
    private JFXButton createBTN;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> list = FXCollections.observableArrayList();
		list.add("Saving Account");
		list.add("OnGoing Account");
		
	}

}
