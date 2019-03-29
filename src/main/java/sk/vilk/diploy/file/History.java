package sk.vilk.diploy.file;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.vilk.diploy.CommitAction;

import java.io.*;
import java.util.Date;
import java.util.Map;

public class History {

    private static final Logger logger = LoggerFactory.getLogger(History.class);

    public static void createUndoLog(Map<String, Pair<CommitAction, Object>> toBeCommitted) {
        // TODO: Delete log file on rollback or when ?
        String filename = "undo-" + new Date() + ".bin";

        try (FileOutputStream fos = new FileOutputStream(filename);
             BufferedOutputStream bos = new BufferedOutputStream(fos)
        ) {
            byte[] bytes;
            for (Map.Entry<String, Pair<CommitAction, Object>> entry: toBeCommitted.entrySet()) {
                bytes = SerializationUtils.serialize(entry.getValue());
                bos.write(bytes);
            }
        } catch (FileNotFoundException e) {
            logger.error("Log file: " + filename + " was not found!", e);
        } catch (IOException e) {
            logger.error("An error occurred while trying to save to undo logs to: " + filename, e);
        }

    }

}
