package sk.vilk.diploy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class Relation {

    private Annotation annotation;
    // TODO: Save the whole field class or just name or sth?
    private Field field;
    private Object foreign;

    public Relation(Annotation annotation, Field field, Object foreign) {
        this.annotation = annotation;
        this.field = field;
        this.foreign = foreign;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Object getForeign() {
        return foreign;
    }

    public void setForeign(Object foreign) {
        this.foreign = foreign;
    }

    @Override
    public String toString() {
        return "Relation{" +
                "annotation=" + annotation +
                ", field=" + field +
                ", foreign=" + foreign +
                '}';
    }
}
