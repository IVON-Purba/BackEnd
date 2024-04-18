package com.ivon.purba.domain.photo.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class PhotoUploadRequest {
    @NotEmpty(message = "User id cannot be empty.")
    private Long userId;
    @NotEmpty(message = "File cannot be empty.")
    private MultipartFile file;

    public PhotoUploadRequest(Long userId, MultipartFile file) {
        this.userId = userId;
        this.file = file;
    }
}
