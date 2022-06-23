package com.syntaxphoenix.spigot.smoothtimber.version.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum MCVersion {

    v1_8x("1.8", "1.8.1", "1.8.2", "1.8.3", "1.8.4", "1.8.5", "1.8.6", "1.8.7", "1.8.8", "1.8.9"),
    v1_9x("1.9", "1.9.1", "1.9.2", "1.9.3", "1.9.4", "1.10", "1.10.1", "1.10.2"),
    v1_11x("1.11", "1.11.1", "1.11.2", "1.12", "1.12.1", "1.12.2"),
    v1_13x("1.13", "1.13.1", "1.13.2", "1.14", "1.14.1", "1.14.2", "1.14.3", "1.14.4", "1.15", "1.15.1", "1.15.2"),
    v1_16x("1.16", "1.16.1", "1.16.2", "1.16.3", "1.16.4", "1.16.5", "1.17", "1.17.1", "1.18", "1.18.1", "1.18.2"),
    v1_19x("1.19", "1.19.1", "1.19.2"),
    Unsupported;

    List<String> supported;

    MCVersion() {
        supported = new ArrayList<>();
    }

    MCVersion(String... supportedVersions) {
        supported = Arrays.asList(supportedVersions);
    }

    public boolean isSupported(String verionString) {
        return supported.contains(verionString);
    }

    public List<String> getSupported() {
        return supported;
    }

    public static MCVersion fromString(String versionString) {
        for (MCVersion version : values()) {
            if (version.isSupported(versionString)) {
                return version;
            }
        }
        return MCVersion.Unsupported;
    }

    public static List<String> getSupportedVersions() {
        List<String> versions = new ArrayList<>();
        for (MCVersion version : values()) {
            versions.addAll(version.getSupported());
        }
        return versions;
    }

}
