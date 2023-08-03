package com.antsiferov.testtask.entities;

import com.antsiferov.testtask.enums.EventType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Reader reader;

    @ManyToOne
    private Book book;

    private LocalDateTime localDateTime;

    @Enumerated(EnumType.STRING)
    private EventType event;

}