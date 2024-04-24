package com.ivon.purba.domain.ai.service.interfaces;

import com.ivon.purba.domain.ai.dto.EventAnalysis;
import com.ivon.purba.domain.event.entity.Event;

public interface AIEventService {
    EventAnalysis analyzeEvent(Event event);
}
