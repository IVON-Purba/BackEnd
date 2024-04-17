package com.ivon.purba.domain.ai.service;

import com.ivon.purba.domain.ai.dto.PhotoAnalysisResponse;

public interface AiService {
    PhotoAnalysisResponse analyzePhoto(String photoUrl);
}
