
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.framework.test;


import java.util.Arrays;
import java.util.Map;

import org.flowr.framework.api.Provider;
import org.flowr.framework.api.Provider.ProviderType;
import org.flowr.framework.core.constants.Constant.FrameworkConstants;
import org.flowr.framework.core.service.Catalog;
import org.flowr.framework.core.service.FrameworkService;
import org.flowr.framework.core.service.Service;
import org.flowr.framework.core.service.Service.ServiceType;
import org.flowr.framework.core.service.ServiceFrameworkComponent;
import org.junit.Assert;
import org.junit.Test;

public class FrameworkServiceTest extends FrameworkTest{

    @Test
    public void testCatalog(){
        
        Catalog catalog = framework.getCatalog();
        
        Assert.assertTrue("Catalog should not be null.", (catalog != null)); 
    
        catalog.getFrameworkServiceList().forEach(
             
                (ServiceFrameworkComponent s) ->
                    Assert.assertTrue("Framework Service/ServiceFrameworkComponent should not be null.", (s != null))
        );
        
        catalog.getServiceList().forEach(
                 
                (ServiceFrameworkComponent s) ->
                    Assert.assertTrue("Framework Service/ServiceFrameworkComponent should not be null.", (s != null))
        );      
    }
    
    @Test
    public void testAdministrationService(){  
        
        Assert.assertTrue("Framework Service for Type : "+ServiceType.ADMINISTRATION+" should not be null.", 
                (framework.lookup(ServiceType.ADMINISTRATION) != null));
        Assert.assertTrue("Framework Service for name : "+FrameworkConstants.FRAMEWORK_SERVICE_ADMINISTRATION+
                " should not be null.", 
          (framework.lookup(ServiceType.ADMINISTRATION, FrameworkConstants.FRAMEWORK_SERVICE_ADMINISTRATION) != null));
    }
    
   @Test
   public void testAPIService(){  
        
        Assert.assertTrue("Framework Service for Type : "+ServiceType.API+" should be null.", 
                (framework.lookup(ServiceType.API) == null));
        Assert.assertTrue("Framework Service for name : "+ServiceType.API.name()+
                " should be null.", 
          (framework.lookup(ServiceType.ADMINISTRATION, ServiceType.API.name()) == null));
    }
   
   @Test
   public void testBusService(){  
        
        Assert.assertTrue("Framework Service for Type : "+ServiceType.BUS+" should be null.", 
                (framework.lookup(ServiceType.BUS) == null));
        Assert.assertTrue("Framework Service for name : "+ServiceType.BUS+" should be null.", 
          (framework.lookup(ServiceType.BUS, ServiceType.BUS.name()) == null));
    }
   
   @Test
   public void testConfigurationService(){  
       
       Assert.assertTrue("Framework Service for Type : "+ServiceType.EVENT+" should not be null.", 
               (framework.lookup(ServiceType.CONFIGURATION) != null));
       Assert.assertTrue("Framework Service for name : "+FrameworkConstants.FRAMEWORK_SERVICE_CONFIGURATION+
               " should not be null.", 
         (framework.lookup(ServiceType.CONFIGURATION, FrameworkConstants.FRAMEWORK_SERVICE_CONFIGURATION) != null));
   }
   
   @Test
   public void testEventService(){  
       
       Assert.assertTrue("Framework Service for Type : "+ServiceType.EVENT+" should not be null.", 
               (framework.lookup(ServiceType.EVENT) != null));
       Assert.assertTrue("Framework Service for name : "+FrameworkConstants.FRAMEWORK_SERVICE_EVENT+
               " should not be null.", 
         (framework.lookup(ServiceType.EVENT, FrameworkConstants.FRAMEWORK_SERVICE_EVENT) != null));
   }
   
   @Test
   public void testFrameworkService(){  
        
        Assert.assertTrue("Framework Service for Type : "+ServiceType.FRAMEWORK+" should be null.", 
                (framework.lookup(ServiceType.FRAMEWORK) == null));
        Assert.assertTrue("Framework Service for name : "+ServiceType.FRAMEWORK+" should be null.", 
          (framework.lookup(ServiceType.FRAMEWORK, ServiceType.FRAMEWORK.name()) == null));
    }
    
   @Test
   public void testHealthService(){  
       
       Assert.assertTrue("Framework Service for Type : "+ServiceType.HEALTH+" should not be null.", 
               (framework.lookup(ServiceType.HEALTH) != null));
       Assert.assertTrue("Framework Service for name : "+FrameworkConstants.FRAMEWORK_SERVICE_HEALTH+
               " should not be null.", 
         (framework.lookup(ServiceType.HEALTH, FrameworkConstants.FRAMEWORK_SERVICE_HEALTH) != null));
   }
   
   @Test
   public void testManagementService(){  
       
       Assert.assertTrue("Framework Service for Type : "+ServiceType.MANAGEMENT+" should not be null.", 
               (framework.lookup(ServiceType.MANAGEMENT) != null));
       Assert.assertTrue("Framework Service for name : "+FrameworkConstants.FRAMEWORK_SERVICE_MANAGEMENT+
               " should not be null.", 
         (framework.lookup(ServiceType.MANAGEMENT, FrameworkConstants.FRAMEWORK_SERVICE_MANAGEMENT) != null));
   }
   
   @Test
   public void testNodeService(){  
       
       Assert.assertTrue("Framework Service for Type : "+ServiceType.NODE+" should not be null.", 
               (framework.lookup(ServiceType.NODE) != null));
       Assert.assertTrue("Framework Service for name : "+FrameworkConstants.FRAMEWORK_SERVICE_NODE+
               " should not be null.", 
         (framework.lookup(ServiceType.NODE, FrameworkConstants.FRAMEWORK_SERVICE_NODE) != null));
   }
   
   @Test
   public void testNetworkService(){  
        
        Assert.assertTrue("Framework Service for Type : "+ServiceType.NETWORK+" should be null.", 
                (framework.lookup(ServiceType.NETWORK) == null));
        Assert.assertTrue("Framework Service for name : "+ServiceType.NETWORK+" should be null.", 
          (framework.lookup(ServiceType.NETWORK, ServiceType.NETWORK.name()) == null));
    }
   
   @Test
   public void testNotificationService(){  
       
       Assert.assertTrue("Framework Service for Type : "+ServiceType.NOTIFICATION+" should not be null.", 
               (framework.lookup(ServiceType.NOTIFICATION) != null));
       Assert.assertTrue("Framework Service for name : "+FrameworkConstants.FRAMEWORK_SERVICE_NOTIFICATION+
               " should not be null.", 
         (framework.lookup(ServiceType.NOTIFICATION, FrameworkConstants.FRAMEWORK_SERVICE_NOTIFICATION) != null));
   }
   
   @Test
   public void testProcessService(){  
        
        Assert.assertTrue("Framework Service for Type : "+ServiceType.PROCESS+" should be null.", 
                (framework.lookup(ServiceType.PROCESS) == null));
        Assert.assertTrue("Framework Service for name : "+ServiceType.PROCESS+" should be null.", 
          (framework.lookup(ServiceType.PROCESS, ServiceType.PROCESS.name()) == null));
   }
   
   @Test
   public void testPromiseService(){  
       
       Assert.assertTrue("Framework Service for Type : "+ServiceType.PROMISE+" should not be null.", 
               (framework.lookup(ServiceType.PROMISE) != null));
       Assert.assertTrue("Framework Service for name : "+FrameworkConstants.FRAMEWORK_SERVICE_PROMISE+
               " should not be null.", 
         (framework.lookup(ServiceType.PROMISE, FrameworkConstants.FRAMEWORK_SERVICE_PROMISE) != null));
   }
   
   @Test
   public void testDefferedPromiseService(){  
       
       Assert.assertTrue("Framework Service for Type : "+ServiceType.PROMISE_DEFFERED+" should not be null.", 
               (framework.lookup(ServiceType.PROMISE_DEFFERED) != null));
       Assert.assertTrue("Framework Service for name : "+FrameworkConstants.FRAMEWORK_SERVICE_PROMISE_DEFFERED+
               " should not be null.", 
         (framework.lookup(ServiceType.PROMISE_DEFFERED, 
                 FrameworkConstants.FRAMEWORK_SERVICE_PROMISE_DEFFERED) != null));
   }
   
   @Test
   public void testMapPromiseService(){  
       
       Assert.assertTrue("Framework Service for Type : "+ServiceType.PROMISE_MAP+" should not be null.", 
               (framework.lookup(ServiceType.PROMISE_MAP) != null));
       Assert.assertTrue("Framework Service for name : "+FrameworkConstants.FRAMEWORK_SERVICE_PROMISE_MAP+
               " should not be null.", 
         (framework.lookup(ServiceType.PROMISE_MAP, FrameworkConstants.FRAMEWORK_SERVICE_PROMISE_MAP) != null));
   }
   
   @Test
   public void testPhasedPromiseService(){  
       
       Assert.assertTrue("Framework Service for Type : "+ServiceType.PROMISE_PHASED+" should not be null.", 
               (framework.lookup(ServiceType.PROMISE_PHASED) != null));
       Assert.assertTrue("Framework Service for name : "+FrameworkConstants.FRAMEWORK_SERVICE_PROMISE_PHASED+
               " should not be null.", 
         (framework.lookup(ServiceType.PROMISE_PHASED, FrameworkConstants.FRAMEWORK_SERVICE_PROMISE_PHASED) != null));
   }
   
   
   @Test
   public void testScheduledPromiseService(){  
       
       Assert.assertTrue("Framework Service for Type : "+ServiceType.PROMISE_SCHEDULED+" should not be null.", 
               (framework.lookup(ServiceType.PROMISE_SCHEDULED) != null));
       Assert.assertTrue("Framework Service for name : "+FrameworkConstants.FRAMEWORK_SERVICE_PROMISE_SCHEDULED+
               " should not be null.", 
         (framework.lookup(ServiceType.PROMISE_SCHEDULED, FrameworkConstants.FRAMEWORK_SERVICE_PROMISE_SCHEDULED)
                 != null));
   }
   
   @Test
   public void testStagePromiseService(){  
       
       Assert.assertTrue("Framework Service for Type : "+ServiceType.PROMISE_STAGE+" should not be null.", 
               (framework.lookup(ServiceType.PROMISE_STAGE) != null));
       Assert.assertTrue("Framework Service for name : "+FrameworkConstants.FRAMEWORK_SERVICE_PROMISE_STAGE+
               " should not be null.", 
         (framework.lookup(ServiceType.PROMISE_STAGE, FrameworkConstants.FRAMEWORK_SERVICE_PROMISE_STAGE)
                 != null));
   }
   
   @Test
   public void testStreamPromiseService(){  
       
       Assert.assertTrue("Framework Service for Type : "+ServiceType.PROMISE_STREAM+" should  be null.", 
               (framework.lookup(ServiceType.PROMISE_STREAM) != null));
       
       Assert.assertTrue("Framework Service for name : "+FrameworkConstants.FRAMEWORK_SERVICE_PROMISE_STREAM+
               " should not be null.", 
         (framework.lookup(ServiceType.PROMISE_STREAM, FrameworkConstants.FRAMEWORK_SERVICE_PROMISE_STREAM) != null));
   }
   
   @Test
   public void testProviderService(){  
        
        Assert.assertTrue("Framework Service for Type : "+ServiceType.PROVIDER+" should be null.", 
                (framework.lookup(ServiceType.PROVIDER) == null));
        Assert.assertTrue("Framework Service for name : "+ServiceType.PROVIDER+" should be null.", 
          (framework.lookup(ServiceType.PROVIDER, FrameworkConstants.FRAMEWORK_SERVICE_PROVIDER) == null));
   }
   
   @Test
   public void testRegistryService(){  
        
        Assert.assertTrue("Framework Service for Type : "+ServiceType.REGISTRY+" should not be null.", 
                (framework.lookup(ServiceType.REGISTRY) != null));
        Assert.assertTrue("Framework Service for name : "+ServiceType.REGISTRY+" should not be null.", 
          (framework.lookup(ServiceType.REGISTRY, FrameworkConstants.FRAMEWORK_SERVICE_REGISTRY) != null));
   }
   
   @Test
   public void testRoutingService(){  
        
        Assert.assertTrue("Framework Service for Type : "+ServiceType.ROUTING+" should not be null.", 
                (framework.lookup(ServiceType.ROUTING) != null));
        Assert.assertTrue("Framework Service for name : "+ServiceType.ROUTING+" should not be null.", 
          (framework.lookup(ServiceType.ROUTING, FrameworkConstants.FRAMEWORK_SERVICE_ROUTING) != null));
   }
   
   @Test
   public void testSecurityService(){  
        
        Assert.assertTrue("Framework Service for Type : "+ServiceType.SECURITY+" should not be null.", 
                (framework.lookup(ServiceType.SECURITY) != null));
        Assert.assertTrue("Framework Service for name : "+ServiceType.SECURITY+" should not be null.", 
          (framework.lookup(ServiceType.SECURITY, FrameworkConstants.FRAMEWORK_SERVICE_SECURITY) != null));
   }
   
   @Test
   public void testServerService(){  
        
        Assert.assertTrue("Framework Service for Type : "+ServiceType.SERVER+" should be null.", 
                (framework.lookup(ServiceType.SERVER) == null));
        Assert.assertTrue("Framework Service for name : "+ServiceType.SERVER+" should be null.", 
          (framework.lookup(ServiceType.SERVER, ServiceType.SERVER.name()) == null));
   }
   
   @Test
   public void testSubscriptionService(){  
        
        Assert.assertTrue("Framework Service for Type : "+ServiceType.SUBSCRIPTION+" should not be null.", 
                (framework.lookup(ServiceType.SUBSCRIPTION) != null));
        Assert.assertTrue("Framework Service for name : "+ServiceType.SUBSCRIPTION+" should not be null.", 
          (framework.lookup(ServiceType.SUBSCRIPTION, FrameworkConstants.FRAMEWORK_SERVICE_SUBSCRIPTION) != null));
   }
   
   
    @Test
    public void testProviders(){
        
        Service service = framework.lookup(ServiceType.CONFIGURATION);
        
        if(service != null) {
            
            Map<ProviderType, Provider> map = ((FrameworkService)service).getProviderMap();
            
            Assert.assertTrue("Framework Service for Type : "+ServiceType.CONFIGURATION+" should not be null.", 
                    ((map != null) && !map.isEmpty()));
            
            Arrays.asList(ProviderType.values()).forEach(
                    
                    (ProviderType p) ->{ 
                        
                        if(p == ProviderType.CLIENT || p == ProviderType.FRAMEWORK || p == ProviderType.SERVER) {
                            
                            Assert.assertTrue("Provider for Type : "+p+" should be null.",
                                    (map.get(p) == null));
                        }else {
                            Assert.assertTrue("Provider for Type : "+p+" should not be null.",
                                    (map.get(p) != null));       
                        }                        
                       
                    }
            );
            
        }else {
            Assert.assertTrue("Framework Service for Type : "+ServiceType.CONFIGURATION+" should not be null.", 
                    (service != null));
        }
    }
    
       @Test
       public void testNodeProcessHandler(){
            
            Service service = framework.lookup(ServiceType.CONFIGURATION);
            
            if(service != null) {
                
                Assert.assertTrue("NodeProcessHandler should not be null .",
                        ((FrameworkService)service).getNodeProcessHandler() != null);
               
                /*
                try {
                    Process process = ((FrameworkService)service).getNodeProcessHandler().lookupProcess("java");
                    
                    Assert.assertTrue("NodeProcessHandler should not be null .", process != null);
                    
                    Assert.assertTrue("NodeProcessHandler InputStream should not be null .",
                            process.getInputStream() != null);
                    
                    Assert.assertTrue("NodeProcessHandler OutputStream should not be null .",
                            process.getOutputStream() != null);
                    
                    Assert.assertTrue("NodeProcessHandler ErrorStream should not be null .",
                            process.getErrorStream() != null);
                } catch (ConfigurationException e) {
                     Logger.getRootLogger().error( e);
                }*/
                
            }else {
                Assert.assertTrue("Framework Service for Type : "+ServiceType.CONFIGURATION+" should not be null.", 
                        (service != null));
            }
        }
       
       @Test
       public void testManagedProcessHandler(){
            
            Service service = framework.lookup(ServiceType.CONFIGURATION);
            
            if(service != null) {
                
                Assert.assertTrue("ManagedProcessHandler should not be null .",
                        ((FrameworkService)service).getManagedProcessHandler() != null);                
                
                
            }else {
                Assert.assertTrue("Framework Service for Type : "+ServiceType.CONFIGURATION+" should not be null.", 
                        (service != null));
            }
        }


}
