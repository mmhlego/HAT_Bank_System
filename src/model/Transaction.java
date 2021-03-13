package model;

import java.util.Date;

public class Transaction {
    String FromAccountID, ToAccountID, TransactionID;
    long Value;
    Date CompletionDate;

    public Transaction(String fromID, String toID, long value, Date date, String transactionID) {
        FromAccountID = fromID;
        ToAccountID = toID;
        TransactionID = transactionID;
        Value = value;
        CompletionDate = date;
    }
}
