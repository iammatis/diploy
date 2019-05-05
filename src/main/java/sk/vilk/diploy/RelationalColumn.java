package sk.vilk.diploy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class RelationalColumn extends Column {

    private Annotation annotation;

    public RelationalColumn(Field field, byte id, Annotation annotation) {
        super(field, id);
        this.annotation = annotation;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    @Override
    public String toString() {
        return "RelationalColumn{" +
                "annotation=" + annotation +
                ", field=" + getField() +
                ", id=" + getId() +
                '}';
    }
}
