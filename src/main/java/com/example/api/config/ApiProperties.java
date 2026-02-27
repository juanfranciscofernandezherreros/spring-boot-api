package com.example.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api")
public record ApiProperties(
        String basePath,
        String helloPath,
        String counterpartyRiskProfilesPath,
        String transferenciasPath,
        String cryptocurrenciesPath
) {
}
