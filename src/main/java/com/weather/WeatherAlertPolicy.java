package com.weather;

public class WeatherAlertPolicy {

    public WeatherAlertStatus move(WeatherAlertStatus from, WeatherAlertStatus to) {
        if (from == WeatherAlertStatus.PENDING && to == WeatherAlertStatus.ACTIVE) {
            return to;
        }
        if (from == WeatherAlertStatus.ACTIVE && to == WeatherAlertStatus.RESOLVED) {
            return to;
        }

        throw new IllegalStateException("Forbidden status change from " + from + " to " + to);
    }
}