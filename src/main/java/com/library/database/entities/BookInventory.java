package com.library.database.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Map;

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

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "book_id", nullable = false,unique = true)
    private Book book;

    @Column(name="quantity")
    private Integer quantity;

    @Override
    public String toString(){
        if(book.getPublishYear()==null){
            return String.format("Title: %s\nAuthor %s\nGenre: %s\t\tQuantity: %d\nPublish Year: - \nStatus: %s\nResume:\n%s",
                    book.getTitle(), book.getAuthor(),book.getGenre(),quantity,book.getBookStatus(),book.getResume());
        }

        return String.format("Title: %s\nAuthor %s\nGenre: %s\t\tQuantity: %d\nPublish Year: %s\nStatus: %s\nResume:\n%s",
                book.getTitle(), book.getAuthor(),book.getGenre(),quantity,book.getPublishYear(),book.getBookStatus(),book.getResume());
    }
}
