package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class Transaction implements Initializable{

    @FXML
    private AnchorPane MainPanel;

    @FXML
    private TextField fromTXF;

    @FXML
    private TextField toTXF;

    @FXML
    private TextField amountTXF;

    @FXML
    private JFXButton nextBTN;
    
    Parent next;
    boolean check=true;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/TransactionsResume.fxml"));
		try {
			next=loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		nextBTN.setOnAction(e->{
			if (check) {
				nextStep();
			}
		});
	}
	
	
	private void nextStep() {
		MainPanel.getChildren().clear();
		AnchorPane.setTopAnchor(next, 0.0);
		AnchorPane.setBottomAnchor(next, 0.0);
		AnchorPane.setLeftAnchor(next, 0.0);
		AnchorPane.setRightAnchor(next, 0.0);
		MainPanel.getChildren().add(next);

	}


	public AnchorPane getMainPanel() {
		return MainPanel;
	}


	public void setMainPanel(AnchorPane mainPanel) {
		MainPanel = mainPanel;
	}


	public TextField getFromTXF() {
		return fromTXF;
	}


	public void setFromTXF(TextField fromTXF) {
		this.fromTXF = fromTXF;
	}


	public TextField getToTXF() {
		return toTXF;
	}


	public void setToTXF(TextField toTXF) {
		this.toTXF = toTXF;
	}


	public TextField getAmountTXF() {
		return amountTXF;
	}


	public void setAmountTXF(TextField amountTXF) {
		this.amountTXF = amountTXF;
	}


	public JFXButton getNextBTN() {
		return nextBTN;
	}


	public void setNextBTN(JFXButton nextBTN) {
		this.nextBTN = nextBTN;
	}


	public Parent getNext() {
		return next;
	}


	public void setNext(Parent next) {
		this.next = next;
	}


	public boolean isCheck() {
		return check;
	}


	public void setCheck(boolean check) {
		this.check = check;
	}
	
}
