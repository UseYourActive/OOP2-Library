package services;

import com.library.backend.exception.email.EmailException;
import com.library.backend.exception.email.TransportException;
import com.library.backend.services.EmailSenderService;
import org.junit.jupiter.api.Test;

import javax.mail.*;
import javax.mail.internet.MimeMessage;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EmailSenderServiceTest {
    private static final String TEST_USERNAME = "ooplibrary7@gmail.com";
    private static final String TEST_PASSWORD = "ngjh lkzt ehwl urpq";
    private static final String TEST_SMTP_HOST = "smtp.gmail.com";
    private static final String TEST_SMTP_PORT = "587";
    private static final boolean TEST_USE_SSL = false;

    @Test
    public void testSendEmailWithInvalidRecipient() throws TransportException {
        EmailSenderService emailSenderService = new EmailSenderService(TEST_USERNAME, TEST_PASSWORD, TEST_SMTP_HOST, TEST_SMTP_PORT, TEST_USE_SSL);
        assertThrows(EmailException.class, () ->
                emailSenderService.sendEmail("invalid-email", "Test Subject", "This is a test email.")
        );
    }

    @Test
    public void testSendEmailWithTransportError() throws MessagingException {
        Transport mockTransport = mock(Transport.class);

        // Inject the mock Transport into the EmailSender
        EmailSenderService emailSenderService = new EmailSenderService(TEST_USERNAME, TEST_PASSWORD, TEST_SMTP_HOST, TEST_SMTP_PORT, TEST_USE_SSL, mockTransport);

        // Simulate a Transport error
        when(mockTransport.isConnected()).thenReturn(false);

        // Simulate that the Transport's sendMessage method throws a MessagingException
        doThrow(new MessagingException("Simulated transport error")).when(mockTransport).sendMessage(any(Message.class), any(Address[].class));

        // Expect an EmailException to be thrown
        assertThrows(EmailException.class, () -> {
            emailSenderService.sendEmail("recipient@example.com", "Test Subject", "This is a test email.");
        });
    }

    @Test
    public void testSendEmailSuccessfully() throws Exception {
        // Create a mock Transport
        Transport mockTransport = mock(Transport.class);

        // Inject the mock Transport into the EmailSender
        EmailSenderService emailSenderService = new EmailSenderService(TEST_USERNAME, TEST_PASSWORD, TEST_SMTP_HOST, TEST_SMTP_PORT, TEST_USE_SSL, mockTransport);

        // Create a valid email
        String to = "recipient@example.com";
        String subject = "Test Subject";
        String body = "This is a test email.";

        // Execute the sendEmail method
        emailSenderService.sendEmail(to, subject, body);

        // Verify that the mock Transport's connect and sendMessage methods were called
        verify(mockTransport, times(1)).connect(eq(TEST_SMTP_HOST), eq(TEST_USERNAME), eq(TEST_PASSWORD));
        verify(mockTransport, times(1)).sendMessage(any(MimeMessage.class), any(Address[].class));
        verify(mockTransport, times(1)).close();
    }

    @Test
    public void testSendEmailWithTransportInjectionSuccessfully() throws Exception {
        // Create a mock Transport
        Transport mockTransport = mock(Transport.class);

        // Inject the mock Transport into the EmailSender
        EmailSenderService emailSenderService = new EmailSenderService(TEST_USERNAME, TEST_PASSWORD, TEST_SMTP_HOST, TEST_SMTP_PORT, TEST_USE_SSL, mockTransport);

        // Create a valid email
        String to = "recipient@example.com";
        String subject = "Test Subject";
        String body = "This is a test email.";

        // Execute the sendEmail method
        emailSenderService.sendEmail(to, subject, body);

        // Verify that the mock Transport's connect and sendMessage methods were called
        verify(mockTransport, times(1)).connect(eq(TEST_SMTP_HOST), eq(TEST_USERNAME), eq(TEST_PASSWORD));
        verify(mockTransport, times(1)).sendMessage(any(MimeMessage.class), any(Address[].class));
        verify(mockTransport, times(1)).close();
    }

    @Test
    public void testSendEmailWithInvalidRecipientAddress() throws TransportException {
        // Create an EmailSender
        EmailSenderService emailSenderService = new EmailSenderService(TEST_USERNAME, TEST_PASSWORD, TEST_SMTP_HOST, TEST_SMTP_PORT, TEST_USE_SSL);

        // Create an invalid email address
        String to = "invalid-email-address";

        // Execute the sendEmail method and expect an EmailException
        assertThrows(EmailException.class, () -> emailSenderService.sendEmail(to, "Test Subject", "This is a test email."));
    }

    @Test
    public void testSendEmailWithMessagingException() throws Exception {
        // Create a mock Transport that throws a MessagingException
        Transport mockTransport = mock(Transport.class);
        doThrow(new MessagingException("Simulated transport error")).when(mockTransport).sendMessage(any(MimeMessage.class), any(Address[].class));

        // Inject the mock Transport into the EmailSender
        EmailSenderService emailSenderService = new EmailSenderService(TEST_USERNAME, TEST_PASSWORD, TEST_SMTP_HOST, TEST_SMTP_PORT, TEST_USE_SSL, mockTransport);

        // Create a valid email
        String to = "recipient@example.com";
        String subject = "Test Subject";
        String body = "This is a test email.";

        // Execute the sendEmail method and expect an EmailException
        assertThrows(EmailException.class, () -> emailSenderService.sendEmail(to, subject, body));
    }

    @Test
    public void testSendEmailWithAuthenticationException() throws Exception {
        Transport mockTransport = mock(Transport.class);
        doThrow(new AuthenticationFailedException("Simulated authentication error")).when(mockTransport).connect(anyString(), anyString(), anyString());

        EmailSenderService emailSenderService = new EmailSenderService(TEST_USERNAME, TEST_PASSWORD, TEST_SMTP_HOST, TEST_SMTP_PORT, TEST_USE_SSL, mockTransport);

        String to = "recipient@example.com";
        String subject = "Test Subject";
        String body = "This is a test email.";

        assertThrows(EmailException.class, () -> emailSenderService.sendEmail(to, subject, body));
    }

    @Test
    public void testSendEmailWithGeneralException() throws MessagingException {
        Transport mockTransport = mock(Transport.class);
        doThrow(new RuntimeException("Simulated general error")).when(mockTransport).connect(anyString(), anyString(), anyString());

        EmailSenderService emailSenderService = new EmailSenderService(TEST_USERNAME, TEST_PASSWORD, TEST_SMTP_HOST, TEST_SMTP_PORT, TEST_USE_SSL, mockTransport);

        String to = "recipient@example.com";
        String subject = "Test Subject";
        String body = "This is a test email.";

        assertThrows(RuntimeException.class, () -> emailSenderService.sendEmail(to, subject, body));
    }
}
