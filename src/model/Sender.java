package model;

import java.io.*;
import java.net.URL;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Sender {

    public static final int Signupmail = 0;
    public static final int Loginmail = 1;
    private static String HTML;
    public static String ip;

    public static void SendEmail(String recepient, String Title, int MailType) throws Exception {
        if (MailType == Signupmail) {
            LoadSignUpMail();
        } else if (MailType == Loginmail) {
            LoadLoginMail();
        }
        // LoadSignUpMail();
        // LoadLoginMail();
        Properties properties = new Properties();

        // Enable authentication :
        properties.put("mail.smtp.auth", "true");
        // Set TLS encryption enabled :
        properties.put("mail.smtp.starttls.enable", "true");
        // Set SMTP host :
        properties.put("mail.smtp.host", "smtp.gmail.com");
        // Set smtp port :
        properties.put("mail.smtp.port", "587");

        String myAccountEmail = "HAT.Sender.Service@gmail.com";
        String password = "HAT_Logo_Ghost_VLB_80_81_81";

        // Create a session with account credentials :
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        // Prepare email message :
        Message message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject(Title);
            // message.setText(Body);
            message.reply(false);
            // Support HTML Version :
            message.setContent(HTML, "text/html");
        } catch (Exception e) {
            e.printStackTrace();
            // Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Send mail
        Transport.send(message);
        System.out.println("Message sent successfully");
    }

    public static void LoadSignUpMail() {
        try {
            Scanner s = new Scanner(new File(System.getProperty("user.dir") + "\\src/model/Mail.html"));
            HTML = "";
            while (s.hasNextLine()) {
                HTML += s.nextLine();
                HTML += "\n";
            }
            s.close();
            replaceSignupData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void LoadLoginMail() throws IOException {
        try {
            Scanner s = new Scanner(new File(System.getProperty("user.dir") + "\\src/model/LoginMail.html"));
            HTML = "";
            while (s.hasNextLine()) {
                HTML += s.nextLine();
                HTML += "\n";
            }
            s.close();
            replaceLoginData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void replaceSignupData() {
        HTML = HTML.replace("{userfirstname}", "Mattew");
        HTML = HTML.replace("{userlastname}", "Sebasyup");
        HTML = HTML.replace("{useremail}", "Sebasman@gmail.com");
        HTML = HTML.replace("{usernationalcode}", "547-459-451");
        HTML = HTML.replace("{userbirth}", "1993-06-26");
        HTML = HTML.replace("{userphonenumber}", "5147-647-153");
        HTML = HTML.replace("{useraddress}", "New York  - Manher St.2 Ave25");
        HTML = HTML.replace("{userusername}", "Mater");
        HTML = HTML.replace("{userpassword}", "Seb12as54");
    }

    private static void replaceLoginData() throws IOException {
        HTML = HTML.replace("{Location}", getIP());
        HTML = HTML.replace("{Date}", String.format("%s", new Date()));
    }

    public static String getIP() throws IOException {
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
        String ip = in.readLine();
        return ip;
    }
}
