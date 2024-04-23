package com.ivon.purba.domain.event.controller;

import com.ivon.purba.domain.event.service.interfaces.EventDeletionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EventDeletionController {

    private final EventDeletionService eventDeleteService;
    @DeleteMapping("/event/delete")
    public ResponseEntity<String> deleteEvent(@RequestParam("eventId") Long eventId) {
        eventDeleteService.deleteEventById(eventId);
        return ResponseEntity.ok("이벤트 삭제 성공");
    }

    @PutMapping("/event/cancelDelete")
    public ResponseEntity<String> cancelDeleteEvent(@RequestParam("eventId") Long eventId) {
        eventDeleteService.cancelDeleteEventById(eventId);
        return ResponseEntity.ok("이벤트 복구 성공");
    }
}
