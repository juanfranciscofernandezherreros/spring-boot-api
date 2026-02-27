package com.example.api.api.version;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ApiVersionTest {

    @Test
    void fromString_shouldReturnV1_forValidVersion() {
        assertThat(ApiVersion.fromString("1")).isEqualTo(ApiVersion.V1);
    }

    @Test
    void fromString_shouldReturnNull_forInvalidVersion() {
        assertThat(ApiVersion.fromString("99")).isNull();
    }

    @Test
    void fromString_shouldReturnNull_forNullVersion() {
        assertThat(ApiVersion.fromString(null)).isNull();
    }

    @Test
    void getVersion_shouldReturnVersionString() {
        assertThat(ApiVersion.V1.getVersion()).isEqualTo("1");
    }
}
