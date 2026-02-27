package com.example.api.api.resolver;

import com.example.api.api.version.ApiVersion;
import com.example.api.config.ApiVersionProperties;
import com.example.api.exception.UnsupportedVersionException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApiVersionResolverTest {

    @Mock
    private HttpServletRequest request;

    private ApiVersionResolver resolver;

    @BeforeEach
    void setUp() {
        ApiVersionProperties properties = new ApiVersionProperties("X-API-Version", "1", "1");
        resolver = new ApiVersionResolver(properties);
    }

    @Test
    void resolve_shouldReturnV1_whenHeaderIsV1() {
        when(request.getHeader("X-API-Version")).thenReturn("1");

        ApiVersion version = resolver.resolve(request);

        assertThat(version).isEqualTo(ApiVersion.V1);
    }

    @Test
    void resolve_shouldReturnDefault_whenHeaderIsMissing() {
        when(request.getHeader("X-API-Version")).thenReturn(null);

        ApiVersion version = resolver.resolve(request);

        assertThat(version).isEqualTo(ApiVersion.V1);
    }

    @Test
    void resolve_shouldReturnDefault_whenHeaderIsBlank() {
        when(request.getHeader("X-API-Version")).thenReturn("  ");

        ApiVersion version = resolver.resolve(request);

        assertThat(version).isEqualTo(ApiVersion.V1);
    }

    @Test
    void resolve_shouldThrowUnsupportedVersionException_whenHeaderIsInvalid() {
        when(request.getHeader("X-API-Version")).thenReturn("99");

        assertThatThrownBy(() -> resolver.resolve(request))
                .isInstanceOf(UnsupportedVersionException.class)
                .hasMessageContaining("Unsupported API version: 99");
    }
}
