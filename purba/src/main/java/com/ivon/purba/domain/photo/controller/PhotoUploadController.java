package com.ivon.purba.domain.photo.controller;

import com.ivon.purba.domain.photo.dto.PhotoUploadRequest;
import com.ivon.purba.domain.photo.dto.PhotoUploadResponse;
import com.ivon.purba.domain.photo.service.interfaces.PhotoUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
public class PhotoUploadController {

    private final PhotoUploadService photoUploadService;

    @PostMapping(value = "/photo/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CompletableFuture<ResponseEntity<PhotoUploadResponse>> uploadPhoto(@RequestParam("file") MultipartFile file,
                                                                              @RequestParam("userId") Long userId) {
        PhotoUploadRequest request = new PhotoUploadRequest(userId, file);
        return photoUploadService.uploadPhoto(request)
                .thenApply(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
                .exceptionally(ex -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new PhotoUploadResponse("파일 업로드 중 문제 발생: " + ex.getMessage())));
    }
}
