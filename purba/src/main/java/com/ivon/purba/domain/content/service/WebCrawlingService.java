package com.ivon.purba.domain.content.service;

import com.ivon.purba.domain.content.entity.Content;
import com.ivon.purba.domain.content.entity.ContentType;
import com.ivon.purba.domain.content.entity.ContentTypeEnum;
import com.ivon.purba.domain.content.repository.ContentRepository;
import com.ivon.purba.domain.content.service.interfaces.ContentTypeService;
import com.ivon.purba.domain.user.entity.User;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class WebCrawlingService {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private ContentTypeService contentTypeService;

    public void crawlAndSave(String url, User user) {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements titleElements = doc.select("your-title-selector"); // CSS Selector for title
            Elements dataElements = doc.select("your-content-selector"); // CSS Selector for content

            String title = titleElements.text();
            String data = dataElements.text();

            ContentType contentType = contentTypeService.getContentType(ContentTypeEnum.IMAGE); // 콘텐트 타입을 IMAGE로 지정
            Content content = new Content(user, contentType);
            content.createContent(user, contentType, null, title, data, null, null, null, null, null);
            contentRepository.save(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

