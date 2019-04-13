package sk.vilk.diploy;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;
import java.util.Map;

/**
 * Interface used to interact with the entity manager factory
 * for the persistence unit.
 */
public class EntityManagerFactoryImpl implements EntityManagerFactory {

    private boolean closed = false;
    private PersistenceManager persistenceManager = new PersistenceManager();

    public EntityManagerFactoryImpl() {
    }

    /**
     * Create a new application-managed EntityManager.
     * This method returns a new EntityManager instance each time
     * it is invoked.
     * The isOpen method will return true on the returned instance.
     * @return entity manager instance
     * @throws IllegalStateException if the entity manager factory
     * has been closed
     */
    public EntityManager createEntityManager() {
        return new EntityManagerImpl(this);
    }

    /**
     * Create a new application-managed EntityManager with the
     * specified Map of properties.
     * This method returns a new EntityManager instance each time
     * it is invoked.
     * The isOpen method will return true on the returned instance.
     * @param map properties for entity manager
     * @return entity manager instance
     * @throws IllegalStateException if the entity manager factory
     * has been closed
     */
    public EntityManager createEntityManager(Map map) {
        //TODO: Implement EntityManager with Map
        return createEntityManager();
    }

    /**
     * Create a new JTA application-managed EntityManager with the
     * specified synchronization type.
     * This method returns a new EntityManager instance each time
     * it is invoked.
     * The isOpen method will return true on the returned instance.
     * @param synchronizationType how and when the entity manager
     * should be synchronized with the current JTA transaction
     * @return entity manager instance
     * @throws IllegalStateException if the entity manager factory
     * has been configured for resource-local entity managers or has
     * been closed
     */
    public EntityManager createEntityManager(SynchronizationType synchronizationType) {
        //TODO: Implement EntityManager with SynchronizationType
        return createEntityManager();
    }

    /**
     * Create a new JTA application-managed EntityManager with the
     * specified synchronization type and Map of properties.
     * This method returns a new EntityManager instance each time
     * it is invoked.
     * The isOpen method will return true on the returned instance.
     * @param synchronizationType how and when the entity manager
     * should be synchronized with the current JTA transaction
     * @param map properties for entity manager; may be null
     * @return entity manager instance
     * @throws IllegalStateException if the entity manager factory
     * has been configured for resource-local entity managers or has
     * been closed
     */
    public EntityManager createEntityManager(SynchronizationType synchronizationType, Map map) {
        //TODO: Implement EntityManager with SynchronizationType and Map
        return createEntityManager();
    }

    /**
     * Return an instance of CriteriaBuilder for the creation of
     * CriteriaQuery objects.
     * @return CriteriaBuilder instance
     * @throws IllegalStateException if the entity manager factory
     * has been closed
     */
    public CriteriaBuilder getCriteriaBuilder() {
        //TODO: Implement getCriteriaBuilder
        return null;
    }

    /**
     * Return an instance of Metamodel interface for access to the
     * metamodel of the persistence unit.
     * @return Metamodel instance
     * @throws IllegalStateException if the entity manager factory
     * has been closed
     */
    public Metamodel getMetamodel() {
        //TODO: Implement getMetamodel
        return null;
    }

    /**
     * Indicates whether the factory is open. Returns true
     * until the factory has been closed.
     * @return boolean indicating whether the factory is open
     */
    public boolean isOpen() {
        return !closed;
    }

    /**
     * Close the factory, releasing any resources that it holds.
     * After a factory instance has been closed, all methods invoked
     * on it will throw the IllegalStateException, except for isOpen,
     * which will return false. Once an EntityManagerFactory has
     * been closed, all its entity managers are considered to be
     * in the closed state.
     * @throws IllegalStateException if the entity manager factory
     * has been closed
     */
    public void close() {
        //TODO: Implement close
        closed = true;
    }

    /**
     * Get the properties and associated values that are in effect
     * for the entity manager factory. Changing the contents of the
     * map does not change the configuration in effect.
     * @return properties
     * @throws IllegalStateException if the entity manager factory
     * has been closed
     */
    public Map<String, Object> getProperties() {
        //TODO: Implement getProperties
        return null;
    }

    /**
     * Access the cache that is associated with the entity manager
     * factory (the "second level cache").
     * @return instance of the Cache interface or null if no cache
     * is in use
     * @throws IllegalStateException if the entity manager factory
     * has been closed
     */
    public Cache getCache() {
        //TODO: Implement getCache
        return null;
    }

    /**
     * Return interface providing access to utility methods
     * for the persistence unit.
     * @return PersistenceUnitUtil interface
     * @throws IllegalStateException if the entity manager factory
     * has been closed
     */
    public PersistenceUnitUtil getPersistenceUnitUtil() {
        //TODO: Implement getPersistenceUnitUtil
        return null;
    }

    /**
     * Define the query, typed query, or stored procedure query as
     * a named query such that future query objects can be created
     * from it using the createNamedQuery or
     * createNamedStoredProcedureQuery method.
     * Any configuration of the query object (except for actual
     * parameter binding) in effect when the named query is added
     * is retained as part of the named query definition.
     * This includes configuration information such as max results,
     * hints, flush mode, lock mode, result set mapping information,
     * and information about stored procedure parameters.
     * When the query is executed, information that can be set
     * by means of the query APIs can be overridden. Information
     * that is overridden does not affect the named query as
     * registered with the entity manager factory, and thus does
     * not affect subsequent query objects created from it by
     * means of the createNamedQuery or
     * createNamedStoredProcedureQuery methods.
     * If a named query of the same name has been previously
     * defined, either statically via metadata or via this method,
     * that query definition is replaced.
     *
     * @param name  name for the query
     * @param query Query, TypedQuery, or StoredProcedureQuery object
     */
    public void addNamedQuery(String name, Query query) {
        //TODO: Implement addNamedQuery
    }

    /**
     * Return an object of the specified type to allow access to the
     * provider-specific API. If the provider's EntityManagerFactory
     * implementation does not support the specified class, the
     * PersistenceException is thrown.
     * @param cls the class of the object to be returned. This is
     * normally either the underlying EntityManagerFactory
     * implementation class or an interface that it implements.
     * @return an instance of the specified class
     * @throws PersistenceException if the provider does not
     * support the call
     */
    public <T> T unwrap(Class<T> cls) {
        //TODO: Implement unwrap
        return null;
    }

    /**
     * Add a named copy of the EntityGraph to the
     * EntityManagerFactory. If an entity graph with the same name
     * already exists, it is replaced.
     * @param graphName name for the entity graph
     * @param entityGraph entity graph
     */
    public <T> void addNamedEntityGraph(String graphName, EntityGraph<T> entityGraph) {
        //TODO: Implement addNamedEntityGraph
    }

    public PersistenceManager getPersistenceManager() {
        return persistenceManager;
    }
}
