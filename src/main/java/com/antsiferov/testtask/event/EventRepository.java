package com.antsiferov.testtask.event;

import com.antsiferov.testtask.book.Book;
import com.antsiferov.testtask.reader.Reader;
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

    boolean existsByBook(Book book);

    boolean existsByReader(Reader reader);

    @Query("SELECT COUNT(e) FROM Event e WHERE e.reader = :reader AND e.event = :eventType")
    long countByReaderAndEventType(Reader reader, EventType eventType);

    @Query("SELECT COUNT(e) FROM Event e WHERE e.book = :book AND e.event = :eventType")
    long countByBookAndEventType(Book book, EventType eventType);

    void deleteByBookId(Long bookId);

    void deleteByReaderId(Long readerId);
}