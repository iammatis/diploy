package sk.vilk.diploy.file;

import org.apache.commons.lang3.tuple.MutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.vilk.diploy.CommitAction;
import java.io.*;
import java.util.List;

/**
 * This class is used to read and write undo logs,
 * that are used in case commit fails
 */
public class History {

    private static final Logger logger = LoggerFactory.getLogger(History.class);

    public static void createUndoLog(List<MutablePair<CommitAction, String>> pairsOfActionAndId, String savingTime) {
        // TODO: Delete log file on rollback or when ?
        String filename = "undo-" + savingTime + ".bin";

        File file = new File(filename);
        try (
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(fos))
        ) {
            oos.writeObject(pairsOfActionAndId);
        } catch (FileNotFoundException e) {
            logger.error("Log file: " + filename + " was not found!", e);
        } catch (IOException e) {
            logger.error("An error occurred while trying to save to undo logs to: " + filename, e);
        }
    }

    public static List<MutablePair<CommitAction, String>> readUndoLog(String savingTime) {
        // TODO: Delete log file on rollback or when ?
        String filename = "undo-" + savingTime + ".bin";

        File file = new File(filename);
        try (
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(fis))
        ) {
            return (List<MutablePair<CommitAction, String>>) ois.readObject();
        } catch (FileNotFoundException e) {
            logger.error("Log file: " + filename + " was not found!", e);
        } catch (IOException e) {
            logger.error("An error occurred while trying to read undo logs from: " + filename, e);
        } catch (ClassNotFoundException e) {
            logger.error("Undo logs from: " + filename + " could not have been assigned to List", e);
        }
        return null;
    }

}
