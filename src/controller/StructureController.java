package controller;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.fxml.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.util.Duration;
import model.UserController;

public class StructureController implements Initializable {

	@FXML
	private AnchorPane MainPanel;

	@FXML
	private VBox SidePanel;

	@FXML
	private HBox toggleSidepanel;

	HBox box;
	private static AnchorPane Main;
	private static VBox side;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/*	box=new HBox();
			btns=new VBox();
			box.getChildren().add(sidePanel);
			box.getChildren().add(btns);
			exSidePanel.getChildren().add(box);*/
		System.out.println(UserController.getCurrentUser().AccessLevel);

		Main = MainPanel;

		side = SidePanel;
		side.toBack();

		sideTransition();
		switch (UserController.getCurrentUser().AccessLevel) {
		case 0:
			System.out.println("StructureController.initialize()");
			addButton("depositPage");
			addButton("withdrawPage");
			addButton("Transactions");
			addButton("loanStatusPage");
			addButton("settings");
			break;
		case 1:

			break;
		case 2:
			addButton("loanStatusPage");
			break;

		}
		toggleSidepanel.setOnMouseClicked(e -> {
			sideTransition();
		});

		try {
			FXMLLoader loader = new FXMLLoader(new File("src/view/mainPage.fxml").toURI().toURL());

			Main.getChildren().add(loader.load());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addButton(String fxml) {
		try {
			FXMLLoader loader = new FXMLLoader(new File("src/view/components/SidePanelButtons.fxml").toURI().toURL());

			HBox button = loader.load();
			((Label) button.getChildren().get(1)).setText(fxml);

			button.setOnMouseClicked(e -> {
				int len = Main.getChildren().size();
				Main.getChildren().remove(len - 1);
				try {
					FXMLLoader load = new FXMLLoader(new File("src/view/" + fxml + ".fxml").toURI().toURL());
					Main.getChildren().add(load.load());
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			});

			//ImageView x = ((ImageView) button.getChildren().get(0)); ==================================side panel image ----*****

			side.getChildren().add(button);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean check = false;

	private void sideTransition() {
		if (check) {
			TranslateTransition transition = new TranslateTransition(Duration.millis(500), side);
			transition.setCycleCount(1);
			transition.setFromX(220);
			transition.setToX(0);
			transition.play();
			check = false;
		} else {
			TranslateTransition transition = new TranslateTransition(Duration.millis(500), side);
			transition.setCycleCount(1);
			transition.setFromX(0);
			transition.setToX(220);
			transition.play();
			check = true;
		}
	}
}