package com.example.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api.version")
public record ApiVersionProperties(
        String headerName,
        String defaultVersion,
        String supported
) {
}
