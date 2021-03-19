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
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class PasswordChangerController implements Initializable{

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		captchaMaker();
		recaptcha.setOnMouseClicked(e->{
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
		
	}

	private void captchaMaker() {
		try {
			cap=FXMLLoader.load(this.getClass().getResource("../view/captcha.fxml"));
			if (captchaArea.getChildren().size()!=0) {
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
}
