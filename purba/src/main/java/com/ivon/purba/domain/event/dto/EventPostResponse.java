package com.ivon.purba.domain.event.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventPostResponse {
    private Long eventId;
    private String message;


    public EventPostResponse(Long eventId, String message){
        this.eventId = eventId;
        this.message = message;
    }
}
