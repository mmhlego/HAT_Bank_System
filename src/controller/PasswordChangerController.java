package controller;

import java.security.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.*;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.*;

public class PasswordChangerController implements Initializable {
	@FXML
	private AnchorPane MainPanel;
	@FXML
	private JFXTextField usernameTXF;
	@FXML
	private JFXPasswordField currentPassTXF;
	@FXML
	private JFXPasswordField newPassTXF;
	@FXML
	private JFXPasswordField RNewPassTXF;
	@FXML
	private JFXTextField captchaTXF;
	@FXML
	private AnchorPane captchaArea;
	@FXML
	private JFXTextField codeTXF;
	@FXML
	private JFXButton requestBTN;
	@FXML
	private JFXButton saveBTN;
	@FXML
	private ImageView recaptcha;

	AnchorPane cap;
	public static int Code;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

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
				Sender.SendEmail("mmhlegoautosmssender@gmail.com", UserController.getCurrentUser().PhoneNumber, Sender.SMSMail);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		saveBTN.setOnAction((e) -> {
			if (!DBConnector.CheckCurrentData(usernameTXF.getText(), currentPassTXF.getText())) {
				alert("Wrong Username / Password");
			} else if (newPassTXF.getText().equals("") || RNewPassTXF.getText().equals("")) {
				alert("Password Field Is Empty !");
			} else if (!DBConnector.IsPasswordMatches(newPassTXF, RNewPassTXF)) {
				alert("Passwords Don't Match !");
			} else if (!Captcha.captchaResult.equals(captchaTXF.getText())) {
				alert("Captcha Is Wrong !");
				captchaMaker();
			} else if (!codeTXF.getText().equals(String.format("%s", Code))) {
				alert("Wrong OTP");
			} else {
				User currentuser = UserController.getCurrentUser();
				User u = new User(currentuser.FirstName, currentuser.LastName, currentuser.Username, encoder.encode(
						newPassTXF.getText()),
						currentuser.AccessLevel, currentuser.Address, currentuser.ID, currentuser.NationalCode,
						currentuser.BirthDate, currentuser.Email, currentuser.PhoneNumber, currentuser.Theme,
						currentuser.Language);
				try {
					DBConnector.UpdatePassword(u);
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText(null);
					alert.setContentText("Password Changed Successfully !");
					alert.show();
					ClearData();
					UserController.updatePersonalData();
					FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/settings.fxml"));
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

	private static int CreatOTP() {
		SecureRandom r = new SecureRandom();
		Code = r.nextInt(100000) + r.nextInt(100000);
		if (Code >= 100000 && Code <= 999999) {
			return Code;
		} else {
			return CreatOTP();
		}
	}

	private void alert(String Content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText(null);
		alert.setContentText(Content);
		alert.show();
	}

	private void ClearData() {
		usernameTXF.setText("");
		currentPassTXF.setText("");
		newPassTXF.setText("");
		RNewPassTXF.setText("");
		captchaTXF.setText("");
		codeTXF.setText("");
	}

}
