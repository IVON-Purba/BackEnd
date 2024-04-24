package com.ivon.purba.domain.event.service;

import com.ivon.purba.domain.event.dto.EventUpdateRequest;
import com.ivon.purba.domain.event.entity.Event;
import com.ivon.purba.domain.event.entity.EventType;
import com.ivon.purba.domain.event.service.interfaces.EventRetrievalService;
import com.ivon.purba.domain.event.service.interfaces.EventTypeService;
import com.ivon.purba.domain.event.service.interfaces.EventUpdateService;
import com.ivon.purba.domain.user.entity.User;
import com.ivon.purba.domain.user.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventUpdateServiceImpl implements EventUpdateService {
    private final EventRetrievalService eventRetrievalService;
    private final EventTypeService eventTypeService;
    private final UserService userService;

    @Override
    public void updateEvent(EventUpdateRequest request) {
        Event event = eventRetrievalService.getEventById(request.getEventId());
        User user = userService.getUserById(request.getUserId());
        EventType eventType = eventTypeService.getEventTypeByName(request.getEventTypeName());

        event.updateEvent(
                user,
                eventType,
                request.getTitle(),
                request.getLocation(),
                request.getData(),
                request.getPhotoUrl(),
                request.getStartDate(),
                request.getEndDate(),
                request.getCharge(),
                request.getBackAccount(),
                request.getSummary()
        );
    }
}
