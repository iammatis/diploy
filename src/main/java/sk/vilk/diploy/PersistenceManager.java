package sk.vilk.diploy;

import org.apache.commons.lang3.SerializationUtils;
import sk.vilk.diploy.model.MetaObject;

import javax.persistence.Id;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
            saveMeta(object, metaObject, true);
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

    private List<byte[]> readInBulk(Object object) {
        String fileName = createFileNameFromObject(object, false);
        List<byte[]> byteList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(fileName)) {
            //TODO: cachedObjects might be empty => Remove everything in main file
            for(Map.Entry<Object, MetaObject> entry : getCachedMetaObjects().entrySet()) {
                Object objectId = entry.getKey();
                MetaObject metaObject = entry.getValue();

                byte[] bytes = new byte[metaObject.getLength()];

                fis.getChannel().position(metaObject.getFrom());
                fis.read(bytes);

                byteList.add(bytes);
            }

            return byteList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean saveInBulk(Object object, List<byte[]> byteList) {
        String fileName = createFileNameFromObject(object, false);

        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            for (byte[] bytes: byteList) {
                fos.write(bytes);
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean remove(Object object) {
        //TODO: Remove from main file not just meta file
        String fileName = createFileNameFromObject(object, true);
        MetaObject metaObject = getCachedMetaObject(object);

        if (metaObject == null) return false;

        cachedMetaObjects.remove(metaObject.getId());
        saveMeta(object, metaObject, false);

        return true;
    }

    public <T> boolean update(T t) {
        //TODO: Better implementation, meta file is now saved twice in remove() and save()
        remove(t);
        save(t);

        return true;
    }

    private boolean saveMeta(Object object, MetaObject metaObject, boolean append) {
        String fileName = createFileNameFromObject(object, true);

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

    public boolean performVacuum(Object object) {
        List<byte[]> byteList = readInBulk(object);

        if (byteList == null || byteList.isEmpty()) {
            return false;
        }

        return saveInBulk(object, byteList);
    }

    private MetaObject createMetaObject(Object object, File file, byte[] bytes) {
        Object objectId = annotationManager.getAttributeValue(object, Id.class);
        return new MetaObject(objectId,file.length() - bytes.length, bytes.length);
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
