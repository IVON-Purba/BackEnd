package com.ivon.purba.domain.content.service;

import com.ivon.purba.domain.content.entity.ContentType;
import com.ivon.purba.domain.content.repository.ContentTypeRepository;
import com.ivon.purba.domain.content.service.ContentTypeService;
import com.ivon.purba.exception.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ContentTypeServiceImpl implements ContentTypeService {

    private final ContentTypeRepository contentTypeRepository;

    @Autowired
    public ContentTypeServiceImpl(ContentTypeRepository contentTypeRepository) {
        this.contentTypeRepository = contentTypeRepository;
    }

    @Override
    public ContentType getContentType(String contentTypeName) {
        return contentTypeRepository.findByName(contentTypeName)
                .orElseThrow(() -> new ResourceNotFoundException("해당 콘텐트의 종류가 존재하지 않습니다.: " + contentTypeName));
    }
}
