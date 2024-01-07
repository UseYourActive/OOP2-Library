package com.library.backend.services;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.library.backend.engines.SearchEngine;
import com.library.backend.exception.IncorrectInputException;
import com.library.backend.exception.ReaderException;
import com.library.backend.exception.email.EmailException;
import com.library.backend.exception.email.TransportException;
import com.library.backend.exception.searchengine.SearchEngineException;
import com.library.database.entities.*;
import com.library.database.enums.BookFormStatus;
import com.library.database.enums.BookStatus;
import com.library.database.enums.Ratings;
import com.library.database.repositories.*;
import com.library.frontend.utils.DialogUtils;
import com.library.frontend.utils.SceneLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OperatorService implements Service {
    private static final Logger logger = LoggerFactory.getLogger(OperatorService.class);
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final BookInventoryRepository bookInventoryRepository;
    private final BookFormRepository bookFormRepository;
    private final EventNotificationRepository eventNotificationRepository;
    private final ReaderRatingRepository readerRatingRepository;

    private EmailSenderService emailSenderService;

    private SearchEngine<Reader> readerSearchEngine;

    private SearchEngine<BookInventory> bookInventorySearchEngine;

    private double ratingValue;

    private List<BookForm> overdueBookForms;
    private ObservableList<Book> selectedBooks;

    public OperatorService(BookRepository bookRepository, ReaderRepository readerRepository, BookInventoryRepository bookInventoryRepository, BookFormRepository bookFormRepository, EventNotificationRepository eventNotificationRepository, ReaderRatingRepository readerRatingRepository) {
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
        this.bookInventoryRepository = bookInventoryRepository;
        this.bookFormRepository = bookFormRepository;
        this.eventNotificationRepository = eventNotificationRepository;
        this.readerRatingRepository = readerRatingRepository;
    }


    //public void archiveBook(Book book){
    //    book.setBookStatus(BookStatus.ARCHIVED);
//
    //    //operatorService.saveBook(book);
    //}

    public void updateBookForms(){
        List<BookForm> bookForms=bookFormRepository.findAll();
        for (BookForm bookForm : bookForms) {
            if (bookForm.isPresent() && bookForm.isOverdue()) {
                bookForm.setStatus(BookFormStatus.LATE);
                EventNotification eventNotification= EventNotification.builder()
                        .user(SceneLoader.getUser())
                        .timestamp(LocalDateTime.now())
                        .message("The deadline for returning books of: "+bookForm.getReader().getFullName() + " has passed.")
                        .build();

                eventNotificationRepository.save(eventNotification);
                //operatorService.saveEventNotification(eventNotification);
                bookFormRepository.save(bookForm);
                //operatorService.saveNewBookForm(bookForm);
            }
        }
    }

    public void removeFromSelectedBooks(Book book){
        selectedBooks.remove(book);
    }

    public void addSelectedBookToList(Book book){
        if (!selectedBooks.contains(book))
            selectedBooks.add(book);
    }
    public void initializeSelectedBooks(){
        selectedBooks = FXCollections.observableArrayList();
    }

    public ObservableList<Book> getSelectedBooks(){
        return selectedBooks;
    }


    public void setAllOverdueBooks(){
        overdueBookForms=bookFormRepository.findAll().stream().filter(BookForm::isOverdue).toList();
    }

    public List<BookForm> getOverdueBookForms(){
        return overdueBookForms;
    }

    public Collection<BookInventory> searchBookInventory(String stringToSearch) throws SearchEngineException {
        return bookInventorySearchEngine.search(bookInventoryRepository.findAll(),stringToSearch);
    }

    public List<EventNotification> getEventNotifications(User user){
        List<EventNotification> allEventNotifications =  eventNotificationRepository.findAll();
        return allEventNotifications.stream().filter(event -> event.getUser().equals(user)).toList();
    }

    public void createReader(String firstName,String middleName,String lastName,String phoneNumber,String email) throws IncorrectInputException {
        checkInput(firstName,middleName,lastName,phoneNumber);

        ReaderRating readerRating= ReaderRating.builder()
                .rating(Ratings.NONE)
                .currentValue(-1)
                .coefficient(0)
                //.readers(new ArrayList<>())
                .build();


        Reader reader = Reader.builder()
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .email(email)
                .phoneNumber(phoneNumber)
                .bookForms(Lists.newArrayList())
                .readerRating(readerRating)
                .build();

        // readerRating.getReaders().add(reader);

        //operatorService.saveRating(readerRating);
        readerRepository.save(reader);
        //operatorService.createReader(reader);
    }


    private void checkInput(String firstName,String middleName,String lastName,String phoneNumber) throws IncorrectInputException {
        if (firstName.isEmpty())
            throw new IncorrectInputException("Please enter first name.");

        if (middleName.isEmpty())
            throw new IncorrectInputException("Please enter middle name.");

        if (lastName.isEmpty())
            throw new IncorrectInputException("Please enter last name.");

        if (phoneNumber.isEmpty())
            throw new IncorrectInputException("Please enter phone number.");

    }

    public double getRatingValue(){
        return ratingValue;
    }
    public void setRatingValue(double value){
        ratingValue=value;
    }
    public void lendReadingRoomBooks(Reader selectedReader,List<Book> bookList) throws ReaderException {
        if (selectedReader.getReaderRating().getRating() == Ratings.ZERO_STAR){
            throw new ReaderException("Reader is not allowed to take books anymore.\nHis rating is too low.");
        }

        changeBookStatus(bookList, BookStatus.IN_READING_ROOM);

        BookForm bookForm = BookForm.builder()
                .reader(selectedReader)
                .books(bookList)
                .status(BookFormStatus.IN_USE)
                .expirationDate(LocalDateTime.now().plusHours(12))
                .dateOfCreation(LocalDateTime.now())
                .build();

        bookFormRepository.save(bookForm);
        //operatorService.saveNewBookForm(bookForm);

        selectedReader.getBookForms().add(bookForm);

        readerRepository.save(selectedReader);
        //operatorService.saveReader(selectedReader);
    }

    public void lendBooks(Reader selectedReader,List<Book> bookList) throws ReaderException {
        //Reader selectedReader = readerTableView.getSelectionModel().getSelectedItem();

        if (selectedReader.getReaderRating().getRating() == Ratings.ZERO_STAR)
            throw new ReaderException("The reader is not allowed to take books anymore.");

        if (bookList.stream().allMatch(book -> book.getBookStatus().equals(BookStatus.AVAILABLE))) {

            changeBookStatus(bookList, BookStatus.LENT);

            BookForm bookForm = BookForm.builder()
                    .reader(selectedReader)
                    .books(bookList)
                    .status(BookFormStatus.IN_USE)
                    .expirationDate(LocalDateTime.now().plusMonths(1))
                    .dateOfCreation(LocalDateTime.now())
                    .build();

            bookFormRepository.save(bookForm);
            //operatorService.saveNewBookForm(bookForm);

            selectedReader.getBookForms().add(bookForm);

            readerRepository.save(selectedReader);
            //operatorService.saveReader(selectedReader);

        } else{
            throw new ReaderException("For normal lending reader\ncan take only AVAILABLE books.");
        }
    }

    public Collection<Reader> searchReader(String stringToSearch) throws SearchEngineException {
        return readerSearchEngine.search(readerRepository.findAll(),stringToSearch);
    }


    public void returnBooks(BookForm bookForm,List<Book> damagedBooks,List<Book> allBooks){
        List<Book> booksToReturn = new ArrayList<>();

        for (Book book : allBooks) {
            if (damagedBooks.contains(book))
                book.setBookStatus(BookStatus.DAMAGED);
            else
            {
                if(book.getPreviousBookStatus()==null)
                    throw new RuntimeException("Failed to return books");

                if(book.getPreviousBookStatus()==BookStatus.ARCHIVED)
                    book.setBookStatus(BookStatus.ARCHIVED);
                else
                    book.setBookStatus(BookStatus.AVAILABLE);
            }

            book.setNumberOfTimesUsed(book.getNumberOfTimesUsed()+1);
            booksToReturn.add(book);

            if(book.getPreviousBookStatus()!=BookStatus.ARCHIVED&&book.getNumberOfTimesUsed()==50){
                EventNotification eventNotification= EventNotification.builder()
                        .user(SceneLoader.getUser())
                        .timestamp(LocalDateTime.now())
                        .message("("+LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))+")"+"Consider archiving of: "+book.getTitle()+"("+book.getId()+")")
                        .build();

                eventNotificationRepository.save(eventNotification);
                //operatorService.saveEventNotification(eventNotification);
            }
        }

        bookRepository.saveAll(booksToReturn);
        //operatorService.saveAllBooks(booksToReturn);

        Reader reader=bookForm.getReader();


        //Reader is going to be demoted if deadline of returning books have passed
        if (bookForm.isOverdue()) {
            reader.demote();
        } else {
            reader.promote();
        }

        //For every damaged book returned reader is going to be demoted
        for(int i=0;i<damagedBooks.size();i++){
            reader.demote();
        }

        bookForm.setStatus(BookFormStatus.RETURNED);

        readerRepository.save(reader);
        //operatorService.saveReader(reader);
    }


    public void loadEmailSettings(String username,String password){
        try {
            emailSenderService = new EmailSenderService(username, password, "smtp.gmail.com", "587", false);
        } catch (TransportException e) {
            logger.error("Couldn't send email", e);
        }

    }

    public void sendEmail(Reader reader,String subject,String message) throws EmailException {
        emailSenderService.sendEmail(reader.getEmail(), subject, message);
    }


    public void saveEventNotification(EventNotification eventNotification){
        eventNotificationRepository.saveNotification(eventNotification);
    }

    public void saveBook(Book book){
        bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        logger.info("Retrieved {} books from the repository.", books.size());
        return books;
    }


    public void saveAllBooks(List<Book> books) {
        bookRepository.saveAll(books);
    }

    public void removeAllBookForms(List<BookForm> bookForms) {
        bookFormRepository.deleteAll(bookForms);
    }

    public List<BookForm> getAllBookForms() {
        List<BookForm> bookForms = bookFormRepository.findAll();
        logger.info("Retrieved {} books from the repository.", bookForms.size());
        return bookForms;
    }

    public void lendBook(Book book) {
        updateBookStatus(book, BookStatus.LENT, "lent");
    }

    public void archiveBook(Book book) {
        updateBookStatus(book, BookStatus.ARCHIVED, "archived");
    }

    private void updateBookStatus(Book book, BookStatus newStatus, String action) {
        Preconditions.checkNotNull(book, "Book cannot be null");
        book.setBookStatus(newStatus);
        boolean result = bookRepository.save(book);
        if (result) {
            logger.info("Book {} successfully: {}", action, book.getTitle());
        } else {
            logger.error("Failed to {} book: {}", action, book.getTitle());
        }
    }

    public void saveReader(Reader reader) {
        readerRepository.save(reader);
    }

    private void changeBookStatus(Collection<Book> books, BookStatus status) {

        Set<Book> bookSet = new HashSet<>();
        for (Book book : books) {
            book.updatePreviousBookStatus();
            book.setBookStatus(status);
            bookSet.add(book);
        }
        bookRepository.saveAll(bookSet);
    }

    public void saveNewBookForm(BookForm bookForm) {
        Preconditions.checkNotNull(bookForm, "Book form cannot be null");
        bookFormRepository.save(bookForm);
        logger.info("Created new book form: {}", bookForm);
    }

    public List<Reader> getAllReaders() {
        List<Reader> readers = readerRepository.findAll();
        logger.info("Retrieved {} readers from the repository.", readers.size());
        return readers;
    }

    public void createReader(Reader reader) {
        Preconditions.checkNotNull(reader, "Reader cannot be null");
        readerRepository.save(reader);
        logger.info("Created a new reader: {}", reader);
    }

    public void removeReader(Reader reader) {
        Preconditions.checkNotNull(reader, "Reader cannot be null");
        readerRepository.delete(reader);
        logger.info("Removed reader: {}", reader);
    }

    public List<BookInventory> getAllBookInventories() {
        List<BookInventory> inventories = bookInventoryRepository.findAll();
        logger.info("Retrieved {} book inventories from the repository.", inventories.size());
        return inventories;
    }
}
