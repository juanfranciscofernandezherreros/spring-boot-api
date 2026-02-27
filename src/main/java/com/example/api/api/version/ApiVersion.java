package com.example.api.api.version;

public enum ApiVersion {

    V1("1");

    private final String version;

    ApiVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public static ApiVersion fromString(String version) {
        for (ApiVersion v : values()) {
            if (v.version.equals(version)) {
                return v;
            }
        }
        return null;
    }
}
