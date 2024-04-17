package com.ivon.purba.domain.content.photo.controller;

import com.ivon.purba.domain.content.entity.ContentType;
import com.ivon.purba.domain.content.service.ContentTypeServiceImpl;
import com.ivon.purba.domain.content.photo.dto.PhotoUploadRequest;
import com.ivon.purba.domain.content.photo.dto.PhotoUploadResponse;
import com.ivon.purba.domain.content.photo.entity.Photo;
import com.ivon.purba.domain.user.entity.User;
import com.ivon.purba.domain.content.ai.file.FileServiceImpl;
import com.ivon.purba.domain.user.service.UserServiceImpl;
import com.ivon.purba.domain.content.photo.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;
    private final UserServiceImpl userService;
    private final ContentTypeServiceImpl contentTypeService;
    private final FileServiceImpl fileService;

    //사진 업로드 및 AI 분석
    @PostMapping(value = "/photo/ai-analysis", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadPhoto(@ModelAttribute PhotoUploadRequest photoUploadRequest) {
        User user = userService.getUserById(photoUploadRequest.getUserId());
        ContentType contentType = contentTypeService.getContentType("image");

        MultipartFile file = photoUploadRequest.getFile();

        String storedFileName = fileService.storeFile(file);

        Photo photo = photoService.createPhoto(user, storedFileName, contentType);
        Photo savedPhoto = photoService.save(photo);

        savedPhoto = photoService.analyzeAndSavePhotoDetails(storedFileName, savedPhoto);

        PhotoUploadResponse response = new PhotoUploadResponse(contentType.getName(), savedPhoto.getTitle(), savedPhoto.getSummary(), savedPhoto.getLocation(), savedPhoto.getStartDate(), savedPhoto.getEndDate(), savedPhoto.getCharge(), savedPhoto.getBankAccount());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
