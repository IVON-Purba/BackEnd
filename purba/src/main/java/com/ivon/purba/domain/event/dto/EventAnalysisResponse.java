package com.ivon.purba.domain.event.dto;

import lombok.Getter;

import java.util.Date;

@Getter
public class EventAnalysisResponse {
    private String title;
    private String summary;
    private String location;
    private String bankAccount;
    private Integer charge;
    private Date startDate;
    private Date endDate;

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
