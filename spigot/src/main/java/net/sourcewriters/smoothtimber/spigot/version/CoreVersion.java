package net.sourcewriters.smoothtimber.spigot.version;

public enum CoreVersion {

    v1_13(CoreFunction.requireClass("org.bukkit.block.data.BlockData")),
    v1_14(CoreFunction.requireClass("org.bukkit.persistence.PersistentDataContainer")),
    v1_15(CoreFunction.requireClass("org.bukkit.entity.Bee")),
    v1_16(CoreFunction.requireClass("org.bukkit.entity.Strider")),
    v1_17(CoreFunction.requireClass("org.bukkit.entity.Goat")),
    v1_18(CoreFunction.requireClass("org.bukkit.generator.WorldInfo")),
    v1_19(CoreFunction.requireClass("org.bukkit.entity.Warden"));

    public static final CoreVersion VERSION = detectVersion();

    private static CoreVersion detectVersion() {
        CoreVersion[] versions = CoreVersion.values();
        for (int index = versions.length - 1; index >= 0; index--) {
            CoreVersion version = versions[index];
            if (version.isSupported()) {
                return version;
            }
        }
        return null;
    }

    private final boolean supported;

    CoreVersion() {
        this.supported = true;
    }

    CoreVersion(CoreFunction function) {
        this.supported = function.isSupported();
    }

    public boolean isSupported() {
        return supported;
    }

    @FunctionalInterface
    private static interface CoreFunction {

        static CoreFunction requireClass(String className) {
            return () -> {
                try {
                    Class.forName(className);
                    return true;
                } catch (ClassNotFoundException | LinkageError ignore) {
                    return false;
                }
            };
        }

        boolean isSupported();

    }

}
