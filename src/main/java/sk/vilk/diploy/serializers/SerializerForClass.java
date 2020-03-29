package sk.vilk.diploy.serializers;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import static sk.vilk.diploy.serializers.Serializers.*;

public class SerializerForClass {
    private static HashMap<Class<?>, Serializer<?>> SERIALIZER_FOR_CLASS = new HashMap<>();

    static {
        SERIALIZER_FOR_CLASS.put(short.class, SHORT);
        SERIALIZER_FOR_CLASS.put(Short.class, SHORT);
        SERIALIZER_FOR_CLASS.put(Integer.class, INTEGER);
        SERIALIZER_FOR_CLASS.put(Integer[].class, INTEGER_ARRAY);
        SERIALIZER_FOR_CLASS.put(long.class, LONG);
        SERIALIZER_FOR_CLASS.put(Long.class, LONG);
        SERIALIZER_FOR_CLASS.put(double.class, DOUBLE);
        SERIALIZER_FOR_CLASS.put(Double.class, DOUBLE);
        SERIALIZER_FOR_CLASS.put(float.class, FLOAT);
        SERIALIZER_FOR_CLASS.put(Float.class, FLOAT);

        SERIALIZER_FOR_CLASS.put(char.class, CHAR);
        SERIALIZER_FOR_CLASS.put(Character.class, CHAR);
        SERIALIZER_FOR_CLASS.put(String.class, STRING);

        SERIALIZER_FOR_CLASS.put(boolean.class, BOOLEAN);
        SERIALIZER_FOR_CLASS.put(Boolean.class, BOOLEAN);

        SERIALIZER_FOR_CLASS.put(UUID.class, UUID);
        SERIALIZER_FOR_CLASS.put(Date.class, DATE);
    }

    public static <R> Serializer<?> get(Class<R> clazz) {
        return SERIALIZER_FOR_CLASS.get(clazz);
    }
}
