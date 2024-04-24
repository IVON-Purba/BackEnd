package com.ivon.purba.domain.event.dto;

import com.ivon.purba.domain.event.entity.Event;
import lombok.Getter;

@Getter
public class EventGetResponse {
    private final Long userId;
    private final String eventTypeName;
    private final String location;
    private final String title;
    private final String data;
    private final String photoUrl;
    private final String startDate;
    private final String endDate;
    private final Integer charge;
    private final String backAccount;
    private final String summary;

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
