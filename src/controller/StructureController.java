package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StructureController implements Initializable {

    public static final int Accounts = 0, ClientLoans = 1, ClientsList = 2, AllLoans = 3, EmployeesList = 4;

    @FXML
    private AnchorPane SidePanel;

    @FXML
    private AnchorPane MainPanel;

    static Parent[] root = new Parent[5];

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadPage("ClientAccounts", 0);

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
        Button b = new Button();
        b.setPrefWidth(280);
        b.setPrefHeight(80);

        b.setOnAction(e -> {
            ((Stage) b.getScene().getWindow()).setScene(new Scene(root[page]));

            //loader = new FXMLLoader(this.getClass().getResource("../view/MainStructure.fxml"));
        });
    }
}
