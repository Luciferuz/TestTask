package com.antsiferov.testtask.book;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {

    private BookService bookService;

    @PostMapping("")
    public Book createBook(@RequestBody Book book) {
        return bookService.save(book);
    }

    @DeleteMapping("{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return "Deleted id: " + id;
    }

    @GetMapping("{id}")
    public Book getBook(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PutMapping("{id}")
    public Book update(@PathVariable Long id, @RequestBody Book updatedBook) {
        return bookService.update(id, updatedBook);
    }
}