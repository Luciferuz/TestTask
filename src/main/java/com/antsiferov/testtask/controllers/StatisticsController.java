package com.antsiferov.testtask.controllers;

import com.antsiferov.testtask.entities.Book;
import com.antsiferov.testtask.entities.Reader;
import com.antsiferov.testtask.enums.EventType;
import com.antsiferov.testtask.services.EventService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
@RequestMapping("/statistics")
public class StatisticsController {

    private EventService eventService;

    @GetMapping("/popular/book")
    public Book findMostPopularBook(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return eventService.findMostPopularBook(EventType.TAKE, from, to);
    }

    @GetMapping("/returned/book")
    public Book findMostReturnedBook(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return eventService.findMostPopularBook(EventType.RETURN, from, to);
    }

    @GetMapping("/popular/reader")
    public Reader findMostPopularReader(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return eventService.findMostPopularReader(EventType.TAKE, from, to);
    }

    @GetMapping("/best/reader")
    public Reader findBestReader(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return eventService.findMostPopularReader(EventType.RETURN, from, to);
    }
}