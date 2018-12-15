package sk.vilk.diploy;

import org.apache.commons.lang3.SerializationUtils;
import sk.vilk.diploy.model.MetaObject;

import java.io.*;
import java.util.Map;

public class FileManager extends AbstractFileManager {

    private AnnotationManager annotationManager = new AnnotationManager();
    // Used for saving and reading Meta Files for entities
    private MetaFileManager metaFileManager;

    public FileManager(String extension, MetaFileManager metaFileManager) {
        super(extension);
        this.metaFileManager = metaFileManager;
    }

    /**
     * @param entity Provides class name for entity file
     * @param id Entity is found base on id
     * @return found entity or null if not found
     */
    public Object findSingleEntity(Object entity, Object id) {
        String fileName = createFileNameFromObject(entity);
        MetaObject metaObject = metaFileManager.getMetaObject(entity, id);

        if (metaObject == null) return null;

        try (FileInputStream fis = new FileInputStream(fileName)) {
            byte[] bytes = new byte[metaObject.getLength()];

            fis.getChannel().position(metaObject.getFrom());
            // Returns number of bytes, could be used for byte[] bytes size
            fis.read(bytes);

            return deserializeObject(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Saves cached entities for every class
     * @param entityCacheMap contains class of entities and cached entities
     */
    public void saveAllEntities(Map<Class, EntityCacheManager> entityCacheMap) {
        for (Map.Entry<Class, EntityCacheManager> entry: entityCacheMap.entrySet()) {
            saveEntitiesByClass(entry.getKey(), entry.getValue());
        }
    }

    /**
     * //TODO: Save only dirty entities
     * Saves all cached entities
     * @param clazz provides file name to save entities in
     * @param entityCacheManager contains cached entities of a given clazz
     * @return true if save was successful, false otherwise
     */
    private boolean saveEntitiesByClass(Class clazz, EntityCacheManager entityCacheManager) {
        String fileName = createFileNameFromClass(clazz);

        try (FileOutputStream fos = new FileOutputStream(fileName)){
            File file = new File(fileName);
            long fileLength = file.length();

            for (Map.Entry<Object, Object> entry: entityCacheManager.getCachedEntities().entrySet()) {
                byte[] bytes = serializeObject(entry.getValue());
                fos.write(bytes);

                metaFileManager.createAndAddObject(entry.getKey(), fileLength, bytes.length);
                fileLength += bytes.length;
            }

            //TODO: Save MetaObjects
//            metaFileManager.save(bytesList, file.length());
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //TODO: Read all entities by given criteria

    /**
     * @param object Object that needs to be serialized
     * @return array of bytes
     */
    private byte[] serializeObject(Object object) {
        return SerializationUtils.serialize((Serializable) object);
    }

    /**
     * @param bytes array of bytes that need to be deserialized
     * @return a deserialized object
     */
    private Object deserializeObject(byte[] bytes) {
        //TODO: Check for an exception
        return SerializationUtils.deserialize(bytes);
    }


//    public void save(Object object) {
//        String fileName = createFileNameFromObject(object, false);
//
//        try (FileOutputStream fos = new FileOutputStream(fileName, true)) {
//            File file = new File(fileName);
//
//            byte[] bytes = serializeObject(object);
//            fos.write(bytes);
//
//            MetaObject metaObject = createMetaObject(object, file, bytes);
//            saveMeta(object, metaObject, true);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//
//    public Object read(Object object) {
//
//
//        String fileName = createFileNameFromObject(object, false);
//        MetaObject metaObject = getCachedMetaObject(object);
//
//        if (metaObject == null) return null;
//
//        try (FileInputStream fis = new FileInputStream(fileName)) {
//            byte[] bytes = new byte[metaObject.getLength()];
//
//            fis.getChannel().position(metaObject.getFrom());
//            fis.read(bytes);
//
//            return deserializeObject(bytes);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    private List<byte[]> readInBulk(Object object) {
//        String fileName = createFileNameFromObject(object, false);
//        List<byte[]> byteList = new ArrayList<>();
//
//        try (FileInputStream fis = new FileInputStream(fileName)) {
//            //TODO: cachedObjects might be empty => Remove everything in main file
//            for(Map.Entry<Object, MetaObject> entry : getCachedMetaObjects().entrySet()) {
//                Object objectId = entry.getKey();
//                MetaObject metaObject = entry.getValue();
//
//                byte[] bytes = new byte[metaObject.getLength()];
//
//                fis.getChannel().position(metaObject.getFrom());
//                fis.read(bytes);
//
//                byteList.add(bytes);
//            }
//
//            return byteList;
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private boolean saveInBulk(Object object, List<byte[]> byteList) {
//        String fileName = createFileNameFromObject(object, false);
//
//        try (FileOutputStream fos = new FileOutputStream(fileName)) {
//            for (byte[] bytes: byteList) {
//                fos.write(bytes);
//            }
//            return true;
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
//
//
//    private boolean saveMeta(Object object, MetaObject metaObject, boolean append) {
//        String fileName = createFileNameFromObject(object, true);
//
//        File file = new File(fileName);
//        try (FileOutputStream fos = new FileOutputStream(file); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
//            //TODO: Always cache
//            if (append) appendMetaObject(metaObject);
//            oos.writeObject(getCachedMetaObjects());
//            return true;
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public boolean remove(Object object) {
//        //TODO: Remove from main file not just meta file
//        String fileName = createFileNameFromObject(object, true);
//        MetaObject metaObject = getCachedMetaObject(object);
//
//        if (metaObject == null) return false;
//
//        cachedMetaObjects.remove(metaObject.getId());
//        saveMeta(object, metaObject, false);
//
//        return true;
//    }
//
//    public <T> boolean update(T t) {
//        //TODO: Better implementation, meta file is now saved twice in remove() and save()
//        remove(t);
//        save(t);
//
//        return true;
//    }
//
//    private boolean readMeta(Object object) {
//        String fileName = createFileNameFromObject(object, true);
//
//        File file = new File(fileName);
//        try (FileInputStream fis = new FileInputStream(file); ObjectInputStream ois = new ObjectInputStream(fis)) {
//            setCachedMetaObjects((Map<Object, MetaObject>) ois.readObject());
//            return true;
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
//
//    public boolean performVacuum(Object object) {
//        List<byte[]> byteList = readInBulk(object);
//
//        if (byteList == null || byteList.isEmpty()) {
//            return false;
//        }
//
//        return saveInBulk(object, byteList);
//    }
//
//    private MetaObject createMetaObject(Object object, File file, byte[] bytes) {
//        Object objectId = annotationManager.getAttributeValue(object, Id.class);
//        return new MetaObject(objectId,file.length() - bytes.length, bytes.length);
//    }
//
//    private MetaObject getCachedMetaObject(Object object) {
//        if (cachedMetaObjects == null || cachedMetaObjects.isEmpty()) {
//            readMeta(object);
//        }
//        return cachedMetaObjects.get(annotationManager.getAttributeValue(object, Id.class));
//    }
//
//    private void appendMetaObject(MetaObject metaObject) {
//        cachedMetaObjects.put(metaObject.getId(), metaObject);
//    }
//
//    public Map<Object, MetaObject> getCachedMetaObjects() {
//        return cachedMetaObjects;
//    }
//
//    public void setCachedMetaObjects(Map<Object, MetaObject> cachedMetaObjects) {
//        this.cachedMetaObjects = cachedMetaObjects;
//    }

}
