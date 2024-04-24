package com.ivon.purba.domain.event.service;

import com.ivon.purba.domain.ai.dto.EventAnalysis;
import com.ivon.purba.domain.ai.service.interfaces.AIEventService;
import com.ivon.purba.domain.event.dto.EventAnalysisRequest;
import com.ivon.purba.domain.event.entity.Event;
import com.ivon.purba.domain.event.service.interfaces.EventAnalysisService;
import com.ivon.purba.domain.event.service.interfaces.EventRetrievalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventAnalysisServiceImpl implements EventAnalysisService {
    private final EventRetrievalService eventRetrievalService;
    private final AIEventService aiEventService;

    @Override
    public EventAnalysis analyzeEvent(EventAnalysisRequest request) {
        Event event = eventRetrievalService.getEventById(request.getEventId());
        return aiEventService.analyzeEvent(event);
    }
}
