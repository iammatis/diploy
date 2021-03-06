package sk.vilk.diploy.meta;

import sk.vilk.diploy.file.MetaFileManager;
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

    public void initMeta() {
        setMetaObjects(MetaFileManager.readAllMetaObjects());
        if (metaObjects == null) setMetaObjects(new HashMap<>());
    }

    public MetaObject get(Object primaryKey) {
        return metaObjects.get(primaryKey);
    }

    public void add(String primaryKey, MetaObject metaObject) {
        metaObjects.put(primaryKey, metaObject);
        /* TODO: Needs to write to Meta File
                 Might throw an Exception!
         */
        // TODO: Save the whole HashMap or convert .values() to ArrayList ?
        MetaFileManager.saveAllMetaObjects(metaObjects);
    }

    public void add(String primaryKey, long fileLength, int bytesLength) {
        MetaObject metaObject = new MetaObject(primaryKey, fileLength - bytesLength, bytesLength);
        metaObjects.put(primaryKey, metaObject);
    }

    public Map<String, MetaObject> getMetaObjects() {
        return metaObjects;
    }

    public void setMetaObjects(Map<String, MetaObject> metaObjects) {
        this.metaObjects = metaObjects;
    }
}
