package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class SortAndSearch implements Initializable {

    @FXML
    private TextField searchTXF;

    @FXML
    private ImageView searchBTN;

    @FXML
    private ComboBox<String> firstCMB;

    @FXML
    private ComboBox<String> secondCMB;

    public TextField getSearchTXF() {
		return searchTXF;
	}

	public void setSearchTXF(TextField searchTXF) {
		this.searchTXF = searchTXF;
	}

	public ImageView getSearchBTN() {
		return searchBTN;
	}

	public void setSearchBTN(ImageView searchBTN) {
		this.searchBTN = searchBTN;
	}

	public ComboBox<String> getFirstCMB() {
		return firstCMB;
	}

	public void setFirstCMB(ComboBox<String> firstCMB) {
		this.firstCMB = firstCMB;
	}

	public ComboBox<String> getSecondCMB() {
		return secondCMB;
	}

	public void setSecondCMB(ComboBox<String> secondCMB) {
		this.secondCMB = secondCMB;
	}

	public ComboBox<String> getThirdCMB() {
		return thirdCMB;
	}

	public void setThirdCMB(ComboBox<String> thirdCMB) {
		this.thirdCMB = thirdCMB;
	}

	public JFXButton getSortBTN() {
		return sortBTN;
	}

	public void setSortBTN(JFXButton sortBTN) {
		this.sortBTN = sortBTN;
	}

	@FXML
    private ComboBox<String> thirdCMB;

    @FXML
    private JFXButton sortBTN;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    
    }
}
