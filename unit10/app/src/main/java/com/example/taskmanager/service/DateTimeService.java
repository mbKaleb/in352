package com.example.taskmanager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Service
public class DateTimeService {

    private static final Logger log = LoggerFactory.getLogger(DateTimeService.class);

    private final RestClient restClient;
    private final String datetimeApiUrl;

    public DateTimeService(RestClient restClient, @Value("${datetime.api.url}") String datetimeApiUrl) {
        this.restClient = restClient;
        this.datetimeApiUrl = datetimeApiUrl;
    }

    public String getCurrentDateTime() {
        try {
            DateTimeResponse response = restClient.get()
                    .uri(datetimeApiUrl)
                    .retrieve()
                    .body(DateTimeResponse.class);

            return response != null ? response.dateTime() : null;
        } catch (Exception e) {
            log.warn("Failed to fetch current date/time from external API: {}", e.getMessage());
            return null;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record DateTimeResponse(String dateTime) {
    }
}
