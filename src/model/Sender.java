package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Sender {

    public static String HTML;

    public static void SendEmail(String recepient, String Title, String Body) throws Exception {
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

    public static void Load() {
        try {
            Scanner s = new Scanner(new File(System.getProperty("user.dir") + "/model/Mail.html"));

            while (s.hasNextLine()) {
                HTML += s.nextLine();
                HTML += "\n";
            }

            s.close();

            replaceData();

            System.out.println(HTML);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void replaceData() {
        HTML = HTML.replace("{firstName}", UserController.getCurrentUser().FirstName);
    }
}