package com.library.database.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book_inventories")
public class BookInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long id;


    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Book> bookList;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", nullable = false,unique = true)
    private Book representiveBook; // Represents one book from the bookList

    @Override
    public String toString(){

        if(representiveBook.getPublishYear()==null){
            return String.format("Title: %s\nAuthor %s\nGenre: %s\t\tQuantity: %d\nPublish Year: - \nStatus: %s\nResume:\n%s",
                    representiveBook.getTitle(), representiveBook.getAuthor(), representiveBook.getGenre(),bookList.size(), representiveBook.getBookStatus(),representiveBook.getResume());
        }

        return String.format("Title: %s\nAuthor %s\nGenre: %s\t\tQuantity: %d\nPublish Year: %s\nStatus: %s\nResume:\n%s",
                representiveBook.getTitle(), representiveBook.getAuthor(),representiveBook.getGenre(),bookList.size(),representiveBook.getPublishYear(),representiveBook.getBookStatus(),representiveBook.getResume());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookInventory that = (BookInventory) o;
        return bookList.equals(that.bookList) && representiveBook.equals(that.representiveBook);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookList, representiveBook);
    }

    public boolean addBook(Book book){
        return bookList.add(book);
    }
}
