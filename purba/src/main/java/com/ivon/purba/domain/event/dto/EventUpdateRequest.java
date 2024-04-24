package com.ivon.purba.domain.event.dto;

import lombok.Getter;

@Getter
public class EventUpdateRequest {
    private Long eventId;
    private Long userId;
    private String eventTypeName;
    private String title;
    private String location;
    private String data;
    private String photoUrl;
    private String startDate;
    private String endDate;
    private Integer charge;
    private String backAccount;
    private String summary;
}
