package net.sourcewriters.smoothtimber.api.data.json;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.syntaxphoenix.syntaxapi.json.JsonObject;
import com.syntaxphoenix.syntaxapi.json.JsonValue;

import net.sourcewriters.smoothtimber.api.data.AbstractDataContainer;
import net.sourcewriters.smoothtimber.api.data.IDataAdapterContext;
import net.sourcewriters.smoothtimber.api.data.IDataAdapterRegistry;

@SuppressWarnings("rawtypes")
public final class JsonContainer extends AbstractDataContainer<JsonValue> implements IDataAdapterContext {

    private JsonObject root = new JsonObject();

    public JsonContainer(IDataAdapterRegistry<JsonValue> registry) {
        super(registry);
    }

    @Override
    public JsonContainer newContainer() {
        return new JsonContainer(registry);
    }

    @Override
    public IDataAdapterContext getContext() {
        return this;
    }

    @Override
    public boolean has(String key) {
        return root.has(key);
    }

    @Override
    public boolean remove(String key) {
        return root.remove(key).isPresent();
    }

    @Override
    public Set<String> getKeyspaces() {
        HashSet<String> set = new HashSet<>();
        Collections.addAll(set, root.keys());
        return set;
    }

    @Override
    public boolean isEmpty() {
        return root.isEmpty();
    }

    @Override
    public int size() {
        return root.size();
    }

    @Override
    public JsonValue<?> getRaw(String key) {
        return root.get(key);
    }

    @Override
    public void set(String key, JsonValue value) {
        root.set(key, value);
    }

    final JsonObject getRoot() {
        return root;
    }

    final JsonContainer setRoot(JsonObject root) {
        this.root = root;
        return this;
    }

}
