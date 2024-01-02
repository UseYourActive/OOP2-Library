package com.library.backend.services;

import com.library.backend.exception.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * The {@code EmailSender} class provides functionality for sending emails using JavaMail API.
 * It allows customization of the email configuration such as SMTP host, port, and SSL settings.
 * <p>
 * Example Usage:
 * <pre>
 * {@code
 * // Create an EmailSender instance with email configuration
 * EmailSender emailSender = new EmailSender("yourUsername", "yourPassword", "smtp.example.com", "587", true);
 *
 * // Send an email
 * try {
 *     emailSender.sendEmail("recipient@example.com", "Subject", "Body");
 *     // Email sent successfully
 * } catch (EmailException e) {
 *     // Handle the case where sending the email fails
 * }
 * }
 * </pre>
 * In this example, an {@code EmailSender} instance is created with the necessary email configuration,
 * and the {@code sendEmail} method is used to send an email. If the email is sent successfully,
 * the log will indicate success; otherwise, an {@link com.library.backend.exception.EmailException EmailException}
 * is thrown with details about the failure.
 * <p>
 * The {@code EmailSender} class supports both standard SMTP and SMTP with SSL connections.
 *
 * @see com.library.backend.exception.EmailException
 */
public class EmailSenderService {
    private static final Logger logger = LoggerFactory.getLogger(EmailSenderService.class);
    private final String username;
    private final String password;
    private final String smtpHost;
    private final String smtpPort;
    private final boolean useSSL;
    private final Transport transport;

    /**
     * Constructs an {@code EmailSender} instance with the specified email configuration.
     *
     * @param username The username for authentication.
     * @param password The password for authentication.
     * @param smtpHost The SMTP server host.
     * @param smtpPort The SMTP server port.
     * @param useSSL   True if SSL should be used, false otherwise.
     */
    public EmailSenderService(String username, String password, String smtpHost, String smtpPort, boolean useSSL) {
        this.username = username;
        this.password = password;
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.useSSL = useSSL;
        this.transport = initializeTransport();
    }

    /**
     * Constructs an {@code EmailSender} instance with the specified email configuration and pre-initialized transport.
     *
     * @param username   The username for authentication.
     * @param password   The password for authentication.
     * @param smtpHost   The SMTP server host.
     * @param smtpPort   The SMTP server port.
     * @param useSSL     True if SSL should be used, false otherwise.
     * @param transport  Pre-initialized Transport for sending emails.
     */
    public EmailSenderService(String username, String password, String smtpHost, String smtpPort, boolean useSSL, Transport transport) {
        this.username = username;
        this.password = password;
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.useSSL = useSSL;
        this.transport = transport;
    }

    /**
     * Sends an email with the specified recipient, subject, and body.
     *
     * @param to      The recipient's email address.
     * @param subject The subject of the email.
     * @param body    The body/content of the email.
     * @throws EmailException If there is an issue sending the email.
     */
    public void sendEmail(String to, String subject, String body) throws EmailException {
        if (transport == null) {
            throw new EmailException("Transport is not set. Cannot send email.");
        }

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

            transport.connect(smtpHost, username, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            logger.info("Email sent successfully!");

        } catch (MessagingException e) {
            logger.error("Error sending email", e);
            throw new EmailException("Failed to send email", e);
        }
    }

    /**
     * Retrieves and configures the email properties based on the provided SMTP configuration.
     * This method is used internally to set up the properties required for sending emails.
     *
     * @return A {@link Properties} object containing the configured email properties.
     */
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

    /**
     * Initializes and returns the Transport object for sending emails.
     * This method sets up the email session with the provided authentication and properties.
     *
     * @return A configured {@link Transport} object for sending emails.
     * @throws RuntimeException If an error occurs while initializing the Transport object.
     */
    private Transport initializeTransport() {
        Properties props = getProperties();
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            return session.getTransport("smtp");
        } catch (NoSuchProviderException e) {
            throw new RuntimeException("Error initializing Transport", e);
        }
    }
}
