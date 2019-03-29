package sk.vilk.diploy;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import sk.vilk.diploy.file.FileManager;
import sk.vilk.diploy.file.MetaFileManager;
import sk.vilk.diploy.meta.MetaManager;
import sk.vilk.diploy.meta.MetaObject;
import javax.persistence.EntityExistsException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class PersistenceManager {

    // UUID - Pair <PERSIST/REMOVE, Entity Object>
    // Entities to be persisted and removed after calling commit method
    private Map<String, Pair<CommitAction, Object>> toBeCommitted = new HashMap<>();
    // UUID - Entity Object
    // Entities loaded from file (database) or persisted
    private Map<String, Object> entities = new HashMap<>();
    private Map<String, Object> untouched = new HashMap<>();
    // MetaManager
    private MetaManager metaManager;

    PersistenceManager() {
        metaManager = new MetaManager();
        metaManager.initMeta();
    }

    void persist(Object entity) {
        String entityId = AnnotationManager.getIdValue(entity);

        if (toBeCommitted.containsKey(entityId) || entities.containsKey(entityId)) {
            throw new EntityExistsException("Entity with id: " + entityId + " already exists!");
        }

        /* TODO: Could also be in file (database)
            Check Meta File maybe ?
         */

        // TODO: Convert to String or use UUID model ?
        entityId = UUID.randomUUID().toString();
        // TODO: Should be id set now or at commit() ?
        AnnotationManager.setIdValue(entity, entityId);

        /* TODO: Serialize entity
            Might not want to serialize but save as plain object
            HashCode comparison seems to be faster than byte array comparison
         byte[] entityBytes = null;
         */
        toBeCommitted.put(entityId, new MutablePair<>(CommitAction.PERSIST, entity));
    }

    void remove(Object entity) {
        String entityId = AnnotationManager.getIdValue(entity);

        /* TODO: Serialize entity
            Might not want to serialize but save as plain object
            HashCode comparison seems to be faster than byte array comparison
         byte[] entityBytes = null;
         */
        toBeCommitted.put(entityId, new MutablePair<>(CommitAction.REMOVE, entity));
    }

    <T> T find(Class<T> entityClass, Object primaryKey) {
        Object entity = entities.get(primaryKey);
        if (entity == null) {
            // First look in metaObjects
            MetaObject metaObject = getMetaManager().get(primaryKey);
            // If it's not there it does not exist
            if (metaObject == null) return null;

            // Otherwise load it from Main file
            // TODO: If byte[] is null => error occurred
            byte[] entityBytes = FileManager.readEntity(metaObject);
            // Deserialize byte array
            Object entityObject = SerializationUtils.deserialize(entityBytes);
            // Java is pass-by-value => therefore we need to clone the entity's object
            Object clonedEntity = SerializationUtils.clone((Serializable) entityObject);
            // And save to entity Map
            entities.put((String) primaryKey, entityObject);
            untouched.put((String) primaryKey, clonedEntity);
            return (T) entityObject;
        }
        return (T) entity;
    }

    void flush() {
        /*
            TODO:
                1. Go through _ entity Map
                2. Compare with _ entity Map
                3. Save entities with non-matching hashcodes
         */
        Stream<Map.Entry<String, Object>> dirtyEntities = entities
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().hashCode() != untouched.get(entry.getKey()).hashCode());

        Map<String, List<? extends Number>> metaObjects = FileManager.saveEntities(dirtyEntities.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

        // Save Meta Objects to Map
        for (Map.Entry<String, List<? extends Number>> entry: metaObjects.entrySet()) {
            long fileLength = entry.getValue().get(0).longValue();
            int bytesLength = entry.getValue().get(1).intValue();
            metaManager.add(entry.getKey(), fileLength, bytesLength);
        }

        // Write metaObjects to Meta File
        MetaFileManager.saveAllMetaObjects(new ArrayList(metaManager.getMetaObjects().values()));
    }

    public boolean contains(Object entity) {
        String entityId = AnnotationManager.getIdValue(entity);
        return entities.containsKey(entityId);
    }


    /*

            GETTERS AND SETTERS

     */


    public MetaManager getMetaManager() {
        return metaManager;
    }

    public Map<String, Pair<CommitAction, Object>> getToBeCommitted() {
        return toBeCommitted;
    }

    void clearToBeCommitted() {
        toBeCommitted = new HashMap<>();
    }

    public Map<String, Object> getEntities() {
        return entities;
    }

    public Map<String, Object> getUntouched() {
        return untouched;
    }
}
