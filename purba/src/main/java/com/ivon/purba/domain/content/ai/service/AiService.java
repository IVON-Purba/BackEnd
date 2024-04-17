package com.ivon.purba.domain.content.ai.service;

import com.ivon.purba.domain.content.ai.dto.PhotoAnalysisResponse;

public interface AiService {
    PhotoAnalysisResponse analyzePhoto(String photoUrl);
}
