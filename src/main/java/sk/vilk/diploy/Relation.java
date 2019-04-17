package sk.vilk.diploy;

import java.io.Serializable;
import java.lang.annotation.Annotation;

public class Relation implements Serializable {

    private Annotation annotation;
    // TODO: Save the whole fieldName class or just name or sth?
    private String fieldName;
    private Object foreign;

    public Relation(Annotation annotation, String fieldName, Object foreign) {
        this.annotation = annotation;
        this.fieldName = fieldName;
        this.foreign = foreign;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
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
                ", fieldName=" + fieldName +
                ", foreign=" + foreign +
                '}';
    }
}
