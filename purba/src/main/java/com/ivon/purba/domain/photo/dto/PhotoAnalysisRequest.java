package com.ivon.purba.domain.photo.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class PhotoAnalysisRequest {
    @NotEmpty(message = "Photo url cannot be empty.")
    private String photoUrl;
}
