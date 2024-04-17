package com.ivon.purba.domain.content.ai.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private String message;
    private String type;
    private String param;
    private String code;
}
