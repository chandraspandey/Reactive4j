
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */


package org.framework.test;

import java.util.AbstractMap.SimpleEntry;

import org.flowr.framework.core.promise.Scale;
import org.flowr.framework.core.promise.Promise.PromiseState;
import org.flowr.framework.core.promise.Promise.PromiseStatus;

public interface ConfigurableTest {

    public interface PromiseCallback<T, R>{
        
        public enum CallbackType{
            CLIENT,
            SERVER
        }
        
        void doCallback(SimpleEntry<T,R> callbackEntry);
        SimpleEntry<PromiseCallback.CallbackType,Scale> getEntry();
        boolean hasCompleteSequence();
        
        class CallbackHandler implements PromiseCallback<PromiseCallback.CallbackType,Scale>{
            
            private SimpleEntry<PromiseCallback.CallbackType,Scale> callbackEntry;
            private boolean hasCompleteSequence;
            
            @Override
            public void doCallback(SimpleEntry<PromiseCallback.CallbackType,Scale> callbackEntry) {

                this.callbackEntry = callbackEntry;
                
                if(callbackEntry.getValue().getPromiseStatus() == PromiseStatus.COMPLETED &&
                    (  
                            callbackEntry.getValue().getPromiseState() == PromiseState.FULFILLED ||
                            callbackEntry.getValue().getPromiseState() == PromiseState.ERROR ||
                            callbackEntry.getValue().getPromiseState() == PromiseState.TIMEOUT
                    )) {
                    
                    this.hasCompleteSequence = true;
                }
            }
            
            @Override
            public SimpleEntry<PromiseCallback.CallbackType,Scale> getEntry(){
                
                return this.callbackEntry;
            }
            
            @Override
            public boolean hasCompleteSequence() {
                
                return this.hasCompleteSequence;
            }
        }
    }
}

