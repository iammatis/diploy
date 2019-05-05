package sk.vilk.diploy;

import java.lang.reflect.Field;

public class Column {

    private Field field;
    private byte id;

    public Column() {
    }

    public Column(Field field, byte id) {
        this.field = field;
        this.id = id;
    }

    public Field getField() {
        return field;
    }

    public byte getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Column{" +
                "field=" + field +
                ", id=" + id +
                '}';
    }
}
