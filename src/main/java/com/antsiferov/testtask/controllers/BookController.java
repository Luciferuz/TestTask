package com.antsiferov.testtask.controllers;

import com.antsiferov.testtask.entities.Book;
import com.antsiferov.testtask.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
@AllArgsConstructor
public class BookController {

    private BookService bookService;

    @PostMapping("/create")
    public Book createBook(@RequestBody Book book) {
        return bookService.save(book);
    }

    @DeleteMapping("delete/{id}")
    public String deleteBook(@RequestBody Book book, @PathVariable Long id) {
        bookService.delete(book);
        return "Deleted id: " + id;
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PutMapping("{id}")
    public Book update(@PathVariable Long id, @RequestBody Book updatedBook) {
        return bookService.update(id, updatedBook);
    }
}