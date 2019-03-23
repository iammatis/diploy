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

    public EntityManagerImpl(EntityManagerFactoryImpl factory) {
        this.factory = factory;
    }

    /**
     * Make an instance managed and persistent.
     *
     * @param entity
     * @throws EntityExistsException        if the entity already exists.
     *                                      (If the entity already exists, the EntityExistsException may
     *                                      be thrown when the persist operation is invoked, or the
     *                                      EntityExistsException or another PersistenceException may be
     *                                      thrown at flush or commit time.)
     * @throws IllegalArgumentException     if the instance is not an
     *                                      entity
     * @throws TransactionRequiredException if there is no
     *                                      transaction when invoked on a container-managed
     *                                      entity manager that is of type
     *                                      PersistenceContextType.TRANSACTION.
     */
    public void persist(Object entity) {
        //TODO: Implement persist
    }

    /**
     * Merge the state of the given entity into the
     * current persistence context.
     *
     * @param entity
     * @return the managed instance that the state was merged to
     * @throws IllegalArgumentException     if instance is not an
     *                                      entity or is a removed entity
     * @throws TransactionRequiredException if there is no
     *                                      transaction when invoked on a container-managed
     *                                      entity manager that is of type
     *                                      PersistenceContextType.TRANSACTION.
     */
    public <T> T merge(T entity) {
        //TODO: Implement merge
        return null;
    }

    /**
     * Remove the entity instance.
     *
     * @param entity
     * @throws IllegalArgumentException     if the instance is not an
     *                                      entity or is a detached entity
     * @throws TransactionRequiredException if there is no
     *                                      transaction when invoked on a container-managed
     *                                      entity manager that is of type
     *                                      PersistenceContextType.TRANSACTION.
     */
    public void remove(Object entity) {
        //TODO: Implement remove
    }

    /**
     * Find by primary key.
     * Search for an entity of the specified class and primary key.
     * If the entity instance is contained in the persistence context
     * it is returned from there.
     *
     * @param entityClass
     * @param primaryKey
     * @return the found entity instance or null if the entity does
     * not exist
     * @throws IllegalArgumentException if the first argument does
     *                                  not denote an entity type or the second argument is
     *                                  is not a valid type for that entity’s primary key or
     *                                  is null
     */
    public <T> T find(Class<T> entityClass, Object primaryKey) {
        //TODO: Implement find
        return null;
    }

    /**
     * Find by primary key, using the specified properties.
     * Search for an entity of the specified class and primary key.
     * If the entity instance is contained in the persistence context
     * it is returned from there.
     * If a vendor-specific property or hint is not recognized,
     * it is silently ignored.
     *
     * @param entityClass
     * @param primaryKey
     * @param properties  standard and vendor-specific properties
     *                    and hints
     * @return the found entity instance or null if the entity does
     * not exist
     * @throws IllegalArgumentException if the first argument does
     *                                  not denote an entity type or the second argument is
     *                                  is not a valid type for that entity’s primary key or
     *                                  is null
     */
    public <T> T find(Class<T> entityClass, Object primaryKey, Map<String, Object> properties) {
        //TODO: Implement find
        return null;
    }

    /**
     * Find by primary key and lock.
     * Search for an entity of the specified class and primary key
     * and lock it with respect to the specified lock type.
     * If the entity instance is contained in the persistence context
     * it is returned from there, and the effect of this method is
     * the same as if the lock method had been called on the entity.
     * If the entity is found within the persistence context and the
     * lock mode type is pessimistic and the entity has a version
     * attribute, the persistence provider must perform optimistic
     * version checks when obtaining the database lock. If these
     * checks fail, the OptimisticLockException will be thrown.
     * If the lock mode type is pessimistic and the entity instance
     * is found but cannot be locked:
     * - the PessimisticLockException will be thrown if the database
     * locking failure causes transaction-level rollback
     * - the LockTimeoutException will be thrown if the database
     * locking failure causes only statement-level rollback
     *
     * @param entityClass
     * @param primaryKey
     * @param lockMode
     * @return the found entity instance or null if the entity does
     * not exist
     * @throws IllegalArgumentException     if the first argument does
     *                                      not denote an entity type or the second argument is
     *                                      not a valid type for that entity's primary key or
     *                                      is null
     * @throws TransactionRequiredException if there is no
     *                                      transaction and a lock mode other than NONE is
     *                                      specified or if invoked on an entity manager which has
     *                                      not been joined to the current transaction and a lock
     *                                      mode other than NONE is specified
     * @throws OptimisticLockException      if the optimistic version
     *                                      check fails
     * @throws PessimisticLockException     if pessimistic locking
     *                                      fails and the transaction is rolled back
     * @throws LockTimeoutException         if pessimistic locking fails and
     *                                      only the statement is rolled back
     * @throws PersistenceException         if an unsupported lock call
     *                                      is made
     */
    public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode) {
        //TODO: Implement find
        return null;
    }

    /**
     * Find by primary key and lock, using the specified properties.
     * Search for an entity of the specified class and primary key
     * and lock it with respect to the specified lock type.
     * If the entity instance is contained in the persistence context
     * it is returned from there. If the entity is found
     * within the persistence context and the lock mode type
     * is pessimistic and the entity has a version attribute, the
     * persistence provider must perform optimistic version checks
     * when obtaining the database lock. If these checks fail,
     * the OptimisticLockException will be thrown.
     * If the lock mode type is pessimistic and the entity instance
     * is found but cannot be locked:
     * - the PessimisticLockException will be thrown if the database
     * locking failure causes transaction-level rollback
     * - the LockTimeoutException will be thrown if the database
     * locking failure causes only statement-level rollback
     * If a vendor-specific property or hint is not recognized,
     * it is silently ignored.
     * Portable applications should not rely on the standard timeout
     * hint. Depending on the database in use and the locking
     * mechanisms used by the provider, the hint may or may not
     * be observed.
     *
     * @param entityClass
     * @param primaryKey
     * @param lockMode
     * @param properties  standard and vendor-specific properties
     *                    and hints
     * @return the found entity instance or null if the entity does
     * not exist
     * @throws IllegalArgumentException     if the first argument does
     *                                      not denote an entity type or the second argument is
     *                                      not a valid type for that entity's primary key or
     *                                      is null
     * @throws TransactionRequiredException if there is no
     *                                      transaction and a lock mode other than NONE is
     *                                      specified or if invoked on an entity manager which has
     *                                      not been joined to the current transaction and a lock
     *                                      mode other than NONE is specified
     * @throws OptimisticLockException      if the optimistic version
     *                                      check fails
     * @throws PessimisticLockException     if pessimistic locking
     *                                      fails and the transaction is rolled back
     * @throws LockTimeoutException         if pessimistic locking fails and
     *                                      only the statement is rolled back
     * @throws PersistenceException         if an unsupported lock call
     *                                      is made
     */
    public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode, Map<String, Object> properties) {
        //TODO: Implement find
        return null;
    }

    /**
     * Get an instance, whose state may be lazily fetched.
     * If the requested instance does not exist in the database,
     * the EntityNotFoundException is thrown when the instance
     * state is first accessed. (The persistence provider runtime is
     * permitted to throw the EntityNotFoundException when
     * getReference is called.)
     * The application should not expect that the instance state will
     * be available upon detachment, unless it was accessed by the
     * application while the entity manager was open.
     *
     * @param entityClass
     * @param primaryKey
     * @return the found entity instance
     * @throws IllegalArgumentException if the first argument does
     *                                  not denote an entity type or the second argument is
     *                                  not a valid type for that entity’s primary key or
     *                                  is null
     * @throws EntityNotFoundException  if the entity state
     *                                  cannot be accessed
     */
    public <T> T getReference(Class<T> entityClass, Object primaryKey) {
        //TODO: Implement getReference
        return null;
    }

    /**
     * Synchronize the persistence context to the
     * underlying database.
     *
     * @throws TransactionRequiredException if there is
     *                                      no transaction or if the entity manager has not been
     *                                      joined to the current transaction
     * @throws PersistenceException         if the flush fails
     */
    public void flush() {
        //TODO: Implement flush
    }

    /**
     * Set the flush mode that applies to all objects contained
     * in the persistence context.
     *
     * @param flushMode
     */
    public void setFlushMode(FlushModeType flushMode) {
        //TODO: Implement setFlushMode
    }

    /**
     * Get the flush mode that applies to all objects contained
     * in the persistence context.
     *
     * @return flushMode
     */
    public FlushModeType getFlushMode() {
        //TODO: Implement getFlushMode
        return null;
    }

    /**
     * Lock an entity instance that is contained in the persistence
     * context with the specified lock mode type.
     * If a pessimistic lock mode type is specified and the entity
     * contains a version attribute, the persistence provider must
     * also perform optimistic version checks when obtaining the
     * database lock. If these checks fail, the
     * OptimisticLockException will be thrown.
     * If the lock mode type is pessimistic and the entity instance
     * is found but cannot be locked:
     * - the PessimisticLockException will be thrown if the database
     * locking failure causes transaction-level rollback
     * - the LockTimeoutException will be thrown if the database
     * locking failure causes only statement-level rollback
     *
     * @param entity
     * @param lockMode
     * @throws IllegalArgumentException     if the instance is not an
     *                                      entity or is a detached entity
     * @throws TransactionRequiredException if there is no
     *                                      transaction or if invoked on an entity manager which
     *                                      has not been joined to the current transaction
     * @throws EntityNotFoundException      if the entity does not exist
     *                                      in the database when pessimistic locking is
     *                                      performed
     * @throws OptimisticLockException      if the optimistic version
     *                                      check fails
     * @throws PessimisticLockException     if pessimistic locking fails
     *                                      and the transaction is rolled back
     * @throws LockTimeoutException         if pessimistic locking fails and
     *                                      only the statement is rolled back
     * @throws PersistenceException         if an unsupported lock call
     *                                      is made
     */
    public void lock(Object entity, LockModeType lockMode) {
        //TODO: Implement lock
    }

    /**
     * Lock an entity instance that is contained in the persistence
     * context with the specified lock mode type and with specified
     * properties.
     * If a pessimistic lock mode type is specified and the entity
     * contains a version attribute, the persistence provider must
     * also perform optimistic version checks when obtaining the
     * database lock. If these checks fail, the
     * OptimisticLockException will be thrown.
     * If the lock mode type is pessimistic and the entity instance
     * is found but cannot be locked:
     * - the PessimisticLockException will be thrown if the database
     * locking failure causes transaction-level rollback
     * - the LockTimeoutException will be thrown if the database
     * locking failure causes only statement-level rollback
     * If a vendor-specific property or hint is not recognized,
     * it is silently ignored.
     * Portable applications should not rely on the standard timeout
     * hint. Depending on the database in use and the locking
     * mechanisms used by the provider, the hint may or may not
     * be observed.
     *
     * @param entity
     * @param lockMode
     * @param properties standard and vendor-specific properties
     *                   and hints
     * @throws IllegalArgumentException     if the instance is not an
     *                                      entity or is a detached entity
     * @throws TransactionRequiredException if there is no
     *                                      transaction or if invoked on an entity manager which
     *                                      has not been joined to the current transaction
     * @throws EntityNotFoundException      if the entity does not exist
     *                                      in the database when pessimistic locking is
     *                                      performed
     * @throws OptimisticLockException      if the optimistic version
     *                                      check fails
     * @throws PessimisticLockException     if pessimistic locking fails
     *                                      and the transaction is rolled back
     * @throws LockTimeoutException         if pessimistic locking fails and
     *                                      only the statement is rolled back
     * @throws PersistenceException         if an unsupported lock call
     *                                      is made
     */
    public void lock(Object entity, LockModeType lockMode, Map<String, Object> properties) {
        //TODO: Implement lock
    }

    /**
     * Refresh the state of the instance from the database,
     * overwriting changes made to the entity, if any.
     *
     * @param entity
     * @throws IllegalArgumentException     if the instance is not
     *                                      an entity or the entity is not managed
     * @throws TransactionRequiredException if there is no
     *                                      transaction when invoked on a container-managed
     *                                      entity manager that is of type
     *                                      PersistenceContextType.TRANSACTION.
     * @throws EntityNotFoundException      if the entity no longer
     *                                      exists in the database
     */
    public void refresh(Object entity) {
        //TODO: Implement refresh
    }

    /**
     * Refresh the state of the instance from the database, using
     * the specified properties, and overwriting changes made to
     * the entity, if any.
     * If a vendor-specific property or hint is not recognized,
     * it is silently ignored.
     *
     * @param entity
     * @param properties standard and vendor-specific properties
     *                   and hints
     * @throws IllegalArgumentException     if the instance is not
     *                                      an entity or the entity is not managed
     * @throws TransactionRequiredException if there is no
     *                                      transaction when invoked on a container-managed
     *                                      entity manager that is of type
     *                                      PersistenceContextType.TRANSACTION.
     * @throws EntityNotFoundException      if the entity no longer
     *                                      exists in the database
     */
    public void refresh(Object entity, Map<String, Object> properties) {
        //TODO: Implement refresh
    }

    /**
     * Refresh the state of the instance from the database,
     * overwriting changes made to the entity, if any, and
     * lock it with respect to given lock mode type.
     * If the lock mode type is pessimistic and the entity instance
     * is found but cannot be locked:
     * - the PessimisticLockException will be thrown if the database
     * locking failure causes transaction-level rollback
     * - the LockTimeoutException will be thrown if the
     * database locking failure causes only statement-level
     * rollback.
     *
     * @param entity
     * @param lockMode
     * @throws IllegalArgumentException     if the instance is not
     *                                      an entity or the entity is not managed
     * @throws TransactionRequiredException if invoked on an entity
     *                                      manager of type PersistenceContextType.TRANSACTION
     *                                      when there is no transaction; if invoked on an
     *                                      extended entity manager when there is no transaction
     *                                      and a lock mode other than NONE has been specified;
     *                                      or if invoked on an extended entity manager that has
     *                                      not been joined to the current transaction and a lock
     *                                      mode other than NONE has been specified
     * @throws EntityNotFoundException      if the entity no longer exists
     *                                      in the database
     * @throws PessimisticLockException     if pessimistic locking fails
     *                                      and the transaction is rolled back
     * @throws LockTimeoutException         if pessimistic locking fails and
     *                                      only the statement is rolled back
     * @throws PersistenceException         if an unsupported lock call
     *                                      is made
     */
    public void refresh(Object entity, LockModeType lockMode) {
        //TODO: Implement refresh
    }

    /**
     * Refresh the state of the instance from the database,
     * overwriting changes made to the entity, if any, and
     * lock it with respect to given lock mode type and with
     * specified properties.
     * If the lock mode type is pessimistic and the entity instance
     * is found but cannot be locked:
     * - the PessimisticLockException will be thrown if the database
     * locking failure causes transaction-level rollback
     * - the LockTimeoutException will be thrown if the database
     * locking failure causes only statement-level rollback
     * If a vendor-specific property or hint is not recognized,
     * it is silently ignored.
     * Portable applications should not rely on the standard timeout
     * hint. Depending on the database in use and the locking
     * mechanisms used by the provider, the hint may or may not
     * be observed.
     *
     * @param entity
     * @param lockMode
     * @param properties standard and vendor-specific properties
     *                   and hints
     * @throws IllegalArgumentException     if the instance is not
     *                                      an entity or the entity is not managed
     * @throws TransactionRequiredException if invoked on an entity
     *                                      manager of type PersistenceContextType.TRANSACTION
     *                                      when there is no transaction; if invoked on an
     *                                      extended entity manager when there is no transaction
     *                                      and a lock mode other than NONE has been specified;
     *                                      or if invoked on an extended entity manager that has
     *                                      not been joined to the current transaction and a lock
     *                                      mode other than NONE has been specified
     * @throws EntityNotFoundException      if the entity no longer exists
     *                                      in the database
     * @throws PessimisticLockException     if pessimistic locking fails
     *                                      and the transaction is rolled back
     * @throws LockTimeoutException         if pessimistic locking fails and
     *                                      only the statement is rolled back
     * @throws PersistenceException         if an unsupported lock call
     *                                      is made
     */
    public void refresh(Object entity, LockModeType lockMode, Map<String, Object> properties) {
        //TODO: Implement refresh
    }

    /**
     * Clear the persistence context, causing all managed
     * entities to become detached. Changes made to entities that
     * have not been flushed to the database will not be
     * persisted.
     */
    public void clear() {
        //TODO: Implement clear
    }

    /**
     * Remove the given entity from the persistence context, causing
     * a managed entity to become detached. Unflushed changes made
     * to the entity if any (including removal of the entity),
     * will not be synchronized to the database. Entities which
     * previously referenced the detached entity will continue to
     * reference it.
     *
     * @param entity
     * @throws IllegalArgumentException if the instance is not an
     *                                  entity
     */
    public void detach(Object entity) {
        //TODO: Implement detach
    }

    /**
     * Check if the instance is a managed entity instance belonging
     * to the current persistence context.
     *
     * @param entity
     * @return boolean indicating if entity is in persistence context
     * @throws IllegalArgumentException if not an entity
     */
    public boolean contains(Object entity) {
        //TODO: Implement contains
        return false;
    }

    /**
     * Get the current lock mode for the entity instance.
     *
     * @param entity
     * @return lock mode
     * @throws TransactionRequiredException if there is no
     *                                      transaction or if the entity manager has not been
     *                                      joined to the current transaction
     * @throws IllegalArgumentException     if the instance is not a
     *                                      managed entity and a transaction is active
     */
    public LockModeType getLockMode(Object entity) {
        //TODO: Implement getLockMode
        return null;
    }

    /**
     * Set an entity manager property or hint. If a vendor-specific
     * property or hint is not recognized, it is silently ignored.
     *
     * @param propertyName name of property or hint
     * @param value
     * @throws IllegalArgumentException if the second argument is
     *                                  not valid for the implementation
     */
    public void setProperty(String propertyName, Object value) {
        //TODO: Implement getProperties
    }

    /**
     * Get the properties and hints and associated values that are
     * in effect for the entity manager. Changing the contents of
     * the map does not change the configuration in effect.
     *
     * @return map of properties and hints in effect
     */
    public Map<String, Object> getProperties() {
        //TODO: Implement getProperties
        return null;
    }

    /**
     * Create an instance of Query for executing a
     * Java Persistence query language statement.
     *
     * @param qlString a Java Persistence query string
     * @return the new query instance
     * @throws IllegalArgumentException if the query string is
     *                                  found to be invalid
     */
    public Query createQuery(String qlString) {
        //TODO: Implement createQuery
        return null;
    }

    /**
     * Create an instance of TypedQuery for executing a
     * criteria query.
     *
     * @param criteriaQuery a criteria query object
     * @return the new query instance
     * @throws IllegalArgumentException if the criteria query is
     *                                  found to be invalid
     */
    public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
        //TODO: Implement createQuery
        return null;
    }

    /**
     * Create an instance of Query for executing a criteria
     * update query.
     *
     * @param updateQuery a criteria update query object
     * @return the new query instance
     * @throws IllegalArgumentException if the update query is
     *                                  found to be invalid
     */
    public Query createQuery(CriteriaUpdate updateQuery) {
        //TODO: Implement createQuery
        return null;
    }

    /**
     * Create an instance of Query for executing a criteria
     * delete query.
     *
     * @param deleteQuery a criteria delete query object
     * @return the new query instance
     * @throws IllegalArgumentException if the delete query is
     *                                  found to be invalid
     */
    public Query createQuery(CriteriaDelete deleteQuery) {
        //TODO: Implement createQuery
        return null;
    }

    /**
     * Create an instance of TypedQuery for executing a
     * Java Persistence query language statement.
     * The select list of the query must contain only a single
     * item, which must be assignable to the type specified by
     * the resultClass argument.[26]
     *
     * @param qlString    a Java Persistence query string
     * @param resultClass the type of the query result
     * @return the new query instance
     * @throws IllegalArgumentException if the query string is found
     *                                  to be invalid or if the query result is found to
     *                                  not be assignable to the specified type
     */
    public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
        //TODO: Implement createQuery
        return null;
    }

    /**
     * Create an instance of Query for executing a named query
     * (in the Java Persistence query language or in native SQL).
     *
     * @param name the name of a query defined in metadata
     * @return the new query instance
     * @throws IllegalArgumentException if a query has not been
     *                                  defined with the given name or if the query string is
     *                                  found to be invalid
     */
    public Query createNamedQuery(String name) {
        //TODO: Implement createNamedQuery
        return null;
    }

    /**
     * Create an instance of TypedQuery for executing a
     * Java Persistence query language named query.
     * The select list of the query must contain only a single
     * item, which must be assignable to the type specified by
     * the resultClass argument.[27]
     *
     * @param name        the name of a query defined in metadata
     * @param resultClass the type of the query result
     * @return the new query instance
     * @throws IllegalArgumentException if a query has not been
     *                                  defined with the given name or if the query string is
     *                                  found to be invalid or if the query result is found to
     *                                  not be assignable to the specified type
     */
    public <T> TypedQuery<T> createNamedQuery(String name, Class<T> resultClass) {
        //TODO: Implement createNamedQuery
        return null;
    }

    /**
     * Create an instance of Query for executing a native SQL
     * statement, e.g., for update or delete.
     * If the query is not an update or delete query, query
     * execution will result in each row of the SQL result
     * being returned as a result of type Object[] (or a result
     * of type Object if there is only one column in the select
     * list.) Column values are returned in the order of their
     * appearance in the select list and default JDBC type
     * mappings are applied.
     *
     * @param sqlString a native SQL query string
     * @return the new query instance
     */
    public Query createNativeQuery(String sqlString) {
        //TODO: Implement createNativeQuery
        return null;
    }

    /**
     * Create an instance of Query for executing
     * a native SQL query.
     *
     * @param sqlString   a native SQL query string
     * @param resultClass the class of the resulting instance(s)
     * @return the new query instance
     */
    public Query createNativeQuery(String sqlString, Class resultClass) {
        //TODO: Implement createNativeQuery
        return null;
    }

    /**
     * Create an instance of Query for executing
     * a native SQL query.
     *
     * @param sqlString        a native SQL query string
     * @param resultSetMapping the name of the result set mapping
     * @return the new query instance
     */
    public Query createNativeQuery(String sqlString, String resultSetMapping) {
        //TODO: Implement createNativeQuery
        return null;
    }

    /**
     * Create an instance of StoredProcedureQuery for executing a
     * stored procedure in the database.
     *
     * @param name name assigned to the stored procedure query
     *             in metadata
     * @return the new stored procedure query instance
     * @throws IllegalArgumentException if a query has not been
     *                                  defined with the given name
     */
    public StoredProcedureQuery createNamedStoredProcedureQuery(String name) {
        //TODO: Implement createNamedStoredProcedureQuery
        return null;
    }

    /**
     * Create an instance of StoredProcedureQuery for executing a
     * stored procedure in the database.
     * Parameters must be registered before the stored procedure can
     * be executed.
     * If the stored procedure returns one or more result sets,
     * any result set will be returned as a list of type Object[].
     *
     * @param procedureName name of the stored procedure in the
     *                      database
     * @return the new stored procedure query instance
     * @throws IllegalArgumentException if a stored procedure of the
     *                                  given name does not exist (or the query execution
     *                                  will fail)
     */
    public StoredProcedureQuery createStoredProcedureQuery(String procedureName) {
        //TODO: Implement createStoredProcedureQuery
        return null;
    }

    /**
     * Create an instance of StoredProcedureQuery for executing a
     * stored procedure in the database.
     * Parameters must be registered before the stored procedure can
     * be executed.
     * The resultClass arguments must be specified in the order in
     * which the result sets will be returned by the stored procedure
     * invocation.
     *
     * @param procedureName name of the stored procedure in the
     *                      database
     * @param resultClasses classes to which the result sets
     *                      produced by the stored procedure are to
     *                      be mapped
     * @return the new stored procedure query instance
     * @throws IllegalArgumentException if a stored procedure of the
     *                                  given name does not exist (or the query execution
     *                                  will fail)
     */
    public StoredProcedureQuery createStoredProcedureQuery(String procedureName, Class... resultClasses) {
        //TODO: Implement createStoredProcedureQuery
        return null;
    }

    /**
     * Create an instance of StoredProcedureQuery for executing a
     * stored procedure in the database.
     * Parameters must be registered before the stored procedure can
     * be executed.
     * The resultSetMapping arguments must be specified in the order
     * in which the result sets will be returned by the stored
     * procedure invocation.
     *
     * @param procedureName     name of the stored procedure in the
     *                          database
     * @param resultSetMappings the names of the result set mappings
     *                          to be used in mapping result sets
     *                          returned by the stored procedure
     * @return the new stored procedure query instance
     * @throws IllegalArgumentException if a stored procedure or
     *                                  result set mapping of the given name does not exist
     *                                  (or the query execution will fail)
     */
    public StoredProcedureQuery createStoredProcedureQuery(String procedureName, String... resultSetMappings) {
        //TODO: Implement createStoredProcedureQuery
        return null;
    }

    /**
     * Indicate to the entity manager that a JTA transaction is
     * active. This method should be called on a JTA application
     * managed entity manager that was created outside the scope
     * of the active transaction or on an entity manager of type
     * SynchronizationType.UNSYNCHRONIZED to associate it with the
     * current JTA transaction.
     *
     * @throws TransactionRequiredException if there is
     *                                      no transaction
     */
    public void joinTransaction() {
        //TODO: Implement joinTransaction
    }

    /**
     * Determine whether the entity manager is joined to the
     * current transaction. Returns false if the entity manager
     * is not joined to the current transaction or if no
     * transaction is active
     *
     * @return boolean
     */
    public boolean isJoinedToTransaction() {
        //TODO: Implement isJoinedToTransaction
        return false;
    }

    /**
     * Return an object of the specified type to allow access to the
     * provider-specific API. If the provider's EntityManager
     * implementation does not support the specified class, the
     * PersistenceException is thrown.
     *
     * @param cls the class of the object to be returned. This is
     *            normally either the underlying EntityManager implementation
     *            class or an interface that it implements.
     * @return an instance of the specified class
     * @throws PersistenceException if the provider does not
     *                              support the call
     */
    public <T> T unwrap(Class<T> cls) {
        //TODO: Implement unwrap
        return null;
    }

    /**
     * Return the underlying provider object for the EntityManager,
     * if available. The result of this method is implementation
     * specific. The unwrap method is to be preferred for new
     * applications.
     *
     * @return underlying provider object for EntityManager
     */
    public Object getDelegate() {
        //TODO: Implement getDelegate
        return null;
    }

    /**
     * Close an application-managed entity manager.
     * After the close method has been invoked, all methods
     * on the EntityManager instance and any Query, TypedQuery, and
     * StoredProcedureQuery objects obtained from it will throw the
     * IllegalStateException except for getProperties,
     * getTransaction, and isOpen (which will return false).
     * If this method is called when the entity manager is
     * joined to an active transaction, the persistence
     * context remains managed until the transaction completes.
     *
     * @throws IllegalStateException if the entity manager
     *                               is container-managed
     */
    public void close() {
        //TODO: Implement close
        closed = true;
    }

    /**
     * Determine whether the entity manager is open.
     *
     * @return true until the entity manager has been closed
     */
    public boolean isOpen() {
        return !closed;
    }

    /**
     * Return the resource-level EntityTransaction object.
     * The EntityTransaction instance may be used serially to
     * begin and commit multiple transactions.
     *
     * @return EntityTransaction instance
     * @throws IllegalStateException if invoked on a JTA
     *                               entity manager
     */
    public EntityTransaction getTransaction() {
        //TODO: Implement getTransaction
        return null;
    }

    /**
     * Return the entity manager factory for the entity manager.
     *
     * @return EntityManagerFactory instance
     * @throws IllegalStateException if the entity manager has
     *                               been closed
     */
    public EntityManagerFactory getEntityManagerFactory() {
        return factory;
    }

    /**
     * Return an instance of CriteriaBuilder for the creation of
     * CriteriaQuery objects.
     *
     * @return CriteriaBuilder instance
     * @throws IllegalStateException if the entity manager has
     *                               been closed
     */
    public CriteriaBuilder getCriteriaBuilder() {
        //TODO: Implement getCriteriaBuilder
        return null;
    }

    /**
     * Return an instance of Metamodel interface for access to the
     * metamodel of the persistence unit.
     *
     * @return Metamodel instance
     * @throws IllegalStateException if the entity manager has
     *                               been closed
     */
    public Metamodel getMetamodel() {
        //TODO: Implement getMetamodel
        return null;
    }

    /**
     * Return a mutable EntityGraph that can be used to dynamically
     * create an EntityGraph.
     *
     * @param rootType class of entity graph
     * @return entity graph
     */
    public <T> EntityGraph<T> createEntityGraph(Class<T> rootType) {
        //TODO: Implement createEntityGraph
        return null;
    }

    /**
     * Return a mutable copy of the named EntityGraph. If there
     * is no entity graph with the specified name, null is returned.
     *
     * @param graphName name of an entity graph
     * @return entity graph
     */
    public EntityGraph<?> createEntityGraph(String graphName) {
        //TODO: Implement createEntityGraph
        return null;
    }

    /**
     * Return a named EntityGraph. The returned EntityGraph
     * should be considered immutable.
     *
     * @param graphName name of an existing entity graph
     * @return named entity graph
     * @throws IllegalArgumentException if there is no EntityGraph of
     *                                  the given name
     */
    public EntityGraph<?> getEntityGraph(String graphName) {
        //TODO: Implement getEntityGraph
        return null;
    }

    /**
     * Return all named EntityGraphs that have been defined for the
     * provided class type.
     *
     * @param entityClass entity class
     * @return list of all entity graphs defined for the entity
     * @throws IllegalArgumentException if the class is not an entity
     */
    public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> entityClass) {
        //TODO: Implement getEntityGraphs
        return null;
    }

}
