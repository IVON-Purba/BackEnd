package com.ivon.purba.domain.ai.service.interfaces;

import com.ivon.purba.domain.ai.dto.PhotoAnalysis;

public interface AIPhotoService {
    PhotoAnalysis analyzePhoto(String photoUrl);
}
