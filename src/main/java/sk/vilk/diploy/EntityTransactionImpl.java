package sk.vilk.diploy;

import javax.persistence.EntityTransaction;

public class EntityTransactionImpl implements EntityTransaction {

    private PersistenceManager persistenceManager;

    EntityTransactionImpl(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }

    @Override
    public void begin() {
        // TODO: Clean entity Maps ? toBePersisted and toBeRemoved
    }

    @Override
    public void commit() {
        // TODO: Remove from Meta File
        // TODO: Implement vacuum to clean up Main File

        // TODO: Empty toBeRemoved Map and
        // TODO: Remove entities of toBeRemoved Map from entities Map
    }

    @Override
    public void rollback() {

    }

    @Override
    public void setRollbackOnly() {

    }

    @Override
    public boolean getRollbackOnly() {
        return false;
    }

    @Override
    public boolean isActive() {
        return false;
    }
}
