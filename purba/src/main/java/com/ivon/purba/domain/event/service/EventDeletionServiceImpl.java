package com.ivon.purba.domain.event.service;

import com.ivon.purba.domain.event.entity.Event;
import com.ivon.purba.domain.event.repository.EventRepository;
import com.ivon.purba.domain.event.service.interfaces.EventDeletionService;
import com.ivon.purba.domain.event.service.interfaces.EventRetrievalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventDeletionServiceImpl implements EventDeletionService {

    private final EventRepository eventRepository;
    private final EventRetrievalService eventRetrievalService;

    @Override
    public void deleteEventById(Long eventId) {
        Event event = eventRetrievalService.getEventById(eventId);
        event.updateEasyDelete(true);
        eventRepository.save(event);
    }

    @Override
    public void cancelDeleteEventById(Long eventId) {
        Event event = eventRetrievalService.getEventById(eventId);
        event.updateEasyDelete(false);
        eventRepository.save(event);
    }
}
