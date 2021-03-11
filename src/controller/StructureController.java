package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StructureController implements Initializable {

    public static final int Accounts = 0, ClientLoans = 1, ClientsList = 2, AllLoans = 3, EmployeesList = 4;

    @FXML
    private VBox sidePanel;

    @FXML
    private AnchorPane MainPanel;
    
    @FXML
    private VBox exSidePanel;

    @FXML
    private ImageView changeImage;
    static Parent[] root = new Parent[5];
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	exSidePanel.setVisible(false);
    	changeImage.setCursor(Cursor.HAND);
    	changeImage.toFront();
    	changeImage.setOnMouseClicked(e->sideTransition());
    	exSidePanel.toBack();
     /*   try {
            loadPage("ClientAccounts", 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/}

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
        b.setPrefWidth(280);
        b.setPrefHeight(80);

        b.setOnAction(e -> {
            ((Stage) b.getScene().getWindow()).setScene(new Scene(root[page]));

            //loader = new FXMLLoader(this.getClass().getResource("../view/MainStructure.fxml"));
        });
    }
    private boolean check=true;
    private void sideTransition() {
    	if (check) {
    		sidePanel.setVisible(false);
    		exSidePanel.setVisible(true);
			TranslateTransition transition =  new TranslateTransition(Duration.seconds(2), exSidePanel);
			transition.setCycleCount(1);
			transition.setFromX(230);
			transition.setToX(0);
			transition.play();
			check=false;
		}else {
			sidePanel.setVisible(true);
    		exSidePanel.setVisible(false);
			TranslateTransition transition =  new TranslateTransition(Duration.seconds(2), exSidePanel);
			transition.setCycleCount(1);
			transition.setFromX(0);
			transition.setToX(230);
			transition.play();
			check=true;
		}
		
		
	}
}
