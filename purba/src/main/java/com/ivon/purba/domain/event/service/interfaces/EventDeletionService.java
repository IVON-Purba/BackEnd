package com.ivon.purba.domain.event.service.interfaces;

public interface EventDeletionService {
    void deleteEventById(Long eventId);

    void cancelDeleteEventById(Long eventId);
}
