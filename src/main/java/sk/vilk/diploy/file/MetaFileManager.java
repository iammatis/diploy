package sk.vilk.diploy.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.vilk.diploy.meta.MetaObject;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class MetaFileManager {

    private static final Logger logger = LoggerFactory.getLogger(MetaFileManager.class);

    public static void saveAllMetaObjects(Map<String, MetaObject> metaObjects) {
        // TODO: Don't hardcode
        String filename = "diploy_meta.bin";

        File file = new File(filename);
        try (
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(fos))
        ) {
            oos.writeObject(metaObjects);
        } catch (FileNotFoundException e) {
            logger.error("Meta file: " + filename + " was not found!", e);
        } catch (IOException e) {
            logger.error("An error occurred trying to save to Meta file " + filename, e);
        }
    }

    public static Map<String, MetaObject> readAllMetaObjects() {
        // TODO: Don't hardcode
        String filename = "diploy_meta.bin";

        File file = new File(filename);
        if (!file.exists()) return null;

        try (
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(fis))
        ) {
            return (Map<String, MetaObject>) ois.readObject();
        } catch (FileNotFoundException e) {
            logger.error("Meta file: " + filename + " was not found!", e);
        } catch (IOException e) {
            logger.error("An error occurred trying to read from Meta file " + filename, e);
        } catch (ClassNotFoundException e) {
            logger.error("While reading Meta file found an unknown class", e);
        }
        return null;
    }
}
