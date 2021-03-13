package model;

public class UserController {
    private static User CurrentUser;

    public static User getCurreentUsr() {
        return CurrentUser;
    }

    public static void setCurrentUser(User u) {
        CurrentUser = u;
    }
}
