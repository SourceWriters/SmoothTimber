package net.sourcewriters.smoothtimber.api.resource.key;

public class NamedResourceKey extends ResourceKey {

    protected final String name;

    private String resourceString;

    /**
     * Constructs a named namespaced key
     * 
     * @param namespace the namespace
     * @param key       the key
     * @param name      the localised name
     */
    public NamedResourceKey(final String namespace, final String key, final String name) {
        super(namespace, key);
        this.name = name;
    }

    /**
     * Gets the localised name of this resource
     * 
     * @return the localised name
     */
    public final String getName() {
        return name;
    }

    @Override
    public String toResourceString() {
        return resourceString == null ? (resourceString = super.asString()) : resourceString;
    }

    /**
     * Gets or builds the full string representation of this namespaced key
     * 
     * @return the full string representation
     */
    @Override
    protected String asString() {
        return super.asString() + "@\"" + name + "\"";
    }

}
