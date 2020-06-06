
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.framework.test.promise;


import java.util.Properties;

import org.flowr.framework.core.app.AppBuilder.PromiseConfigBuilder;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.promise.PromiseTypeClient;
import org.framework.fixture.mock.BusinessMock.BusinessMockSettings;
import org.framework.fixture.mock.BusinessMock.BusinessPhasedPromiseMockRequest;
import org.framework.fixture.mock.BusinessMock.BusinessPhasedPromiseMockResponse;
import org.framework.fixture.notification.BusinessClientNotificationAdapter;
import org.framework.fixture.notification.BusinessClientNotificationAdapterTask;
import org.framework.fixture.notification.BusinessServerNotificationAdapter;
import org.framework.fixture.notification.BusinessServerNotificationAdapterTask;
import org.framework.test.FrameworkTest;
import org.framework.test.promise.client.BusinessPhasedPromiseClient;
import org.junit.Test;


public class PhasedPromiseTest extends FrameworkTest{

    @Test
    public void testConfig() throws ConfigurationException{
        
        BusinessPhasedPromiseClient businessPromiseClient = (BusinessPhasedPromiseClient) promiseClient();
        businessPromiseClient.setCallbackHandler(callbackHandler);
        
        app.runWithPromise(businessPromiseClient);
        
        while(!callbackHandler.hasCompleteSequence()) {
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
               
               Thread.currentThread().interrupt();
            }
            
            verifyHandler();
        }
     }
    
    
   private static PromiseTypeClient promiseClient() throws ConfigurationException{
        
        return app.createPromiseTypeClient(
                
             new PromiseConfigBuilder()
                .withPromiseTypeClientAs(new BusinessPhasedPromiseClient())
                .withRequestModelAs(new BusinessPhasedPromiseMockRequest())
                .withResponseModelAs(new BusinessPhasedPromiseMockResponse())
                .withClientNotificationServiceAdapterAs(new BusinessClientNotificationAdapter())
                .withClientAdapterPropertiesAs(new Properties())
                .withClientNotificationTaskAs(new BusinessClientNotificationAdapterTask())
                .withClientTaskTopologyPropertiesAs(new Properties())
                .withClientSubscriptionMapAs(BusinessMockSettings.clientSubscriptionMap())
                .withServerNotificationServiceAdapterAs(new BusinessServerNotificationAdapter())
                .withServerAdapterPropertiesAs(new Properties())
                .withServerNotificationTaskAs(new BusinessServerNotificationAdapterTask())
                .withServerTaskTopologyPropertiesAs(new Properties())
                .withServerSubscriptionMapAs(BusinessMockSettings.serverSubscriptionMap())
                .build()               
                
           );
    }  
   
}

