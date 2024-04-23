package com.ivon.purba.domain.event.service.interfaces;

import com.ivon.purba.domain.event.dto.EventUpdateRequest;
import com.ivon.purba.domain.event.entity.Event;

import java.util.List;

public interface EventService {

    Event getEventById(Long eventId);

    void updateEvent(EventUpdateRequest request);

    List<Event> getAllEventsByLocation(String location);
}
