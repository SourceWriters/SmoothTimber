package net.sourcewriters.smoothtimber.api.data.json;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Function;

import com.syntaxphoenix.syntaxapi.json.JsonArray;
import com.syntaxphoenix.syntaxapi.json.JsonObject;
import com.syntaxphoenix.syntaxapi.json.JsonValue;
import com.syntaxphoenix.syntaxapi.json.ValueType;
import com.syntaxphoenix.syntaxapi.utils.java.Primitives;

import net.sourcewriters.smoothtimber.api.data.AbstractDataAdapter;
import net.sourcewriters.smoothtimber.api.data.AbstractDataContainer;
import net.sourcewriters.smoothtimber.api.data.IDataAdapterRegistry;
import net.sourcewriters.smoothtimber.api.data.IDataContainer;

@SuppressWarnings("rawtypes")
public class JsonAdapter<P, C extends JsonValue> extends AbstractDataAdapter<P, C, JsonValue> {

    protected JsonAdapter(final Class<P> primitiveType, final Class<C> resultType, final Function<P, C> builder,
        final Function<C, P> extractor) {
        super(primitiveType, resultType, builder, extractor);
    }

    @Override
    public Class<JsonValue> getBaseType() {
        return JsonValue.class;
    }

    /*
     * 
     */

    protected static AbstractDataAdapter<?, ? extends JsonValue, JsonValue> createAdapter(final JsonAdapterRegistry registry,
        Class<?> type) {
        type = Primitives.fromPrimitive(type);

        if (Primitives.isInstance(type)) {
            return new JsonAdapter<>(Object.class, JsonValue.class, JsonValue::fromPrimitive, JsonValue::getValue);
        }

        /*
         * Containers
         */

        if (IDataContainer.class.isAssignableFrom(type)) {
            return new JsonAdapter<>(IDataContainer.class, JsonObject.class,
                container -> toJsonObject(registry, container), object -> fromJsonObject(registry, object));
        }

        if (Objects.equals(IDataContainer[].class, type)) {
            return new JsonAdapter<>(IDataContainer[].class, JsonArray.class, containers -> {
                final JsonArray array = new JsonArray();
                for (final IDataContainer container : containers) {
                    array.add(toJsonObject(registry, container));
                }
                return array;
            }, array -> {
                if (array.isEmpty()) {
                    return new IDataContainer[0];
                }
                final ArrayList<JsonContainer> containers = new ArrayList<>();
                for (final JsonValue<?> value : array) {
                    if (!value.hasType(ValueType.OBJECT)) {
                        continue;
                    }
                    containers.add(fromJsonObject(registry, (JsonObject) value));
                }
                return containers.toArray(IDataContainer[]::new);
            });
        }

        if (Objects.equals(AbstractDataContainer[].class, type)) {
            return new JsonAdapter<>(AbstractDataContainer[].class, JsonArray.class, containers -> {
                final JsonArray array = new JsonArray();
                for (final IDataContainer container : containers) {
                    array.add(toJsonObject(registry, container));
                }
                return array;
            }, array -> {
                if (array.isEmpty()) {
                    return new AbstractDataContainer[0];
                }
                final ArrayList<JsonContainer> containers = new ArrayList<>();
                for (final JsonValue<?> value : array) {
                    if (!value.hasType(ValueType.OBJECT)) {
                        continue;
                    }
                    containers.add(fromJsonObject(registry, (JsonObject) value));
                }
                return containers.toArray(AbstractDataContainer[]::new);
            });
        }

        if (Objects.equals(JsonContainer[].class, type)) {
            return new JsonAdapter<>(JsonContainer[].class, JsonArray.class, containers -> {
                final JsonArray array = new JsonArray();
                for (final IDataContainer container : containers) {
                    array.add(toJsonObject(registry, container));
                }
                return array;
            }, array -> {
                if (array.isEmpty()) {
                    return new JsonContainer[0];
                }
                final ArrayList<JsonContainer> containers = new ArrayList<>();
                for (final JsonValue<?> value : array) {
                    if (!value.hasType(ValueType.OBJECT)) {
                        continue;
                    }
                    containers.add(fromJsonObject(registry, (JsonObject) value));
                }
                return containers.toArray(JsonContainer[]::new);
            });
        }

        return null;
    }

    static JsonObject toJsonObject(final IDataAdapterRegistry<JsonValue> registry, final IDataContainer container) {
        if (container instanceof JsonContainer) {
            return ((JsonContainer) container).getRoot();
        }
        final JsonObject compound = new JsonObject();
        for (final String key : container.getKeyspaces()) {
            final Object object = container.get(key);
            final JsonValue tag = registry.wrap(object);
            if (tag != null) {
                compound.set(key, tag);
            }
        }
        return compound;
    }

    static JsonContainer fromJsonObject(final IDataAdapterRegistry<JsonValue> registry, final JsonObject object) {
        return new JsonContainer(registry).setRoot(object);
    }

}
