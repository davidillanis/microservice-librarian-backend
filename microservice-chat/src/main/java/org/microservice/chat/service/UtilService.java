package org.microservice.chat.service;

import java.time.OffsetDateTime;

public interface UtilService {
    String getFromDataAPI(String url);
    OffsetDateTime getDateTimePeru(String url);
}
