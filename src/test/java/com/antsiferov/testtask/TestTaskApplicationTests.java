package com.antsiferov.testtask;

import com.antsiferov.testtask.entities.Book;
import com.antsiferov.testtask.entities.Event;
import com.antsiferov.testtask.entities.Reader;
import com.antsiferov.testtask.enums.EventType;
import com.antsiferov.testtask.enums.Genre;
import com.antsiferov.testtask.services.BookService;
import com.antsiferov.testtask.services.EventService;
import com.antsiferov.testtask.services.ReaderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.transaction.BeforeTransaction;

import java.time.LocalDateTime;
import java.time.Month;

@SpringBootTest
@Transactional
class TestTaskApplicationTests {

    private final LocalDateTime timeFrom = LocalDateTime.of(2020, Month.APRIL, 29, 19, 30, 40);
    private final LocalDateTime timeTo = LocalDateTime.of(2024, Month.APRIL, 29, 19, 30, 40);
    @Autowired
    private BookService bookService;
    @Autowired
    private ReaderService readerService;
    @Autowired
    private EventService eventService;
    private Book mostPopularBook;
    private Book mostReturnedBook;
    private Reader bestReader;
    private Reader mostPopularReader;

    @Test
    @BeforeTransaction
    void setUp() {
        mostPopularBook = new Book();
        mostPopularBook.setTitle("Название самой популярной книги");
        mostPopularBook.setAuthor("Паук");
        mostPopularBook.setGenre(Genre.Thriller);
        mostPopularBook.setTotalCopies(2048);
        bookService.save(mostPopularBook);

        mostReturnedBook = new Book();
        mostReturnedBook.setTitle("Название самой возвращаемой книги");
        mostReturnedBook.setAuthor("Чистый код");
        mostReturnedBook.setGenre(Genre.Humor);
        mostReturnedBook.setTotalCopies(4781);
        bookService.save(mostReturnedBook);

        bestReader = new Reader();
        bestReader.setName("Кобра");
        bestReader.setEmail("email@email.com");
        bestReader.setPhone("+79113456677");
        bestReader.setAddress("Москва");
        readerService.save(bestReader);

        mostPopularReader = new Reader();
        mostPopularReader.setName("Артем");
        mostPopularReader.setEmail("artem@mail.ru");
        mostPopularReader.setPhone("+79155550000");
        mostPopularReader.setAddress("Санкт-Петербург");
        readerService.save(mostPopularReader);
    }

    @Test
    void testFindMostPopularBook() {
        Event event = new Event();
        event.setEvent(EventType.TAKE);
        event.setLocalDateTime(LocalDateTime.now());
        event.setBook(mostPopularBook);
        event.setReader(mostPopularReader);

        eventService.save(event);
        eventService.save(event);
        eventService.save(event);
        eventService.save(event);
        eventService.save(event);

        Book actual = eventService.findMostPopularBook(EventType.TAKE, timeFrom, timeTo);
        Assertions.assertEquals(mostPopularBook, actual);
    }

    @Test
    void testFindMostReturnedBook() {
        Event event = new Event();
        event.setEvent(EventType.RETURN);
        event.setLocalDateTime(LocalDateTime.now());
        event.setBook(mostReturnedBook);
        event.setReader(bestReader);

        eventService.save(event);
        eventService.save(event);
        eventService.save(event);
        eventService.save(event);
        eventService.save(event);

        Book actual = eventService.findMostPopularBook(EventType.RETURN, timeFrom, timeTo);
        Assertions.assertEquals(mostReturnedBook, actual);
    }

    @Test
    void testFindMostPopularReader() {
        Event event = new Event();
        event.setEvent(EventType.TAKE);
        event.setLocalDateTime(LocalDateTime.now());
        event.setBook(mostPopularBook);
        event.setReader(mostPopularReader);

        eventService.save(event);
        eventService.save(event);
        eventService.save(event);
        eventService.save(event);
        eventService.save(event);

        Reader actual = eventService.findMostPopularReader(EventType.TAKE, timeFrom, timeTo);
        Assertions.assertEquals(mostPopularReader, actual);
    }

    @Test
    void findBestReader() {
        Event event = new Event();
        event.setEvent(EventType.RETURN);
        event.setLocalDateTime(LocalDateTime.now());
        event.setBook(mostReturnedBook);
        event.setReader(bestReader);

        eventService.save(event);
        eventService.save(event);
        eventService.save(event);
        eventService.save(event);
        eventService.save(event);

        Reader actual = eventService.findMostPopularReader(EventType.RETURN, timeFrom, timeTo);
        Assertions.assertEquals(bestReader, actual);
    }

    @Test
    void getReader() {
        Reader reader = new Reader();
        reader.setName("Имя");
        reader.setEmail("email@email.com");
        reader.setPhone("+79113456677");
        reader.setAddress("Красноярск");

        Assertions.assertEquals(reader, readerService.findById(readerService.save(reader).getId()));
        Assertions.assertEquals(bestReader, readerService.findById(readerService.save(bestReader).getId()));
        Assertions.assertEquals(mostPopularReader, readerService.findById(readerService.save(mostPopularReader).getId()));
    }

    @Test
    void deleteReader() {
        Reader reader = new Reader();
        Long id = readerService.save(reader).getId();
        Assertions.assertEquals(reader, readerService.findById(id));
        readerService.delete(reader);
        Assertions.assertThrows(EntityNotFoundException.class, () -> readerService.findById(id));
    }

    @Test
    @Transactional
    void createReader() {
        Reader reader = new Reader();
        reader.setName("Имя");
        reader.setEmail("email@email.com");
        reader.setPhone("+79113456677");
        reader.setAddress("Красноярск");
        Assertions.assertEquals(reader, readerService.save(reader));
    }

    @Test
    void updateReader() {
        Reader reader = new Reader();
        reader.setName("Имя");
        reader.setEmail("email@email.com");
        reader.setPhone("+79113456677");
        reader.setAddress("Красноярск");

        Long id = readerService.save(reader).getId();
        Assertions.assertEquals(reader, readerService.findById(id));

        reader.setName("Сергей");
        reader.setEmail("email@email.ru");
        reader.setPhone("+79153456677");
        reader.setAddress("Москва");
        Assertions.assertEquals(reader, readerService.update(id, reader));
    }

    @Test
    void getBook() {
        Book book = new Book();
        book.setTitle("Чертоги разума");
        book.setAuthor("Игорь Галактионов 1");
        book.setGenre(Genre.Humor);
        book.setTotalCopies(40);

        Assertions.assertEquals(book, bookService.findById(bookService.save(book).getId()));
        Assertions.assertEquals(mostReturnedBook, bookService.findById(bookService.save(mostReturnedBook).getId()));
        Assertions.assertEquals(mostPopularBook, bookService.findById(bookService.save(mostPopularBook).getId()));
    }

    @Test
    void deleteBook() {
        Book book = new Book();
        Long id = bookService.save(book).getId();
        Assertions.assertEquals(book, bookService.findById(id));
        bookService.delete(book);
        Assertions.assertThrows(EntityNotFoundException.class, () -> bookService.findById(id));
    }

    @Test
    void createBook() {
        Book book = new Book();
        book.setTitle("Чертоги разума");
        book.setAuthor("Игорь Галактионов 1");
        book.setGenre(Genre.Humor);
        book.setTotalCopies(40);
        Assertions.assertEquals(book, bookService.save(book));
    }

    @Test
    void updateBook() {
        Book book = new Book();
        book.setTitle("Чертоги разума");
        book.setAuthor("Игорь Галактионов 1");
        book.setGenre(Genre.Humor);
        book.setTotalCopies(40);

        Long id = bookService.save(book).getId();
        Assertions.assertEquals(book, bookService.findById(id));

        book.setTitle("Чертоги разума 2");
        book.setAuthor("Игорь Галактионов 2");
        book.setGenre(Genre.Thriller);
        book.setTotalCopies(80);
        Assertions.assertEquals(book, bookService.update(id, book));
    }

    @Test
    void createEvent() {
        Event event = new Event();
        event.setEvent(EventType.TAKE);
        event.setLocalDateTime(LocalDateTime.now());
        event.setBook(mostReturnedBook);
        event.setReader(bestReader);
        Assertions.assertEquals(event, eventService.save(event));
    }

}