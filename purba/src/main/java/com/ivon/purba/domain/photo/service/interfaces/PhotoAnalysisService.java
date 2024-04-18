package com.ivon.purba.domain.photo.service.interfaces;

import com.ivon.purba.domain.ai.dto.PhotoAnalysis;
import com.ivon.purba.domain.photo.dto.PhotoAnalysisRequest;

public interface PhotoAnalysisService {
    PhotoAnalysis analyzePhoto(PhotoAnalysisRequest request);
}
