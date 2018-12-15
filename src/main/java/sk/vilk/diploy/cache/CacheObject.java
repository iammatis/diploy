package sk.vilk.diploy.cache;

import java.util.HashMap;
import java.util.Map;

public class CacheObject {

    private Map<Object, Object> cachedObjects = new HashMap<>();

    public CacheObject() {
    }

    public void add(Object object, Object id) {
        cachedObjects.put(id, object);
    }

    public Object get(Object object, Object id) {
        return cachedObjects.get(id);
    }
}
