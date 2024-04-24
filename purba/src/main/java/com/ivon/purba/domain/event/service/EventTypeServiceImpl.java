package com.ivon.purba.domain.event.service;

import com.ivon.purba.domain.event.entity.EventType;
import com.ivon.purba.domain.event.repository.EventTypeRepository;
import com.ivon.purba.domain.event.service.interfaces.EventTypeService;
import com.ivon.purba.exception.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EventTypeServiceImpl implements EventTypeService {
    private final EventTypeRepository eventTypeRepository;
    public EventTypeServiceImpl(EventTypeRepository eventTypeRepository) {
        this.eventTypeRepository = eventTypeRepository;
    }

    @Override
    public EventType getEventTypeByName(String typeName) {
        return eventTypeRepository.findByName(typeName)
                .orElseThrow(() -> new ResourceNotFoundException("해당 이벤트의 종류 존재하지 않습니다.: " + typeName));
    }
}
