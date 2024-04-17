package com.ivon.purba.domain.content.photo.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PhotoUploadRequest {
    private Long userId;
    private MultipartFile file;
}
