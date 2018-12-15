package sk.vilk.diploy.cache;

import java.util.HashMap;
import java.util.Map;

public class AbstractCacheManager {

    /**
     * Contains class - entities for given class
     */
    private Map<Class, CacheObject> cache = new HashMap<>();

    public AbstractCacheManager() {
    }

    public void addToCache(Object object, Object id) {
        if (!cache.containsKey(object.getClass()))
            cache.put(object.getClass(), new CacheObject());

        CacheObject cacheObject = cache.get(object.getClass());
        cacheObject.add(object, id);
    }

    public Object getFromCache(Object object, Object id) {
        CacheObject cacheObject = cache.get(object.getClass());
        if (cacheObject == null) return null;

        return cacheObject.get(object, id);
    }
}
