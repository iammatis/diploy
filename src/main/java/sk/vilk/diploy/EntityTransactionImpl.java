package sk.vilk.diploy;

import javax.persistence.EntityTransaction;

public class EntityTransactionImpl implements EntityTransaction {

    public EntityTransactionImpl() {
    }

    @Override
    public void begin() {
        //TODO: Implement begin
    }

    @Override
    public void commit() {
        //TODO: Implement commit
    }

    @Override
    public void rollback() {
        //TODO: Implement rollback
    }

    @Override
    public void setRollbackOnly() {
        //TODO: Implement setRollBackOnly
    }

    @Override
    public boolean getRollbackOnly() {
        //TODO: Implement getRollbackyOnly
        return false;
    }

    @Override
    public boolean isActive() {
        //TODO: Implement isActive
        return false;
    }

}
