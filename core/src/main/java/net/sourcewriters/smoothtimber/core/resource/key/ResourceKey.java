package net.sourcewriters.smoothtimber.core.resource.key;

public class ResourceKey {

    protected final String namespace;
    protected final String key;

    private String string;

    public ResourceKey(final String namespace, final String key) {
        this.namespace = namespace;
        this.key = key;
    }

    public final String getNamespace() {
        return namespace;
    }

    public final String getKey() {
        return key;
    }

    protected String asString() {
        return namespace + ':' + key;
    }

    @Override
    public final String toString() {
        return string == null ? (string = asString()) : string;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    /**
     * Parses the string argument as a namespaced key.
     * <p>
     * An exception of type {@code KeyFormatException} is thrown if any of the
     * following situations occurs:
     * <ul>
     * <li>The first argument is {@code null} or is a length of zero.
     * <li>The string doesn't meet the required format of 'namespace:key'
     * <li>Any character of the namespace string is not a lowercase alphanumeric
     * character, period, underscore, hyphen
     * <li>Any character of the key string is not a lowercase alphanumeric
     * character, period, underscore, hyphen or forward slash
     * <p>
     * Examples: <blockquote>
     * 
     * <pre>
     * fromString("minecraft:stone") returns a valid ResourceKey ("minecraft:stone")
     * fromString("minecraftstone") throws a KeyFormatException
     * fromString("minecraft:stone:stone") throws a KeyFormatException
     * fromString("minecraft::stone") throws a KeyFormatException
     * </pre>
     * 
     * </blockquote>
     * 
     * @param  value              the {@code String} containing the namespaced key
     *                                representation to be parsed
     * 
     * @return                    the namespaced key represented by the string
     *                                argument
     * 
     * @throws KeyFormatException if the {@code String} does not contain a parseable
     *                                {@code ResourceKey}
     */
    public static ResourceKey fromString(final String value) throws KeyFormatException {
        return null;
    }

}
