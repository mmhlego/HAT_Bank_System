package model;

import java.time.LocalDate;

public class Account {
    public String OwnerID, BIC, IBAN, CVV, CVV2, AccountID;
    public LocalDate ExDate;
    public int Status;
    public long Value;
    public static final int SAVEDACCOUNT = 0, OngoingAccount = 1;

    public Account(String ownerID, String bic, String iban, String cvv, String cvv2, LocalDate exDate, int status,
            long value, String accountID) {
        OwnerID = ownerID;
        BIC = bic;
        IBAN = iban;
        CVV = cvv;
        CVV2 = cvv2;
        AccountID = accountID;
        ExDate = exDate;
        Status = status;
        Value = value;
    }

    public static String generateID() {
        int all = DBConnector.numberOfAccounts();

        String ID = "A-";
        String number = Integer.toString(all + 1);
        for (int i = 0; i < (5 - number.length()); i++) {
            ID += "0";
        }
        ID += number;

        return ID;
    }

    public static String generateID(int n) {
        String ID = "A-";
        String number = Integer.toString(n);
        for (int i = 0; i < (5 - number.length()); i++) {
            ID += "0";
        }
        ID += number;

        return ID;
    }
}
