package com.antsiferov.testtask.repositories;

import com.antsiferov.testtask.entities.Book;
import com.antsiferov.testtask.entities.Event;
import com.antsiferov.testtask.entities.Reader;
import com.antsiferov.testtask.enums.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(value = "SELECT e.book FROM Event e WHERE e.event = :eventType AND e.localDateTime BETWEEN :from AND :to GROUP BY e.book ORDER BY COUNT(e.id) DESC LIMIT 1", nativeQuery = false)
    Book findMostPopularBook(EventType eventType, LocalDateTime from, LocalDateTime to);

    @Query(value = "SELECT e.reader FROM Event e WHERE e.event = :eventType AND e.localDateTime BETWEEN :from AND :to GROUP BY e.reader ORDER BY COUNT(e.id) DESC LIMIT 1", nativeQuery = false)
    Reader findMostPopularReader(EventType eventType, LocalDateTime from, LocalDateTime to);
}