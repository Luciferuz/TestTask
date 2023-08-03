package com.antsiferov.testtask.services;

import com.antsiferov.testtask.entities.Book;
import com.antsiferov.testtask.entities.Event;
import com.antsiferov.testtask.entities.Reader;
import com.antsiferov.testtask.enums.EventType;
import com.antsiferov.testtask.repositories.EventRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Transactional
public class EventService {

    private EventRepository eventRepository;

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public Book findMostPopularBook(EventType event, LocalDateTime from, LocalDateTime to) {
        return eventRepository.findMostPopularBook(event, from, to);
    }

    public Reader findMostPopularReader(EventType event, LocalDateTime from, LocalDateTime to) {
        return eventRepository.findMostPopularReader(event, from, to);
    }

}