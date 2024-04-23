package com.ivon.purba.domain.event.dto;

import com.ivon.purba.domain.event.entity.Event;
import lombok.Getter;

@Getter
public class EventGetResponse {
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
    public EventGetResponse(Event event){
        this.userId = event.getId();
        this.eventTypeName = event.getEventType().getName();
        this.location = event.getLocation();
        this.title = event.getTitle();
        this.data = event.getData();
        this.photoUrl = event.getPhotoUrl();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
        this.charge = event.getCharge();
        this.backAccount = event.getBankAccount();
        this.summary = event.getSummary();
    }
}
