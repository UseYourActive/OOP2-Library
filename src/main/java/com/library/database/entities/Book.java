package com.library.database.entities;

import com.library.database.enums.BookStatus;
import com.library.database.enums.Genre;
import jakarta.persistence.*;
import lombok.*;

import java.lang.reflect.Field;
import java.time.Year;
import java.util.*;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(name = "amount_of_copies")
    private Integer amountOfCopies;

    @Column(name = "number_of_times_used", nullable = false)
    private Integer numberOfTimesUsed;

    @Column(name = "publish_date",nullable = false)
    private Year publishYear;

    @Column(name = "title", length = 64, nullable = false)
    private String title;

    @Column(name = "resume", length = 512)
    private String resume;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @Column(name = "isbn", length = 16, nullable = false,unique = true)
    private String isbn;

    @Enumerated(EnumType.STRING)
    @Column(name = "genres")
    private Genre genre;

    @Enumerated(EnumType.STRING)
    @Column(name = "book_status", nullable = false)
    private BookStatus bookStatus;

    @Override
    public String toString() {

        //Map<Field,String> nonNullFields=new HashMap<>();
        //List<Field> fields=  Arrays.stream(getClass().getDeclaredFields()).toList();
        //for(Field field:fields){
        //    if(field !=null){
//
        //        switch (field.getName()) {
        //            case "title" -> nonNullFields.put(field, "Title: " + field);
        //            case "author" -> nonNullFields.put(field, "\nAuthor: " + field);
        //            case "genre" -> nonNullFields.put(field, "\nGenre: " + field);
        //            case "publishYear" ->nonNullFields.put(field, "\nPublish Year: " + field);
        //            case "isbn" -> nonNullFields.put(field, "\nISBN: " + field);
        //            case "resume" -> nonNullFields.put(field, "\nResume: " + field);
        //        }
        //    }
//
        //    try {
        //        if(field == bookStatus.getClass().getDeclaredField("bookStatus")){
//
        //        }
        //    } catch (NoSuchFieldException e) {
        //        throw new RuntimeException(e);
        //    }
        //}
//
        //StringBuilder builder=new StringBuilder();
//
        //for(Map.Entry<Field,String> entry: nonNullFields.entrySet()){
        //    builder.append(entry.getValue());
        //}
//
        //return builder.toString();
        return String.format("Title: %s\nAuthor %s\nGenre: %s\nPublish Year: %s\nISBN: %s\nResume:\n%s",
                title, author,genre,publishYear, isbn,resume);
    }
}
