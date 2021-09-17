package net.sourcewriters.smoothtimber.core.resource.key;

public class ResourceKey {

    protected final String namespace;
    protected final String key;

    private String string;

    /**
     * Constructs a namespaced key
     * 
     * @param namespace the namespace
     * @param key       the key
     */
    public ResourceKey(final String namespace, final String key) {
        this.namespace = namespace;
        this.key = key;
    }

    /**
     * Gets the namespace of this resource
     * 
     * @return the namespace
     */
    public final String getNamespace() {
        return namespace;
    }

    /**
     * Gets the key of this resource
     * 
     * @return the key
     */
    public final String getKey() {
        return key;
    }

    /**
     * Builds the full string representation of this namespaced key
     * 
     * @return the built string
     */
    protected String asString() {
        return namespace + ':' + key;
    }

    /**
     * Gets or builds the full string representation of this namespaced key
     * 
     * @return the full string representation
     */
    @Override
    public final String toString() {
        return string == null ? (string = asString()) : string;
    }

    /**
     * Gets the hash of the string representation of this namespaced key and uses it
     * as hash for itself
     * 
     * @return the hash
     */
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
