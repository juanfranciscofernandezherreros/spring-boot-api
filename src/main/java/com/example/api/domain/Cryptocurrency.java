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

@Entity
@Table(name = "cryptocurrencies", indexes = {
        @Index(name = "idx_symbol", columnList = "symbol"),
        @Index(name = "idx_slug", columnList = "slug"),
        @Index(name = "idx_rank_position", columnList = "rank_position")
})
public class Cryptocurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "symbol", nullable = false, unique = true, length = 20)
    private String symbol;

    @Column(name = "slug", nullable = false, unique = true, length = 120)
    private String slug;

    @Column(name = "market_cap", precision = 20, scale = 2)
    private BigDecimal marketCap;

    @Column(name = "price_usd", precision = 18, scale = 8)
    private BigDecimal priceUsd;

    @Column(name = "volume_24h", precision = 20, scale = 2)
    private BigDecimal volume24h;

    @Column(name = "circulating_supply", precision = 30, scale = 8)
    private BigDecimal circulatingSupply;

    @Column(name = "max_supply", precision = 30, scale = 8)
    private BigDecimal maxSupply;

    @Column(name = "percent_change_1h", precision = 6, scale = 2)
    private BigDecimal percentChange1h;

    @Column(name = "percent_change_24h", precision = 6, scale = 2)
    private BigDecimal percentChange24h;

    @Column(name = "percent_change_7d", precision = 6, scale = 2)
    private BigDecimal percentChange7d;

    @Column(name = "rank_position")
    private Integer rankPosition;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    protected Cryptocurrency() {
    }

    public Cryptocurrency(String name, String symbol, String slug,
                          BigDecimal marketCap, BigDecimal priceUsd, BigDecimal volume24h,
                          BigDecimal circulatingSupply, BigDecimal maxSupply,
                          BigDecimal percentChange1h, BigDecimal percentChange24h,
                          BigDecimal percentChange7d, Integer rankPosition,
                          Boolean isActive) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }
        if (symbol == null || symbol.isBlank()) {
            throw new IllegalArgumentException("Symbol must not be blank");
        }
        if (slug == null || slug.isBlank()) {
            throw new IllegalArgumentException("Slug must not be blank");
        }
        if (priceUsd != null && priceUsd.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price must not be negative");
        }
        this.name = name;
        this.symbol = symbol;
        this.slug = slug;
        this.marketCap = marketCap;
        this.priceUsd = priceUsd;
        this.volume24h = volume24h;
        this.circulatingSupply = circulatingSupply;
        this.maxSupply = maxSupply;
        this.percentChange1h = percentChange1h;
        this.percentChange24h = percentChange24h;
        this.percentChange7d = percentChange7d;
        this.rankPosition = rankPosition;
        this.isActive = isActive != null ? isActive : Boolean.TRUE;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public void updateDetails(String name, String symbol, String slug,
                              BigDecimal marketCap, BigDecimal priceUsd,
                              BigDecimal volume24h, BigDecimal circulatingSupply,
                              BigDecimal maxSupply, BigDecimal percentChange1h,
                              BigDecimal percentChange24h, BigDecimal percentChange7d,
                              Integer rankPosition, Boolean isActive) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }
        if (symbol == null || symbol.isBlank()) {
            throw new IllegalArgumentException("Symbol must not be blank");
        }
        if (slug == null || slug.isBlank()) {
            throw new IllegalArgumentException("Slug must not be blank");
        }
        if (priceUsd != null && priceUsd.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price must not be negative");
        }
        this.name = name;
        this.symbol = symbol;
        this.slug = slug;
        this.marketCap = marketCap;
        this.priceUsd = priceUsd;
        this.volume24h = volume24h;
        this.circulatingSupply = circulatingSupply;
        this.maxSupply = maxSupply;
        this.percentChange1h = percentChange1h;
        this.percentChange24h = percentChange24h;
        this.percentChange7d = percentChange7d;
        this.rankPosition = rankPosition;
        this.isActive = isActive;
        this.updatedAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getSlug() {
        return slug;
    }

    public BigDecimal getMarketCap() {
        return marketCap;
    }

    public BigDecimal getPriceUsd() {
        return priceUsd;
    }

    public BigDecimal getVolume24h() {
        return volume24h;
    }

    public BigDecimal getCirculatingSupply() {
        return circulatingSupply;
    }

    public BigDecimal getMaxSupply() {
        return maxSupply;
    }

    public BigDecimal getPercentChange1h() {
        return percentChange1h;
    }

    public BigDecimal getPercentChange24h() {
        return percentChange24h;
    }

    public BigDecimal getPercentChange7d() {
        return percentChange7d;
    }

    public Integer getRankPosition() {
        return rankPosition;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
