package com.syntaxphoenix.spigot.smoothtimber.version;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;

public final class VersionManager {

    private static final VersionState[] STATES = VersionState.values();

    public static enum VersionState {
        
        SUPPORTED,
        UNTESTED,
        UNSUPPORTED,
        INCOMPATIBLE;

    }

    private final EnumMap<VersionState, ArrayList<Version>> map = new EnumMap<>(VersionState.class);

    public VersionManager() {
        for (VersionState state : STATES) {
            map.put(state, new ArrayList<>());
        }
    }

    public void clear() {
        for (VersionState state : STATES) {
            clear(state);
        }
    }

    public void clear(VersionState state) {
        Objects.requireNonNull(state, "VersionState can't be null");
        map.get(state).clear();
    }

    public void sort() {
        for (VersionState state : STATES) {
            sort(state);
        }
    }

    public void sort(VersionState state) {
        Objects.requireNonNull(state, "VersionState can't be null");
        ArrayList<Version> list = map.get(state);
        if (list.size() < 2) {
            return;
        }
        Collections.sort(list);
    }

    public VersionState state(Version version) {
        VersionState exactState = exactState(version);
        if (exactState != null) {
            return exactState;
        }
        Version lowest = getLowest(VersionState.SUPPORTED, VersionState.UNTESTED);
        if (lowest != null && version.isLower(lowest)) {
            return VersionState.INCOMPATIBLE;
        }
        Version highest = getHighest(VersionState.SUPPORTED, VersionState.UNTESTED);
        if (highest != null && version.isHigher(highest)) {
            return VersionState.UNTESTED;
        }
        return VersionState.UNSUPPORTED;
    }

    public VersionState exactState(Version version) {
        for (VersionState state : STATES) {
            if (has(state, version)) {
                return state;
            }
        }
        return null;
    }

    public boolean has(VersionState state, Version version) {
        Objects.requireNonNull(state, "VersionState can't be null");
        return map.get(state).contains(version);
    }

    public List<Version> get(VersionState state) {
        Objects.requireNonNull(state, "VersionState can't be null");
        return Collections.unmodifiableList(map.get(state));
    }

    public Version getLowest(VersionState state) {
        Objects.requireNonNull(state, "VersionState can't be null");
        ArrayList<Version> list = map.get(state);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public Version getLowest(VersionState... states) {
        Objects.requireNonNull(states, "VersionState[] can't be null");
        Version lowest = null;
        for (VersionState state : states) {
            Version current = getHighest(state);
            if (current == null || (lowest != null && !current.isLower(lowest))) {
                continue;
            }
            lowest = current;
        }
        return lowest;
    }

    public Version getHighest(VersionState state) {
        Objects.requireNonNull(state, "VersionState can't be null");
        ArrayList<Version> list = map.get(state);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(list.size() - 1);
    }

    public Version getHighest(VersionState... states) {
        Objects.requireNonNull(states, "VersionState[] can't be null");
        Version highest = null;
        for (VersionState state : states) {
            Version current = getHighest(state);
            if (current == null || (highest != null && !current.isHigher(highest))) {
                continue;
            }
            highest = current;
        }
        return highest;
    }

    public void set(VersionState state, Version version) {
        Objects.requireNonNull(state, "VersionState can't be null");
        ArrayList<Version> list = map.get(state);
        list.clear();
        list.add(version);
    }

    public void set(VersionState state, Version... versions) {
        Objects.requireNonNull(state, "VersionState can't be null");
        ArrayList<Version> list = map.get(state);
        list.clear();
        for (Version version : versions) {
            if (list.contains(version)) {
                continue;
            }
            list.add(version);
        }
        sort(state);
    }

    public void add(VersionState state, Version version) {
        Objects.requireNonNull(state, "VersionState can't be null");
        ArrayList<Version> list = map.get(state);
        if (list.contains(version)) {
            return;
        }
        list.add(version);
        sort(state);
    }

    public void add(VersionState state, Version... versions) {
        Objects.requireNonNull(state, "VersionState can't be null");
        ArrayList<Version> list = map.get(state);
        for (Version version : versions) {
            if (list.contains(version)) {
                continue;
            }
            list.add(version);
        }
        sort(state);
    }

    public void remove(VersionState state, Version version) {
        Objects.requireNonNull(state, "VersionState can't be null");
        map.get(state).remove(version);
    }

    public void remove(VersionState state, Version... versions) {
        Objects.requireNonNull(state, "VersionState can't be null");
        ArrayList<Version> list = map.get(state);
        for (Version version : versions) {
            list.remove(version);
        }
    }

}
