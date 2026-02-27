package com.example.api.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CounterpartyRiskProfileTest {

    @Test
    void constructor_shouldCreateEntity_withValidData() {
        CounterpartyRiskProfile entity = new CounterpartyRiskProfile(
                "Acme Corp", "USA", "AA+", new BigDecimal("85.50"), new BigDecimal("1000000.00"));

        assertThat(entity.getLegalName()).isEqualTo("Acme Corp");
        assertThat(entity.getCountryCode()).isEqualTo("USA");
        assertThat(entity.getCreditRating()).isEqualTo("AA+");
        assertThat(entity.getRiskScore()).isEqualByComparingTo(new BigDecimal("85.50"));
        assertThat(entity.getExposureLimit()).isEqualByComparingTo(new BigDecimal("1000000.00"));
        assertThat(entity.getCreatedAt()).isNotNull();
        assertThat(entity.getCounterpartyId()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenLegalNameIsNull() {
        assertThatThrownBy(() -> new CounterpartyRiskProfile(
                null, "USA", "AA+", new BigDecimal("85.50"), new BigDecimal("1000000.00")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Legal name must not be blank");
    }

    @Test
    void constructor_shouldThrow_whenLegalNameIsBlank() {
        assertThatThrownBy(() -> new CounterpartyRiskProfile(
                "  ", "USA", "AA+", new BigDecimal("85.50"), new BigDecimal("1000000.00")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Legal name must not be blank");
    }

    @Test
    void constructor_shouldThrow_whenCountryCodeIsNull() {
        assertThatThrownBy(() -> new CounterpartyRiskProfile(
                "Acme Corp", null, "AA+", new BigDecimal("85.50"), new BigDecimal("1000000.00")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Country code must not be blank");
    }

    @Test
    void constructor_shouldThrow_whenCountryCodeIsBlank() {
        assertThatThrownBy(() -> new CounterpartyRiskProfile(
                "Acme Corp", " ", "AA+", new BigDecimal("85.50"), new BigDecimal("1000000.00")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Country code must not be blank");
    }

    @Test
    void constructor_shouldThrow_whenRiskScoreIsNegative() {
        assertThatThrownBy(() -> new CounterpartyRiskProfile(
                "Acme Corp", "USA", "AA+", new BigDecimal("-1.00"), new BigDecimal("1000000.00")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Risk score must not be negative");
    }

    @Test
    void constructor_shouldThrow_whenExposureLimitIsNegative() {
        assertThatThrownBy(() -> new CounterpartyRiskProfile(
                "Acme Corp", "USA", "AA+", new BigDecimal("85.50"), new BigDecimal("-1.00")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Exposure limit must not be negative");
    }

    @Test
    void constructor_shouldAllowNullRiskScoreAndExposureLimit() {
        CounterpartyRiskProfile entity = new CounterpartyRiskProfile(
                "Acme Corp", "USA", "AA+", null, null);

        assertThat(entity.getRiskScore()).isNull();
        assertThat(entity.getExposureLimit()).isNull();
    }

    @Test
    void updateDetails_shouldUpdateAllFields() {
        CounterpartyRiskProfile entity = new CounterpartyRiskProfile(
                "Acme Corp", "USA", "AA+", new BigDecimal("85.50"), new BigDecimal("1000000.00"));

        entity.updateDetails("Updated Corp", "GBR", "A-", new BigDecimal("70.00"), new BigDecimal("500000.00"));

        assertThat(entity.getLegalName()).isEqualTo("Updated Corp");
        assertThat(entity.getCountryCode()).isEqualTo("GBR");
        assertThat(entity.getCreditRating()).isEqualTo("A-");
        assertThat(entity.getRiskScore()).isEqualByComparingTo(new BigDecimal("70.00"));
        assertThat(entity.getExposureLimit()).isEqualByComparingTo(new BigDecimal("500000.00"));
    }

    @Test
    void updateDetails_shouldThrow_whenLegalNameIsNull() {
        CounterpartyRiskProfile entity = new CounterpartyRiskProfile(
                "Acme Corp", "USA", "AA+", new BigDecimal("85.50"), new BigDecimal("1000000.00"));

        assertThatThrownBy(() -> entity.updateDetails(
                null, "GBR", "A-", new BigDecimal("70.00"), new BigDecimal("500000.00")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Legal name must not be blank");
    }

    @Test
    void updateDetails_shouldThrow_whenCountryCodeIsNull() {
        CounterpartyRiskProfile entity = new CounterpartyRiskProfile(
                "Acme Corp", "USA", "AA+", new BigDecimal("85.50"), new BigDecimal("1000000.00"));

        assertThatThrownBy(() -> entity.updateDetails(
                "Updated Corp", null, "A-", new BigDecimal("70.00"), new BigDecimal("500000.00")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Country code must not be blank");
    }

    @Test
    void updateDetails_shouldThrow_whenRiskScoreIsNegative() {
        CounterpartyRiskProfile entity = new CounterpartyRiskProfile(
                "Acme Corp", "USA", "AA+", new BigDecimal("85.50"), new BigDecimal("1000000.00"));

        assertThatThrownBy(() -> entity.updateDetails(
                "Updated Corp", "GBR", "A-", new BigDecimal("-1.00"), new BigDecimal("500000.00")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Risk score must not be negative");
    }

    @Test
    void updateDetails_shouldThrow_whenExposureLimitIsNegative() {
        CounterpartyRiskProfile entity = new CounterpartyRiskProfile(
                "Acme Corp", "USA", "AA+", new BigDecimal("85.50"), new BigDecimal("1000000.00"));

        assertThatThrownBy(() -> entity.updateDetails(
                "Updated Corp", "GBR", "A-", new BigDecimal("70.00"), new BigDecimal("-1.00")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Exposure limit must not be negative");
    }
}
