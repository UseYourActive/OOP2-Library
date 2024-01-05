package com.library.database.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * The {@code EventNotification} class represents a notification associated with an event for a specific user.
 * <p>
 * This class is annotated with JPA annotations for entity mapping and is designed to be used with a relational database.
 *
 * @see DBEntity
 * @see Table
 * @see Column
 * @see GeneratedValue
 * @see GenerationType
 * @see ManyToOne
 * @see JoinColumn
 * @see Enumerated
 * @see lombok.Getter
 * @see lombok.Setter
 * @see lombok.NoArgsConstructor
 * @see lombok.AllArgsConstructor
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "event_notifications", indexes = {
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_timestamp", columnList = "timestamp")
})
public class EventNotification implements DBEntity{
    /**
     * The unique identifier for the event notification.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    /**
     * The user associated with the event notification.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * The message content of the event notification (not nullable).
     */
    @Column(name = "message" , nullable = false)
    private String message;

    /**
     * The timestamp indicating when the event notification occurred (not nullable, unique constraint).
     */
    @Column(name = "timestamp", nullable = false, unique = true)
    private LocalDateTime timestamp;

}
