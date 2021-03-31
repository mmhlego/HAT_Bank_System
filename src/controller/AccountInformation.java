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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class AccountInformation implements Initializable{

    @FXML
    private AnchorPane MainPanel;

    @FXML
    private JFXTextField cardNumberTXF;

    @FXML
    private JFXTextField cvvTXF;

    @FXML
    private JFXTextField expTXF;

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

	public AnchorPane getCardPlace() {
		return cardPlace;
	}

	public void setCardPlace(AnchorPane cardPlace) {
		this.cardPlace = cardPlace;
	}

	public ImageView getCardImage() {
		return cardImage;
	}

	public void setCardImage(ImageView cardImage) {
		this.cardImage = cardImage;
	}

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
    private AnchorPane cardPlace;

    @FXML
    private ImageView cardImage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		changeCvvBTN.setCursor(Cursor.HAND);
		historyBTN.setCursor(Cursor.HAND);
		withdrawBTN.setCursor(Cursor.HAND);
		depositBTn.setCursor(Cursor.HAND);
		changeCvvBTN.setOnAction(e->loadPage("changeCvvPage"));
		withdrawBTN.setOnAction(e->loadPage("withdrawPage"));
		depositBTn.setOnAction(e->loadPage("depositPage"));
	}
	
	private void loadPage(String fxml) {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/"+fxml+".fxml"));
		try {
			MainPanel.getChildren().add(loader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
