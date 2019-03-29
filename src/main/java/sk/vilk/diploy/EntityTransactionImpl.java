package sk.vilk.diploy;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.tuple.Pair;
import sk.vilk.diploy.file.FileManager;
import sk.vilk.diploy.file.History;
import sk.vilk.diploy.file.MetaFileManager;
import sk.vilk.diploy.meta.MetaObject;
import javax.persistence.EntityTransaction;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class EntityTransactionImpl implements EntityTransaction {

    private PersistenceManager persistenceManager;
    private boolean isActive = false;
    private boolean rollbackOnly = false;

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
//     * @throws RollbackException if the commit fails
     */
    @Override
    public void commit() {
        if (!isActive()) throw new IllegalStateException("No active transaction found!");

        // Create log file
        History.createUndoLog(persistenceManager.getToBeCommitted());

        // Loop through toBeCommitted Map
        Map<String, Object> entities = persistenceManager.getEntities();
        Map<String, Object> untouched = persistenceManager.getUntouched();
        Map<String, MetaObject> metaObjects = persistenceManager.getMetaManager().getMetaObjects();
        // TODO: Don't hardcode!
        String filename = "diploy.bin";
        File file = new File(filename);
        long fileLength = file.length();
        ArrayList<byte[]> bytesToSave = new ArrayList<>();
        for (Map.Entry<String, Pair<CommitAction, Object>> entry: persistenceManager.getToBeCommitted().entrySet()) {
            String id = entry.getKey();
            Pair<CommitAction, Object> pair = entry.getValue();
            CommitAction action = pair.getLeft();
            Object entity = pair.getRight();

            switch (action) {
                case PERSIST:
                    // add to entities Map
                    entities.put(id, entity);
                    // TODO: Is clone needed ?
                    untouched.put(id, SerializationUtils.clone((Serializable) entity));
                    byte[] bytes = SerializationUtils.serialize((Serializable) entity);
                    bytesToSave.add(bytes);
                    int bytesLength = bytes.length;
                    MetaObject metaObject = new MetaObject(id, fileLength, bytesLength);
                    fileLength += bytesLength;
                    metaObjects.put(id, metaObject);
                    break;
                case REMOVE:
                    entities.remove(id);
                    untouched.remove(id);
                    metaObjects.remove(id);
                    break;
            }
        }

        // Save Meta Objects
        MetaFileManager.saveAllMetaObjects(new ArrayList(metaObjects.values()));
        // Save entity bytes to File
        FileManager.saveEntities(bytesToSave);

        isActive = false;
    }

    /**
     * Roll back the current resource transaction.
     * @throws IllegalStateException if isActive() is false
//     * @throws PersistenceException if an unexpected error
     * condition is encountered
     */
    @Override
    public void rollback() {
        if (!isActive()) throw new IllegalStateException("No active transaction found!");
        // TODO: Implement rollback
    }

    /**
     * Mark the current resource transaction so that the only
     * possible outcome of the transaction is for the transaction
     * to be rolled back.
     * @throws IllegalStateException if isActive() is false
     */
    @Override
    public void setRollbackOnly() {
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
        if (!isActive()) throw new IllegalStateException("No active transaction found!");
        return this.rollbackOnly;
    }

    /**
     * Indicate whether a resource transaction is in progress.
     * @return boolean indicating whether transaction is
     * in progress
//     * @throws PersistenceException if an unexpected error
     * condition is encountered
     */
    @Override
    public boolean isActive() {
        return this.isActive;
    }
}
