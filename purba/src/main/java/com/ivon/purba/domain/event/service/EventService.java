package com.ivon.purba.domain.event.service;


import com.ivon.purba.domain.content.entity.ContentType;
import com.ivon.purba.domain.event.dto.EventUpdateRequest;
import com.ivon.purba.domain.event.entity.Event;
import com.ivon.purba.domain.event.entity.EventType;
import com.ivon.purba.domain.user.entity.User;
import com.ivon.purba.domain.event.dto.EventPostRequest;
import com.ivon.purba.exception.exceptions.AIAnalysisException;

import java.util.List;

public interface EventService {

    Long saveEvent(Event event);

    Event getEvent(Long id);

    Long createEvent(ContentType contentType, EventType eventType, User user, EventPostRequest request);

    void updateEvent(Long eventId, EventType eventType, User user, EventUpdateRequest request);

    void deleteEventById(Long id);

    List<Event> getAllEventsByLocation(String location);

    Long analyzeAndSaveEventDetails(Event event) throws AIAnalysisException;
}
