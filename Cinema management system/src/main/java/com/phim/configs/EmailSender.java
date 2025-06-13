
package com.phim.configs;

import java.io.FileInputStream; // Correct import statement
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
    
    private String host;
    private String user;
    private String pass;
    private String port;
    private String encryption;
    private String from;
    
    public EmailSender() {
        host = "smtp.gmail.com";
        port = "587";
        user = "mailanaanh35@gmail.com";
        pass = "nzfh hesi efzl tbqs"; 
        encryption = "TLS";
        from = "CoffeMate@gmail.com";  // Nếu bạn muốn hiển thị người gửi là tên khác
    }


    public void sendEmail(String to, String subject, String content) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", encryption.equalsIgnoreCase("TLS"));
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from.isEmpty() ? user : from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);

            System.out.println("Email sent successfully");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
