package com.ivon.purba.domain.content.ai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhotoAnalysisRequest {
    private Long userId;
    private String photoURL;
}
