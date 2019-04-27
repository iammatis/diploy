package sk.vilk.diploy.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.vilk.diploy.meta.MetaObject;
import java.io.*;
import java.util.Map;

public class MetaFileManager {

    private static final Logger logger = LoggerFactory.getLogger(MetaFileManager.class);
    private static final String fileName = "diploy_metaa.bin";

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

    public static void append(byte[] bytes) {
        File file = new File(fileName);
        try (
                FileOutputStream fos = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fos)
        ) {
            bos.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] read() {
        File file = new File(fileName);
        if (!file.exists()) return null;
        try (
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis)
        ) {
            int available = bis.available();
            byte[] bytes = new byte[available];
            bis.read(bytes, 0, available);
            return bytes;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
