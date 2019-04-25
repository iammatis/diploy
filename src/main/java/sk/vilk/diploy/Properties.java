package sk.vilk.diploy;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Properties {

    private Class entityClass;
    // Unique identifier of class; UUID instead of Integer ? Wouldn't need to check for last Integer, just generate UUID
    private Integer classId;
    // Id field in class
    private Field idField;
    private List<Field> fields;
    private List<Pair<Annotation, Field>> relations;

    public Properties() {
        fields = new ArrayList<>();
        relations = new ArrayList<>();
    }

    public void addField(Field field) {
        fields.add(field);
    }

    public void addRelation(Annotation annotation, Field field) {
        relations.add(new ImmutablePair<>(annotation, field));
    }

    public Field getIdField() {
        return idField;
    }

    public void setIdField(Field idField) {
        this.idField = idField;
    }

    public List<Field> getFields() {
        return fields;
    }

    public List<Pair<Annotation, Field>> getRelations() {
        return relations;
    }

    public Class getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class entityClass) {
        this.entityClass = entityClass;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    @Override
    public String toString() {
        return "Properties{" +
                "idField=" + idField +
                ", fields=" + fields +
                ", relations=" + relations +
                '}';
    }
}
