package com.ivon.purba.domain.event.service;

import com.ivon.purba.domain.content.entity.ContentType;
import com.ivon.purba.domain.content.entity.ContentTypeEnum;
import com.ivon.purba.domain.content.service.ContentTypeServiceImpl;
import com.ivon.purba.domain.event.dto.EventPostRequest;
import com.ivon.purba.domain.event.entity.Event;
import com.ivon.purba.domain.event.entity.EventType;
import com.ivon.purba.domain.event.repository.EventRepository;
import com.ivon.purba.domain.event.service.interfaces.EventRegistrationService;
import com.ivon.purba.domain.user.entity.User;
import com.ivon.purba.domain.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EventRegistrationServiceImpl implements EventRegistrationService {
    private final EventRepository eventRepository;
    private final ContentTypeServiceImpl contentTypeService;
    private final EventTypeServiceImpl eventTypeService;
    private final UserServiceImpl userService;

    @Override
    public Long registerEvent(EventPostRequest request) {
        ContentType contentType = contentTypeService.getContentType(ContentTypeEnum.EVENT);
        EventType eventType = eventTypeService.getEventTypeByName(request.getEventTypeName());
        User user = userService.getUserById(request.getUserId());

        Event event = new Event(
                user, contentType, eventType, request.getLocation(), request.getTitle(),
                request.getData(), request.getPhotoUrl(), request.getStartDate(), request.getEndDate(),
                request.getCharge(), request.getBackAccount(), request.getSummary()
        );

        return eventRepository.save(event).getId();
    }
}
