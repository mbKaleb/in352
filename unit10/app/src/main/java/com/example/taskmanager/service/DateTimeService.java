package com.example.taskmanager.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Service
public class DateTimeService {

    private static final Logger log = LoggerFactory.getLogger(DateTimeService.class);

    // timeapi.io returns a plain ISO-8601 local date/time, e.g. "2026-08-17T12:34:56.1234567" -
    // reformat it into something clock-like instead of handing that raw string to the template.
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM d, yyyy h:mm a");

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

            if (response == null || response.dateTime() == null) {
                return null;
            }

            return LocalDateTime.parse(response.dateTime()).format(DISPLAY_FORMAT);
        } catch (Exception e) {
            log.warn("Failed to fetch current date/time from external API: {}", e.getMessage());
            return null;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record DateTimeResponse(String dateTime) {
    }
}
