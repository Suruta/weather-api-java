package com.weather;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class WeatherAlertPolicyTest {

    private final WeatherAlertPolicy policy = new WeatherAlertPolicy();

    @ParameterizedTest
    @CsvSource({
            "PENDING, ACTIVE, true",
            "ACTIVE, RESOLVED, true",
            "RESOLVED, ACTIVE, false",
            "PENDING, RESOLVED, false"
    })
    @DisplayName("Verify status transitions match README specification")
    void testStatusTransitions(WeatherAlertStatus from, WeatherAlertStatus to, boolean allowed) {
        if (allowed) {
            WeatherAlertStatus result = policy.move(from, to);
            assertEquals(to, result);
        } else {
            assertThrows(IllegalStateException.class, () -> policy.move(from, to));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    @DisplayName("Blank ID must throw exception")
    void testBlankIdThrows(String invalidId) {
        assertThrows(IllegalArgumentException.class, () -> new WeatherAlertId(invalidId));
    }

    @Test
    @DisplayName("Null ID must throw exception")
    void testNullIdThrows() {
        assertThrows(IllegalArgumentException.class, () -> new WeatherAlertId(null));
    }
}