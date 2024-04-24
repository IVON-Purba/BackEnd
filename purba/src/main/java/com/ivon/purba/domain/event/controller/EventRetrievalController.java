package com.ivon.purba.domain.event.controller;


import com.ivon.purba.domain.event.dto.EventGetAllRequest;
import com.ivon.purba.domain.event.dto.EventGetResponse;
import com.ivon.purba.domain.event.dto.EventPostRequest;
import com.ivon.purba.domain.event.dto.EventPostResponse;
import com.ivon.purba.domain.event.entity.Event;
import com.ivon.purba.domain.event.service.interfaces.EventRegistrationService;
import com.ivon.purba.domain.event.service.interfaces.EventRetrievalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class EventRetrievalController {
    private final EventRetrievalService eventRetrievalService;
    private final EventRegistrationService eventRegistrationService;

    @PostMapping("/event/upload")
    public ResponseEntity<EventPostResponse> uploadEvent(@RequestBody EventPostRequest request) {
        Long eventId =  eventRegistrationService.registerEvent(request);
        return ResponseEntity.ok(new EventPostResponse(eventId));
    }

    @GetMapping("/event")
    public ResponseEntity<EventGetResponse> getEvent(@RequestParam("eventId") Long eventId) {
        Event event = eventRetrievalService.getEventById(eventId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new EventGetResponse(event));
    }

    @GetMapping("/event/events")
    public ResponseEntity<List<Event>> getAllEventsByLocation(@RequestBody EventGetAllRequest request) {
        List<Event> events = eventRetrievalService.getAllEventsByLocation(request.getLocation());
        return ResponseEntity.ok(events);
    }
}
