package model;

import java.io.File;
import java.sql.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DBConnector {

    private static Connection con;
    private static Stage stage;
    private static ResultSet results;
    private static double offsetLeft = 0.0;

    //============================================================================================= GETTERS & SETTERS

    public static void setOffsetLeft(Double d) {
        offsetLeft = d;
    }

    public static void setStage(Stage st) {
        stage = st;
    }

    private static ResultSet getResults() {
        return results;
    }

    //============================================================================================= LOADING SCREEN METHODS

    public static boolean showLoading() {
        try {
            FXMLLoader loader = new FXMLLoader(new File("src\\view\\DatabaseLoadingOverlay.fxml").toURI().toURL());
            AnchorPane root = loader.load();

            AnchorPane ap = ((AnchorPane) stage.getScene().getRoot());
            ap.getChildren().add(root);
            AnchorPane.setTopAnchor(root, 26.0);
            AnchorPane.setLeftAnchor(root, offsetLeft);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public static void closeLoading() {
        AnchorPane ap = ((AnchorPane) stage.getScene().getRoot());
        int len = ap.getChildren().size();
        ap.getChildren().remove(len - 1);
    }

    //============================================================================================= CONNECTION METHODS

    public static boolean connect() throws SQLException {
        con = DriverManager.getConnection("jdbc:mysql://sql6.freemysqlhosting.net:3306/sql6398536", "sql6398536",
                "t8YnlV3Mzt");

        if (con != null) {
            return true;
        }
        return false;
    }

    private static void closeConnection() throws Exception {
        con.close();
    }

    //============================================================================================= COMPLETE METHODS

    private static ResultSet runCommand(String command) throws Exception {
        results = null;

        Statement stmt = con.createStatement();
        results = stmt.executeQuery(command);

        return results;
    }

    public static boolean checkUser(String username, String hashPassword, int AccessLevel) throws Exception {
        runCommand("select * from User WHERE Username=\'" + username + "\' AND Password=\'" + hashPassword
                + "\' AND AccessLevel=" + AccessLevel);

        if (results == null) {
            return false;
        } else if (results.next()) {
            return true;
        }
        return false;
    }

    //============================================================================================= 

    /*private static void connect() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://sql6.freemysqlhosting.net:3306/sql6398536", "sql6398536",
                "t8YnlV3Mzt");
    
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from Sample");
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
    }*/

    public static void addUser() {

    }
}