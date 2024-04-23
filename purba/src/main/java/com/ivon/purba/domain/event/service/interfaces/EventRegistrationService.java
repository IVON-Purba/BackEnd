package com.ivon.purba.domain.event.service.interfaces;

import com.ivon.purba.domain.event.dto.EventPostRequest;

public interface EventRegistrationService {
    Long registerEvent(EventPostRequest request);
}
