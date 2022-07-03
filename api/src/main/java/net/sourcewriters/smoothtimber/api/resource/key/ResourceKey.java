package net.sourcewriters.smoothtimber.api.resource.key;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResourceKey {

    public static final Pattern VALID_NAMED_KEY = Pattern
        .compile("(?<Namespace>[0-9a-zA-Z]+):(?<Key>[0-9a-zA-Z]([/.]?[0-9a-zA-Z])*)(@\\\"(?<Name>.+)\\\")?");

    protected final String namespace;
    protected final String key;

    private String string;

    /**
     * Constructs a resource key
     * 
     * @param namespace the namespace of the resource
     * @param key       the id of the resource
     */
    public ResourceKey(final String namespace, final String key) {
        this.namespace = Objects.requireNonNull(namespace).toLowerCase();
        this.key = Objects.requireNonNull(key).toLowerCase();
        if (!VALID_NAMED_KEY.matcher(toString()).matches()) {
            throw new IllegalArgumentException("'" + namespace + ':' + key + "' is not valid!");
        }
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
     * Gets or builds the resource string representation of this namespaced key
     * 
     * @return the full string representation
     */
    public String toResourceString() {
        return toString();
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
     * fromString("minecraft:stone@\"test\"") returns a valid NamedResourceKey ("minecraft:stone@\"test\"")
     * fromString("minecraftstone") throws a KeyFormatException
     * fromString("minecraft:stone:stone") throws a KeyFormatException
     * fromString("minecraft::stone") throws a KeyFormatException
     * </pre>
     * 
     * </blockquote>
     * 
     * @param  value              the {@code String} containing the namespaced named
     *                                key representation to be parsed
     * 
     * @return                    the namespaced key represented by the string
     *                                argument
     * 
     * @throws KeyFormatException if the {@code String} does not contain a parseable
     *                                {@code ResourceKey}
     */
    public static ResourceKey fromString(final String value) throws KeyFormatException {
        final Matcher matcher = VALID_NAMED_KEY.matcher(value);
        if (!matcher.matches()) {
            throw new KeyFormatException("0");
        }
        final String namespace = matcher.group("Namespace");
        final String key = matcher.group("Key");
        final String name = matcher.group("Name");
        return name == null ? new ResourceKey(namespace, key) : new NamedResourceKey(namespace, key, name);
    }

}
