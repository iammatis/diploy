package sk.vilk.diploy;

import java.util.HashMap;
import java.util.Map;

public class EntityCacheManager {

    private Map<Object, Object> cachedEntities = new HashMap<>();

    public EntityCacheManager() {
    }

    public void addEntityToCache(Object id, Object object) {
        cachedEntities.put(id, object);
    }

    public Object getCachedEntity(Object id) {
        // Returns null or value with specified key
        return cachedEntities.get(id);
    }

    public boolean removeCachedEntity(Object id) {
        if (cachedEntities.containsKey(id)) {
            cachedEntities.remove(id);
            return true;
        }
        return false;
    }

    public boolean containsCachedEntity(Object id) {
        return cachedEntities.containsKey(id);
    }

    public void mergeCachedEntity(Object id, Object object) {
        addEntityToCache(id, object);
    }

    public Map<Object, Object> getCachedEntities() {
        return cachedEntities;
    }

    public void setCachedEntities(Map<Object, Object> cachedEntities) {
        this.cachedEntities = cachedEntities;
    }

}
