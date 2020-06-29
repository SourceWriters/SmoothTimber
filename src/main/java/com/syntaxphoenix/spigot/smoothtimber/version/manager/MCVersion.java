package com.syntaxphoenix.spigot.smoothtimber.version.manager;

import java.util.ArrayList;
import java.util.List;

import com.syntaxphoenix.spigot.smoothtimber.utilities.Lists;

public enum MCVersion {
	
	 v1_8x("1.8", "1.8.1", "1.8.2", "1.8.3", "1.8.4", "1.8.5", "1.8.6", "1.8.7", "1.8.8", "1.8.9")
	,v1_9x("1.9", "1.9.1", "1.9.2", "1.9.3", "1.9.4", "1.10", "1.10.1", "1.10.2")
	,v1_11x("1.11", "1.11.1", "1.11.2", "1.12", "1.12.1", "1.12.2")
	,v1_13x("1.13", "1.13.1", "1.13.2", "1.14", "1.14.1", "1.14.2", "1.14.3", "1.14.4", "1.15", "1.15.1", "1.15.2", "1.16", "1.16.1")
	,Unsupported;
	
	List<String> supported;
	MCVersion(String... supportedVersions) {
		supported = Lists.asList(supportedVersions);
	}
	
	public boolean isSupported(String ver) {
		return supported.contains(ver);
	}
	
	public List<String> getSupported() {
		return supported;
	}
	
	public static MCVersion fromString(String ver) {
		for(MCVersion v : values()) {
			if(v.isSupported(ver)) {
				return v;
			}
		}
		return MCVersion.Unsupported;
	}
	
	public static List<String> getSupportedVersions() {
		List<String> versions = new ArrayList<>();
		for(MCVersion v : values()) {
			versions.addAll(v.getSupported());
		}
		return versions;
	}

}
