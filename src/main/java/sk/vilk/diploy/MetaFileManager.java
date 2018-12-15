package sk.vilk.diploy;

import sk.vilk.diploy.cache.AbstractCacheManager;
import sk.vilk.diploy.model.MetaObject;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MetaFileManager extends AbstractFileManager {

    private Map<Object, MetaObject> cachedMetaObjects = new HashMap<>();
    private AbstractCacheManager cacheManager = new AbstractCacheManager();


    /**
     * If manager is dirty an object was added and meta file needs to be saved
     */
    private boolean isDirty = false;

    public MetaFileManager(String extension) {
        super(extension);
    }

    /**
     * Returns cached MetaObject if not found in cached re-read the meta file
     * @param object Object to look for
     * @param id object's id
     * @return Found MetaObject or null
     */
    public MetaObject getMetaObject(Object object, Object id) {
        if (!checkMetaObjects(object))
            return null;

        return cachedMetaObjects.get(id);
    }

    /**
     * @param object Object that need to be removed
     * @param id object's id
     * @return true if object was removed, false otherwise
     */
    public boolean removeMetaObject(Object object, Object id) {
        if (!checkMetaObjects(object))
            return false;

        if (cachedMetaObjects.remove(id) == null)
            return false;

        //TODO: Update meta file
        isDirty = true;
        return true;
    }

    private boolean checkMetaObjects(Object object) {
        if (cachedMetaObjects == null || cachedMetaObjects.isEmpty()) {
            // File might not exist => object not in database
            return readMetaObjectFile(object);
        }
        return true;
    }

    private boolean saveMetaObjectsToFile(Object object) {
        if (!isDirty)
            return true;
        //TODO: Save to file


        return false;
    }

    private boolean readMetaObjectFile(Object object) {
        String fileName = createFileNameFromObject(object);

        //TODO: Check if file exists
        File file = new File(fileName);

        if (!file.exists())
            return false;

        try (FileInputStream fis = new FileInputStream(file); ObjectInputStream ois = new ObjectInputStream(fis)) {
            setCachedMetaObjects((Map<Object, MetaObject>) ois.readObject());
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }



    // GETTERS AND SETTERS



    public Map<Object, MetaObject> getCachedMetaObjects() {
        return cachedMetaObjects;
    }

    public void setCachedMetaObjects(Map<Object, MetaObject> cachedMetaObjects) {
        this.cachedMetaObjects = cachedMetaObjects;
    }

    public void createAndAddObject(Object id, long fileLength, int bytesLength) {
        isDirty = true;
        MetaObject metaObject = new MetaObject(id, fileLength, bytesLength);
        cachedMetaObjects.put(id, metaObject);
    }
}
