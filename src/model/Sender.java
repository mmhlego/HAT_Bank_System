package model;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Sender {

    public static String HTML = "<!DOCTYPE html>"+
"<html lang=\"en\">" +
"<head>"+
    "<meta charset=\"UTF-8\">"+
    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"+
            "<title>Account Info</title>" +
    "<style>"+
        "fieldset{"+
            "border: 10px double white;" +
            "font-family:sans-serif;" +
        "}"+
        "h1 , h4{"+
            "font-family: monospace;"+
        "}"+
        "a{" +
            "text-decoration: none;" +
            "text-decoration-color: black;" +
            "cursor: text;" +
        "}" +
    "</style>"+
"</head>" +
"<body style=\"text-align: center;background-image: linear-gradient(to right, #43F061, #12E7DD);\">" +
    "<div class=\"container\" style=\"text-align: center; display: inline-block; position: relative;\">" +
        "<h1 style=\"color: #fff; letter-spacing: 14px; word-spacing: 10px;\"> WELCOME TO HAT BANK </h1>" +
        "<h4 style=\"text-align: left; font-family: Times New Roman;\"> Dear  , </h4>" +
        "<p style=\"text-align: left; font-family: fantasy;\"> Your Account Has Been Created Successfully Here Is Your Information</p>" +
        "<fieldset style=\"padding: 30px; text-align: left; border-top-right-radius: 50px; border-bottom-left-radius: 50px;\">" +
            "<legend align=\"center\">&nbsp; Your Account Information &nbsp;</legend>"+
            "<label style=\"color: #0a3d62;\"><strong> Firstname : </strong></label>&nbsp;"+
            "<span>  </span>" +
            "<br><br>" +
            "<label style=\"color: #0a3d62;\"><strong> Lastname : </strong></label>&nbsp;" +
            "<span>  </span>" +
            "<br><br>"+
            "<label style=\"color: #0a3d62;\"><strong> Email : </strong></label>&nbsp;" +
            "<a>  </a>" +
            "<br><br>" +
            "<label style=\"color: #0a3d62;\"><strong> Nationalcode : </strong></label>&nbsp;" +
            "<span>  </span>" +
           " <br><br>" +
            "<label style=\"color: #0a3d62;\"><strong> Birthdate : </strong></label>&nbsp;" +
            "<span>  </span>" +
            "<br><br>" +
            "<label style=\"color: #0a3d62;\"><strong> Phonenumber : </strong></label>&nbsp;" +
            "<span>  </span>" +
            "<br><br>" +
            "<label style=\"color: #0a3d62;\"><strong> Address : </strong></label>&nbsp;" +
            "<span>  </span>" +
            "<br><br>" +
            "<label style=\"color: #0a3d62;\"><strong> Username : </strong></label>&nbsp;" +
            "<span>  </span>" +
            "<br><br>" +
            "<label style=\"color: #0a3d62;\"><strong> Password : </strong></label>&nbsp;" + 
            "<span>  </span>" +
        "</fieldset>" +
            "<br>" +
        "<p style=\"text-align: center;font-family: fantasy;\"> This Email Has Been Generated Automatically And There Is No Need To Reply ! </p> <br>"+
        "<h4 style=\"display: inline;\"> This Message Was Sent To </h4>" +
        "<a style=\"font-size: 16; color: blue; \">  </a>" +
        "<h6> &#128272; <i> Powered By HAT Bank System &#8482; </i> &#9989; </h6>" +
    "</div>" +
"</body>" +
"</html>";

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

}
