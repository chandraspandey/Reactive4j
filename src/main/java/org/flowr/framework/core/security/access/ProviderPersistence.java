package org.flowr.framework.core.security.access;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface ProviderPersistence<ORM_ENTITY> {

	public CriteriaBuilder getCriteriaBuilder();
	
	public EntityTransaction getEntityTransaction();
	
	public ORM_ENTITY get( Integer identifier );
	
	public List< ORM_ENTITY > get();
	
	public void save( ORM_ENTITY entity );
	
	public void create( ORM_ENTITY entity );
	
	public void update( ORM_ENTITY entity );
	
	public void delete( ORM_ENTITY entity );
	
	public void deleteById( Integer identifier );
	
	public EntityManager getEntityManager();
}