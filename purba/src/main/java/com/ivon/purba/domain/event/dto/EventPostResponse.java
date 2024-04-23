package com.ivon.purba.domain.event.dto;

import lombok.Getter;

@Getter
public class EventPostResponse {
    private final Long eventId;
    private final String message;

    public EventPostResponse(Long eventId){
        this.eventId = eventId;
        this.message = "이벤트 저장 성공";
    }
}
