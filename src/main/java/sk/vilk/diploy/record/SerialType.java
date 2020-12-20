package sk.vilk.diploy.record;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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

    UUID(UUID.class, 10),

    ONE_TO_ONE(OneToOne.class, 11),
    ONE_TO_MANY(OneToMany.class, 12),
    MAN__TO_ONE(ManyToOne.class, 13);

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