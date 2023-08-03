package com.antsiferov.testtask.controllers;

import com.antsiferov.testtask.entities.Event;
import com.antsiferov.testtask.services.EventService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/event")
public class EventController {

    private EventService eventService;

    @PostMapping("")
    public Event createEvent(@RequestBody Event event) {
        return eventService.save(event);
    }
}