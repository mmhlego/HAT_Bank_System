package model;

import java.util.Random;
import java.time.LocalDate;

public class SampleCreator {

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

    private static LocalDate randomBirthDate() {
        Random r = new Random(System.currentTimeMillis());

        return LocalDate.of(1980 + r.nextInt(30), r.nextInt(12) + 1, r.nextInt(30) + 1);
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
        return "";
    }

    private static String randomNationalCode() {
        Random r = new Random(System.currentTimeMillis());
        String nc = "";

        for (int i = 0; i < 10; i++) {
            nc += Integer.toString(r.nextInt(10));
        }

        return nc;
    }

    public static void CreateFullData(int n) {
        for (int i = 0; i < n; i++) {
            String fname = randomFirstName();
            String lname = randomLastName();
            String username = "Client" + Integer.toString(i);
            LocalDate birth = randomBirthDate();
            String mail = randomEmail(fname, birth.getYear());
            System.out.println(mail);
            String phone = randomPhone();
            String address = randomAddress();
            String id = User.generateID(User.CLIENT);
            String nationalCode = randomNationalCode();

            //new User(firstName, lastName, username, password, email, phone, accessLevel, address, id, nationalCode, birthDate, theme, language)

            /*User u = new User(fname, lname, username, encoder.encode(username), mail, phone, User.CLIENT, address, id,
                    nationalCode, birth, 0, 0);*/

            try {
                DBConnector.addUser(fname, lname, username, encoder.encode(username), mail, phone, User.CLIENT, address,
                        id, nationalCode, birth, 0, 0);

            } catch (Exception e) {
                e.printStackTrace();
            }

            int m = new Random(System.currentTimeMillis()).nextInt(5);

            for (int j = 0; i < m; j++) {
                //Account c=new Account(ownerID, iban, cvv, cvv2, exDate, status, value, accountID)
            }
        }
    }
}
