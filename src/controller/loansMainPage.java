package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class loansMainPage implements Initializable {

    @FXML
    private AnchorPane MainPanel;

    @FXML
    private AnchorPane allLoans;

    @FXML
    private ImageView allLoansImage;

    @FXML
    private AnchorPane loanRequest;

    @FXML
    private ImageView loanRequestImage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

}
