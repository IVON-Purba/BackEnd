package com.ivon.purba.domain.photo.service.interfaces;

import com.ivon.purba.domain.photo.dto.PhotoUploadRequest;
import com.ivon.purba.domain.photo.dto.PhotoUploadResponse;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface PhotoUploadService {
    @Async
    CompletableFuture<PhotoUploadResponse> uploadPhoto(PhotoUploadRequest request);
}
