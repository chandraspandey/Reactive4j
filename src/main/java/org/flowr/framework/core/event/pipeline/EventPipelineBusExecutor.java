
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.event.pipeline;

import static org.flowr.framework.core.constants.ErrorMap.ERR_CONFIG;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.flowr.framework.core.exception.ClientException;
import org.flowr.framework.core.notification.dispatcher.NotificationBufferQueue;
import org.flowr.framework.core.service.Service.ServiceStatus;
import org.flowr.framework.core.service.ServiceLifecycle;

public final class EventPipelineBusExecutor implements EventBusExecutor,ServiceLifecycle,Runnable {

    private ServiceStatus serviceStatus = ServiceStatus.UNUSED;
    private ExecutorService service     = Executors.newCachedThreadPool();
    private long retryInterval          = 100;
    private EventBus eventBus;
    
    public EventPipelineBusExecutor(EventBus bus) {
        eventBus = bus;        
    }

    @Override
    public ServiceStatus startup(Optional<Properties> configProperties) {
        serviceStatus = ServiceStatus.STARTED;
        return serviceStatus;
    }

    @Override
    public ServiceStatus shutdown(Optional<Properties> configProperties) {
        service.shutdown();
        serviceStatus = ServiceStatus.STOPPED;
        return serviceStatus;   
    }

    @Override
    public synchronized NotificationBufferQueue process() throws ClientException{
        
        service.execute(this);
                
        NotificationBufferQueue notificationBufferQueue = new NotificationBufferQueue();
        
        List<EventPipeline> pipelineList = Collections.unmodifiableList(eventBus.getAllPipelines());
        
        Iterator<EventPipeline> iter = pipelineList.iterator();        
        
        while(iter.hasNext()) {
            
            EventPipeline p = iter.next();
            
            if(!p.isEmpty()) {
                
                EventPipelineTask task = new EventPipelineTask(p);
                
                Future<NotificationBufferQueue> future = service.submit(task);
                
                notificationBufferQueue.setEventType(p.getEventType());
                notificationBufferQueue.addAll(retrieveFutureResult(future));
             
            }            
        }         
        
        return notificationBufferQueue;
    }
    
    private <V> V retrieveFutureResult(Future<V>  future) throws ClientException{
        
        V v = null;
        
        try {
            
            while(!future.isDone()) {
            
                Thread.sleep(retryInterval);
                
                if(future.isDone()) {
                    v = future.get();
                }
            }
        
        } catch (InterruptedException | ExecutionException e) {
            
            Logger.getRootLogger().error(e);
            Thread.currentThread().interrupt();
            throw new ClientException(ERR_CONFIG, e.getMessage(),e);
        } 
        
        return v;
    }
    
    @Override
    public void run() {
        
        while(serviceStatus != ServiceStatus.STOPPED) {
            
            try {
                process();
                
                Thread.sleep(1000);
            } catch ( ClientException | InterruptedException e) {
                Logger.getRootLogger().error(e);
                Thread.currentThread().interrupt();
            }
        }
    }

    
}
