package net.sourcewriters.smoothtimber.api.data.json;

import java.util.function.Function;

import com.syntaxphoenix.syntaxapi.json.JsonValue;
import com.syntaxphoenix.syntaxapi.json.ValueType;
import com.syntaxphoenix.syntaxapi.json.value.JsonNull;

import net.sourcewriters.smoothtimber.api.data.AbstractDataAdapterRegistry;
import net.sourcewriters.smoothtimber.api.data.AbstractDataContainer;
import net.sourcewriters.smoothtimber.api.data.IDataAdapter;
import net.sourcewriters.smoothtimber.api.data.IDataContainer;

@SuppressWarnings("rawtypes")
public class JsonAdapterRegistry extends AbstractDataAdapterRegistry<JsonValue> {

    public JsonAdapterRegistry() {
        adapters.add(build(IDataContainer.class));
        adapters.add(build(IDataContainer[].class));
        adapters.add(build(AbstractDataContainer[].class));
        adapters.add(build(JsonContainer[].class));
    }

    @Override
    public Class<JsonValue> getBase() {
        return JsonValue.class;
    }

    @Override
    public JsonValue wrap(Object value) {
        if (value == null) {
            return JsonNull.get();
        }
        return super.wrap(value);
    }

    @Override
    public Object extract(JsonValue base) {
        if (base.hasType(ValueType.NULL)) {
            return null;
        }
        return super.extract(base);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <P, C extends JsonValue> IDataAdapter<P, C, JsonValue> build(Class<?> clazz) {
        return (IDataAdapter<P, C, JsonValue>) JsonAdapter.createAdapter(this, clazz);
    }

    @Override
    public <P, C extends JsonValue> IDataAdapter<P, C, JsonValue> create(Class<P> primitiveType, Class<C> complexType,
        Function<P, C> builder, Function<C, P> extractor) {
        return new JsonAdapter<>(primitiveType, complexType, builder, extractor);
    }

}
