package model;

import java.util.Date;

public class User {
    String FirstName, LastName, Username, Password, Address, ID, NationalCode;
    Date BirthDate;
    int AccessLevel, Theme, Language;

    public User(String firstName, String lastName, String password, String address, String id, String nationalCode,
            Date birthDate, int accessLevel) {
        FirstName = firstName;
        LastName = lastName;
        Password = password;
        Address = address;
        ID = id;
        NationalCode = nationalCode;
        BirthDate = birthDate;
        AccessLevel = accessLevel;
    }

    public static String generateID(int AccessLevel, Long SameAccessUsersCount) {
        // (C/E/M)-(00001-99999)
        String[] alph = { "C", "E", "M" };
        String ID = alph[AccessLevel] + "-";
        String number = Long.toString(SameAccessUsersCount + 1);
        for (int i = 0; i < (5 - number.length()); i++) {
            ID += "0";
        }
        ID += number;

        return ID;
    }
}
