package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class SortAndSearch implements Initializable{

    @FXML
    private TextField searchTXF;

    @FXML
    private ImageView searchBTN;

    @FXML
    private ComboBox<?> firstCMB;

    @FXML
    private ComboBox<?> secondCMB;

    @FXML
    private ComboBox<?> thirdCMB;

    @FXML
    private JFXButton sortBTN;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
