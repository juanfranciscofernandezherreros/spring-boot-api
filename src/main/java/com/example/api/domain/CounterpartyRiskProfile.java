package com.example.api.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "counterparty_risk_profile", indexes = {
        @Index(name = "idx_legal_name", columnList = "legal_name"),
        @Index(name = "idx_country_code", columnList = "country_code")
})
public class CounterpartyRiskProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "counterparty_id")
    private UUID counterpartyId;

    @Column(name = "legal_name", nullable = false, length = 200)
    private String legalName;

    @Column(name = "country_code", nullable = false, length = 3)
    private String countryCode;

    @Column(name = "credit_rating", length = 10)
    private String creditRating;

    @Column(name = "risk_score", precision = 5, scale = 2)
    private BigDecimal riskScore;

    @Column(name = "exposure_limit", precision = 18, scale = 2)
    private BigDecimal exposureLimit;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    protected CounterpartyRiskProfile() {
    }

    public CounterpartyRiskProfile(String legalName, String countryCode, String creditRating,
                                   BigDecimal riskScore, BigDecimal exposureLimit) {
        if (legalName == null || legalName.isBlank()) {
            throw new IllegalArgumentException("Legal name must not be blank");
        }
        if (countryCode == null || countryCode.isBlank()) {
            throw new IllegalArgumentException("Country code must not be blank");
        }
        if (riskScore != null && riskScore.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Risk score must not be negative");
        }
        if (exposureLimit != null && exposureLimit.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Exposure limit must not be negative");
        }
        this.legalName = legalName;
        this.countryCode = countryCode;
        this.creditRating = creditRating;
        this.riskScore = riskScore;
        this.exposureLimit = exposureLimit;
        this.createdAt = Instant.now();
    }

    public void updateDetails(String legalName, String countryCode, String creditRating,
                              BigDecimal riskScore, BigDecimal exposureLimit) {
        if (legalName == null || legalName.isBlank()) {
            throw new IllegalArgumentException("Legal name must not be blank");
        }
        if (countryCode == null || countryCode.isBlank()) {
            throw new IllegalArgumentException("Country code must not be blank");
        }
        if (riskScore != null && riskScore.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Risk score must not be negative");
        }
        if (exposureLimit != null && exposureLimit.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Exposure limit must not be negative");
        }
        this.legalName = legalName;
        this.countryCode = countryCode;
        this.creditRating = creditRating;
        this.riskScore = riskScore;
        this.exposureLimit = exposureLimit;
    }

    public UUID getCounterpartyId() {
        return counterpartyId;
    }

    public String getLegalName() {
        return legalName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCreditRating() {
        return creditRating;
    }

    public BigDecimal getRiskScore() {
        return riskScore;
    }

    public BigDecimal getExposureLimit() {
        return exposureLimit;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
