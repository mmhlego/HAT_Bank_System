package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
		allLoans.setOnMouseClicked(e->allLoansLoad());
		allLoansImage.setOnMouseClicked(e->allLoansLoad());
		loanRequest.setOnMouseClicked(e->loanRequestLoad());
		loanRequestImage.setOnMouseClicked(e->loanRequestLoad());
	}
	
	private void allLoansLoad() {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/loanStatusPage.fxml"));
		try {
			MainPanel.getChildren().add(loader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private void loanRequestLoad() {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/loanRequest.fxml"));
		try {
			MainPanel.getChildren().add(loader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
