package com.library.backend.services.operator;

import com.library.backend.exception.ReturnBookException;
import com.library.backend.exception.email.EmailException;
import com.library.backend.exception.email.TransportException;
import com.library.backend.services.EmailSenderService;
import com.library.backend.services.Service;
import com.library.database.entities.Book;
import com.library.database.entities.BookForm;
import com.library.database.entities.EventNotification;
import com.library.database.entities.Reader;
import com.library.database.enums.BookFormStatus;
import com.library.database.enums.BookStatus;
import com.library.database.repositories.BookRepository;
import com.library.database.repositories.EventNotificationRepository;
import com.library.database.repositories.ReaderRepository;
import com.library.frontend.SceneLoader;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BookFormShowService implements Service {
    private static final Logger logger = LoggerFactory.getLogger(BookFormShowService.class);
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final EventNotificationRepository eventNotificationRepository;
    @Getter private EmailSenderService emailSenderService;

    public BookFormShowService(BookRepository bookRepository, ReaderRepository readerRepository, EventNotificationRepository eventNotificationRepository) {
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
        this.eventNotificationRepository = eventNotificationRepository;
    }

    public void loadEmailSettings(String username, String password) {
        try {
            emailSenderService = new EmailSenderService(username, password, "smtp.gmail.com", "587", false);
        } catch (TransportException e) {
            logger.error("Couldn't send email", e);
        }
    }

    public void sendEmail(Reader reader, String subject, String message) throws EmailException {
        try {
            emailSenderService.sendEmail(reader.getEmail(), subject, message);
            logger.info("Email sent successfully to '{}'", reader.getEmail());
        } catch (EmailException e) {
            logger.error("Failed to send email to '{}'", reader.getEmail(), e);
            throw e;
        }
    }

    public void returnBooks(BookForm bookForm, List<Book> damagedBooks, List<Book> allBooks) throws ReturnBookException {
        List<Book> booksToReturn = new ArrayList<>();

        try {
            for (Book book : allBooks) {
                if (damagedBooks.contains(book))
                    book.setBookStatus(BookStatus.DAMAGED);
                else {
                    if (book.getPreviousBookStatus() == null)
                        throw new ReturnBookException("Failed to return books");

                    if (book.getPreviousBookStatus() == BookStatus.ARCHIVED)
                        book.setBookStatus(BookStatus.ARCHIVED);
                    else
                        book.setBookStatus(BookStatus.AVAILABLE);
                }

                book.setNumberOfTimesUsed(book.getNumberOfTimesUsed() + 1);
                booksToReturn.add(book);

                if (book.getPreviousBookStatus() != BookStatus.ARCHIVED && book.getNumberOfTimesUsed() == 50) {
                    EventNotification eventNotification = EventNotification.builder()
                            .user(SceneLoader.getUser())
                            .timestamp(LocalDateTime.now())
                            .message("(" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) + ")" + "Consider archiving of: " + book.getTitle() + "(" + book.getId() + ")")
                            .build();

                    eventNotificationRepository.save(eventNotification);
                    logger.info("Event notification saved: '{}'", eventNotification.getMessage());
                    //operatorService.saveEventNotification(eventNotification);
                }
            }
        } catch (ReturnBookException e){
            logger.error(e.getMessage(), e);
            throw e;
        }

        bookRepository.saveAll(booksToReturn);
        logger.info("Books returned successfully");

        Reader reader = bookForm.getReader();

        //Reader is going to be demoted if the deadline for returning books has passed
        if (bookForm.isOverdue()) {
            reader.demote();
            logger.info("Reader demoted due to overdue books");
        } else {
            reader.promote();
            logger.info("Reader promoted for returning books on time");
        }

        //For every damaged book returned reader is going to be demoted
        for (int i = 0; i < damagedBooks.size(); i++) {
            reader.demote();
            logger.info("Reader demoted due to damaged book");
        }

        bookForm.setStatus(BookFormStatus.RETURNED);

        readerRepository.save(reader);
        logger.info("Reader information saved successfully");
        //operatorService.saveReader(reader);
    }
}
