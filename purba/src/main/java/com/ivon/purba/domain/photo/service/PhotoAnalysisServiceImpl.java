package com.ivon.purba.domain.photo.service;

import com.ivon.purba.domain.ai.dto.PhotoAnalysis;
import com.ivon.purba.domain.ai.service.interfaces.AIPhotoService;
import com.ivon.purba.domain.photo.dto.PhotoAnalysisRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhotoAnalysisServiceImpl implements com.ivon.purba.domain.photo.service.interfaces.PhotoAnalysisService {

    private final AIPhotoService aiService;

    @Override
    public PhotoAnalysis analyzePhoto(PhotoAnalysisRequest request) {
        return aiService.analyzePhoto(request.getPhotoUrl());
    }
}
