package sk.vilk.diploy;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Properties {

    private Field idField;
    private List<Field> regularFields;
    private List<Pair<Annotation, Field>> relationFields;

    public Properties() {
        regularFields = new ArrayList<>();
        relationFields = new ArrayList<>();
    }

    public void addRegularField(Field field) {
        regularFields.add(field);
    }

    public void addRelationField(Annotation annotation, Field field) {
        relationFields.add(new ImmutablePair<>(annotation, field));
    }

    public Field getIdField() {
        return idField;
    }

    public void setIdField(Field idField) {
        this.idField = idField;
    }

    public List<Field> getRegularFields() {
        return regularFields;
    }

    public void setRegularFields(List<Field> regularFields) {
        this.regularFields = regularFields;
    }

    public List<Pair<Annotation, Field>> getRelationFields() {
        return relationFields;
    }

    public void setRelationFields(List<Pair<Annotation, Field>> relationFields) {
        this.relationFields = relationFields;
    }

    @Override
    public String toString() {
        return "Properties{" +
                "idField=" + idField +
                ", regularFields=" + regularFields +
                ", relationFields=" + relationFields +
                '}';
    }
}
