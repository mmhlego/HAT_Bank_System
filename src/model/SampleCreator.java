package model;

import java.util.Random;
import java.time.LocalDate;

public class SampleCreator {
    private static Random r = new Random(System.currentTimeMillis());

    private static String randomFirstName() {
        String[] firstnames = { "Liam", "Noah", "William", "James", "Oliver", "Benjamin", "Elijah", "Lucas", "Mason",
                "Logan", "Alexander", "Ethan", "Jacob", "Michael", "Daniel", "Henry", "Jackson", "Sebastian", "Aiden",
                "Matthew", "Samuel", "David", "Joseph", "Carter", "Owen", "Wyatt", "John", "Jack", "Luke", "Jayden",
                "Dylan", "Grayson", "Levi", "Isaac", "Gabriel", "Emma", "Olivia", "Ava", "Isabella", "Sophia",
                "Charlotte", "Mia", "Amelia", "Harper", "Evelyn", "Abigail", "Emily", "Elizabeth", "Mila", "Ella",
                "Avery", "Sofia", "Camila", "Aria", "Scarlett", "Victoria", "Madison", "Luna", "Grace", "Chloe",
                "Penelope", "Layla", "Riley", "Zoey", "Nora", "Lily", "Eleanor", "Hannah", "Lillian", "Addison",
                "Aubrey", "Ellie", "Stella" };

        Random r = new Random(System.currentTimeMillis());
        return firstnames[r.nextInt(firstnames.length)];
    }

    private static String randomLastName() {
        String[] lastnames = { "Chung", "Chen", "Melton", "Hill", "Puckett", "Song", "Hamilton", "Bender", "Wagner",
                "McLaughlin", "McNamara", "Raynor", "Moon", "Woodard", "Desai", "Wallace", "Lawrence", "Griffin",
                "Dougherty", "Powers", "May", "Steele", "Teague", "Vick", "Gallagher", "Solomon", "Walsh", "Monroe",
                "Connolly", "Hawkins", "Middleton", "Goldstein", "Watts", "Johnston", "Weeks", "Wilkerson", "Barton",
                "Walton", "Hall", "Ross", "Chung", "Bender", "Woods", "Mangum", "Joseph", "Rosenthal", "Bowden",
                "Barton", "Underwood", "Jones", "Baker", "Merritt", "Cross", "Cooper", "Holmes", "Sharpe", "Morgan",
                "Hoyle", "Allen", "Rich", "Rich", "Grant", "Proctor", "Diaz", "Graham", "Watkins", "Hinton", "Marsh",
                "Hewitt", "Branch" };

        Random r = new Random(System.currentTimeMillis());

        return lastnames[r.nextInt(lastnames.length)];
    }

    private static String randomEmail(String fname, int year) {
        String[] mails = { "gmail", "yahoo", "hotmail", "outlook" };

        Random r = new Random(System.currentTimeMillis());

        if (r.nextDouble() < 0.5) {
            return Integer.toString(year) + fname + "@" + mails[r.nextInt(mails.length)] + ".com";
        }
        return fname + Integer.toString(year) + "@" + mails[r.nextInt(mails.length)] + ".com";
    }

    private static LocalDate randomDate(int min, int var) {
        Random r = new Random(System.currentTimeMillis());

        return LocalDate.of(min + r.nextInt(var), r.nextInt(12) + 1, r.nextInt(28) + 1);
    }

    private static String randomPhone() {
        String phone = "09";
        Random r = new Random(System.currentTimeMillis());

        for (int i = 0; i < 9; i++) {
            phone += Integer.toString(r.nextInt(10));
        }

        return phone;
    }

    private static String randomAddress() {
        String[] regions = { "Zafaranieh", "Sahand", "Abresan", "Shahnaz", "Maralan" };
        String[] alleys = { "Shargi", "Gharbi" };

        return "Tabriz, " + regions[r.nextInt(regions.length)] + ", " + Integer.toString(r.nextInt(20)) + "om "
                + alleys[r.nextInt(2)] + ", Pelak" + Integer.toString(r.nextInt(60));
    }

    private static String randomStringOfNumbers(int n) {
        Random r = new Random(System.currentTimeMillis());
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String ans = "";

        for (int i = 0; i < n; i++) {
            ans += Integer.toString(r.nextInt(10));
        }

        return ans;
    }

    private static String randomBic() {
        String bic;
        do {
            bic = randomStringOfNumbers(16);
        } while (DBConnector.containsBIC(bic) || bic.charAt(0) == '0');

        return bic;
    }

    //===================================================================================================================================
    //===================================================================================================================================
    //===================================================================================================================================
    //===================================================================================================================================

    public static void createRandomTransactions(int count) {
        int countOfAllAccounts = DBConnector.numberOfAccounts();

        for (int i = 0; i < count; i++) {
            int from = r.nextInt(countOfAllAccounts) + 1;
            int to;

            do {
                to = r.nextInt(countOfAllAccounts) + 1;
            } while (to == from);

            long value = (r.nextInt(20) * 5 + 5) * 100000;
            LocalDate date = randomDate(2018, 3);
            String transactionID = Transaction.generateID();

            try {
                DBConnector.addTransaction(Account.generateID(from), Account.generateID(to), value, date,
                        transactionID);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("created total of " + count + " transactions");
        System.exit(0);
    }

    public static void CreateFullData(int n) {
        for (int i = 0; i < n; i++) {
            createUser(i);
        }

        System.out.println("creating samples finished");
        System.exit(0);
    }

    private static void createUser(int index) {
        String fname = randomFirstName();
        String lname = randomLastName();
        String username = "Client" + Integer.toString(index);
        LocalDate birth = randomDate(1980, 30);
        String mail = randomEmail(fname, birth.getYear());
        String phone = randomPhone();
        String address = randomAddress();
        String id = User.generateID(User.CLIENT);
        String nationalCode = randomStringOfNumbers(10);

        try {
            DBConnector.addUser(fname, lname, username, encoder.encode(username), mail, phone, User.CLIENT, address, id,
                    nationalCode, birth, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int m = r.nextInt(4);

        for (int j = 0; j < m; j++) {
            createAccount(id, index);
        }
    }

    private static void createAccount(String ownerID, int userIndex) {
        String bic = randomBic();
        String iban = "IR000000000" + bic;
        String cvv = randomStringOfNumbers(4);
        String cvv2 = randomStringOfNumbers(6);
        LocalDate exDate = randomDate(2015, 15);
        int status = r.nextInt(2);
        long value = r.nextInt(200) * 5 * 10000;
        String accountID = Account.generateID();

        try {
            DBConnector.addAccount(ownerID, bic, iban, cvv, cvv2, exDate, status, value, accountID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (r.nextInt(10) < 1) {
            createLoan(ownerID, accountID, userIndex, Loan.PENDING);
        }

        if (r.nextInt(10) < 1) {
            createLoan(ownerID, accountID, userIndex, Loan.REJECTED);
        }

        if (r.nextInt(10) < 1) {
            createLoan(ownerID, accountID, userIndex, Loan.PAYING);
        }

        int p = r.nextInt(2);
        for (int k = 0; k < p; k++) {
            createLoan(ownerID, accountID, userIndex, Loan.FINISHED);
        }
    }

    private static void createLoan(String ownerID, String accountID, int userIndex, int status) {
        int percentage = r.nextInt(4) * 5 + 5;
        long loanValue = (r.nextInt(10) + 1) * 10000000;
        long totalPay = loanValue * (100 + percentage) / 100;
        long payed = totalPay;
        LocalDate loanDate = randomDate(2020, 10);

        if (userIndex > 1) {
            String guarantorid = User.generateID(User.CLIENT, r.nextInt(userIndex - 1) + 1);

            if (status == Loan.PAYING) {
                payed = payed * (r.nextInt(15) * 5 + 10) / 100;
            } else if (status == Loan.REJECTED || status == Loan.PENDING) {
                payed = 0;
            }

            try {
                DBConnector.addLoan(ownerID, accountID, status, loanValue, percentage, totalPay, payed, loanDate,
                        guarantorid);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void createEmployees() {
        try {
            DBConnector.addUser("Mohammad Mahdi", "Hejazi", "mmhlego",
                    "bab8f06012c8bd59f3e79b36b559c648574f13608a45e0644e1503d1eb76847a", "mmhlego@gmail.com",
                    "09146501380", User.EMPLOYEE, "El Goli - sahand", User.generateID(User.EMPLOYEE), "1111111111",
                    LocalDate.parse("2001-12-21"), 0, 0);

            DBConnector.addUser("Kamyab", "Tabani", "CyberGhost",
                    "61a9d5b22a1bb8a19cee3598859d0d9b74d286b9658aa0c0e7dc40db7e67e2c1", "K.tabani82@gmail.com",
                    "09146559128", User.EMPLOYEE, "Zafaranie", User.generateID(User.EMPLOYEE), "1111111112",
                    LocalDate.parse("2002-01-01"), 0, 0);

            DBConnector.addUser("Pouya", "Afraz", "Pouya",
                    "e5be8dd717c3958b82f0f83c4e0fcb53ea893c632a306e4991d76259e788159d", "pouyaafraz@gmail.com",
                    "09222855759", User.EMPLOYEE, "Tabriz - Unknown", User.generateID(User.EMPLOYEE), "1111111113",
                    LocalDate.parse("2002-01-01"), 0, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createManager() {
        try {
            DBConnector.addUser("Dr J.", "Tanha", "JTanha", encoder.encode("jtanha"), "jtanha@gmail.com", "09127983569",
                    User.MANAGER, "Tabriz university", User.generateID(User.MANAGER), "1111111110",
                    LocalDate.parse("2001-12-21"), 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
