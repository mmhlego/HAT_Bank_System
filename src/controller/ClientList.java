package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import model.DBConnector;
import model.User;

public class ClientList implements Initializable {

    @FXML
    private AnchorPane MainPanel;

    @FXML
    private AnchorPane cardAnchor;

    private ArrayList<User> allClients = new ArrayList<User>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allClients = DBConnector.getAllUsers(User.CLIENT);

        showAllUsers();
    }

    private void showAllUsers() {
        if (allClients.size() == 0) {
            noResults();
        }

        for (int i = 0; i < allClients.size(); i++) {
            addUser(i);
        }
    }

    private void addUser(int index) {
        User user = allClients.get(index);

        AnchorPane pane = null;
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/components/EachUser.fxml"));
            pane = loader.load();

            EachUser controller = loader.getController();

            controller.getId().setText(user.ID);

            controller.getFirstName().setText(user.FirstName);
            controller.getLastName().setText(user.LastName);

            controller.getUsename().setText(user.Username);
            controller.getNationalCode().setText(user.NationalCode);

            controller.getPhone().setText(user.PhoneNumber);
            controller.getEmail().setText(user.Email);

            // Group group = (Group) pane.getChildren().get(1);
            // ((Label) group.getChildren().get(1)).setText(String.valueOf(loan.Value) + currency);
            // ((Label) group.getChildren().get(3)).setText(loan.AccountID);

            AnchorPane.setTopAnchor(pane, (double) (index * 245));
            cardAnchor.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void noResults() {
        try {
            cardAnchor.getChildren().add(
                    (AnchorPane) FXMLLoader.load(this.getClass().getResource("../view/components/emptyField.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
