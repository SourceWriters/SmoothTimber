package net.sourcewriters.smoothtimber.api.data.json;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Function;

import com.syntaxphoenix.syntaxapi.json.*;
import com.syntaxphoenix.syntaxapi.utils.java.Primitives;

import net.sourcewriters.smoothtimber.api.data.AbstractDataAdapter;
import net.sourcewriters.smoothtimber.api.data.AbstractDataContainer;
import net.sourcewriters.smoothtimber.api.data.IDataAdapterRegistry;
import net.sourcewriters.smoothtimber.api.data.IDataContainer;

@SuppressWarnings("rawtypes")
public class JsonAdapter<P, C extends JsonValue> extends AbstractDataAdapter<P, C, JsonValue> {

    protected JsonAdapter(Class<P> primitiveType, Class<C> resultType, Function<P, C> builder, Function<C, P> extractor) {
        super(primitiveType, resultType, builder, extractor);
    }

    @Override
    public Class<JsonValue> getBaseType() {
        return JsonValue.class;
    }

    /*
     * 
     */

    protected static AbstractDataAdapter<?, ? extends JsonValue, JsonValue> createAdapter(JsonAdapterRegistry registry, Class<?> type) {
        type = Primitives.fromPrimitive(type);

        if (Primitives.isInstance(type)) {
            return new JsonAdapter<Object, JsonValue>(Object.class, JsonValue.class, value -> JsonValue.fromPrimitive(value),
                value -> value.getValue());
        }

        /*
         * Containers
         */

        if (IDataContainer.class.isAssignableFrom(type)) {
            return new JsonAdapter<IDataContainer, JsonObject>(IDataContainer.class, JsonObject.class,
                container -> toJsonObject(registry, container), object -> fromJsonObject(registry, object));
        }

        if (Objects.equals(IDataContainer[].class, type)) {
            return new JsonAdapter<IDataContainer[], JsonArray>(IDataContainer[].class, JsonArray.class, containers -> {
                JsonArray array = new JsonArray();
                for (IDataContainer container : containers) {
                    array.add(toJsonObject(registry, container));
                }
                return array;
            }, array -> {
                if (array.isEmpty()) {
                    return new IDataContainer[0];
                }
                ArrayList<JsonContainer> containers = new ArrayList<>();
                for (JsonValue<?> value : array) {
                    if (!value.hasType(ValueType.OBJECT)) {
                        continue;
                    }
                    containers.add(fromJsonObject(registry, (JsonObject) value));
                }
                return containers.toArray(IDataContainer[]::new);
            });
        }

        if (Objects.equals(AbstractDataContainer[].class, type)) {
            return new JsonAdapter<AbstractDataContainer[], JsonArray>(AbstractDataContainer[].class, JsonArray.class, containers -> {
                JsonArray array = new JsonArray();
                for (IDataContainer container : containers) {
                    array.add(toJsonObject(registry, container));
                }
                return array;
            }, array -> {
                if (array.isEmpty()) {
                    return new AbstractDataContainer[0];
                }
                ArrayList<JsonContainer> containers = new ArrayList<>();
                for (JsonValue<?> value : array) {
                    if (!value.hasType(ValueType.OBJECT)) {
                        continue;
                    }
                    containers.add(fromJsonObject(registry, (JsonObject) value));
                }
                return containers.toArray(AbstractDataContainer[]::new);
            });
        }

        if (Objects.equals(JsonContainer[].class, type)) {
            return new JsonAdapter<JsonContainer[], JsonArray>(JsonContainer[].class, JsonArray.class, containers -> {
                JsonArray array = new JsonArray();
                for (IDataContainer container : containers) {
                    array.add(toJsonObject(registry, container));
                }
                return array;
            }, array -> {
                if (array.isEmpty()) {
                    return new JsonContainer[0];
                }
                ArrayList<JsonContainer> containers = new ArrayList<>();
                for (JsonValue<?> value : array) {
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

    static JsonObject toJsonObject(IDataAdapterRegistry<JsonValue> registry, IDataContainer container) {
        if (container instanceof JsonContainer) {
            return ((JsonContainer) container).getRoot();
        }
        JsonObject compound = new JsonObject();
        for (String key : container.getKeyspaces()) {
            Object object = container.get(key);
            JsonValue tag = registry.wrap(object);
            if (tag != null) {
                compound.set(key, tag);
            }
        }
        return compound;
    }

    static JsonContainer fromJsonObject(IDataAdapterRegistry<JsonValue> registry, JsonObject object) {
        return new JsonContainer(registry).setRoot(object);
    }

}
