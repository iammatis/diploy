package sk.vilk.diploy;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import sk.vilk.diploy.file.FileManager;
import sk.vilk.diploy.file.History;
import sk.vilk.diploy.file.MetaFileManager;
import sk.vilk.diploy.meta.MetaObject;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;
import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EntityTransactionImpl implements EntityTransaction {

    private static final String LOG_FORMAT = "YYYY-MM-dd H:m:s z";
    private PersistenceManager persistenceManager;
    private boolean isActive = false;
    private boolean rollbackOnly = false;
    private String savingTime;

    EntityTransactionImpl(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }

    /**
     * Start a resource transaction.
     * @throws IllegalStateException if isActive() is true
     */
    @Override
    public void begin() {
        if (isActive()) throw new IllegalStateException("Transaction is already active!");

        this.isActive = true;
        persistenceManager.clearToBeCommitted();
    }

    /**
     * Commit the current resource transaction, writing any
     * unflushed changes to the database.
     * @throws IllegalStateException if isActive() is false
     * TODO: RollbackException
     * @throws RollbackException if the commit fails
     */
    @Override
    public void commit() {
        verifyIsActive();

        // Create log file
        setSavingTime();
        List<MutablePair<CommitAction, String>> pairsOfActionAndId = persistenceManager
                .getToBeCommitted()
                .entrySet()
                .stream()
                .map(entry -> new MutablePair<>(entry.getValue().getLeft(), entry.getKey()))
                .collect(Collectors.toList());
        History.createUndoLog(pairsOfActionAndId, savingTime);

        // Loop through toBeCommitted Map
        Map<String, Object> managed = persistenceManager.getManagedEntities();
        Map<String, Object> persisted = persistenceManager.getPersistedEntities();
        Map<String, MetaObject> metaObjects = persistenceManager.getMetaManager().getMetaObjects();
        ArrayList<byte[]> bytesToSave = new ArrayList<>();
        long fileLength = getFileLength();

        for (Map.Entry<String, Pair<CommitAction, Object>> entry: persistenceManager.getToBeCommitted().entrySet()) {
            String id = entry.getKey();
            Pair<CommitAction, Object> pair = entry.getValue();
            CommitAction action = pair.getLeft();
            Object entity = pair.getRight();

            switch (action) {
                case PERSIST:
                    byte[] bytes = SerializationUtils.serialize((Serializable) entity);
                    bytesToSave.add(bytes);

                    int bytesLength = bytes.length;
                    MetaObject metaObject = createMetaObject(id, fileLength, bytesLength);
                    addEntityToMaps(managed, persisted, metaObjects, id, entity, metaObject);

                    fileLength += bytesLength;
                    break;
                case REMOVE:
                    managed.remove(id);
                    persisted.remove(id);
                    metaObjects.remove(id);
                    break;
            }
        }

        saveFiles(metaObjects, bytesToSave);
        persistenceManager.clearToBeCommitted();
        isActive = false;
    }

    /**
     * Roll back the current resource transaction.
     * @throws IllegalStateException if isActive() is false
     * TODO: PersistenceException
//     * @throws PersistenceException if an unexpected error
     * condition is encountered
     */
    @Override
    public void rollback() {
        verifyIsActive();

        List<MutablePair<CommitAction, String>> listOfPairs = History.readUndoLog(savingTime);
        Map<String, MetaObject> metaObjects = persistenceManager.getMetaManager().getMetaObjects();
        Map<String, Object> entities = persistenceManager.getManagedEntities();
        Map<String, Object> untouched = persistenceManager.getPersistedEntities();
        Map<String, Pair<CommitAction, Object>> toBeCommitted = persistenceManager.getToBeCommitted();

        ArrayList<byte[]> bytesToSave = new ArrayList<>();
        long fileLength = getFileLength();

        if (listOfPairs != null) {
            String id;
            for (Pair<CommitAction, String> pair : listOfPairs) {
                id = pair.getRight();
                switch (pair.getLeft()) {
                    // Reverse procedure of commit REMOVE case
                    case PERSIST:
                        metaObjects.remove(id);
                        entities.remove(id);
                        untouched.remove(id);
                        break;
                    // Reverse procedure of commit PERSIST case
                    case REMOVE:
                        Serializable entity = toBeCommitted.get(id);
                        byte[] bytes = SerializationUtils.serialize(entity);
                        bytesToSave.add(bytes);

                        int bytesLength = bytes.length;
                        MetaObject metaObject = createMetaObject(id, fileLength, bytesLength);
                        addEntityToMaps(entities, untouched, metaObjects, id, entity, metaObject);

                        fileLength += bytesLength;
                        break;
                }
            }
        }

        saveFiles(metaObjects, bytesToSave);
        /* Current implementation relies on this to be cleaned only if there was no exception thrown
            and is therefore used in rollback */
        persistenceManager.clearToBeCommitted();
        isActive = false;
    }

    /**
     * Mark the current resource transaction so that the only
     * possible outcome of the transaction is for the transaction
     * to be rolled back.
     * @throws IllegalStateException if isActive() is false
     */
    @Override
    public void setRollbackOnly() {
        verifyIsActive();
        this.rollbackOnly = true;
    }

    /**
     * Determine whether the current resource transaction has been
     * marked for rollback.
     * @return boolean indicating whether the transaction has been
     * marked for rollback
     * @throws IllegalStateException if isActive() is false
     */
    @Override
    public boolean getRollbackOnly() {
        verifyIsActive();
        return this.rollbackOnly;
    }

    /**
     * Indicate whether a resource transaction is in progress.
     * @return boolean indicating whether transaction is
     * in progress
     * TODO: PersistenceException
//     * @throws PersistenceException if an unexpected error
     * condition is encountered
     */
    @Override
    public boolean isActive() {
        return this.isActive;
    }

    private void verifyIsActive() {
        if (!isActive()) throw new IllegalStateException("No active transaction found!");
    }

    /**
     * Set time when the commit happened
     */
    private void setSavingTime() {
        SimpleDateFormat formatter = new SimpleDateFormat(LOG_FORMAT);
        savingTime = formatter.format(new Date());
    }

    /**
     * Length of file is needed at the beginning of commit for later creation of metaObjects
     * @return current length of file
     */
    private long getFileLength() {
        // TODO: Don't hardcode!
        String filename = "diploy.bin";
        File file = new File(filename);
        return file.length();
    }

    private void addEntityToMaps(Map<String, Object> entities, Map<String, Object> untouched, Map<String, MetaObject> metaObjects, String id, Object entity, MetaObject metaObject) {
        entities.put(id, entity);
        // TODO: Is clone needed ?
        untouched.put(id, SerializationUtils.clone((Serializable) entity));
        metaObjects.put(id, metaObject);
    }

    private MetaObject createMetaObject(String id, long fileLength, int bytesLength) {
        return new MetaObject(id, fileLength, bytesLength);
    }

    private void saveFiles(Map<String, MetaObject> metaObjects, ArrayList<byte[]> bytesToSave) {
        // Save Meta Objects
        MetaFileManager.saveAllMetaObjects(metaObjects);
        // Save entity bytes to File
        FileManager.saveEntities(bytesToSave);
    }
}
