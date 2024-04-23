package com.ivon.purba.domain.event.dto;

import lombok.Getter;

@Getter
public class EventPostRequest {
    private Long userId;
    private String eventTypeName;
    private String location;
    private String title;
    private String data;
    private String photoUrl;
    private String startDate;
    private String endDate;
    private Integer charge;
    private String backAccount;
    private String summary;
}
