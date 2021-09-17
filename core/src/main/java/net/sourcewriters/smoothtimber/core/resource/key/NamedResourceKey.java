package net.sourcewriters.smoothtimber.core.resource.key;

public class NamedResourceKey extends ResourceKey {

    protected final String name;

    public NamedResourceKey(final String namespace, final String key, final String name) {
        super(namespace, key);
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    @Override
    protected String asString() {
        return super.asString() + "@\"" + name + "\"";
    }

}
