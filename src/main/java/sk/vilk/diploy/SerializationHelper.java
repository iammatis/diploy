package sk.vilk.diploy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class SerializationHelper {

    private static final Logger logger = LoggerFactory.getLogger(SerializationHelper.class);

    public static byte[] serialize(Serializable object) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
        serialize(object, baos);
        return baos.toByteArray();
    }

    private static void serialize(Serializable object, ByteArrayOutputStream baos) {
        if (baos == null) {
            throw new IllegalArgumentException("ByteArrayOutputStream must not be null!");
        }

        try (ObjectOutputStream out = new ObjectOutputStream(baos)){
            out.writeObject(object);
        } catch (IOException e) {
            logger.error("Error occurred when trying to serialize object", e);
        }
    }

    public static <T> T deserialize(byte[] objectBytes) throws IOException {
        if (objectBytes == null) {
            throw new IllegalArgumentException("Bytes of object must not be null!");
        }

        return deserialize(new ByteArrayInputStream(objectBytes));
    }

    private static <T> T deserialize(InputStream bais) {
        if (bais == null) {
            throw new IllegalArgumentException("InputStream must not be null!");
        }

        try (ObjectInputStream in = new ObjectInputStream(bais)) {
            @SuppressWarnings("unchecked")
            final T obj = (T) in.readObject();
            return obj;
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Exception occurred when trying to deserialize object", e);
        }
        return null;
    }

    // TODO: Missing clone()

}
