package sk.vilk.diploy.record;

import java.util.List;
import java.util.UUID;

public class Relation<T> {
    private RelationType type;
    private Class<?> clazz;
    private T value;

    public Relation() {
    }

    public Relation(RelationType type, Class<?> clazz, T value) {
        this.type = type;
        this.clazz = clazz;
        this.value = value;
    }

    public RelationType getType() {
        return type;
    }

    public SerialType getSerialType() {
        return type.getSerialType();
    }

    public void setType(RelationType type) {
        this.type = type;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public class OneToOneRelation extends Relation<UUID> {
        public OneToOneRelation(RelationType type, Class<?> clazz, UUID value) {
            super(type, clazz, value);
        }
    }

    public class OneToManyRelation extends Relation<List<UUID>> {
        public OneToManyRelation(RelationType type, Class<?> clazz, List<UUID> value) {
            super(type, clazz, value);
        }
    }
}
