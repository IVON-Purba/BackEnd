package com.ivon.purba.domain.event.controller;

import com.ivon.purba.domain.event.dto.EventUpdateRequest;
import com.ivon.purba.domain.event.service.interfaces.EventUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EventUpdateController {

    private final EventUpdateService eventUpdateService;
    @PutMapping("/event/update")
    public ResponseEntity<String> updateEvent(@RequestBody EventUpdateRequest request) {
        eventUpdateService.updateEvent(request);
        return ResponseEntity.ok("이벤트 업데이트 성공");
    }
}
