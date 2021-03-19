package controller;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class StructureController implements Initializable {

	public static final int MAINPAGE = 0, CLIENTLOANS = 1, MANAGERLOANS = 2, CLIENTACCOUNTS = 3, MANGERACCOUNTS = 4,
			SETTINGS = 5;

	@FXML
	private AnchorPane MainPanel;

	@FXML
	private VBox SidePanel;

	@FXML
	private HBox toggleSidepanel;

	static Parent[] root = new Parent[6];
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

		loadPage("mainPage", 0);
		loadPage("clientLoans", 1);
		loadPage("managerLoans", 2);
		loadPage("clientsAccounts", 3);
		loadPage("managerAccounts", 4);
		loadPage("settings", 5);

		Main = MainPanel;

		side = SidePanel;
		side.toBack();

		sideTransition();

		toggleSidepanel.setOnMouseClicked(e -> {
			sideTransition();
		});

		try {
			FXMLLoader loader = new FXMLLoader(new File("../view/mainPage.fxml").toURI().toURL());

			Main.getChildren().add(loader.load());
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		try {
			FXMLLoader loader = new FXMLLoader(new File("../view/components/SidePanelButtons.fxml").toURI().toURL());

			HBox button = loader.load();

			button.setOnMouseClicked(e -> {
				int len = Main.getChildren().size();
				Main.getChildren().remove(len - 1);
				Main.getChildren().add(root[page]);
			});

			((Label) button.getChildren().get(1)).setText(Integer.toString(page));

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