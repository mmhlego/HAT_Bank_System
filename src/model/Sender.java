package model;

import java.io.*;
import java.net.URL;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import controller.PasswordChangerController;

public class Sender {
    public static final int Signupmail = 0, Loginmail = 1,SMSMail=2;
    private static String HTML;
    public static String ip;

    public static void SendEmail(String recepient, String Title, int MailType) throws Exception {
        /*if (MailType == Signupmail) {
            LoadSignUpMail();
        } else if (MailType == Loginmail) {
            LoadLoginMail();
        }*/

        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myAccountEmail = "HAT.Sender.Service@gmail.com";
        String password = "HAT_Logo_Ghost_VLB_80_81_81";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        Message message = new MimeMessage(session);
     
        try {
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject(Title);
            message.reply(false);

            switch(MailType){
                case Signupmail:
                    LoadSignUpMail();
                    message.setContent(HTML, "text/html");
                    break;
                case Loginmail:
                    LoadLoginMail();
                    message.setContent(HTML, "text/html");
                    break;
                case SMSMail:
                    LoadSMS();
                    message.setText(HTML);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Send mail
        Transport.send(message);
        System.out.println("Message sent successfully");
    }

    private static void LoadSMS(){
        HTML="Your OTP Password is : \n"+PasswordChangerController.Code;
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
        User currentuser = UserController.getCurrentUser();
        HTML = HTML.replace("{userfirstname}", currentuser.FirstName);
        HTML = HTML.replace("{userlastname}", currentuser.LastName);
        HTML = HTML.replace("{useremail}", currentuser.Email);
        HTML = HTML.replace("{usernationalcode}", currentuser.NationalCode);
        HTML = HTML.replace("{userbirth}", currentuser.BirthDate.toString());
        HTML = HTML.replace("{userphonenumber}", currentuser.PhoneNumber);
        HTML = HTML.replace("{useraddress}", currentuser.Address);
        HTML = HTML.replace("{userusername}", currentuser.Username);
        HTML = HTML.replace("{userpassword}", currentuser.Password);
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
