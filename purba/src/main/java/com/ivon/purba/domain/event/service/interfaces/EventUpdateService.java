package com.ivon.purba.domain.event.service.interfaces;

import com.ivon.purba.domain.event.dto.EventUpdateRequest;

public interface EventUpdateService {
    void updateEvent(EventUpdateRequest request);
}
