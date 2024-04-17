package com.ivon.purba.domain.content.photo.service;

import com.ivon.purba.domain.content.entity.ContentType;
import com.ivon.purba.domain.content.photo.entity.Photo;
import com.ivon.purba.domain.user.entity.User;
import com.ivon.purba.exception.exceptions.AIAnalysisException;
import com.ivon.purba.exception.exceptions.PhotoSaveException;
import com.ivon.purba.exception.exceptions.ResourceNotFoundException;

public interface PhotoService {
    Photo save(Photo photo) throws PhotoSaveException;

    Photo createPhoto(User user, String photoUrl, ContentType contentType);

    Photo findById(Long id) throws ResourceNotFoundException;
    Photo analyzeAndSavePhotoDetails(String filePath, Photo photo) throws AIAnalysisException;
}
