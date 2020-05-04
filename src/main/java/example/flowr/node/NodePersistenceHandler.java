
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package example.flowr.node;

import java.util.AbstractMap.SimpleEntry;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;

import org.apache.log4j.Logger;
import org.flowr.framework.api.Node.DefaultNode;
import org.flowr.framework.core.context.Context.PersistenceContext;
import org.flowr.framework.core.model.DataAttribute;
import org.flowr.framework.core.model.Model.ModelORM;
import org.flowr.framework.core.model.PersistenceProvider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.jpa.HibernatePersistenceProvider;

public class NodePersistenceHandler extends DefaultNode implements PersistenceProvider{

    private SimpleEntry<ProviderType,String> entry = new SimpleEntry<>(
                                                        ProviderType.PERSISTENCE,
                                                        "example.flowr.node.NodePersistenceHandler"
                                                     );
    
    private HibernatePersistenceProvider hibernateProvider;
    private EntityManagerFactory  entityManagerFactory ;
    private EntityManager entityManager;
    private Session session;
    private SessionFactory sessionFactory;
  
    
    @Override
    public SimpleEntry<ProviderType,String> getProviderConfiguration(){
        
        return this.entry;
    }
        
    public void init() {
             
        
        if(hibernateProvider.getClass().isAssignableFrom(getDataSourceProvider().getClass())) {
        
            hibernateProvider =  (HibernatePersistenceProvider) getDataSourceProvider();
            entityManagerFactory = Persistence.createEntityManagerFactory("PERSISTENCE_UNIT");
            entityManager = entityManagerFactory.createEntityManager();
        }
        
        Properties dbproperties = new Properties();
        dbproperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        dbproperties.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        dbproperties.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/datasource");
        dbproperties.put("hibernate.connection.username", "root");
        dbproperties.put("hibernate.connection.password", "");
        
        Configuration configuration = new Configuration();
        configuration.setProperties(dbproperties);     
        
        Class<?>[] list = new Class<?>[]{DataAttribute.class};
        
        for(int index = 0; index < list.length; index++ ){          
            configuration.addAnnotatedClass(list[index]);
        }       
        
        this.sessionFactory = configuration.buildSessionFactory();
        
        entityManagerFactory.close();
        this.sessionFactory.close();    
        entityManager.close();
    }
    
    public void closeSession(){
        
        if(session!= null){
            session.close();
        }

    }
    
    public Session openSession(){
        
        if(session== null){
            session = sessionFactory.openSession();
        }
        
        return session;
    }
    
    @Override
    public <K,V> void persist(PersistenceContext<K,V> persistenceContext) {
        
        Logger.getRootLogger().info(persistenceContext);
    }

    @Override
    public <K,V> PersistenceContext<K,V> restore(String className) {
        
        Logger.getRootLogger().info("NodePersistenceHandler : restore : "+className);
        return null;
    }
    
    @Override
    public CriteriaBuilder getCriteriaBuilder() {

        return this.entityManager.getCriteriaBuilder();
    }

    @Override
    public EntityTransaction getEntityTransaction() {
        return this.entityManager.getTransaction();
    }

    @Override
    public ModelORM get(Class<ModelORM> entityClass, Object identifier) {
        return this.entityManager.find(entityClass, identifier);
    }
    

    @Override
    public void save(ModelORM entity) {
        this.entityManager.persist(entity);
    }

    @Override
    public void create(ModelORM entity) {
        this.entityManager.persist(entity);
    }

    @Override
    public void update(ModelORM entity) {
        this.entityManager.merge(entity);
    }

    @Override
    public void delete(ModelORM entity) {
        this.entityManager.remove(entity);
    }

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

 
}


