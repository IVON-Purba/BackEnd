package com.ivon.purba.domain.ai.service;

import com.ivon.purba.domain.ai.dto.PhotoAnalysis;

public interface AiService {
    PhotoAnalysis analyzePhoto(String photoUrl);
}
