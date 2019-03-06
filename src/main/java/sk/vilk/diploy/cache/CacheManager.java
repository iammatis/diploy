package sk.vilk.diploy.cache;

import java.util.HashMap;
import java.util.Map;

public abstract class CacheManager {

    private Map<Class, Object> cache;

    public CacheManager() {
        this.cache = new HashMap<>();
    }

    public void addToCache(Object id, Object object) {
        if (!cache.containsKey(object.getClass())) {
            CacheObject cacheObject = new CacheObject();
            cacheObject.addObject(id, object);
            cache.put(object.getClass(), cacheObject);
        } else {
            CacheObject cacheObject = cache.get(object.getClass());
            cacheObject.addObject(id, object);
        }
    }
}
