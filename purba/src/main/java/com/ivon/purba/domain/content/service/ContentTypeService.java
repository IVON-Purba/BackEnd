package com.ivon.purba.domain.content.service;


import com.ivon.purba.domain.content.entity.ContentType;
import com.ivon.purba.domain.content.entity.ContentTypeEnum;

public interface ContentTypeService {

    ContentType getContentType(ContentTypeEnum contentTypeEnum);
}
