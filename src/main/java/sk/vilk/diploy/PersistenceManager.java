package sk.vilk.diploy;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import sk.vilk.diploy.file.FileManager;
import sk.vilk.diploy.file.MetaFileManager;
import sk.vilk.diploy.meta.MetaManager;
import sk.vilk.diploy.meta.MetaObject;
import javax.persistence.EntityExistsException;
import javax.persistence.LockModeType;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class PersistenceManager {

    // UUID - Pair <PERSIST/REMOVE, Entity Object>
    // Entities to be persisted and removed after calling commit method
    private Map<String, Pair<CommitAction, Object>> toBeCommitted = new HashMap<>();
    // Entities managed by EntityManager
    private Map<String, Object> managedEntities = new HashMap<>();
    // Entities in database
    private Map<String, EntityWrapper> persistedEntities = new HashMap<>();
    // MetaManager
    private MetaManager metaManager;
    private EntityScanner entityScanner;

    PersistenceManager() {
        entityScanner = new EntityScanner();
        metaManager = new MetaManager();
        metaManager.initMeta();
    }

    void persist(Object entity) {
        entityScanner.scanClass(entity);

        String entityId = AnnotationManager.getIdValue(entity);

        if (toBeCommitted.containsKey(entityId) || managedEntities.containsKey(entityId)) {
            throw new EntityExistsException("Entity with id: " + entityId + " already exists!");
        }

        /* TODO: Could also be in file (database)
            Check Meta File maybe ?
         */

        // TODO: Convert to String or use UUID model ?
        entityId = UUID.randomUUID().toString();
        // TODO: Should be id set now or at commit() ?
        AnnotationManager.setIdValue(entity, entityId);

        toBeCommitted.put(entityId, new MutablePair<>(CommitAction.PERSIST, entity));
    }

    void remove(Object entity) {
        String entityId = AnnotationManager.getIdValue(entity);
        toBeCommitted.put(entityId, new MutablePair<>(CommitAction.REMOVE, entity));
    }

    <T> T find(Class<T> entityClass, Object primaryKey) {
        Object entity = managedEntities.get(primaryKey);
        if (entity == null) {
            // First look in metaObjects
            MetaObject metaObject = getMetaManager().get(primaryKey);
            // If it's not there it does not exist
            if (metaObject == null) return null;

            // Otherwise load it from Main file
            // TODO: If byte[] is null => error occurred
            byte[] entityBytes = FileManager.readEntity(metaObject);
            // Deserialize byte array
            EntityWrapper entityWrapper = SerializationUtils.deserialize(entityBytes);
            // Java is pass-by-value => therefore we need to clone the entity's object
            Object clonedEntity = SerializationUtils.clone((Serializable) entityWrapper.getEntity());
            // And save to entity Map
            managedEntities.put((String) primaryKey, clonedEntity);
            persistedEntities.put((String) primaryKey, entityWrapper);
            return (T) clonedEntity;
        }
        return (T) entity;
    }

    void flush() {
        // Go though managed Entities and compare them with persisted Entities
        Stream<Map.Entry<String, Object>> dirtyEntities = managedEntities
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().hashCode() != persistedEntities.get(entry.getKey()).hashCode());

        // Save managedEntities with non-matching hashcodes
        Map<String, List<? extends Number>> metaObjects = FileManager.saveEntities(dirtyEntities.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

        // Save Meta Objects to Map
        for (Map.Entry<String, List<? extends Number>> entry: metaObjects.entrySet()) {
            long fileLength = entry.getValue().get(0).longValue();
            int bytesLength = entry.getValue().get(1).intValue();
            metaManager.add(entry.getKey(), fileLength, bytesLength);
        }

        // Write metaObjects to Meta File
        MetaFileManager.saveAllMetaObjects(metaManager.getMetaObjects());
    }

    boolean contains(Object entity) {
        String entityId = AnnotationManager.getIdValue(entity);
        return managedEntities.containsKey(entityId);
    }


    <T> T merge(T entity) {
        String entityId = AnnotationManager.getIdValue(entity);
        return (T) managedEntities.put(entityId, entity);
    }


    void detach(Object entity) {
        String entityId = AnnotationManager.getIdValue(entity);
        managedEntities.remove(entityId);
    }


    /*

            GETTERS AND SETTERS

     */


    MetaManager getMetaManager() {
        return metaManager;
    }

    Map<String, Pair<CommitAction, Object>> getToBeCommitted() {
        return toBeCommitted;
    }

    void clearToBeCommitted() {
        toBeCommitted = new HashMap<>();
    }

    Map<String, Object> getManagedEntities() {
        return managedEntities;
    }

    Map<String, EntityWrapper> getPersistedEntities() {
        return persistedEntities;
    }

    EntityScanner getEntityScanner() {
        return entityScanner;
    }
}
