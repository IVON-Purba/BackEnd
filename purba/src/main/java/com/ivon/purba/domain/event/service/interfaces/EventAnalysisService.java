package com.ivon.purba.domain.event.service.interfaces;

import com.ivon.purba.domain.ai.dto.EventAnalysis;
import com.ivon.purba.domain.event.dto.EventAnalysisRequest;

public interface EventAnalysisService {
    EventAnalysis analyzeEvent(EventAnalysisRequest request);
}
