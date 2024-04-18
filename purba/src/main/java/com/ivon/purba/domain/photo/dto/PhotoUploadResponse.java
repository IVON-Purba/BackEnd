package com.ivon.purba.domain.photo.dto;

import lombok.Getter;

@Getter
public class PhotoUploadResponse {
    private final String message;

    public PhotoUploadResponse(String message) {
        this.message = message;
    }
}
