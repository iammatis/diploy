package sk.vilk.diploy;

import javax.persistence.LockModeType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EntityWrapper implements Serializable {

    private Object entity;
    private List<Relation> relations;
    private LockModeType lockModeType = LockModeType.NONE;

    public EntityWrapper() {
        relations = new ArrayList<>();
    }

    public Object getEntity() {
        return entity;
    }

    public void addRelation(Relation relation) {
        relations.add(relation);
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    public List<Relation> getRelations() {
        return relations;
    }

    public void setRelations(List<Relation> relations) {
        this.relations = relations;
    }

    public LockModeType getLockModeType() {
        return lockModeType;
    }

    public void setLockModeType(LockModeType lockModeType) {
        this.lockModeType = lockModeType;
    }

    @Override
    public String toString() {
        return "EntityWrapper{" +
                "entity=" + entity +
                ", relations=" + relations +
                ", lockModeType=" + lockModeType +
                '}';
    }
}
