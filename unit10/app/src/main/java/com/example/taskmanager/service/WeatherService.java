package com.example.taskmanager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Service
public class WeatherService {

    private static final Logger log = LoggerFactory.getLogger(WeatherService.class);
    private static final String DEFAULT_LOCATION = "New York";
    private static final String PLACEHOLDER_KEY = "YOUR_WEATHER_API_KEY_HERE";

    private final RestClient restClient;
    private final String weatherApiUrl;
    private final String weatherApiKey;

    public WeatherService(RestClient restClient,
                           @Value("${weather.api.url}") String weatherApiUrl,
                           @Value("${weather.api.key}") String weatherApiKey) {
        this.restClient = restClient;
        this.weatherApiUrl = weatherApiUrl;
        this.weatherApiKey = weatherApiKey;
    }

    public String getCurrentWeather() {
        if (weatherApiKey == null || weatherApiKey.isBlank() || weatherApiKey.equals(PLACEHOLDER_KEY)) {
            log.debug("Skipping weather lookup: no real weather.api.key configured");
            return null;
        }

        try {
            WeatherResponse response = restClient.get()
                    .uri(weatherApiUrl + "?key={key}&q={location}", weatherApiKey, DEFAULT_LOCATION)
                    .retrieve()
                    .body(WeatherResponse.class);

            if (response == null || response.current() == null) {
                return null;
            }

            String locationName = response.location() != null ? response.location().name() : DEFAULT_LOCATION;
            return "%s: %.0f°F, %s".formatted(
                    locationName, response.current().tempF(), response.current().condition().text());
        } catch (Exception e) {
            log.warn("Failed to fetch weather from external API: {}", e.getMessage());
            return null;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record WeatherResponse(Location location, Current current) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record Location(String name) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record Current(@JsonProperty("temp_f") double tempF, Condition condition) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record Condition(String text) {
    }
}
