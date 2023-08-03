package com.antsiferov.testtask.controllers;

import com.antsiferov.testtask.entities.Reader;
import com.antsiferov.testtask.services.ReaderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/reader")
public class ReaderController {
    private ReaderService readerService;

    @PostMapping("/create")
    public Reader createReader(@RequestBody Reader reader) {
        return readerService.save(reader);
    }

    @DeleteMapping("delete/{id}")
    public String deleteReader(@RequestBody Reader reader, @PathVariable Long id) {
        readerService.delete(reader);
        return "Deleted id: " + id;
    }

    @GetMapping("/{id}")
    public Reader getReader(@PathVariable Long id) {
        return readerService.findById(id);
    }

    @PutMapping("/{id}")
    public Reader update(@PathVariable Long id, @RequestBody Reader updatedReader) {
        return readerService.update(id, updatedReader);
    }
}