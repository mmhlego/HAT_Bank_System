package controller;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.*;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import model.User;
import model.UserController;

public class StructureController implements Initializable {

	@FXML
	private AnchorPane MainPanel;

	@FXML
	private VBox SidePanel;

	@FXML
	private HBox toggleSidepanel;

	@FXML
	private JFXButton exitBTN;

	@FXML
	private HBox exitBox;

	@FXML
	private ImageView image;
	HBox box;
	private static AnchorPane Main;
	private static VBox side;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		exitBox.setOnMouseClicked(e -> Platform.exit());
		exitBTN.setOnAction(e -> Platform.exit());
		/*	box=new HBox();
			btns=new VBox();
			box.getChildren().add(sidePanel);
			box.getChildren().add(btns);
			exSidePanel.getChildren().add(box);*/

		Main = MainPanel;

		side = SidePanel;
		side.toBack();
		exitBox.toBack();

		sideTransition();
		switch (UserController.getCurrentUser().AccessLevel) {
		case User.CLIENT:
			addButton("loansMainPage", "All Loans");
			addButton("accountsPage", "My Accounts");
			addButton("loanStatusPage", "My Loans");
			addButton("TransactionStatus", "Transactions History");
			addButton("Transactions", "Transfer");
			addButton("depositPage", "Deposit");
			addButton("withdrawPage", "Withdraw");
			addButton("settings", "Settings");
			break;
		case User.EMPLOYEE:
		case User.MANAGER:
			addButton("accountsPage", "All Accounts");
			addButton("loanStatusPage", "All Loans");
			addButton("TransactionStatus", "All Transactions");
			addButton("settings", "Settings");
			break;
		}
		System.out.println((((ImageView) toggleSidepanel.getChildren().get(0)).getImage()));
		toggleSidepanel.setOnMouseClicked(e -> {
			sideTransition();
		});

		toggleSidepanel.setOnMouseEntered(e -> {
			toggleSidepanel.setStyle("-fx-background-color:#0D0F48;-fx-background-radius: 20 20 0 0;");
			((Label) toggleSidepanel.getChildren().get(1))
					.setFont(Font.font(((Label) toggleSidepanel.getChildren().get(1)).getFont().getFamily(),
							FontWeight.BOLD, ((Label) toggleSidepanel.getChildren().get(1)).getFont().getSize()));
		});

		toggleSidepanel.setOnMouseExited(e -> {
			toggleSidepanel.setStyle("-fx-background-color:transparent;-fx-background-radius: 20 20 0 0;");
			((Label) toggleSidepanel.getChildren().get(1))
					.setFont(Font.font(((Label) toggleSidepanel.getChildren().get(1)).getFont().getFamily(),
							FontWeight.NORMAL, ((Label) toggleSidepanel.getChildren().get(1)).getFont().getSize()));

		});

		try {
			FXMLLoader loader = new FXMLLoader(new File("src/view/mainPage.fxml").toURI().toURL());

			Main.getChildren().add(loader.load());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addButton(String fxml, String name) {
		try {
			FXMLLoader loader = new FXMLLoader(new File("src/view/components/SidePanelButtons.fxml").toURI().toURL());

			HBox button = loader.load();
			((Label) button.getChildren().get(1)).setText(name);
			((ImageView) button.getChildren().get(0))
					.setImage(new Image(new FileInputStream(new File("src/view/sideBarIcons/" + fxml + ".png"))));
			button.setCursor(Cursor.HAND);
			button.setOnMouseEntered(e -> {
				button.setStyle("-fx-background-color:#0D0F48;");
				((Label) button.getChildren().get(1))
						.setFont(Font.font(((Label) button.getChildren().get(1)).getFont().getFamily(), FontWeight.BOLD,
								((Label) button.getChildren().get(1)).getFont().getSize()));
			});

			button.setOnMouseExited(e -> {
				button.setStyle("-fx-background-color:transparent;");
				((Label) button.getChildren().get(1))
						.setFont(Font.font(((Label) button.getChildren().get(1)).getFont().getFamily(),
								FontWeight.NORMAL, ((Label) button.getChildren().get(1)).getFont().getSize()));

			});

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
			TranslateTransition exitTransition = new TranslateTransition(Duration.millis(500), exitBox);
			exitTransition.setCycleCount(1);
			exitTransition.setFromX(220);
			exitTransition.setToX(0);
			exitTransition.play();
			check = false;
		} else {
			TranslateTransition transition = new TranslateTransition(Duration.millis(500), side);
			transition.setCycleCount(1);
			transition.setFromX(0);
			transition.setToX(220);
			transition.play();
			TranslateTransition exitTransition = new TranslateTransition(Duration.millis(500), exitBox);
			exitTransition.setCycleCount(1);
			exitTransition.setFromX(0);
			exitTransition.setToX(220);
			exitTransition.play();
			check = true;
		}
	}
}