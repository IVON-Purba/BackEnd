package com.ivon.purba.domain.content.service;

import com.ivon.purba.domain.content.entity.ContentType;
import com.ivon.purba.domain.content.entity.ContentTypeEnum;
import com.ivon.purba.domain.content.repository.ContentTypeRepository;
import com.ivon.purba.exception.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ContentTypeServiceImpl implements ContentTypeService {

    private final ContentTypeRepository contentTypeRepository;

    public ContentType getContentType(ContentTypeEnum contentTypeEnum) {
        return contentTypeRepository.findByName(contentTypeEnum.getName())
                .orElseThrow(() -> new ResourceNotFoundException("해당 콘텐트의 종류가 존재하지 않습니다.: " + contentTypeEnum.getName()));
    }
}
