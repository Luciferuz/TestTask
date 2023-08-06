package com.antsiferov.testtask.event;

import com.antsiferov.testtask.book.Book;
import com.antsiferov.testtask.book.BookRepository;
import com.antsiferov.testtask.reader.Reader;
import com.antsiferov.testtask.reader.ReaderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Transactional
public class EventService {

    private EventRepository eventRepository;

    private ReaderRepository readerRepository;

    private BookRepository bookRepository;

    public Event save(Event event) {
        Long bookId = event.getBook().getId();
        Long readerId = event.getReader().getId();
        if (bookId == null || readerId == null)
            throw new EntityNotFoundException("Невозможно выдать книгу читателю, так как не указан Event");
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Книга с id " + bookId + " не найдена"));
        readerRepository.findById(readerId)
                .orElseThrow(() -> new EntityNotFoundException("Читатель с id " + readerId + " не найден"));
        if (event.getEvent() == EventType.TAKE) {
            if (book.getTotalCopies() > 0) {
                book.setTotalCopies(book.getTotalCopies() - 1);
            } else {
                throw new EntityNotFoundException("Невозможно выдать книгу читателю, так как нет доступных копий в библиотеке");
            }
        } else {
            book.setTotalCopies(book.getTotalCopies() + 1);
        }
        bookRepository.save(book);
        return eventRepository.save(event);
    }

    public Book findMostPopularBook(EventType event, LocalDateTime from, LocalDateTime to) {
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("Дата " + from + " не может быть позже даты " + to);
        }
        return eventRepository.findMostPopularBook(event, from, to);
    }

    public Reader findMostPopularReader(EventType event, LocalDateTime from, LocalDateTime to) {
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("Дата " + from + " не может быть позже даты " + to);
        }
        return eventRepository.findMostPopularReader(event, from, to);
    }

    public Event findById(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event с id " + id + " не найден"));
    }

    public void deleteAll() {
        eventRepository.deleteAll();
    }

}