package sk.vilk.diploy.file;

import org.apache.commons.lang3.SerializationUtils;
import sk.vilk.diploy.meta.MetaObject;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileManager {

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
            // TODO: Implement loggers
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
            // TODO: Implement loggers
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
            // TODO: Implement loggers
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}
