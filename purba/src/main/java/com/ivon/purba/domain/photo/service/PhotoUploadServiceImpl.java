package com.ivon.purba.domain.photo.service;

import com.ivon.purba.domain.ai.file.FileService;
import com.ivon.purba.domain.content.entity.ContentType;
import com.ivon.purba.domain.content.entity.ContentTypeEnum;
import com.ivon.purba.domain.content.service.interfaces.ContentTypeService;
import com.ivon.purba.domain.photo.dto.PhotoUploadRequest;
import com.ivon.purba.domain.photo.dto.PhotoUploadResponse;
import com.ivon.purba.domain.photo.entity.Photo;
import com.ivon.purba.domain.photo.repository.PhotoRepository;
import com.ivon.purba.domain.photo.service.interfaces.PhotoUploadService;
import com.ivon.purba.domain.user.entity.User;
import com.ivon.purba.domain.user.service.interfaces.UserService;
import com.ivon.purba.exception.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@Service
@Transactional
@RequiredArgsConstructor
public class PhotoUploadServiceImpl implements PhotoUploadService {

    private static final Logger logger = LoggerFactory.getLogger(PhotoUploadServiceImpl.class);

    private final PhotoRepository photoRepository;
    private final UserService userService;
    private final FileService fileService;
    private final ContentTypeService contentTypeService;

    @Override
    @Async
    public CompletableFuture<PhotoUploadResponse> uploadPhoto(PhotoUploadRequest request) {
        try {
            String storedFileName = fileService.storeFile(request.getFile());
            ContentType contentType = contentTypeService.getContentType(ContentTypeEnum.IMAGE);
            Photo photo = createPhoto(request.getUserId(), storedFileName, contentType);

            photo = photoRepository.save(photo);
            return CompletableFuture.completedFuture(new PhotoUploadResponse(photo.getUrl()));
        } catch (UserNotFoundException ex) {
            logger.error("User not found: {}", ex.getMessage());
            return CompletableFuture.failedFuture(ex);
        } catch (Exception e) {
            logger.error("Error uploading photo", e);
            return CompletableFuture.failedFuture(e);
        }
    }

    private Photo createPhoto(Long userId, String photoUrl, ContentType contentType) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("Non-existent user ID: " + userId);
        }

        return new Photo(user, contentType, photoUrl);
    }
}
