package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class cardPlus implements Initializable{

    @FXML
    private AnchorPane cardPlus;

    @FXML
    private ImageView plus;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	public AnchorPane getCardPlus() {
		return cardPlus;
	}

	public void setCardPlus(AnchorPane cardPlus) {
		this.cardPlus = cardPlus;
	}

	public ImageView getPlus() {
		return plus;
	}

	public void setPlus(ImageView plus) {
		this.plus = plus;
	}
	

}
