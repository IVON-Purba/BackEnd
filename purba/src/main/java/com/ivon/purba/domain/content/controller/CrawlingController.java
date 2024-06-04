package com.ivon.purba.domain.content.controller;

import com.ivon.purba.domain.content.service.WebCrawlingService;
import com.ivon.purba.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/content")
public class CrawlingController {

    @Autowired
    private WebCrawlingService webCrawlingService;

    @GetMapping("/crawl")
    public ResponseEntity<String> crawl(@RequestParam String url, @RequestParam Long userId) {
        User user = new User();
        user.setId(userId);
        webCrawlingService.crawlAndSave(url, user);
        return ResponseEntity.ok("Crawling Started and Data Saved");
    }
}

