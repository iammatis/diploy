package sk.vilk.diploy;

import org.apache.commons.lang3.SerializationUtils;
import sk.vilk.diploy.model.MetaObject;

import javax.persistence.Id;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PersistenceManager {

    private String extension = ".bin";
    private String metaExtension = "_meta.bin";
    private Map<Object, MetaObject> cachedMetaObjects = new HashMap<>();
    private AnnotationManager annotationManager = new AnnotationManager();

    public PersistenceManager() {
    }

    public void save(Object object) {
        String fileName = createFileNameFromObject(object, false);

        try (FileOutputStream fos = new FileOutputStream(fileName, true)) {
            File file = new File(fileName);

            byte[] bytes = serializeObject(object);
            fos.write(bytes);

            MetaObject metaObject = createMetaObject(object, file, bytes);
            saveMeta(metaObject, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object read(Object object) {
        String fileName = createFileNameFromObject(object, false);
        MetaObject metaObject = getCachedMetaObject(object);

        if (metaObject == null) return null;

        try (FileInputStream fis = new FileInputStream(fileName)) {
            byte[] bytes = new byte[metaObject.getLength()];

            fis.getChannel().position(metaObject.getFrom());
            fis.read(bytes);

            return deserializeObject(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean remove(Object object) {
        String fileName = createFileNameFromObject(object, true);
        MetaObject metaObject = getCachedMetaObject(object);

        if (metaObject == null) return false;

        cachedMetaObjects.remove(metaObject.getId());
        saveMeta(metaObject, false);

        return false;
    }

    private boolean saveMeta(MetaObject metaObject, boolean append) {
        String fileName = createFileNameFromObject(metaObject.getObject(), true);

        File file = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(file); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            //TODO: Always cache
            if (append) appendMetaObject(metaObject);
            oos.writeObject(getCachedMetaObjects());
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean readMeta(Object object) {
        String fileName = createFileNameFromObject(object, true);

        File file = new File(fileName);
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

    private MetaObject createMetaObject(Object object, File file, byte[] bytes) {
        Object objectId = annotationManager.getAttributeValue(object, Id.class);
        return new MetaObject(object, objectId,file.length() - bytes.length, bytes.length);
    }

    private byte[] serializeObject(Object object) {
        return SerializationUtils.serialize((Serializable) object);
    }

    private Object deserializeObject(byte[] bytes) {
        return SerializationUtils.deserialize(bytes);
    }

    private String createFileNameFromObject(Object object, boolean isMeta) {
        return object.getClass().getSimpleName().toLowerCase() + (isMeta ? getMetaExtension() : getExtension());
    }

    private MetaObject getCachedMetaObject(Object object) {
        if (cachedMetaObjects == null || cachedMetaObjects.isEmpty()) {
            readMeta(object);
        }
        return cachedMetaObjects.get(annotationManager.getAttributeValue(object, Id.class));
    }

    private void appendMetaObject(MetaObject metaObject) {
        cachedMetaObjects.put(metaObject.getId(), metaObject);
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

    public Map<Object, MetaObject> getCachedMetaObjects() {
        return cachedMetaObjects;
    }

    public void setCachedMetaObjects(Map<Object, MetaObject> cachedMetaObjects) {
        this.cachedMetaObjects = cachedMetaObjects;
    }
}
