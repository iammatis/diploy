package sk.vilk.diploy;

import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;

//TODO: Implement Dirty objects
public class PersistenceManager {

    private MetaFileManager metaFileManager = new MetaFileManager("_meta.bin");
    private FileManager fileManager = new FileManager(".bin", metaFileManager);
    private AnnotationManager annotationManager = new AnnotationManager();

    private Map<Class, EntityCacheManager> entities = new HashMap<>();

    public PersistenceManager() {
    }

    private EntityCacheManager getEntityCacheManager(Object object) {
        if (!entities.containsKey(object.getClass())) {
            EntityCacheManager entityCacheManager = new EntityCacheManager();
            entities.put(object.getClass(), entityCacheManager);
            return entityCacheManager;
        }
        return entities.get(object.getClass());
    }

    public void persist(Object object) {
        getEntityCacheManager(object).addEntityToCache(getId(object), object);
    }

    public Object find(Object object) {
        //TODO: Check the id exceptions/nulls
        Object id = getId(object);
        Object foundObject = getEntityCacheManager(object).getCachedEntity(id);

        if (foundObject == null) {
            Object objectFromFile = fileManager.findSingleEntity(object, id);
            if (objectFromFile == null) return null;

            getEntityCacheManager(objectFromFile).addEntityToCache(id, objectFromFile);
            return objectFromFile;
        }
        return foundObject;
    }

    public void remove(Object object) {
        EntityCacheManager entityCacheManager = getEntityCacheManager(object);
        Object id = getId(object);
        if (entityCacheManager.containsCachedEntity(id)) {
            entityCacheManager.removeCachedEntity(id);
        }
        metaFileManager.removeMetaObject(object, id);
    }

    public void merge(Object object) {
        //TODO: Might not be cached => Need to mark for update in file
        getEntityCacheManager(object).mergeCachedEntity(getId(object), object);
    }

    private Object getId(Object object) {
        //TODO: Throw  an exception!
        return annotationManager.getAttributeValue(object, Id.class);
    }
}
