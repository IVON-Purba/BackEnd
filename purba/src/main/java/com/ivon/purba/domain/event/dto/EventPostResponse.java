package com.ivon.purba.domain.event.dto;

import lombok.Getter;

@Getter
public class EventPostResponse {
    private Long eventId;
    private String message;


    public EventPostResponse(Long eventId){
        this.eventId = eventId;
        this.message = "이벤트 저장 성공";
    }
}
