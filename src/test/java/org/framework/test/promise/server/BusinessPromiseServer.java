
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */


package org.framework.test.promise.server;

import java.util.AbstractMap.SimpleEntry;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.exception.PromiseException;
import org.flowr.framework.core.model.Model.RequestModel;
import org.flowr.framework.core.promise.Promisable.PromisableType;
import org.flowr.framework.core.promise.Promise.PromiseState;
import org.flowr.framework.core.promise.Promise.PromiseStatus;
import org.flowr.framework.core.promise.PromiseTypeServer;
import org.flowr.framework.core.promise.RequestScale;
import org.flowr.framework.core.promise.Scale;
import org.flowr.framework.core.promise.Scale.Priority;
import org.flowr.framework.core.promise.Scale.PriorityScale;
import org.flowr.framework.core.promise.Scale.Severity;
import org.flowr.framework.core.promise.Scale.SeverityScale;
import org.flowr.framework.core.promise.deferred.ProgressScale;
import org.flowr.framework.core.service.ServiceFramework;
import org.flowr.framework.core.target.ReactiveTarget;
import org.framework.fixture.mock.BusinessMock.BusinessPromiseMockResponse;
import org.framework.test.ConfigurableTest.PromiseCallback.CallbackHandler;
import org.framework.test.ConfigurableTest.PromiseCallback.CallbackType;

public final class BusinessPromiseServer implements PromiseTypeServer{

    private static double artificialNow;
    private UUID acknowledgementId                          = UUID.randomUUID();    
    private long artificialDelay                            = 500; 
    private String serverIdentifier                         = PromiseTypeServer.serverIdentifier();
    private ProgressScale progressScale;
    private PromisableType promisableType                   = PromisableType.PROMISE;
    private boolean isNegotiated;
    private static CallbackHandler callbackHandler;
    
    public static void setCallbackHandler(CallbackHandler handler) {
        
        callbackHandler = handler;
        Logger.getRootLogger().info("BusinessPromiseServer : setCallbackHandler : "+callbackHandler);
    }
    
    @Override
    public ProgressScale buildProgressScale(PromisableType promisableType,double now) throws PromiseException{
        
        if(progressScale == null){
        
            try {
                progressScale = (ProgressScale) ServiceFramework.getInstance().getCatalog().getConfigurationService()
                                    .getProgressScale(promisableType, serverIdentifier);
                
            } catch (ConfigurationException e) {
                
                Logger.getRootLogger().error(e);
                Thread.currentThread().interrupt();
                throw new PromiseException(ErrorMap.ERR_CONFIG, e.getMessage(),e);
            } 
        }
        
        progressScale.getScaleOption().setNow(now);
        Logger.getRootLogger().info("BusinessPromiseServer : buildProgressScale : "+progressScale);
        
        return progressScale;
    }
    
    @Override
    public Scale invokeAndReturn(RequestModel request, RequestScale requestScale) throws PromiseException {
        
        ProgressScale scale = buildProgressScale(promisableType,increment(20.0));
        
        scale.setPromiseState(PromiseState.ACKNOWLEDGED);
        scale.setPromiseStatus(PromiseStatus.INITIATED);
        scale.setAcknowledgmentIdentifier(acknowledgementId.toString());
        scale.setPriorityScale(new PriorityScale(Priority.HIGH, 75));
        scale.setSeverityScale(new SeverityScale(Severity.LOW,25));
                
        try {
            Thread.sleep(artificialDelay);
            
        } catch (InterruptedException e) {
            Logger.getRootLogger().error(e);
            Thread.currentThread().interrupt();
            throw new PromiseException(ErrorMap.ERR_CONFIG, e.getMessage(),e);
        }
        
        Logger.getRootLogger().info("BusinessPromiseServer : invokeAndReturn : "+scale);
        
        callbackHandler.doCallback(
                new SimpleEntry<>(CallbackType.SERVER,scale)
        );
        
        return scale;
    }

    @Override
    public ProgressScale invokeForProgress(String acknowledgmentIdentifier) throws PromiseException {
        
        ProgressScale scale = buildProgressScale(promisableType,increment(20.0));
        scale.setPromiseState(PromiseState.ASSURED);
        scale.setPromiseStatus(PromiseStatus.PROCESSING);    
        scale.setPriorityScale(new PriorityScale(Priority.HIGH, 25));
        scale.setSeverityScale(new SeverityScale(Severity.HIGH,25));
            
        scale.setAcknowledgmentIdentifier(acknowledgmentIdentifier);
        
        
        try {
            Thread.sleep(artificialDelay);
        } catch (InterruptedException e) {
            Logger.getRootLogger().error(e);
            Thread.currentThread().interrupt();
            throw new PromiseException(ErrorMap.ERR_CONFIG, e.getMessage(),e);
        }
        
        callbackHandler.doCallback(
                new SimpleEntry<>(CallbackType.SERVER,scale)
        );
        
        return scale;
    }

    @Override
    public PromisableType getPromisableType() {
        return promisableType;
    }

    @Override
    public String getServerIdentifier(){
    
        return this.serverIdentifier;
    }
    

    @Override
    public BusinessPromiseMockResponse invokeWhenComplete(String acknowledgmentIdentifier) throws PromiseException{
           
        try {
            Thread.sleep(artificialDelay);
        } catch (InterruptedException e) {
            Logger.getRootLogger().error(e);
            Thread.currentThread().interrupt();
            throw new PromiseException(ErrorMap.ERR_CONFIG, e.getMessage(),e);
        }
         
        ProgressScale scale = buildProgressScale(promisableType,100);
        scale.setPromiseState(PromiseState.FULFILLED);
        scale.setPromiseStatus(PromiseStatus.COMPLETED);    
        scale.setPriorityScale(new PriorityScale(Priority.HIGH, 25));
        scale.setSeverityScale(new SeverityScale(Severity.HIGH,25));
        
        callbackHandler.doCallback(
                new SimpleEntry<>(CallbackType.SERVER,scale)
        );
        
        Logger.getRootLogger().info("BusinessPromiseServer : invokeWhenComplete : "+new BusinessPromiseMockResponse());
        
        
        return  new BusinessPromiseMockResponse();
    }

    private static double increment(double now) {

        if((artificialNow+now) <= 100) {
            artificialNow+=now;
        }
        
        return artificialNow;
    }

    @Override
    public ReactiveTarget getReactiveTarget() {
        return  this;
    }


    @Override
    public Scale negotiate(RequestScale requestScale) throws PromiseException {
        
        ProgressScale scale = buildProgressScale(promisableType,increment(20.0));
        scale.acceptIfApplicable(requestScale);
        scale.setPromiseState(PromiseState.NEGOTIATED);        
        scale.setPromiseStatus(PromiseStatus.REGISTERED);   
        scale.setPriorityScale(new PriorityScale(Priority.HIGH,75));
        scale.setSeverityScale(new SeverityScale(Severity.LOW,25));
        isNegotiated = true;
               
        callbackHandler.doCallback(
                new SimpleEntry<>(CallbackType.SERVER,scale)
        );
        Logger.getRootLogger().info("BusinessPromiseServer : negotiate : "+scale);
        return scale;
    }


    @Override
    public boolean isNegotiated() {
        return isNegotiated;
    }
}

