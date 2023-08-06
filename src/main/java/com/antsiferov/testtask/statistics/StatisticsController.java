package com.antsiferov.testtask.statistics;

import com.antsiferov.testtask.book.Book;
import com.antsiferov.testtask.event.EventService;
import com.antsiferov.testtask.event.EventType;
import com.antsiferov.testtask.reader.Reader;
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

    @GetMapping("/popular/reader")
    public Reader findMostPopularReader(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return eventService.findMostPopularReader(EventType.TAKE, from, to);
    }

}