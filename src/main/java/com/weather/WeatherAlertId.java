package com.weather;

public record WeatherAlertId(String value) {
    public WeatherAlertId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("WeatherAlert ID cannot be null or blank.");
        }
    }
}