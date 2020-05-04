
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.node.ha;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.Autonomic.ResponseCode;
import org.flowr.framework.core.promise.PromiseRequest;

public class BackPressureExecutorService implements ExecutorService, BackPressure, Failsafe{

    // Chached Thread pool for executing async tasks 
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private Timestamp startTime;
    private long timeout;
    private int retryCount;
    private long retryInterval = 1000;
    private boolean isConfigured;
    
    public void configureBackPressure(BackPressureWindow requestScale){
        
        if(requestScale.getRetryInterval() > 0) {
            this.retryInterval  = requestScale.getRetryInterval();
        }
        this.timeout        = requestScale.getTimeout();
        this.retryCount     = requestScale.getRetryCount(); 
        this.startTime      =  Timestamp.from(Instant.now());
        this.isConfigured   = true;
    }
    
    
    @Override
    public <V> V react(FailsafeCallable<V> callable) throws ConfigurationException{
                        
        V v = null;
                
        Future<V>  future = submit(callable);   
        
        while(!future.isDone()){
            
            if(this.isTimedOut()){
        
                future.cancel(true);                
                
            }else{

                v = retrieveFutureResult(future);
            }
        }   
        
        return v;
    }
    
    private <V> V retrieveFutureResult(Future<V>  future) throws ConfigurationException{
        
        V v = null;
        
        try {
            
            Thread.sleep(retryInterval);
            
            if(future.isDone()){
                
                v = future.get();  
            }
        
        } catch (InterruptedException | ExecutionException e) {
            
            Logger.getRootLogger().error(e);
            Thread.currentThread().interrupt();
            throw new ConfigurationException(ErrorMap.ERR_CONFIG, e);
        }  
        
        return v;
    }
    
    public void resetTimeout(){
        this.startTime      =  Timestamp.from(Instant.now());
    }
    
    @Override
    public boolean isTimedOut(){
        
        boolean isTimedOut = true;
        
        if( ( Timestamp.from(Instant.now()).getTime() - this.startTime.getTime())   > timeout ){
            isTimedOut = true;
        }else{
            isTimedOut = false;
        }
        
        Logger.getRootLogger().info("BackPressureExecutorService : isTimedOut : "+isTimedOut+" | "+timeout+" > "+ 
                ( Timestamp.from(Instant.now()).getTime() - this.startTime.getTime()));
        
        return isTimedOut;
    }
    
    @Override
    public <V> V fallforward(FailsafeCallable<V> callable) throws ConfigurationException {
        
        V v = null;
        
        for(int index = 0; index < retryCount; index++){
                        
            v = react(callable);
            
            if(v != null){  
                break;
            }else{
                resetTimeout();
            }
            Logger.getRootLogger().info("BackPressureExecutorService : fallforward | retryCount : "
                    +index+" | v : "+v);
        }
        
        return v;
    }

    @Override
    public <V> V fallback(FailsafeCallable<V> callable,PromiseRequest promiseRequest,ResponseCode responseCode) {
        
        return callable.handlePromiseError(promiseRequest,responseCode);
    }

    @Override
    public void execute(Runnable command) {
        executorService.execute(command);
    }

    @Override
    public void shutdown() {
        executorService.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return executorService.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return executorService.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return executorService.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return executorService.awaitTermination(timeout, unit);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return executorService.submit(task);
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return executorService.submit(task, result);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return executorService.submit(task);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return executorService.invokeAll(tasks);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
            throws InterruptedException {
        return executorService.invokeAll(tasks, timeout, unit);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return executorService.invokeAny(tasks);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        return executorService.invokeAny(tasks, timeout, unit);
    }

    public boolean isConfigured(){
        
        return this.isConfigured;
    }

}
