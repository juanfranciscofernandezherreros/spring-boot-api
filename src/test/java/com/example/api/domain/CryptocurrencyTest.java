package com.example.api.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CryptocurrencyTest {

    @Test
    void constructor_shouldCreateEntity_withValidData() {
        Cryptocurrency entity = new Cryptocurrency(
                "Bitcoin", "BTC", "bitcoin",
                new BigDecimal("500000000000.00"), new BigDecimal("30000.00000000"),
                new BigDecimal("20000000000.00"),
                new BigDecimal("19000000.00000000"), new BigDecimal("21000000.00000000"),
                new BigDecimal("0.50"), new BigDecimal("1.20"), new BigDecimal("-3.40"),
                1, true);

        assertThat(entity.getName()).isEqualTo("Bitcoin");
        assertThat(entity.getSymbol()).isEqualTo("BTC");
        assertThat(entity.getSlug()).isEqualTo("bitcoin");
        assertThat(entity.getMarketCap()).isEqualByComparingTo(new BigDecimal("500000000000.00"));
        assertThat(entity.getPriceUsd()).isEqualByComparingTo(new BigDecimal("30000.00000000"));
        assertThat(entity.getVolume24h()).isEqualByComparingTo(new BigDecimal("20000000000.00"));
        assertThat(entity.getCirculatingSupply()).isEqualByComparingTo(new BigDecimal("19000000.00000000"));
        assertThat(entity.getMaxSupply()).isEqualByComparingTo(new BigDecimal("21000000.00000000"));
        assertThat(entity.getPercentChange1h()).isEqualByComparingTo(new BigDecimal("0.50"));
        assertThat(entity.getPercentChange24h()).isEqualByComparingTo(new BigDecimal("1.20"));
        assertThat(entity.getPercentChange7d()).isEqualByComparingTo(new BigDecimal("-3.40"));
        assertThat(entity.getRankPosition()).isEqualTo(1);
        assertThat(entity.getIsActive()).isTrue();
        assertThat(entity.getCreatedAt()).isNotNull();
        assertThat(entity.getUpdatedAt()).isNotNull();
        assertThat(entity.getId()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenNameIsNull() {
        assertThatThrownBy(() -> new Cryptocurrency(
                null, "BTC", "bitcoin",
                null, null, null, null, null, null, null, null, null, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Name must not be blank");
    }

    @Test
    void constructor_shouldThrow_whenNameIsBlank() {
        assertThatThrownBy(() -> new Cryptocurrency(
                "  ", "BTC", "bitcoin",
                null, null, null, null, null, null, null, null, null, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Name must not be blank");
    }

    @Test
    void constructor_shouldThrow_whenSymbolIsNull() {
        assertThatThrownBy(() -> new Cryptocurrency(
                "Bitcoin", null, "bitcoin",
                null, null, null, null, null, null, null, null, null, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Symbol must not be blank");
    }

    @Test
    void constructor_shouldThrow_whenSymbolIsBlank() {
        assertThatThrownBy(() -> new Cryptocurrency(
                "Bitcoin", " ", "bitcoin",
                null, null, null, null, null, null, null, null, null, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Symbol must not be blank");
    }

    @Test
    void constructor_shouldThrow_whenSlugIsNull() {
        assertThatThrownBy(() -> new Cryptocurrency(
                "Bitcoin", "BTC", null,
                null, null, null, null, null, null, null, null, null, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Slug must not be blank");
    }

    @Test
    void constructor_shouldThrow_whenSlugIsBlank() {
        assertThatThrownBy(() -> new Cryptocurrency(
                "Bitcoin", "BTC", "  ",
                null, null, null, null, null, null, null, null, null, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Slug must not be blank");
    }

    @Test
    void constructor_shouldThrow_whenPriceIsNegative() {
        assertThatThrownBy(() -> new Cryptocurrency(
                "Bitcoin", "BTC", "bitcoin",
                null, new BigDecimal("-1.00"), null, null, null, null, null, null, null, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Price must not be negative");
    }

    @Test
    void constructor_shouldAllowNullOptionalFields() {
        Cryptocurrency entity = new Cryptocurrency(
                "Bitcoin", "BTC", "bitcoin",
                null, null, null, null, null, null, null, null, null, null);

        assertThat(entity.getMarketCap()).isNull();
        assertThat(entity.getPriceUsd()).isNull();
        assertThat(entity.getVolume24h()).isNull();
        assertThat(entity.getCirculatingSupply()).isNull();
        assertThat(entity.getMaxSupply()).isNull();
        assertThat(entity.getPercentChange1h()).isNull();
        assertThat(entity.getPercentChange24h()).isNull();
        assertThat(entity.getPercentChange7d()).isNull();
        assertThat(entity.getRankPosition()).isNull();
        assertThat(entity.getIsActive()).isTrue();
    }

    @Test
    void updateDetails_shouldUpdateAllFields() {
        Cryptocurrency entity = new Cryptocurrency(
                "Bitcoin", "BTC", "bitcoin",
                null, new BigDecimal("30000.00"), null, null, null, null, null, null, 1, true);

        entity.updateDetails("Ethereum", "ETH", "ethereum",
                new BigDecimal("200000000000.00"), new BigDecimal("2000.00000000"),
                new BigDecimal("10000000000.00"),
                new BigDecimal("120000000.00000000"), null,
                new BigDecimal("0.30"), new BigDecimal("-0.50"), new BigDecimal("2.10"),
                2, true);

        assertThat(entity.getName()).isEqualTo("Ethereum");
        assertThat(entity.getSymbol()).isEqualTo("ETH");
        assertThat(entity.getSlug()).isEqualTo("ethereum");
        assertThat(entity.getRankPosition()).isEqualTo(2);
    }

    @Test
    void updateDetails_shouldThrow_whenNameIsNull() {
        Cryptocurrency entity = new Cryptocurrency(
                "Bitcoin", "BTC", "bitcoin",
                null, null, null, null, null, null, null, null, null, true);

        assertThatThrownBy(() -> entity.updateDetails(
                null, "ETH", "ethereum",
                null, null, null, null, null, null, null, null, null, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Name must not be blank");
    }

    @Test
    void updateDetails_shouldThrow_whenSymbolIsNull() {
        Cryptocurrency entity = new Cryptocurrency(
                "Bitcoin", "BTC", "bitcoin",
                null, null, null, null, null, null, null, null, null, true);

        assertThatThrownBy(() -> entity.updateDetails(
                "Ethereum", null, "ethereum",
                null, null, null, null, null, null, null, null, null, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Symbol must not be blank");
    }

    @Test
    void updateDetails_shouldThrow_whenSlugIsNull() {
        Cryptocurrency entity = new Cryptocurrency(
                "Bitcoin", "BTC", "bitcoin",
                null, null, null, null, null, null, null, null, null, true);

        assertThatThrownBy(() -> entity.updateDetails(
                "Ethereum", "ETH", null,
                null, null, null, null, null, null, null, null, null, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Slug must not be blank");
    }

    @Test
    void updateDetails_shouldThrow_whenPriceIsNegative() {
        Cryptocurrency entity = new Cryptocurrency(
                "Bitcoin", "BTC", "bitcoin",
                null, null, null, null, null, null, null, null, null, true);

        assertThatThrownBy(() -> entity.updateDetails(
                "Ethereum", "ETH", "ethereum",
                null, new BigDecimal("-1.00"), null, null, null, null, null, null, null, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Price must not be negative");
    }
}
