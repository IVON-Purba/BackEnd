package com.ivon.purba.domain.event.service.interfaces;

import com.ivon.purba.domain.event.entity.Event;

import java.util.List;

public interface EventRetrievalService {
    Event getEventById(Long eventId);

    List<Event> getAllEventsByLocation(String location);
}
