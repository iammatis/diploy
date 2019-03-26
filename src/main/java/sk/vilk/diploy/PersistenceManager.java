package sk.vilk.diploy;

import sk.vilk.diploy.meta.MetaManager;
import sk.vilk.diploy.meta.MetaObject;
import javax.persistence.EntityExistsException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class PersistenceManager {

    // Map => Entity UUID - Serialized Entity byte[]
    // Entities to be persisted after calling persist method
    private Map<String, Object> toBePersisted = new HashMap<>();
    // Entities to be removed after calling remove method
    private Map<String, Object> toBeRemoved = new HashMap<>();
    // Entities loaded from file (database) or persisted
    private Map<String, Object> entities = new HashMap<>();
    // MetaManager
    private MetaManager metaManager;

    PersistenceManager() {
        metaManager = new MetaManager();
    }

    void persist(Object entity) {
        String entityId = AnnotationManager.getIdValue(entity);

        if (toBePersisted.containsKey(entityId) || entities.containsKey(entityId)) {
            throw new EntityExistsException("Entity with id: " + entityId + " already exists!");
        }

        /* TODO: Could also be in file (database)
            Check Meta File maybe ?
         */

        // TODO: Convert to String or use UUID model ?
        entityId = UUID.randomUUID().toString();
        AnnotationManager.setIdValue(entity, entityId);

        /* TODO: Serialize entity
            Might not want to serialize but save as plain object
            HashCode comparison seems to be faster than byte array comparison
         byte[] entityBytes = null;
         */
        toBePersisted.put(entityId, entity);
    }

    void remove(Object entity) {
        String entityId = AnnotationManager.getIdValue(entity);

        /* TODO: Serialize entity
            Might not want to serialize but save as plain object
            HashCode comparison seems to be faster than byte array comparison
         byte[] entityBytes = null;
         */
        toBeRemoved.put(entityId, entity);
    }

    <T> T find(Class<T> entityClass, Object primaryKey) {
        Object entity = entities.get(primaryKey);
        if (entity == null) {
            // First look in metaObjects
            MetaObject metaObject = getMetaManager().get(primaryKey);
            // If it's not there it does not exist
            if (metaObject == null) return null;

            // TODO: Otherwise load it from Main file

            // TODO: And save to entity Map
        }
        return null;
    }

    public MetaManager getMetaManager() {
        return metaManager;
    }
}
