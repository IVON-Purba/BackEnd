package com.ivon.purba.domain.event.service.interfaces;


import com.ivon.purba.domain.event.entity.EventType;

public interface EventTypeService {
    EventType getEventTypeByName(String name);
}
