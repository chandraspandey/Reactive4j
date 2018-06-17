package org.flowr.framework.core.flow.persistence;

import java.io.Serializable;
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

public class PersistenceHandler<ORM_ENTITY extends Serializable> implements ProviderPersistence<ORM_ENTITY>{

	private Class<ORM_ENTITY> clazz;	
	private EntityManager entityManager;
	
	public PersistenceHandler(EntityManager entityManager,Class<ORM_ENTITY> clazz ){
		
		this.entityManager 	= entityManager;
		this.clazz 			= clazz;		
	}
	
	public CriteriaBuilder getCriteriaBuilder() {
		
		return entityManager.getCriteriaBuilder();
	}
	
	public EntityTransaction getEntityTransaction() {
		return entityManager.getTransaction();
	}
	
	public ORM_ENTITY get( Integer identifier ){
		
		return entityManager.find( clazz, identifier );
	}
	
	public List< ORM_ENTITY > get(){
		return entityManager.createQuery( "from " + clazz.getName(), clazz).getResultList();
	}
	
	public void save( ORM_ENTITY entity ){
		entityManager.persist( entity );
	}
	
	public void create( ORM_ENTITY entity ){
		save( entity );
	}
	
	public void update( ORM_ENTITY entity ){
		entityManager.merge( entity );
	}
	
	public void delete( ORM_ENTITY entity ){
		entityManager.remove( entity );
	}
	
	public void deleteById( Integer identifier ){
		
		ORM_ENTITY entity = get( identifier );
		delete( entity );
	}
	
	public EntityManager getEntityManager(){
		
		return this.entityManager;
	}

	public String toString(){
		
		return "PersistenceHandler{\n"+
				" clazz : "+clazz.getCanonicalName()+	
				" | entityManager : "+entityManager+	
				"}\n";
	}
}
