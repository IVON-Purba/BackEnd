package com.ivon.purba.domain.event.controller;

import com.ivon.purba.domain.ai.dto.EventAnalysis;
import com.ivon.purba.domain.event.dto.EventAnalysisRequest;
import com.ivon.purba.domain.event.service.interfaces.EventAnalysisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EventAnalysisController {

    private final EventAnalysisService eventAnalysisService;
    @PostMapping(value = "/event/analyze")
    public ResponseEntity<EventAnalysis> analyzePhoto(@Valid @RequestBody EventAnalysisRequest request) {
        EventAnalysis response = eventAnalysisService.analyzeEvent(request);
        return ResponseEntity.ok(response);
    }
}
