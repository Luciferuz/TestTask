package com.antsiferov.testtask.reader;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/readers")
public class ReaderController {
    private ReaderService readerService;

    @PostMapping("")
    public Reader createReader(@RequestBody Reader reader) {
        return readerService.save(reader);
    }

    @DeleteMapping("{id}")
    public String deleteReader(@PathVariable Long id) {
        readerService.delete(id);
        return "Deleted id: " + id;
    }

    @GetMapping("{id}")
    public Reader getReader(@PathVariable Long id) {
        return readerService.findById(id);
    }

    @PutMapping("{id}")
    public Reader update(@PathVariable Long id, @RequestBody Reader updatedReader) {
        return readerService.update(id, updatedReader);
    }
}