package sk.vilk.diploy;

import sk.vilk.diploy.file.FileManager;
import sk.vilk.diploy.file.MetaFileManager;
import sk.vilk.diploy.meta.MetaObject;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        // TODO: Clean entity Maps ? toBePersisted and toBeRemoved
        this.persistenceManager.setToBePersisted(new HashMap<>());
        this.persistenceManager.setToBeRemoved(new HashMap<>());
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

        Map<String, MetaObject> metaObjects = new HashMap<>();
        // Write to main file in bulk and get needed info and create MetaObjects
        Map<String, List<? extends Number>> list = FileManager.saveEntities(this.persistenceManager.getToBePersisted());

        // Save Meta Objects to Map
        for (Map.Entry<String, List<? extends Number>> entry: list.entrySet()) {
            long fileLength = entry.getValue().get(0).longValue();
            int bytesLength = entry.getValue().get(1).intValue();
            persistenceManager.getMetaManager().add(entry.getKey(), fileLength, bytesLength);
        }

        // Write metaObjects to Meta File
        MetaFileManager.saveAllMetaObjects(new ArrayList(persistenceManager.getMetaManager().getMetaObjects().values()));
        // Save Persisted entities to Map
        persistenceManager.addEntities(this.persistenceManager.getToBePersisted());
        this.persistenceManager.setToBePersisted(new HashMap<>());

        // TODO: Lock access to both Maps ? Necessary or not ?

        // TODO: Remove from Meta File
        // TODO: Implement vacuum to clean up Main File

        // TODO: Empty toBeRemoved Map and
        // TODO: Remove entities of toBeRemoved Map from entities Map
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
