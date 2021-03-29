package model;

import java.time.LocalDate;

public class Transaction {
    String FromAccountID, ToAccountID, TransactionID;
    long Value;
    LocalDate CompletionDate;

    public Transaction(String fromID, String toID, long value, LocalDate date, String transactionID) {
        FromAccountID = fromID;
        ToAccountID = toID;
        TransactionID = transactionID;
        Value = value;
        CompletionDate = date;
    }

    public static String generateID() {
        int count = DBConnector.numberOfTransactions();
        String ID = "T-";
        String number = Integer.toString(count + 1);
        for (int i = 0; i < (5 - number.length()); i++) {
            ID += "0";
        }
        ID += number;

        return ID;
    }
}
