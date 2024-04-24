package com.ivon.purba.domain.event.dto;

import lombok.Getter;

import java.util.Date;

@Getter
public class EventAnalysisResponse {
    private final String title;
    private final String summary;
    private final String location;
    private final String bankAccount;
    private final Integer charge;
    private final Date startDate;
    private final Date endDate;

    public EventAnalysisResponse(String title, String summary, String location, Date startDate, Date endDate, Integer charge, String bankAccount) {
        this.title = title;
        this.summary = summary;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.charge = charge;
        this.bankAccount = bankAccount;
    }
}
