package com.ivon.purba.domain.content.event.service;


import com.ivon.purba.domain.content.event.entity.EventType;

public interface EventTypeService {
    EventType getEventTypeByName(String name);
}
