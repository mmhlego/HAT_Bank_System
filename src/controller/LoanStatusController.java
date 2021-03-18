package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class LoanStatusController implements Initializable {

    @FXML
    private AnchorPane MainPanel;

    @FXML
    private Group pendingGroup;

    @FXML
    private Label pendingLoanValue;

    @FXML
    private Label pendingLoanID;

    @FXML
    private Group acceptedGroup;

    @FXML
    private Label acceptedLoanValue;

    @FXML
    private Label acceptedLoanID;

    @FXML
    private Group finishedGroup;

    @FXML
    private Label finishedLoanValue;

    @FXML
    private Label finishedLoanID;

    @FXML
    private Group rejectedGroup;

    @FXML
    private Label rejectedLoanValue;

    @FXML
    private Label rejectedLoanID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
