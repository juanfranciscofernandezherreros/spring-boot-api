package com.example.api.api.resolver;

import com.example.api.api.version.ApiVersion;
import com.example.api.config.ApiVersionProperties;
import com.example.api.exception.UnsupportedVersionException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class ApiVersionResolver {

    private final ApiVersionProperties versionProperties;

    public ApiVersionResolver(ApiVersionProperties versionProperties) {
        this.versionProperties = versionProperties;
    }

    public ApiVersion resolve(HttpServletRequest request) {
        String headerValue = request.getHeader(versionProperties.headerName());
        if (headerValue == null || headerValue.isBlank()) {
            return ApiVersion.fromString(versionProperties.defaultVersion());
        }
        ApiVersion version = ApiVersion.fromString(headerValue);
        if (version == null) {
            throw new UnsupportedVersionException(
                    "Unsupported API version: " + headerValue + ". Supported versions: " + versionProperties.supported());
        }
        return version;
    }
}
