package sk.vilk.diploy.record;

import java.util.Date;
import java.util.UUID;

public enum SerialType {
    SHORT(Short.class, 1),
    INTEGER(Integer.class, 2),
    LONG(Long.class, 3),
    DOUBLE(Double.class, 4),
    FLOAT(Float.class, 5),

    BOOLEAN(Boolean.class, 6),

    CHARACTER(Character.class, 7),
    STRING(String.class, 8),
    DATE(Date.class, 9),

    UUID(UUID.class, 10);

    private Class<?> clazz;
    private int serialValue;

    SerialType(Class<?> clazz, int serialValue) {
        this.clazz = clazz;
        this.serialValue = serialValue;
    }

    public Class<?> getClazz() {
        return this.clazz;
    }

    public int getSerialValue() {
        return this.serialValue;
    }
}