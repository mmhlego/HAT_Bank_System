package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class eachTransition implements Initializable{

    @FXML
    private AnchorPane pendingAnchor;

    @FXML
    private Label from;

    @FXML
    private Label to;

    @FXML
    private Label amount;

    @FXML
    private Label date;

    @FXML
    private Label id;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	public AnchorPane getPendingAnchor() {
		return pendingAnchor;
	}

	public void setPendingAnchor(AnchorPane pendingAnchor) {
		this.pendingAnchor = pendingAnchor;
	}

	public Label getFrom() {
		return from;
	}

	public void setFrom(Label from) {
		this.from = from;
	}

	public Label getTo() {
		return to;
	}

	public void setTo(Label to) {
		this.to = to;
	}

	public Label getAmount() {
		return amount;
	}

	public void setAmount(Label amount) {
		this.amount = amount;
	}

	public Label getDate() {
		return date;
	}

	public void setDate(Label date) {
		this.date = date;
	}

	public Label getId() {
		return id;
	}

	public void setId(Label id) {
		this.id = id;
	}

}
