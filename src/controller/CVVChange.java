package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.*;
import java.security.*;

public class CVVChange implements Initializable {

	@FXML
	private AnchorPane MainPanel;

	@FXML
	private JFXTextField cardNumberTXF;

	@FXML
	private JFXPasswordField currentCvvTXF;

	@FXML
	private JFXPasswordField newCvvTXF;

	@FXML
	private JFXPasswordField RNewCvvTXF;

	@FXML
	private JFXTextField captchaTXF;

	@FXML
	private AnchorPane captchaArea;

	@FXML
	private ImageView recaptcha;

	@FXML
	private JFXTextField codeTXF;

	@FXML
	private JFXButton requestBTN;

	@FXML
	private JFXButton saveBTN;

	public static int Code;
	AnchorPane cap;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LimitandNext();
		captchaMaker();
		recaptcha.setOnMouseClicked(e -> {
			rotation();
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							captchaMaker();
						}
					});
				}
			});
			thread.start();
		});

		requestBTN.setOnAction((e) -> {
			CreatOTP();
			try {
				Sender.SendEmail("mmhlegoautosmssender@gmail.com", UserController.getCurrentUser().PhoneNumber,
						Sender.SMSMail);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setContentText("A Verification Code Was Sent To *******"
						+ UserController.getCurrentUser().PhoneNumber.substring(7, 11));
				alert.show();
			} catch (Exception e1) {
				alert("Check Your Internet Connection");
			}
		});

		saveBTN.setOnAction((e) -> {
			if (!DBConnector.CheckCurrentCVV(currentCvvTXF.getText(), cardNumberTXF.getText())) {
				alert("Wrong CVV !");
			} else if (newCvvTXF.getText().equals("") || RNewCvvTXF.getText().equals("")) {
				alert("CVV Field Is Empty !");
			} else if (!newCvvTXF.getText().equals(RNewCvvTXF.getText())) {
				alert("CVVs Don't Match !");
			} else if (!Captcha.captchaResult.equals(captchaTXF.getText())) {
				alert("Captcha Is Wrong !");
				captchaMaker();
			} else if (!codeTXF.getText().equals(String.format("%s", Code))) {
				alert("Wrong OTP");
			} else if (!DBConnector.IsDestinationCardAlive(cardNumberTXF.getText())) {
				alert("Card Is Expired !");
			}
			 else {
				try {
					DBConnector.UpdateCVV(cardNumberTXF.getText(), newCvvTXF.getText());
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText(null);
					alert.setContentText("CVV Changed Successfully !");
					alert.show();
					ClearData();
					UserController.updatePersonalData();
					FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/accountsPage.fxml"));
					try {
						MainPanel.getChildren().add(loader.load());
					} catch (IOException e1) {
						Alert alert1 = new Alert(AlertType.ERROR);
						alert1.setHeaderText(null);
						alert1.setContentText("Check Your Internet Connection !");
						alert1.show();
					}
				} catch (Exception e1) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setContentText("Check Your Internet Connection !");
					alert.show();
				}
			}
		});
	}

	private void LimitandNext() {
		PassToNext.NextField(currentCvvTXF, 4, true);
		PassToNext.NextField(newCvvTXF, 4, true);
		PassToNext.NextField(RNewCvvTXF, 4, true);
		PassToNext.NextField(codeTXF, 6, true);
	}

	private static int CreatOTP() {
		SecureRandom r = new SecureRandom();
		Code = r.nextInt(100000) + r.nextInt(100000);
		if (Code >= 100000 && Code <= 999999) {
			return Code;
		} else {
			return CreatOTP();
		}
	}

	private void ClearData() {
		cardNumberTXF.setText("");
		currentCvvTXF.setText("");
		newCvvTXF.setText("");
		RNewCvvTXF.setText("");
		captchaTXF.setText("");
		codeTXF.setText("");
	}

	private void alert(String Content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText(null);
		alert.setContentText(Content);
		alert.show();
	}

	private void captchaMaker() {
		try {
			cap = FXMLLoader.load(this.getClass().getResource("../view/captcha.fxml"));
			if (captchaArea.getChildren().size() != 0) {
				captchaArea.getChildren().clear();
			}
			captchaArea.getChildren().add(cap);
			AnchorPane.setTopAnchor(cap, (double) 0);
			AnchorPane.setBottomAnchor(cap, (double) 0);
			AnchorPane.setLeftAnchor(cap, (double) 0);
			AnchorPane.setRightAnchor(cap, (double) 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void rotation() {
		RotateTransition rotate = new RotateTransition();
		rotate.setNode(recaptcha);
		rotate.setCycleCount(1);
		rotate.setAxis(new Point3D(0, 0, 1));
		rotate.setByAngle(360);
		rotate.setDuration(Duration.seconds(1));
		rotate.play();
	}

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

	public JFXPasswordField getCurrentCvvTXF() {
		return currentCvvTXF;
	}

	public void setCurrentCvvTXF(JFXPasswordField currentCvvTXF) {
		this.currentCvvTXF = currentCvvTXF;
	}

	public JFXPasswordField getNewCvvTXF() {
		return newCvvTXF;
	}

	public void setNewCvvTXF(JFXPasswordField newCvvTXF) {
		this.newCvvTXF = newCvvTXF;
	}

	public JFXPasswordField getRNewCvvTXF() {
		return RNewCvvTXF;
	}

	public void setRNewCvvTXF(JFXPasswordField rNewCvvTXF) {
		RNewCvvTXF = rNewCvvTXF;
	}

	public JFXTextField getCaptchaTXF() {
		return captchaTXF;
	}

	public void setCaptchaTXF(JFXTextField captchaTXF) {
		this.captchaTXF = captchaTXF;
	}

	public AnchorPane getCaptchaArea() {
		return captchaArea;
	}

	public void setCaptchaArea(AnchorPane captchaArea) {
		this.captchaArea = captchaArea;
	}

	public ImageView getRecaptcha() {
		return recaptcha;
	}

	public void setRecaptcha(ImageView recaptcha) {
		this.recaptcha = recaptcha;
	}

	public JFXTextField getCodeTXF() {
		return codeTXF;
	}

	public void setCodeTXF(JFXTextField codeTXF) {
		this.codeTXF = codeTXF;
	}

	public JFXButton getRequestBTN() {
		return requestBTN;
	}

	public void setRequestBTN(JFXButton requestBTN) {
		this.requestBTN = requestBTN;
	}

	public JFXButton getSaveBTN() {
		return saveBTN;
	}

	public void setSaveBTN(JFXButton saveBTN) {
		this.saveBTN = saveBTN;
	}

	public AnchorPane getCap() {
		return cap;
	}

	public void setCap(AnchorPane cap) {
		this.cap = cap;
	}
}
