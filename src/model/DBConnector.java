package model;

import java.sql.*;

public class DBConnector {

    private static Connection con;

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

    private static ResultSet runCommand(String command) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://sql6.freemysqlhosting.net:3306/sql6398536", "sql6398536",
                "t8YnlV3Mzt");

        Statement stmt = con.createStatement();
        ResultSet results = stmt.executeQuery(command);

        return results;
    }

    public static boolean checkUser(String username, String hashPassword, int AccessLevel) throws Exception {
        ResultSet results = runCommand("select * from User WHERE Username=\'" + username + "\' AND Password=\'"
                + hashPassword + "\' AND AccessLevel=" + AccessLevel);

        if (results.next()) {
            return true;
        }

        return false;
    }

    private static void close() throws Exception {
        con.close();
    }

    public static void addUser() {

    }
}
