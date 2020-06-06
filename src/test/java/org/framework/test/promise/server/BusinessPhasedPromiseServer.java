
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */


package org.framework.test.promise.server;

import java.nio.charset.Charset;
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
import org.flowr.framework.core.promise.phase.PhasedProgressScale;
import org.flowr.framework.core.promise.phase.PhasedService.ServicePhase;
import org.flowr.framework.core.service.ServiceFramework;
import org.flowr.framework.core.target.ReactiveTarget;
import org.framework.fixture.mock.BusinessMock.BusinessMockChunkedResponse;
import org.framework.fixture.mock.BusinessMock.BusinessPhasedPromiseMockResponse;
import org.framework.test.ConfigurableTest.PromiseCallback.CallbackHandler;
import org.framework.test.ConfigurableTest.PromiseCallback.CallbackType;

public final class BusinessPhasedPromiseServer implements PromiseTypeServer{

    private static double artificialNow;
    private UUID acknowledgementId                      = UUID.randomUUID();    
    private long artificialDelay                        = 3000; 
    private String serverIdentifier                     = PromiseTypeServer.serverIdentifier();
    private PromisableType promisableType               = PromisableType.PROMISE_PHASED;
    private PhasedProgressScale phasedProgressScale;
    private boolean isNegotiated;
    private static CallbackHandler callbackHandler;
    
    public static void setCallbackHandler(CallbackHandler handler) {
        
        callbackHandler = handler;
        Logger.getRootLogger().info("BusinessPromiseServer : setCallbackHandler : "+callbackHandler);
    }
    
    @Override
    public PromisableType getPromisableType() {
        return promisableType;
    }
    
    @Override
    public Scale buildProgressScale(PromisableType promisableType,double now) throws PromiseException{
        
        if(phasedProgressScale == null){
        
            try {
                phasedProgressScale =  (PhasedProgressScale) ServiceFramework.getInstance().getCatalog()
                                        .getConfigurationService().getProgressScale(promisableType, serverIdentifier);
                
            } catch (ConfigurationException e) {
                
                Logger.getRootLogger().error(e);
                Thread.currentThread().interrupt();
                throw new PromiseException(ErrorMap.ERR_CONFIG, e.getMessage(),e);
            }       
        }
        
        phasedProgressScale.getScaleOption().setNow(now);
        
        Logger.getRootLogger().info("BusinessPhasedPromiseServer : "+phasedProgressScale);
        
        return phasedProgressScale;
    }
    
    @Override
    public Scale invokeAndReturn(RequestModel request, RequestScale requestScale) throws PromiseException {
        
        PhasedProgressScale scale = (PhasedProgressScale) buildProgressScale(promisableType,increment(20.0));
        
        scale.setPromiseState(PromiseState.ACKNOWLEDGED);
        scale.setPromiseStatus(PromiseStatus.INITIATED);
        scale.setAcknowledgmentIdentifier(acknowledgementId.toString());
        scale.setServicePhase(ServicePhase.DISCOVERY); 
        scale.setSubscriptionClientId(requestScale.getSubscriptionClientId());
        scale.setPriorityScale(new PriorityScale(Priority.HIGH, 75));
        scale.setSeverityScale(new SeverityScale(Severity.LOW,25));
                
        try {
            Thread.sleep(artificialDelay);
        } catch (InterruptedException e) {
            Logger.getRootLogger().error(e);
            Thread.currentThread().interrupt();
            throw new PromiseException(ErrorMap.ERR_CONFIG, e.getMessage(),e);
        }       
                
        Logger.getRootLogger().info("BusinessPhasedPromiseServer : invokeAndReturn : "+scale);
        
        callbackHandler.doCallback(
                new SimpleEntry<>(CallbackType.SERVER,scale)
        );
        
        return scale;
    }
    
    private static double increment(double now) {

        if((artificialNow+now) <= 100) {
            artificialNow+=now;
        }
        return artificialNow;
    }
    
    @Override
    public Scale invokeForProgress(String acknowledgmentIdentifier) throws PromiseException {
        
        
        BusinessMockChunkedResponse chunkBuffer     = new BusinessMockChunkedResponse();
        
        for(double index = artificialNow; index < artificialNow+20 ; index++){
            String val = "10.101.241."+((int)index)+",";
            chunkBuffer.appendChunk(val.getBytes(Charset.defaultCharset()) );
        }
                
        
        PhasedProgressScale scale = (PhasedProgressScale)
                    buildProgressScale(promisableType,increment(20.0)); 
            
        scale.setChunkBuffer(chunkBuffer);
        
        if(artificialNow < 100.0){
            
            scale.setPromiseState(PromiseState.ASSURED);
            scale.setPromiseStatus(PromiseStatus.PROCESSING);
            scale.setServicePhase(ServicePhase.AGGREGATION); 
            scale.setPriorityScale(new PriorityScale(Priority.LOW, 25));
            scale.setSeverityScale(new SeverityScale(Severity.LOW,25));
            
            callbackHandler.doCallback( new SimpleEntry<>(CallbackType.SERVER,scale));
        }else{
            scale.setPromiseState(PromiseState.FULFILLED);
            scale.setPromiseStatus(PromiseStatus.COMPLETED);    
            scale.setServicePhase(ServicePhase.COMPLETE); 
            scale.setPriorityScale(new PriorityScale(Priority.HIGH, 25));
            scale.setSeverityScale(new SeverityScale(Severity.LOW,25));
        }
        
        
        try {
            Thread.sleep(artificialDelay);
        } catch (InterruptedException e) {
            Logger.getRootLogger().error(e);
            Thread.currentThread().interrupt();
            throw new PromiseException(ErrorMap.ERR_CONFIG, e.getMessage(),e);
        }
        
        scale.setAcknowledgmentIdentifier(acknowledgmentIdentifier);
        
        Logger.getRootLogger().info("BusinessPhasedPromiseServer : invokeForProgress : "+scale);
        

        
        return scale;
    }
    
    @Override
    public String getServerIdentifier(){
    
        return this.serverIdentifier;
    }
    

    @Override
    public BusinessPhasedPromiseMockResponse invokeWhenComplete(String acknowledgmentIdentifier) 
            throws PromiseException{
        
        try {
            Thread.sleep(artificialDelay);
        } catch (InterruptedException e) {
            Logger.getRootLogger().error(e);
            Thread.currentThread().interrupt();
            throw new PromiseException(ErrorMap.ERR_CONFIG, e.getMessage(),e);
        }
        
        PhasedProgressScale scale = (PhasedProgressScale) buildProgressScale(promisableType,100);
        scale.setPromiseState(PromiseState.FULFILLED);
        scale.setPromiseStatus(PromiseStatus.COMPLETED);    
        scale.setPriorityScale(new PriorityScale(Priority.HIGH, 25));
        scale.setSeverityScale(new SeverityScale(Severity.HIGH,25));
        
        callbackHandler.doCallback(
                new SimpleEntry<>(CallbackType.SERVER,scale)
        );
        
        Logger.getRootLogger().info("BusinessPhasedPromiseServer : invokeWhenComplete : "+
                new BusinessPhasedPromiseMockResponse());
        
        return new BusinessPhasedPromiseMockResponse();
    }

    @Override
    public ReactiveTarget getReactiveTarget() {
        return this;
    }


    @Override
    public Scale negotiate(RequestScale requestScale) throws PromiseException {
        
        PhasedProgressScale scale = (PhasedProgressScale) 
                buildProgressScale(promisableType,increment(20.0));
        scale.acceptIfApplicable(requestScale);
        scale.setPromiseState(PromiseState.NEGOTIATED);        
        scale.setPromiseStatus(PromiseStatus.REGISTERED);
        scale.setPriorityScale(new PriorityScale(Priority.HIGH, 100));
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

