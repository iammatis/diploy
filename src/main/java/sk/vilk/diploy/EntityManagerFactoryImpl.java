package sk.vilk.diploy;

import sk.vilk.diploy.model.MetaObject;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EntityManagerFactoryImpl implements EntityManagerFactory {

    private boolean closed = false;

    private Set<String> entities;
    private Map<String, String> entityMap = new HashMap<>();
    private PersistenceManager persistenceManager = new PersistenceManager();
//    private AnnotationManager annotationManager;

    public EntityManagerFactoryImpl() {
//        annotationManager = new AnnotationManager();
    }

    public EntityManager createEntityManager() {
        return new EntityManagerImpl(this);
    }

    public EntityManager createEntityManager(Map map) {
        //TODO: Implement EntityManager with Map
        return createEntityManager();
    }

    public EntityManager createEntityManager(SynchronizationType synchronizationType) {
        //TODO: Implement EntityManager with SynchronizationType
        return createEntityManager();
    }

    public EntityManager createEntityManager(SynchronizationType synchronizationType, Map map) {
        //TODO: Implement EntityManager with SynchronizationType and Map
        return createEntityManager();
    }

    public CriteriaBuilder getCriteriaBuilder() {
        //TODO: Implement getCriteriaBuilder
        return null;
    }

    public Metamodel getMetamodel() {
        //TODO: Implement getMetamodel
        return null;
    }

    public boolean isOpen() {
        return !closed;
    }

    public void close() {
        //TODO: Implement close
        closed = true;
    }

    public Map<String, Object> getProperties() {
        //TODO: Implement getProperties
        return null;
    }

    public Cache getCache() {
        //TODO: Implement getCache
        return null;
    }

    public PersistenceUnitUtil getPersistenceUnitUtil() {
        //TODO: Implement getPersistenceUnitUtil
        return null;
    }

    public void addNamedQuery(String s, Query query) {
        //TODO: Implement addNamedQuery
    }

    public <T> T unwrap(Class<T> aClass) {
        //TODO: Implement unwrap
        return null;
    }

    public <T> void addNamedEntityGraph(String s, EntityGraph<T> entityGraph) {
        //TODO: Implement addNamedEntityGraph
    }

    public PersistenceManager getPersistenceManager() {
        return persistenceManager;
    }

    public void setPersistenceManager(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }
}
