
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;

import org.flowr.framework.api.Provider;
import org.flowr.framework.core.context.Context.PersistenceContext;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.model.Model.ModelORM;

public interface PersistenceProvider extends Provider{
    
    public enum PeristenceType{
        TUPLE,
        TUPLE_LIST
    }
    
    <K,V> void persist(PersistenceContext<K,V> persistenceContext) throws ConfigurationException;

    <K,V> PersistenceContext<K,V> restore(String className) throws ConfigurationException;

    CriteriaBuilder getCriteriaBuilder();
    
    EntityTransaction getEntityTransaction();
    
    ModelORM get(Class<ModelORM> entityClass, Object identifier);
    
    void save(ModelORM entity);
    
    void create(ModelORM entity);
    
    void update(ModelORM entity);
    
    void delete(ModelORM entity);
    
    EntityManager getEntityManager();
}
