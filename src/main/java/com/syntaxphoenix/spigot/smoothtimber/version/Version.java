package com.syntaxphoenix.spigot.smoothtimber.version;

import java.util.Objects;

public final class Version implements Comparable<Version> {

    public static final Version ZERO = new Version(0, 0, 0);

    public static Version fromString(String string) {
        if (string.isBlank()) {
            return ZERO;
        }
        String[] parts = string.contains(".") ? string.split("\\.")
            : new String[] {
                string
            };
        int major = 0;
        int minor = 0;
        int patch = 0;
        try {
            major = Integer.parseInt(parts[0]);
            if (parts.length >= 2) {
                minor = Integer.parseInt(parts[1]);
                if (parts.length >= 3) {
                    patch = Integer.parseInt(parts[2]);
                }
            }
        } catch (NumberFormatException nfe) {
        }
        if (major == 0 && minor == 0 && patch == 0) {
            return ZERO;
        }
        return new Version(major, minor, patch);
    }

    private final int major, minor, patch;

    public Version(int major) {
        this(major, 0, 0);
    }

    public Version(int major, int minor) {
        this(major, minor, 0);
    }

    public Version(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    public int major() {
        return major;
    }

    public Version major(int major) {
        return new Version(major, minor, patch);
    }

    public int minor() {
        return minor;
    }

    public Version minor(int minor) {
        return new Version(major, minor, patch);
    }

    public int patch() {
        return patch;
    }

    public Version patch(int patch) {
        return new Version(major, minor, patch);
    }

    public boolean isLower(Version version) {
        return compareTo(version) < 0;
    }

    public boolean isHigher(Version version) {
        return compareTo(version) > 0;
    }

    public boolean isSimilar(Version version) {
        return version != null && (this == version || (major == version.major && minor == version.minor && patch == version.patch));
    }

    @Override
    public int hashCode() {
        return Objects.hash(major, minor, patch);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(major).append('.').append(minor);
        if (patch != 0) {
            builder.append('.').append(patch);
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Version)) {
            return false;
        }
        return isSimilar((Version) obj);
    }

    @Override
    public int compareTo(Version o) {
        if (this == o) {
            return 0;
        } else if (o == null) {
            return -1;
        }
        int comp = Integer.compare(major, o.major);
        if (comp != 0) {
            return comp;
        }
        comp = Integer.compare(minor, o.minor);
        if (comp != 0) {
            return comp;
        }
        return Integer.compare(patch, o.patch);
    }

}
