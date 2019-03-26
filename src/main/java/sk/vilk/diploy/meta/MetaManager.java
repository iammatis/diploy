package sk.vilk.diploy.meta;

import java.util.HashMap;
import java.util.Map;

/**
 * Meta Manager manages Meta Objects
 * Meta Objects are objects that act as "pointers" to the main file
 */
public class MetaManager {

    private Map<String, MetaObject> metaObjects;

    public MetaManager() {
        metaObjects = new HashMap<>();
    }

    public MetaObject get(Object primaryKey) {
        return metaObjects.get(primaryKey);
    }

    public Map<String, MetaObject> getMetaObjects() {
        return metaObjects;
    }

    public void setMetaObjects(Map<String, MetaObject> metaObjects) {
        this.metaObjects = metaObjects;
    }
}
