
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.framework.test;


import java.util.Optional;

import org.apache.log4j.Logger;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.promise.Scale;
import org.flowr.framework.core.promise.Promise.PromiseState;
import org.flowr.framework.core.promise.Promise.PromiseStatus;
import org.flowr.framework.core.service.ServiceFramework;
import org.framework.test.ConfigurableTest.PromiseCallback.CallbackHandler;
import org.framework.test.app.BusinessApp;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;

public class FrameworkTest implements ConfigurableTest{

    protected static BusinessApp app            = new BusinessApp();
    protected ServiceFramework framework        = ServiceFramework.getInstance();
    protected CallbackHandler callbackHandler   = new CallbackHandler();
   
    
    @BeforeClass
    public static void setup() throws ConfigurationException{
        
        printUsage();
        app.startup(Optional.empty());
    }

    @AfterClass
    public static void teardown() throws ConfigurationException{
 
        app.shutdown(Optional.empty());        

    }
    
    protected void verifyHandler() {
        
        if(callbackHandler.getEntry() != null) {            
                      
            switch(callbackHandler.getEntry().getKey()) {
               
                case CLIENT:
                    verifyClientScale(); 
                    break;
                case SERVER:
                    verifyServerScale(); 
                    break;
                default:
                    break;
            
            }
            
        }
    }


    protected void verifyClientScale() {
        
        Assert.assertTrue("Client Scale should not be null.", (callbackHandler.getEntry().getValue() != null));
        
        Assert.assertTrue("Client Scale PromiseState is not valid.", 
                (callbackHandler.getEntry().getValue().getPromiseState() == PromiseState.FULFILLED));
        Assert.assertTrue("Client Scale PromiseStatus is not valid.", 
                (callbackHandler.getEntry().getValue().getPromiseStatus() == PromiseStatus.COMPLETED));
        
    }
    
    protected void verifyServerScale() {
        
        Scale scale = callbackHandler.getEntry().getValue();
        
        Assert.assertTrue("Server Scale should not be null.", (scale != null)); 
                
        PromiseStatus promiseStatus = scale.getPromiseStatus();
        PromiseState promiseState = scale.getPromiseState();
        
        switch(promiseStatus) {
        
            case COMPLETED:
                Assert.assertTrue("Server Scale Now is not valid.", (scale.getScaleOption().getNow() == 100));
                Assert.assertTrue("Server Scale PromiseState is not valid.", 
                (
                        promiseState == PromiseState.FULFILLED || 
                        promiseState == PromiseState.ERROR ||
                        promiseState == PromiseState.TIMEOUT
                ));
                break;
            case INITIATED:
                Assert.assertTrue("Server Scale Now is not valid.", (scale.getScaleOption().getNow() > 0));
                Assert.assertTrue("Server Scale PromiseState is not valid.", 
                        (promiseState == PromiseState.ACKNOWLEDGED));
                break;
            case PROCESSING:
                Assert.assertTrue("Server Scale Now is not valid.", (scale.getScaleOption().getNow() > 0));
                Assert.assertTrue("Server Scale PromiseState is not valid.", 
                        (promiseState == PromiseState.ASSURED));
                break;
            case REGISTERED:
                Assert.assertTrue("Server Scale Now is not valid.", (scale.getScaleOption().getNow() > 0));
                Assert.assertTrue("Server Scale PromiseState is not valid.", 
                        (promiseState == PromiseState.NEGOTIATED));
                break;
            default:
                break;
        
        }        
         
    }
    
    protected static void printUsage() {
        
        Logger.getRootLogger().info("USAGE : Reguires FRAMEWORK_CONFIG_PATH as key for loading flowr.xml"
                + " in classpath settings for running.");
        Logger.getRootLogger().info("USAGE : It can be provided as part of VM of program arguments for execution.");
        Logger.getRootLogger().info("USAGE : -D<property-name>=<value> i.e. "
                + "-DFRAMEWORK_CONFIG_PATH=<Install_HOME>/../../flowr.xml");
    }
}
