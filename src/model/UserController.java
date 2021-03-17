package model;

import java.util.ArrayList;

public class UserController {
    private static User CurrentUser;
    ArrayList<String> Loans = new ArrayList<String>();
    ArrayList<String> Transactions = new ArrayList<String>();

    public static User getCurrentUsr() {
        return CurrentUser;
    }

    public static void setCurrentUser(User u) {
        CurrentUser = u;
    }
}
