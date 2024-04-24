package com.ivon.purba.domain.event.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class EventAnalysisRequest {
    @NotEmpty(message = "Event id cannot be empty.")
    private Long eventId;
}
