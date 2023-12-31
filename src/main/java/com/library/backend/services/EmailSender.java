package com.library.backend.services;

import com.library.backend.exception.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {
    private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);
    private final String username; // email address: alexorozov@gmail.com
    private final String password; // email password: Parolka123!
    private final String smtpHost; // The SMTP server host - smtp.gmail.com - for gmail
    private final String smtpPort; // The SMTP server port - for gmail is 587
    private final boolean useSSL;  // Set this to true if SSL is required, false for TLS

    public EmailSender(String username, String password, String smtpHost, String smtpPort, boolean useSSL) {
        this.username = username;
        this.password = password;
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.useSSL = useSSL;
    }

    public void sendEmail(String to, String subject, String body) throws EmailException {
        Properties props = getProperties();

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            logger.info("Email sent successfully!");

        } catch (MessagingException e) {
            logger.error("Error sending email", e);
            throw new EmailException("Error sending email", e);
        }
    }

    private Properties getProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        if (useSSL) {
            props.put("mail.smtp.socketFactory.port", smtpPort);
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.fallback", "false");
        } else {
            props.put("mail.smtp.starttls.enable", "true");
        }

        return props;
    }
}


// primer:
//public static void main(String[] args) {
//    String username = "alexorozov@gmail.com";
//    String password = "Parolka123!";
//    String smtpHost = "smtp.gmail.com";
//    String smtpPort = "587";
//    boolean useSSL = false;  // Use true if SSL is required, false for TLS
//
//    EmailSender emailSender = new EmailSender(username, password, smtpHost, smtpPort, useSSL);
//
//    try {
//        String to = "recipient@example.com";
//        String subject = "Test Subject";
//        String body = "This is a test email.";
//
//        emailSender.sendEmail(to, subject, body);
//    } catch (EmailException e) {
//        e.printStackTrace();
//    }
//}