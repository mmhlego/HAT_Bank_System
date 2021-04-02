package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class eachAccount {

    @FXML
    private AnchorPane accountAnchor;

    @FXML
    private Label accountId;

    @FXML
    private Label ownerId;

    @FXML
    private Label iban;

    @FXML
    private Label bic;

    @FXML
    private Label amount1;

    @FXML
    private Label status;

	public AnchorPane getAccountAnchor() {
		return accountAnchor;
	}

	public void setAccountAnchor(AnchorPane accountAnchor) {
		this.accountAnchor = accountAnchor;
	}

	public Label getAccountId() {
		return accountId;
	}

	public void setAccountId(Label accountId) {
		this.accountId = accountId;
	}

	public Label getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Label ownerId) {
		this.ownerId = ownerId;
	}

	public Label getIban() {
		return iban;
	}

	public void setIban(Label iban) {
		this.iban = iban;
	}

	public Label getBic() {
		return bic;
	}

	public void setBic(Label bic) {
		this.bic = bic;
	}

	public Label getAmount1() {
		return amount1;
	}

	public void setAmount1(Label amount1) {
		this.amount1 = amount1;
	}

	public Label getStatus() {
		return status;
	}

	public void setStatus(Label status) {
		this.status = status;
	}

}
