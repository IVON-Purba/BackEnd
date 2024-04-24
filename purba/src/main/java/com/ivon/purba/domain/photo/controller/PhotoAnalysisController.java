package com.ivon.purba.domain.photo.controller;

import com.ivon.purba.domain.ai.dto.PhotoAnalysis;
import com.ivon.purba.domain.photo.dto.PhotoAnalysisRequest;
import com.ivon.purba.domain.photo.service.interfaces.PhotoAnalysisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PhotoAnalysisController {

    private final PhotoAnalysisService photoAnalysisService;

    @PostMapping(value = "/photo/analyze")
    public ResponseEntity<PhotoAnalysis> analyzePhoto(@Valid @RequestBody PhotoAnalysisRequest request) {
        PhotoAnalysis response = photoAnalysisService.analyzePhoto(request);
        return ResponseEntity.ok(response);
    }
}
