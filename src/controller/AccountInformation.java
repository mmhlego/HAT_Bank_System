package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class AccountInformation implements Initializable {
	@FXML
	private AnchorPane MainPanel;
	@FXML
	private JFXTextField cardNumberTXF;
	@FXML
	private JFXTextField cvvTXF;
	@FXML
	private JFXTextField expTXF;
	@FXML
	private JFXTextField cvv2TXF;
	@FXML
	private JFXButton withdrawBTN;
	@FXML
	private JFXButton depositBTn;
	@FXML
	private JFXButton historyBTN;
	@FXML
	private JFXButton changeCvvBTN;
	@FXML
	private ImageView cardImage;
	@FXML
	private ImageView backBtn;
	@FXML
	private Label cardNumber;
	@FXML
	private Label exp;
	@FXML
	private Label cvv2;
	@FXML
	private Label iban;
	@FXML
	private AnchorPane cardPlace;

	public AnchorPane getMainPanel() {
		return MainPanel;
	}

	public void setMainPanel(AnchorPane mainPanel) {
		MainPanel = mainPanel;
	}

	public JFXTextField getCardNumberTXF() {
		return cardNumberTXF;
	}

	public void setCardNumberTXF(JFXTextField cardNumberTXF) {
		this.cardNumberTXF = cardNumberTXF;
	}

	public JFXTextField getCvvTXF() {
		return cvvTXF;
	}

	public void setCvvTXF(JFXTextField cvvTXF) {
		this.cvvTXF = cvvTXF;
	}

	public JFXTextField getExpTXF() {
		return expTXF;
	}

	public void setExpTXF(JFXTextField expTXF) {
		this.expTXF = expTXF;
	}

	public JFXTextField getCvv2TXF() {
		return cvv2TXF;
	}

	public void setCvv2TXF(JFXTextField cvv2txf) {
		cvv2TXF = cvv2txf;
	}

	public JFXButton getWithdrawBTN() {
		return withdrawBTN;
	}

	public void setWithdrawBTN(JFXButton withdrawBTN) {
		this.withdrawBTN = withdrawBTN;
	}

	public JFXButton getDepositBTn() {
		return depositBTn;
	}

	public void setDepositBTn(JFXButton depositBTn) {
		this.depositBTn = depositBTn;
	}

	public JFXButton getHistoryBTN() {
		return historyBTN;
	}

	public void setHistoryBTN(JFXButton historyBTN) {
		this.historyBTN = historyBTN;
	}

	public JFXButton getChangeCvvBTN() {
		return changeCvvBTN;
	}

	public void setChangeCvvBTN(JFXButton changeCvvBTN) {
		this.changeCvvBTN = changeCvvBTN;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		changeCvvBTN.setCursor(Cursor.HAND);
		historyBTN.setCursor(Cursor.HAND);
		withdrawBTN.setCursor(Cursor.HAND);
		depositBTn.setCursor(Cursor.HAND);
		changeCvvBTN.setOnAction(e -> loadPage("changeCvvPage"));
		withdrawBTN.setOnAction(e -> loadPage("withdrawPage"));
		depositBTn.setOnAction(e -> loadPage("depositPage"));
		historyBTN.setOnAction(e -> loadPage("Transactions"));
		backBtn.setOnMouseClicked(e -> {
			((AnchorPane) MainPanel.getParent()).getChildren()
					.remove(((AnchorPane) MainPanel.getParent()).getChildren().size() - 1);
		});
	}

	private void loadPage(String fxml) {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/" + fxml + ".fxml"));
		try {
			MainPanel.getChildren().add(loader.load());
			AnchorPane card = cardPlace;
			switch (fxml) {
			case "changeCvvPage":
				CVVChange control1 = loader.getController();
				control1.getCardNumberTXF().setText(((Label) card.getChildren().get(2)).getText());
				control1.getCardNumberTXF().setEditable(false);
				break;
			case "withdrawPage":
				Withdraw control2 = loader.getController();
				control2.getCardTXF().setText(((Label) card.getChildren().get(2)).getText());
				control2.getCVV2TXF()
						.setText(((Label) ((AnchorPane) card.getChildren().get(4)).getChildren().get(0)).getText());
				control2.getCardTXF().setEditable(false);
				control2.getCVV2TXF().setEditable(false);
				break;
			case "depositPage":
				deposit control3 = loader.getController();
				control3.getCardTXF().setText(((Label) card.getChildren().get(2)).getText());
				control3.getCVV2TXF()
						.setText(((Label) ((AnchorPane) card.getChildren().get(4)).getChildren().get(0)).getText());
				control3.getCardTXF().setEditable(false);
				control3.getCVV2TXF().setEditable(false);
				break;
			case "Transactions":
				Transaction control4 = loader.getController();
				control4.getFromTXF().setText(((Label) card.getChildren().get(2)).getText());
				control4.getFromTXF().setEditable(false);
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public ImageView getBackBtn() {
		return backBtn;
	}

	public void setBackBtn(ImageView backBtn) {
		this.backBtn = backBtn;
	}

	public Label getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(Label cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Label getExp() {
		return exp;
	}

	public void setExp(Label exp) {
		this.exp = exp;
	}

	public Label getCvv2() {
		return cvv2;
	}

	public void setCvv2(Label cvv2) {
		this.cvv2 = cvv2;
	}

	public Label getIban() {
		return iban;
	}

	public void setIban(Label iban) {
		this.iban = iban;
	}

	public AnchorPane getCardPlace() {
		return cardPlace;
	}

	public void setCardPlace(AnchorPane cardPlace) {
		this.cardPlace = cardPlace;
	}

}
