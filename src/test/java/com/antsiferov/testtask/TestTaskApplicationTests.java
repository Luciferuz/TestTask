package com.antsiferov.testtask;

import com.antsiferov.testtask.book.Book;
import com.antsiferov.testtask.book.BookService;
import com.antsiferov.testtask.book.Genre;
import com.antsiferov.testtask.event.Event;
import com.antsiferov.testtask.event.EventService;
import com.antsiferov.testtask.event.EventType;
import com.antsiferov.testtask.reader.Reader;
import com.antsiferov.testtask.reader.ReaderService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ContextConfiguration(initializers = {TestTaskApplicationTests.Initializer.class})
@Testcontainers
@Transactional
class TestTaskApplicationTests {

    @Container
    public static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("TestTask_DB")
            .withUsername("root")
            .withPassword("root")
            .withExposedPorts(5432);
    private final LocalDateTime timeFrom = LocalDateTime.of(2020, Month.APRIL, 29, 19, 30, 40);
    private final LocalDateTime timeTo = LocalDateTime.of(2024, Month.APRIL, 29, 19, 30, 40);
    @Autowired
    private BookService bookService;
    @Autowired
    private ReaderService readerService;
    @Autowired
    private EventService eventService;
    private Book mostPopularBook;
    private Reader mostPopularReader;

    @BeforeAll
    static void setUp() {
        postgresContainer.start();
    }

    private void fillDB() {
        Book book1 = new Book(null, "Тестовая книга 1", "Иванов И.И", Genre.Thriller, 3);
        Book book2 = new Book(null, "Тестовая книга 2", "Иванов И.И", Genre.Thriller, 6);
        Book book3 = new Book(null, "Тестовая книга 3", "Иванов И.И", Genre.Thriller, 13);
        Book book4 = new Book(null, "Тестовая книга 4", "Иванов И.И", Genre.Thriller, 76);
        Book book5 = new Book(null, "Тестовая книга 5", "Иванов И.И", Genre.Thriller, 59);
        Book book6 = new Book(null, "Тестовая книга 6", "Иванов И.И", Genre.Thriller, 39);
        Book book7 = new Book(null, "Тестовая книга 7", "Иванов И.И", Genre.Thriller, 876);
        Book book8 = new Book(null, "Тестовая книга 8", "Иванов И.И", Genre.Thriller, 879);
        Book book9 = new Book(null, "Тестовая книга 9", "Иванов И.И", Genre.Thriller, 547);
        Book book10 = new Book(null, "Тестовая книга 10", "Иванов И.И", Genre.Thriller, 64);
        Book mostReturnedBook = new Book(null, "Чистая архитектура", "Автор", Genre.Science, 100);
        mostPopularBook = new Book(null, "Чистый код", "Легендарный автор", Genre.Science, 5);

        Reader reader1 = new Reader(null, "Сергей", "email1@mail.ru", "+79950098765б", "Москва");
        Reader reader2 = new Reader(null, "Иван", "email2@mail.ru", "+79990498765б", "Москва");
        Reader reader3 = new Reader(null, "Александр", "email3@mail.ru", "+79690098765б", "Москва");
        Reader bestReader = new Reader(null, "Александр", "icloudaaa@icloud.com", "+79990198765б", "Красноярск");
        mostPopularReader = new Reader(null, "Александр Александрович", "icloaa@icloud.com", "+71990198715б", "Подгорный");

        bookService.save(book1);
        bookService.save(book2);
        bookService.save(book3);
        bookService.save(book4);
        bookService.save(book5);
        bookService.save(book6);
        bookService.save(book7);
        bookService.save(book8);
        bookService.save(book9);
        bookService.save(book10);
        bookService.save(mostReturnedBook);
        bookService.save(mostPopularBook);

        readerService.save(reader1);
        readerService.save(reader2);
        readerService.save(reader3);
        readerService.save(bestReader);
        readerService.save(mostPopularReader);

        eventService.save(new Event(null, reader1, book1, LocalDateTime.now(), EventType.TAKE));
        eventService.save(new Event(null, reader1, book1, LocalDateTime.now(), EventType.RETURN));
        eventService.save(new Event(null, reader1, book2, LocalDateTime.now(), EventType.TAKE));
        eventService.save(new Event(null, reader1, book3, LocalDateTime.now(), EventType.TAKE));
        eventService.save(new Event(null, reader1, book10, LocalDateTime.now(), EventType.TAKE));
        eventService.save(new Event(null, reader1, book3, LocalDateTime.now(), EventType.RETURN));
        eventService.save(new Event(null, reader1, book2, LocalDateTime.now(), EventType.RETURN));
        eventService.save(new Event(null, reader2, book1, LocalDateTime.now(), EventType.TAKE));
        eventService.save(new Event(null, reader2, book1, LocalDateTime.now(), EventType.RETURN));
        eventService.save(new Event(null, reader2, book4, LocalDateTime.now(), EventType.TAKE));
        eventService.save(new Event(null, reader2, book5, LocalDateTime.now(), EventType.TAKE));
        eventService.save(new Event(null, reader2, book9, LocalDateTime.now(), EventType.TAKE));
        eventService.save(new Event(null, reader2, book9, LocalDateTime.now(), EventType.RETURN));
        eventService.save(new Event(null, reader2, book3, LocalDateTime.now(), EventType.TAKE));
        eventService.save(new Event(null, reader2, book3, LocalDateTime.now(), EventType.RETURN));
        eventService.save(new Event(null, reader2, book4, LocalDateTime.now(), EventType.RETURN));
        eventService.save(new Event(null, reader2, book5, LocalDateTime.now(), EventType.RETURN));
        eventService.save(new Event(null, reader1, book10, LocalDateTime.now(), EventType.RETURN));
        eventService.save(new Event(null, reader2, mostPopularBook, LocalDateTime.now(), EventType.TAKE));
        eventService.save(new Event(null, reader3, mostReturnedBook, LocalDateTime.now(), EventType.TAKE));
        eventService.save(new Event(null, reader3, mostReturnedBook, LocalDateTime.now(), EventType.RETURN));
        eventService.save(new Event(null, mostPopularReader, mostPopularBook, LocalDateTime.now(), EventType.TAKE));
        eventService.save(new Event(null, mostPopularReader, mostPopularBook, LocalDateTime.now(), EventType.RETURN));
        eventService.save(new Event(null, mostPopularReader, mostPopularBook, LocalDateTime.now(), EventType.TAKE));
        eventService.save(new Event(null, mostPopularReader, mostPopularBook, LocalDateTime.now(), EventType.RETURN));
        eventService.save(new Event(null, reader2, mostPopularBook, LocalDateTime.now(), EventType.RETURN));
        eventService.save(new Event(null, mostPopularReader, mostPopularBook, LocalDateTime.now(), EventType.TAKE));
        eventService.save(new Event(null, mostPopularReader, book1, LocalDateTime.now(), EventType.TAKE));
        eventService.save(new Event(null, mostPopularReader, book2, LocalDateTime.now(), EventType.TAKE));
        eventService.save(new Event(null, bestReader, book3, LocalDateTime.now(), EventType.TAKE));
        eventService.save(new Event(null, bestReader, book4, LocalDateTime.now(), EventType.TAKE));
        eventService.save(new Event(null, bestReader, book5, LocalDateTime.now(), EventType.TAKE));
        eventService.save(new Event(null, mostPopularReader, mostPopularBook, LocalDateTime.now(), EventType.RETURN));
        eventService.save(new Event(null, mostPopularReader, book6, LocalDateTime.now(), EventType.TAKE));
        eventService.save(new Event(null, bestReader, book7, LocalDateTime.now(), EventType.TAKE));
        eventService.save(new Event(null, bestReader, book8, LocalDateTime.now(), EventType.TAKE));
        eventService.save(new Event(null, bestReader, book7, LocalDateTime.now(), EventType.RETURN));
        eventService.save(new Event(null, bestReader, book4, LocalDateTime.now(), EventType.RETURN));
        eventService.save(new Event(null, bestReader, book8, LocalDateTime.now(), EventType.RETURN));
        eventService.save(new Event(null, mostPopularReader, book6, LocalDateTime.now(), EventType.RETURN));
        eventService.save(new Event(null, bestReader, book9, LocalDateTime.now(), EventType.TAKE));
        eventService.save(new Event(null, mostPopularReader, book10, LocalDateTime.now(), EventType.TAKE));
        eventService.save(new Event(null, bestReader, book5, LocalDateTime.now(), EventType.RETURN));
        eventService.save(new Event(null, bestReader, book3, LocalDateTime.now(), EventType.RETURN));
        eventService.save(new Event(null, bestReader, book9, LocalDateTime.now(), EventType.RETURN));
        eventService.save(new Event(null, mostPopularReader, book10, LocalDateTime.now(), EventType.RETURN));
        eventService.save(new Event(null, mostPopularReader, book2, LocalDateTime.now(), EventType.RETURN));
        eventService.save(new Event(null, bestReader, book4, LocalDateTime.now(), EventType.RETURN));
        eventService.save(new Event(null, mostPopularReader, book1, LocalDateTime.now(), EventType.RETURN));

        eventService.save(new Event(null, mostPopularReader, mostReturnedBook, LocalDateTime.now(), EventType.TAKE));
        eventService.save(new Event(null, mostPopularReader, mostReturnedBook, LocalDateTime.now(), EventType.RETURN));

        eventService.save(new Event(null, bestReader, mostPopularBook, LocalDateTime.now(), EventType.TAKE));
        eventService.save(new Event(null, mostPopularReader, mostPopularBook, LocalDateTime.now(), EventType.RETURN));
    }

    @BeforeEach
    void deleteAll() {
        bookService.deleteAll();
        readerService.deleteAll();
        eventService.deleteAll();
    }

    @Test
    void databaseConnectionTest() {
        assertTrue(postgresContainer.isRunning());
    }

    @Test
    void findByIdBookTest() {
        Book book1 = new Book(null, "Тестовая книга 1", "Иванов И.И", Genre.Thriller, 1);
        Book book2 = new Book(null, "Тестовая книга 2", "Иванов И.И", Genre.Humor, 2);
        Book book3 = new Book(null, "Тестовая книга 3", "Иванов И.И", Genre.Detective, 3);
        Book book4 = new Book(null, "Тестовая книга 4", "Иванов И.И", Genre.Social, 4);
        Book book5 = new Book(null, "Тестовая книга 5", "Иванов И.И", Genre.Science, 5);
        Long id1 = bookService.save(book1).getId();
        Long id2 = bookService.save(book2).getId();
        Long id3 = bookService.save(book3).getId();
        Long id4 = bookService.save(book4).getId();
        Long id5 = bookService.save(book5).getId();
        Book actual1 = bookService.findById(id1);
        Book actual2 = bookService.findById(id2);
        Book actual3 = bookService.findById(id3);
        Book actual4 = bookService.findById(id4);
        Book actual5 = bookService.findById(id5);
        assertNotNull(actual1);
        assertNotNull(actual2);
        assertNotNull(actual3);
        assertNotNull(actual4);
        assertNotNull(actual5);
        assertEquals(book1, actual1);
        assertEquals(book2, actual2);
        assertEquals(book3, actual3);
        assertEquals(book4, actual4);
        assertEquals(book5, actual5);
        assertThrows(EntityNotFoundException.class, () -> bookService.findById(8998989L));
    }

    @Test
    void findByIdReaderTest() {
        Reader reader1 = new Reader(null, "Сергей", "email1@mail.ru", "+79990098765б", "Москва");
        Reader reader2 = new Reader(null, "Иван", "email2@mail.ru", "+79990098765б", "Москва");
        Reader reader3 = new Reader(null, "Ольга", "email3@mail.ru", "+79990098765б", "Москва");
        Reader reader4 = new Reader(null, "Александр", "email4@mail.ru", "+79990098765б", "Москва");
        Long id1 = readerService.save(reader1).getId();
        Long id2 = readerService.save(reader2).getId();
        Long id3 = readerService.save(reader3).getId();
        Long id4 = readerService.save(reader4).getId();
        Reader actual1 = readerService.findById(id1);
        Reader actual2 = readerService.findById(id2);
        Reader actual3 = readerService.findById(id3);
        Reader actual4 = readerService.findById(id4);
        assertNotNull(actual1);
        assertNotNull(actual2);
        assertNotNull(actual3);
        assertNotNull(actual4);
        assertEquals(reader1, actual1);
        assertEquals(reader2, actual2);
        assertEquals(reader3, actual3);
        assertEquals(reader4, actual4);
        assertThrows(EntityNotFoundException.class, () -> bookService.findById(899898989L));
    }

    @Test
    void findByIdEventTest() {
        Reader reader = new Reader(null, "Сергей", "email1@mail.ru", "+79990098765б", "Москва");
        Book book = new Book(null, "Тестовая книга 1", "Иванов И.И", Genre.Thriller, 1);
        readerService.save(reader);
        bookService.save(book);

        Event event1 = new Event(null, reader, book, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), EventType.TAKE);
        Event event2 = new Event(null, reader, book, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), EventType.RETURN);
        Long id1 = eventService.save(event1).getId();
        Long id2 = eventService.save(event2).getId();
        Event actual1 = eventService.findById(id1);
        Event actual2 = eventService.findById(id2);
        assertNotNull(actual1);
        assertNotNull(actual2);
        assertEquals(event1, actual1);
        assertEquals(event2, actual2);
        assertThrows(EntityNotFoundException.class, () -> eventService.findById(899898989L));
    }

    @Test
    void findMostPopularBookTest() {
        fillDB();
        Book actual = eventService.findMostPopularBook(EventType.TAKE, timeFrom, timeTo);
        assertEquals(mostPopularBook, actual);
        assertThrows(IllegalArgumentException.class, () -> eventService.findMostPopularBook(EventType.TAKE, timeTo, timeFrom));
    }

    @Test
    void findMostPopularReaderTest() {
        fillDB();
        Reader actual = eventService.findMostPopularReader(EventType.TAKE, timeFrom, timeTo);
        assertEquals(mostPopularReader, actual);
        assertThrows(IllegalArgumentException.class, () -> eventService.findMostPopularReader(EventType.TAKE, timeTo, timeFrom));
    }

    @Test
    void deleteReaderTest() {
        Reader reader1 = new Reader(null, "Сергей", "se@mail.ru", "79999999999", "Address");
        Book book = new Book(null, "Книга", "Автор книги", Genre.Thriller, 1);
        bookService.save(book);
        Long id1 = readerService.save(reader1).getId();
        assertEquals(reader1.getEmail(), readerService.findById(id1).getEmail());
        assertEquals(reader1.getAddress(), readerService.findById(id1).getAddress());
        assertEquals(reader1.getPhone(), readerService.findById(id1).getPhone());
        assertEquals(reader1.getName(), readerService.findById(id1).getName());
        readerService.delete(id1);
        Assertions.assertThrows(EntityNotFoundException.class, () -> readerService.findById(id1));
        Assertions.assertThrows(EntityNotFoundException.class, () -> readerService.delete(id1));

        Reader reader2 = new Reader(null, "Иван", "iv@ya.ru", "79999999999", "Address");
        Long id2 = readerService.save(reader2).getId();
        assertEquals(reader2.getEmail(), readerService.findById(id2).getEmail());
        assertEquals(reader2.getAddress(), readerService.findById(id2).getAddress());
        assertEquals(reader2.getPhone(), readerService.findById(id2).getPhone());
        assertEquals(reader2.getName(), readerService.findById(id2).getName());

        Event eventTake = new Event(null, reader2, book, LocalDateTime.now(), EventType.TAKE);
        eventService.save(eventTake);
        Assertions.assertThrows(IllegalStateException.class, () -> readerService.delete(id2));

        Event eventReturn = new Event(null, reader2, book, LocalDateTime.now(), EventType.RETURN);
        eventService.save(eventReturn);
        readerService.delete(id2);
        Assertions.assertThrows(EntityNotFoundException.class, () -> readerService.findById(id2));
    }

    @Test
    void saveReaderTest() {
        Reader reader1 = new Reader(null, "Сергей", "email@mail.ru", "+79990098765б", "Москва");
        Reader reader2 = new Reader(null, "Иван", "hotmail@hotmail.ru", "+79990098765б", "Красноярск");
        Reader reader3 = new Reader(null, "Ольга", "gmail@gmail.ru", "+79990098765б", "Санкт-Петербург");
        Reader reader4 = new Reader(null, "Александр", "icloud@icloud.com", "+79990098765б", "Красноярск");
        Reader reader5 = new Reader(null, "Александр1", "icloud@icloud.com", "+79990098765б", "Санкт-Петербург");
        Reader actual1 = readerService.save(reader1);
        Reader actual2 = readerService.save(reader2);
        Reader actual3 = readerService.save(reader3);
        Reader actual4 = readerService.save(reader4);
        assertEquals(reader1, actual1);
        assertEquals(reader2, actual2);
        assertEquals(reader3, actual3);
        assertEquals(reader4, actual4);
        assertThrows(IllegalArgumentException.class, () -> readerService.save(reader1));
        assertThrows(IllegalArgumentException.class, () -> readerService.save(reader5));
    }

    @Test
    void updateReaderTest() {
        Reader reader1 = new Reader();
        Reader reader2 = new Reader();
        reader1.setName("Имя");
        reader1.setEmail("email@email.com");
        reader1.setPhone("+79113456677");
        reader1.setAddress("Красноярск");

        Long id1 = readerService.save(reader1).getId();
        Long id2 = readerService.save(reader2).getId();
        assertEquals(reader1, readerService.findById(id1));
        assertEquals(reader2, readerService.findById(id2));

        reader1.setName("Сергей");
        reader1.setEmail("email@email.ru");
        reader1.setPhone("+79153456677");
        reader1.setAddress("Москва");

        assertEquals(reader1, readerService.update(id1, reader1));
        assertEquals(reader2, readerService.update(id2, reader2));

        Reader actual1 = readerService.update(id2, reader1);
        Reader actual2 = readerService.update(id1, reader2);
        reader1.setId(id2);

        assertEquals(reader1, actual1);
        assertEquals(reader1, actual2);
        assertThrows(EntityNotFoundException.class, () -> readerService.update(50809L, reader1));
    }

    @Test
    void deleteBookTest() {
        Book book1 = new Book(null, "Книга для удаления", "Автор книги", Genre.Thriller, 1);
        Long id1 = bookService.save(book1).getId();
        assertEquals(book1, bookService.findById(id1));
        bookService.delete(id1);
        Assertions.assertThrows(EntityNotFoundException.class, () -> bookService.findById(id1));
        Assertions.assertThrows(EntityNotFoundException.class, () -> bookService.delete(555555898L));

        Book book2 = new Book(null, "Книга, которую возьмут почитать", "Автор", Genre.Thriller, 1);
        Long id2 = bookService.save(book2).getId();
        assertEquals(book2, bookService.findById(id2));
        Reader reader = new Reader(null, "Сергей", "email1@mail.ru", "+79990098765б", "Москва");
        readerService.save(reader);
        Event eventTake = new Event(null, reader, book2, LocalDateTime.now(), EventType.TAKE);
        eventService.save(eventTake);
        Assertions.assertThrows(IllegalStateException.class, () -> bookService.delete(id2));

        Event eventReturn = new Event(null, reader, book2, LocalDateTime.now(), EventType.RETURN);
        eventService.save(eventReturn);
        bookService.delete(id2);
        Assertions.assertThrows(EntityNotFoundException.class, () -> bookService.findById(id2));
    }

    @Test
    void saveBookTest() {
        Book book1 = new Book();
        book1.setTitle("Чертоги разума");
        book1.setAuthor("Игорь Галактионов 1");
        book1.setGenre(Genre.Humor);
        book1.setTotalCopies(40);

        Book savedBook = bookService.save(book1);
        assertNotNull(savedBook);
        assertEquals(savedBook, book1);

        Book book2 = new Book();
        book2.setTitle("Чертоги разума");
        book2.setAuthor("Игорь Галактионов 1");
        book2.setGenre(Genre.Humor);
        book2.setTotalCopies(55);

        Book updatedBook = bookService.save(book2);

        assertEquals(savedBook.getId(), updatedBook.getId());
        assertEquals(savedBook.getTitle(), updatedBook.getTitle());
        assertEquals(savedBook.getAuthor(), updatedBook.getAuthor());
        assertEquals(savedBook.getGenre(), updatedBook.getGenre());
        assertEquals(95, updatedBook.getTotalCopies());
    }

    @Test
    void updateBookTest() {
        Book book1 = new Book();
        Book book2 = new Book();
        book1.setTitle("Чертоги разума");
        book1.setAuthor("Игорь Галактионов 1");
        book1.setGenre(Genre.Humor);
        book1.setTotalCopies(40);

        Long id1 = bookService.save(book1).getId();
        Long id2 = bookService.save(book2).getId();
        assertEquals(book1, bookService.findById(id1));
        assertEquals(book2, bookService.findById(id2));

        book1.setTitle("Чертоги разума 2");
        book1.setAuthor("Игорь Галактионов 2");
        book1.setGenre(Genre.Thriller);
        book1.setTotalCopies(80);

        assertEquals(book1, bookService.update(id1, book1));
        assertEquals(book2, bookService.update(id2, book2));

        Book actual1 = bookService.update(id2, book1);
        Book actual2 = bookService.update(id1, book2);
        book1.setId(id2);

        assertEquals(book1, actual1);
        assertEquals(book1, actual2);
        assertThrows(EntityNotFoundException.class, () -> bookService.update(5009090L, book1));
    }

    @Test
    void saveEventTest() {
        Book book1 = new Book(null, "Книга 1", "Автор книги", Genre.Thriller, 1);
        Book book2 = new Book(null, "Книга 2", "Автор книги", Genre.Thriller, 0);
        Reader reader = new Reader(null, "Сергей", "emaildd1@mail.ru", "+79990098765б", "Москва");
        Event eventTake1 = new Event(null, reader, book1, LocalDateTime.now(), EventType.TAKE);
        Event eventReturn1 = new Event(null, reader, book1, LocalDateTime.now(), EventType.RETURN);
        Event eventTake2 = new Event(null, reader, book2, LocalDateTime.now(), EventType.TAKE);

        Assertions.assertThrows(EntityNotFoundException.class, () -> eventService.save(eventTake1));
        bookService.save(book1);
        Assertions.assertThrows(EntityNotFoundException.class, () -> eventService.save(eventTake1));
        readerService.save(reader);
        Event actual1 = eventService.save(eventTake1);
        eventTake1.getBook().setTotalCopies(eventTake1.getBook().getTotalCopies() - 1);
        assertEquals(eventTake1, actual1);
        Assertions.assertThrows(EntityNotFoundException.class, () -> eventService.save(eventTake2));
        Event actual2 = eventService.save(eventReturn1);
        eventReturn1.getBook().setTotalCopies(eventReturn1.getBook().getTotalCopies() + 1);
        assertEquals(eventReturn1, actual2);
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgresContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgresContainer.getUsername(),
                    "spring.datasource.password=" + postgresContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

}