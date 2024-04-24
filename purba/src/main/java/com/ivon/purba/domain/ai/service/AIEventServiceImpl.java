package com.ivon.purba.domain.ai.service;

import com.ivon.purba.domain.ai.dto.EventAnalysis;
import com.ivon.purba.domain.ai.service.interfaces.AIEventService;
import com.ivon.purba.domain.event.entity.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AIEventServiceImpl implements AIEventService {

    @Override
    public EventAnalysis analyzeEvent(Event event) {
        return null;
    }
}
