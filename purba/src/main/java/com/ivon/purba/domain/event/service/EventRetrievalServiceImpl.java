package com.ivon.purba.domain.event.service;

import com.ivon.purba.domain.event.entity.Event;
import com.ivon.purba.domain.event.repository.EventRepository;
import com.ivon.purba.domain.event.service.interfaces.EventRetrievalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventRetrievalServiceImpl implements EventRetrievalService {
    private final EventRepository eventRepository;

    @Override
    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found: " + eventId));
    }

    @Override
    public List<Event> getAllEventsByLocation(String location) {
        return eventRepository.findAllByLocation(location);
    }
}