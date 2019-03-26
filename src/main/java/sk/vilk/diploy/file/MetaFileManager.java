package sk.vilk.diploy.file;

import sk.vilk.diploy.meta.MetaObject;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class MetaFileManager {

    public static void saveAllMetaObjects(ArrayList metaObjects) {
        // TODO: Don't hardcode
        String filename = "diploy_meta.bin";

        File file = new File(filename);
        try (
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(metaObjects);
        } catch (FileNotFoundException e) {
            // TODO: Implement loggers
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Collection<MetaObject> readAllMetaObjects() {
        // TODO: Don't hardcode
        String filename = "diploy_meta.bin";

        File file = new File(filename);
        if (!file.exists()) return null;

        try (
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            Collection<MetaObject> metaObjects = (Collection<MetaObject>) ois.readObject();
            return metaObjects;
        } catch (FileNotFoundException e) {
            // TODO: Implement loggers
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
