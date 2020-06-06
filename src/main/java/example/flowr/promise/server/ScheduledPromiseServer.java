
/**
 * use private long timeout = 11000 for timeout scenario testing
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package example.flowr.promise.server;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.exception.PromiseException;
import org.flowr.framework.core.model.Model.RequestModel;
import org.flowr.framework.core.promise.Promisable.PromisableType;
import org.flowr.framework.core.promise.Promise.PromiseState;
import org.flowr.framework.core.promise.Promise.PromiseStatus;
import org.flowr.framework.core.promise.Promise.ScheduleStatus;
import org.flowr.framework.core.promise.PromiseTypeServer;
import org.flowr.framework.core.promise.RequestScale;
import org.flowr.framework.core.promise.Scale;
import org.flowr.framework.core.promise.Scale.Priority;
import org.flowr.framework.core.promise.Scale.PriorityScale;
import org.flowr.framework.core.promise.Scale.Severity;
import org.flowr.framework.core.promise.Scale.SeverityScale;
import org.flowr.framework.core.promise.scheduled.ScheduledProgressScale;
import org.flowr.framework.core.service.ServiceFramework;
import org.flowr.framework.core.target.ReactiveTarget;

import example.flowr.mock.Mock.ScheduledPromiseMockResponse;

public class ScheduledPromiseServer implements PromiseTypeServer{

    private static double artificialNow;
    private UUID acknowledgementId                          = UUID.randomUUID();    
    private long artificialDelay                            = 5000; 
    private String serverIdentifier                         = PromiseTypeServer.serverIdentifier();
    private ScheduledProgressScale scheduledProgressScale;
    
    private Timestamp scheduledTimestamp;   
    private boolean isDeferred;
    private PromisableType promisableType                   = PromisableType.PROMISE_SCHEDULED;
    private boolean isNegotiated;
    
    @Override
    public Scale buildProgressScale(PromisableType promisableType,double now) throws PromiseException{
        
        if(scheduledProgressScale == null){
        
            try {
                scheduledProgressScale = (ScheduledProgressScale) ServiceFramework.getInstance().getCatalog()
                                            .getConfigurationService().getProgressScale(
                                                    promisableType, serverIdentifier);     
                
            } catch (ConfigurationException e) {
                
                Logger.getRootLogger().error(e);
                Thread.currentThread().interrupt();
                throw new PromiseException(ErrorMap.ERR_CONFIG, e.getMessage(),e);
            } 
        }
        
        scheduledProgressScale.getScaleOption().setNow(now);
        
        Logger.getRootLogger().info("ScheduledPromiseServer : "+scheduledProgressScale);
        
        return scheduledProgressScale;
    }
    
    @Override
    public PromisableType getPromisableType() {
        return promisableType;
    }
    
    @Override
    public Scale invokeAndReturn(RequestModel request, RequestScale requestScale) throws PromiseException {
        
        ScheduledProgressScale scale = (ScheduledProgressScale) buildProgressScale(promisableType,
                                        increment(20.0));
        
        scale.setPromiseState(PromiseState.ACKNOWLEDGED);
        scale.setPromiseStatus(PromiseStatus.INITIATED);
        scale.setAcknowledgmentIdentifier(acknowledgementId.toString());
        scale.setScheduleStatus(ScheduleStatus.REGISTERED);
        scale.setSubscriptionClientId(requestScale.getSubscriptionClientId());
        scale.setPriorityScale(new PriorityScale(Priority.HIGH, 75));
        scale.setSeverityScale(new SeverityScale(Severity.LOW,25));
        
        if(scheduledTimestamp == null){
            
            scheduledTimestamp = new Timestamp(Instant.now().toEpochMilli());
            scale.setScheduledTimestamp(scheduledTimestamp);
        }
                
        try {
            Thread.sleep(artificialDelay);
        } catch (InterruptedException e) {
            Logger.getRootLogger().error(e);
            Thread.currentThread().interrupt();  
            throw new PromiseException(ErrorMap.ERR_CONFIG,e.getMessage(),e);
        }
        
        Logger.getRootLogger().info("ScheduledEventServer : invokeAndReturn : "+scale);
        
        return scale;
    }
    
    // Defer by 30 milli seconds
    public Timestamp defer() {
                
        
        Timestamp deferredTimestamp = new Timestamp(Instant.now().toEpochMilli()+artificialDelay);
        
        Logger.getRootLogger().info("ScheduledPromiseServer : Execution Deffered for  : "+deferredTimestamp);           
        Logger.getRootLogger().info("ScheduledPromiseServer : Expected time lag  : "+
                (deferredTimestamp.getTime()-scheduledTimestamp.getTime()));
        
        return deferredTimestamp;   
        
    }
    
    private static double increment(double now) {

        artificialNow+=now;
        return artificialNow;
    }

    @Override
    public Scale invokeForProgress(String acknowledgmentIdentifier) throws PromiseException {

        ScheduledProgressScale scale = (ScheduledProgressScale) buildProgressScale(promisableType,increment(20.0)); 

        scale.setPriorityScale(new PriorityScale(Priority.LOW, 25));
        scale.setSeverityScale(new SeverityScale(Severity.LOW,25));
        scale.setPromiseState(PromiseState.ASSURED);
        scale.setPromiseStatus(PromiseStatus.PROCESSING);    
        scale.setAcknowledgmentIdentifier(acknowledgmentIdentifier);

        
        if(isDeferred()){            

            if(artificialNow < 100) {
                
                scale.setScheduleStatus(ScheduleStatus.DEFFERED); 
            }else{
                scale.setPromiseState(PromiseState.FULFILLED);
                scale.setPromiseStatus(PromiseStatus.COMPLETED);    
                scale.setScheduleStatus(ScheduleStatus.OVER); 
                scale.setPriorityScale(new PriorityScale(Priority.HIGH, 25));
                scale.setSeverityScale(new SeverityScale(Severity.LOW,25));
                isDeferred = false;  
            }
        }else {
                
            scale.setScheduleStatus(ScheduleStatus.SCHEDULED); 
            scale.setDeferredTimestamp(defer());
            setDeferred(true);
            Logger.getRootLogger().info("BusinessScheduledPromiseServer : Execution scheduledTimestamp at  : "+
                    scheduledTimestamp); 
         }

        
        Logger.getRootLogger().info("ScheduledPromiseServer : invokeForProgress : "+scale);
        
        return scale;
    }

    @Override
    public String getServerIdentifier(){
    
        return this.serverIdentifier;
    }
    

    @Override
    public ScheduledPromiseMockResponse invokeWhenComplete(String acknowledgmentIdentifier) throws PromiseException{
        
        ScheduledProgressScale scale = (ScheduledProgressScale) buildProgressScale(promisableType,100);
        scale.setPromiseState(PromiseState.FULFILLED);
        scale.setPromiseStatus(PromiseStatus.COMPLETED);    
        scale.setPriorityScale(new PriorityScale(Priority.HIGH, 25));
        scale.setSeverityScale(new SeverityScale(Severity.HIGH,25));
        scale.setScheduleStatus(ScheduleStatus.OVER);   
  
        Logger.getRootLogger().info("ScheduledPromiseServer : invokeWhenComplete : "+
                new ScheduledPromiseMockResponse());
        
        return  new ScheduledPromiseMockResponse();
    }

    @Override
    public ReactiveTarget getReactiveTarget() {
        return this;
    }

    
    @Override
    public Scale negotiate(RequestScale requestScale) throws PromiseException {
        
        ScheduledProgressScale scale = (ScheduledProgressScale) 
                buildProgressScale(promisableType,increment(20.0));
        scale.acceptIfApplicable(requestScale);
        scale.setPromiseState(PromiseState.NEGOTIATED);        
        scale.setPromiseStatus(PromiseStatus.REGISTERED);
        scale.setPriorityScale(new PriorityScale(Priority.HIGH, 75));
        scale.setSeverityScale(new SeverityScale(Severity.LOW,25));
        isNegotiated = true;
        return scale;
    }

    @Override
    public boolean isNegotiated() {
        
        return isNegotiated;
    }

    public boolean isDeferred() {
        return isDeferred;
    }

    public void setDeferred(boolean isDeferred) {
        this.isDeferred = isDeferred;
    }



}
