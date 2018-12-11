package sk.vilk.diploy;

import sk.vilk.diploy.model.MetaObject;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PersistenceManager {

    private String extension = ".bin";
    private String metaExtension = "_meta.bin";
    private Map<Object, MetaObject> cachedEntities = new HashMap<>();

    public PersistenceManager() {
    }

    public void save(Object object) {
        String fileName = createFileNameFromObject(object, false);

        try (FileOutputStream fos = new FileOutputStream(fileName, true)) {
            File file = new File(fileName);

            byte[] bytes = serializeObject(object);
            fos.write(bytes);

            MetaObject metaObject = createMetaObject(object, file, bytes);
            saveMeta(metaObject);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean saveMeta(MetaObject metaObject) {
        String fileName = createFileNameFromObject(metaObject.getObject(), true);

        File file = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(file); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            //TODO: Always cache
            oos.writeObject(cachedEntities);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private MetaObject createMetaObject(Object object, File file, byte[] bytes) {
        return new MetaObject(object, file.length() - bytes.length, bytes.length);
    }

    private byte[] serializeObject(Object object) {
        return null;
    }

    private String createFileNameFromObject(Object object, boolean isMeta) {
        if (isMeta) {
            return object.getClass().getSimpleName().toLowerCase() + getMetaExtension();
        } else {
            return object.getClass().getSimpleName().toLowerCase() + getExtension();
        }
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getMetaExtension() {
        return metaExtension;
    }

    public void setMetaExtension(String metaExtension) {
        this.metaExtension = metaExtension;
    }

    public Map<Object, MetaObject> getCachedEntities() {
        return cachedEntities;
    }

    public void setCachedEntities(Map<Object, MetaObject> cachedEntities) {
        this.cachedEntities = cachedEntities;
    }
}
