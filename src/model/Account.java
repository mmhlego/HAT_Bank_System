package model;

import java.util.Date;

public class Account {
    String OwnerID, IBAN, CVV, CVV2, AccountID;
    Date ExDate;
    int Status;
    long Value;

    public Account(String ownerID, String iban, String cvv, String cvv2, Date exDate, int status, long value,
            String accountID) {
        OwnerID = ownerID;
        IBAN = iban;
        CVV = cvv;
        CVV2 = cvv2;
        AccountID = accountID;
        ExDate = exDate;
        Status = status;
        Value = value;
    }
}
