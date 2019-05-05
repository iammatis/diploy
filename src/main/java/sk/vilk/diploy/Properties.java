package sk.vilk.diploy;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Properties {

    private Class entityClass;
    // Unique identifier of class; UUID instead of Integer ? Wouldn't need to check for last Integer, just generate UUID
    private Integer classId;
    // Id field in class
    private Field idField;
    private List<Column> plainObjects;
    private List<RelationalColumn> relations;

    public Properties() {
        plainObjects = new ArrayList<>();
        relations = new ArrayList<>();
    }

    public void addPlainObject(Column column) {
        plainObjects.add(column);
    }

    public void addRelation(RelationalColumn column) {
        relations.add(column);
    }

    public Field getIdField() {
        return idField;
    }

    public void setIdField(Field idField) {
        this.idField = idField;
    }

    public List<Column> getPlainObjects() {
        return plainObjects;
    }

    public List<RelationalColumn> getRelations() {
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
                ", plainObjects=" + plainObjects +
                ", relations=" + relations +
                '}';
    }
}
