package sk.vilk.diploy.file;

import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.vilk.diploy.meta.MetaObject;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileManager {

    private static final Logger logger = LoggerFactory.getLogger(FileManager.class);

    /*

        SINGLE ENTITY SAVING AND READING

    */

    public static long saveEntity(byte[] entityBytes) {
        // TODO: Do not hardcode
        String filename = "diploy.bin";

        try (FileOutputStream fos = new FileOutputStream(filename, true)) {
            File file = new File(filename);
            fos.write(entityBytes);
            // Return file length used to create new Meta Object
            return file.length();
        } catch (FileNotFoundException e) {
            logger.error("Main file: " + filename + " was not found!", e);
        } catch (IOException e) {
            logger.error("An error occurred trying to save to Main file " + filename, e);
        }
        return 0;
    }

    public static byte[] readEntity(MetaObject metaObject) {
        // TODO: Do not hardcode
        String filename = "diploy.bin";

        try (FileInputStream fis = new FileInputStream(filename)) {
            byte[] bytes = new byte[metaObject.getLength()];
            fis.getChannel().position(metaObject.getFrom());
            fis.read(bytes);
            return bytes;
        } catch (FileNotFoundException e) {
            logger.error("Main file: " + filename + " was not found!", e);
        } catch (IOException e) {
            logger.error("An error occurred trying to read from Main file " + filename, e);
        }
        return null;
    }

    /*

            BULK SAVING AND READING

     */

    public static Map<String, List<? extends Number>> saveEntities(Map<String, Object> toBePersisted) {
        // TODO: Do not hardcode
        String filename = "diploy.bin";
        Map<String, List<? extends Number>> list = new HashMap<>();

        try (FileOutputStream fos = new FileOutputStream(filename)) {
            File file = new File(filename);
            for (Map.Entry<String, Object> entry: toBePersisted.entrySet()) {
                byte[] bytes = SerializationUtils.serialize((Serializable) entry.getValue());
                fos.write(bytes);
                list.put(entry.getKey(), List.of(file.length(), bytes.length));
            }
        } catch (FileNotFoundException e) {
            logger.error("Main file: " + filename + " was not found!", e);
        } catch (IOException e) {
            logger.error("An error occurred trying to save to Main file " + filename, e);
        }

        return list;
    }
}
