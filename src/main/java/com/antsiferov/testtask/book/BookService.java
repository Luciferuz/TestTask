package com.antsiferov.testtask.book;

import com.antsiferov.testtask.event.EventRepository;
import com.antsiferov.testtask.event.EventType;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class BookService {

    private BookRepository bookRepository;

    private EventRepository eventRepository;

    public Book save(Book book) {
        Book existingBook = bookRepository.findByTitleAndAuthor(book.getTitle(), book.getAuthor());
        if (existingBook != null) {
            existingBook.setTotalCopies(existingBook.getTotalCopies() + book.getTotalCopies());
            return bookRepository.save(existingBook);
        } else {
            return bookRepository.save(book);
        }
    }

    public void delete(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Книга с id " + id + " не найдена"));
        long takeCount = eventRepository.countByBookAndEventType(book, EventType.TAKE);
        long returnCount = eventRepository.countByBookAndEventType(book, EventType.RETURN);
        if (takeCount != returnCount) {
            throw new IllegalStateException("Нельзя удалить книгу с id " + id + ", так как она находится на руках у кого-то");
        } else {
            eventRepository.deleteByBookId(id);
        }
        bookRepository.deleteById(id);
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Книга с id " + id + " не найдена"));
    }

    public Book update(Long id, Book updatedBook) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Книга с id " + id + " не найдена"));
        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setGenre(updatedBook.getGenre());
        book.setTotalCopies(updatedBook.getTotalCopies());
        return bookRepository.save(book);
    }

    public void deleteAll() {
        bookRepository.deleteAll();
    }

}