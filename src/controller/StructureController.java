package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StructureController implements Initializable, Runnable {

	public static final int MAINPAGE=0,CLIENTLOANS=1,MANAGERLOANS=2,CLIENTACCOUNTS=3,MANGERACCOUNTS=4,SETTINGS=5;

	@FXML
	private VBox sidePanel;

	@FXML
	private AnchorPane MainPanel;

	@FXML
	private VBox exSidePanel;

	@FXML
	private ImageView changeImage;
	
	 @FXML
	 private Group miniGroup;

	 @FXML
	 private Group exitGroup;
	    
	static Parent[] root = new Parent[6];
	HBox box;
	static VBox btns;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		exitGroup.setCursor(Cursor.HAND);
		exitGroup.setOnMouseClicked(e->Platform.exit());
		miniGroup.setCursor(Cursor.HAND);
		miniGroup.setOnMouseClicked(e->((Stage)miniGroup.getScene().getWindow()).setIconified(true));
		
		changeImage.setVisible(true);
		exSidePanel.setVisible(false);
		changeImage.setCursor(Cursor.HAND);
		changeImage.toFront();
		changeImage.setOnMouseClicked(e -> sideTransition());
		exSidePanel.toBack();
		/*	box=new HBox();
			btns=new VBox();
			box.getChildren().add(sidePanel);
			box.getChildren().add(btns);
			exSidePanel.getChildren().add(box);*/
		   
		    loadPage("mainPage", 0);
		    loadPage("clientLoans", 1);
		    loadPage("managerLoans", 2);
		    loadPage("clientsAccounts", 3);
		    loadPage("managerAccounts", 4);
		    loadPage("settings", 5);
		
		
		}

	public void loadPage(String fxml, int page) {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/" + fxml + ".fxml"));
		try {
			root[page] = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addButton(int page) {
		Button b = new Button();
		btns.getChildren().add(b);
		b.setPrefWidth(280);
		b.setPrefHeight(80);

		b.setOnAction(e -> {
			((Stage) b.getScene().getWindow()).setScene(new Scene(root[page]));

			//loader = new FXMLLoader(this.getClass().getResource("../view/MainStructure.fxml"));
		});
	}

	private boolean check = true;

	private void sideTransition() {
		Thread thread = new Thread(this);
		thread.start();
		if (check) {
			//sidePanel.setVisible(false);
			sidePanel.toFront();
			exSidePanel.setVisible(true);
			TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), exSidePanel);
			transition.setCycleCount(1);
			transition.setFromX(200);
			transition.setToX(0);
			transition.play();
			check = false;
		} else {

			TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), exSidePanel);
			transition.setCycleCount(1);
			transition.setFromX(0);
			transition.setToX(200);
			transition.play();
			check = true;
		}

	}

	@Override
	public void run() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if (check) {
					sidePanel.setVisible(true);
					exSidePanel.setVisible(false);
				}
			}
		});
	}
}