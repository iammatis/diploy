package sk.vilk.diploy;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EntityManagerImpl implements EntityManager {

    private boolean closed = false;
    private EntityManagerFactoryImpl factory;
    private ArrayList<Object> entities = new ArrayList<Object>();

    public EntityManagerImpl(EntityManagerFactoryImpl factory) {
        this.factory = factory;
    }

    public void persist(Object o) {
        factory.getPersistenceManager().save(o);
    }

    public <T> T merge(T t) {
        //TODO: Implement merge
        return null;
    }

    public void remove(Object o) {
        //TODO: Implement remove
        if (o == null) {
            return;
        }
        factory.getPersistenceManager().remove(o);
    }

    public <T> T find(Class<T> aClass, Object o) {
        if (!isOpen()) {
            throw new PersistenceException("Entity manager is closed!");
        }
        if (o == null) {
            throw new IllegalArgumentException("Object must not be null!");
        }
        //TODO: [Cache] Look for object in cache first
        Object object = factory.getPersistenceManager().read(o);
        return (T) object;
    }

    public <T> T find(Class<T> aClass, Object o, Map<String, Object> map) {
        //TODO: Implement find
        return find(aClass, o);
    }

    public <T> T find(Class<T> aClass, Object o, LockModeType lockModeType) {
        //TODO: Implement find
        return find(aClass, o);
    }

    public <T> T find(Class<T> aClass, Object o, LockModeType lockModeType, Map<String, Object> map) {
        //TODO: Implement find
        return find(aClass, o);
    }

    public <T> T getReference(Class<T> aClass, Object o) {
        //TODO: Implement getReference
        return null;
    }

    public void flush() {
        //TODO: Implement flush
    }

    public void setFlushMode(FlushModeType flushModeType) {
        //TODO: Implement setFlushMode
    }

    public FlushModeType getFlushMode() {
        //TODO: Implement getFlushMode
        return null;
    }

    public void lock(Object o, LockModeType lockModeType) {
        //TODO: Implement lock
    }

    public void lock(Object o, LockModeType lockModeType, Map<String, Object> map) {
        //TODO: Implement lock
    }

    public void refresh(Object o) {
        //TODO: Implement refresh
    }

    public void refresh(Object o, Map<String, Object> map) {
        //TODO: Implement refresh
    }

    public void refresh(Object o, LockModeType lockModeType) {
        //TODO: Implement refresh
    }

    public void refresh(Object o, LockModeType lockModeType, Map<String, Object> map) {
        //TODO: Implement refresh
    }

    public void clear() {
        //TODO: Implement clear
    }

    public void detach(Object o) {
        //TODO: Implement detach
    }

    public boolean contains(Object o) {
        //TODO: Implement contains
        return false;
    }

    public LockModeType getLockMode(Object o) {
        //TODO: Implement getLockMode
        return null;
    }

    public void setProperty(String s, Object o) {
        //TODO: Implement getProperties
    }

    public Map<String, Object> getProperties() {
        //TODO: Implement getProperties
        return null;
    }

    public Query createQuery(String s) {
        //TODO: Implement createQuery
        return null;
    }

    public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
        //TODO: Implement createQuery
        return null;
    }

    public Query createQuery(CriteriaUpdate criteriaUpdate) {
        //TODO: Implement createQuery
        return null;
    }

    public Query createQuery(CriteriaDelete criteriaDelete) {
        //TODO: Implement createQuery
        return null;
    }

    public <T> TypedQuery<T> createQuery(String s, Class<T> aClass) {
        //TODO: Implement createQuery
        return null;
    }

    public Query createNamedQuery(String s) {
        //TODO: Implement createNamedQuery
        return null;
    }

    public <T> TypedQuery<T> createNamedQuery(String s, Class<T> aClass) {
        //TODO: Implement createNamedQuery
        return null;
    }

    public Query createNativeQuery(String s) {
        //TODO: Implement createNativeQuery
        return null;
    }

    public Query createNativeQuery(String s, Class aClass) {
        //TODO: Implement createNativeQuery
        return null;
    }

    public Query createNativeQuery(String s, String s1) {
        //TODO: Implement createNativeQuery
        return null;
    }

    public StoredProcedureQuery createNamedStoredProcedureQuery(String s) {
        //TODO: Implement createNamedStoredProcedureQuery
        return null;
    }

    public StoredProcedureQuery createStoredProcedureQuery(String s) {
        //TODO: Implement createStoredProcedureQuery
        return null;
    }

    public StoredProcedureQuery createStoredProcedureQuery(String s, Class... classes) {
        //TODO: Implement createStoredProcedureQuery
        return null;
    }

    public StoredProcedureQuery createStoredProcedureQuery(String s, String... strings) {
        //TODO: Implement createStoredProcedureQuery
        return null;
    }

    public void joinTransaction() {
        //TODO: Implement joinTransaction
    }

    public boolean isJoinedToTransaction() {
        //TODO: Implement isJoinedToTransaction
        return false;
    }

    public <T> T unwrap(Class<T> aClass) {
        //TODO: Implement unwrap
        return null;
    }

    public Object getDelegate() {
        //TODO: Implement getDelegate
        return null;
    }

    public void close() {
        //TODO: Implement close
        closed = true;
    }

    public boolean isOpen() {
        return !closed;
    }

    public EntityTransaction getTransaction() {
        //TODO: Implement getTransaction
        return null;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return factory;
    }

    public CriteriaBuilder getCriteriaBuilder() {
        //TODO: Implement getCriteriaBuilder
        return null;
    }

    public Metamodel getMetamodel() {
        //TODO: Implement getMetamodel
        return null;
    }

    public <T> EntityGraph<T> createEntityGraph(Class<T> aClass) {
        //TODO: Implement createEntityGraph
        return null;
    }

    public EntityGraph<?> createEntityGraph(String s) {
        //TODO: Implement createEntityGraph
        return null;
    }

    public EntityGraph<?> getEntityGraph(String s) {
        //TODO: Implement getEntityGraph
        return null;
    }

    public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> aClass) {
        //TODO: Implement getEntityGraphs
        return null;
    }

}
