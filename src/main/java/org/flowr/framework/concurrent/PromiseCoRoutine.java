
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.concurrent;

import org.apache.log4j.Logger;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.promise.PromiseTypeClient;

public class PromiseCoRoutine extends Thread implements CoRoutine{

    private PromiseTypeClient promiseTypeClient;
    private PromiseSession promiseSession;
    
    public PromiseCoRoutine(PromiseTypeClient promiseTypeClient) {
        
        super(promiseTypeClient);
        this.promiseTypeClient  = promiseTypeClient;     
        
    }
    
    @Override
    public void prePromise(){
        
        try {
            
            this.setName(promiseTypeClient.getClientIdentity().getClientApplicationName());
            this.promiseSession     = new PromiseSession(promiseTypeClient.getClientIdentity());
            this.promiseSession.start();
        } catch (ConfigurationException e) {
            Logger.getRootLogger().error(e);
        }
        
        Logger.getRootLogger().info("Starting Promise Session for : "+this.getName());
    }
    
    @Override
    public void run() {
        
        prePromise();
        super.run();        
        postPromise();
    }
    
    @Override
    public void postPromise() {
        
        this.promiseSession.end(promiseTypeClient.getTimelineOption());
        
        while(this.isAlive() && this.getState() != Thread.State.TERMINATED) {
        
            try {
            
                if(this.isAlive()) {
                    
                    Logger.getRootLogger().info("Closing Promise Session for : "+this.getName());
                    Logger.getRootLogger().info("Promise Session : "+promiseSession);
                    join();       
                }
            } catch (InterruptedException e) {
                Logger.getRootLogger().error(e);
                this.interrupt();
            }
        }
    }
}
