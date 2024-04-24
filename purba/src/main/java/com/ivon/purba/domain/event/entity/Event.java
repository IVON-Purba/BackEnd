package com.ivon.purba.domain.event.entity;

import com.ivon.purba.domain.content.entity.Content;
import com.ivon.purba.domain.content.entity.ContentType;
import com.ivon.purba.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "event")
public class Event extends Content {

    @ManyToOne
    @JoinColumn(name = "event_type", nullable = false)
    private EventType eventType;

    @Column(name = "photo_url")
    private String photoUrl;

    public Event() {
        // Default constructor
    }

    public Event(User user, ContentType contentType, EventType eventType, String location, String title, String data, String photoUrl, String startDate, String endDate, Integer charge, String backAccount, String summary) {
        this.eventType = eventType;
        this.photoUrl = photoUrl;
        super.createContent(user, contentType, location, title, data, startDate, endDate, charge, backAccount, summary);
    }

    public void updateEvent(User user, EventType eventType, String title, String location, String data, String photoUrl, String startDate,
                            String endDate, Integer charge, String bankAccount, String summary) {
        if (eventType != null) {
            this.eventType = eventType;
        }
        if (photoUrl != null) {
            this.photoUrl = photoUrl;
        }
        super.updateFields(user, title, location, data, startDate, endDate, charge, bankAccount, summary);
    }
}
