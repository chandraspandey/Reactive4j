
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.service;

import java.util.ArrayList;
import java.util.List;

import org.flowr.framework.core.service.extension.DefferedPromiseService;
import org.flowr.framework.core.service.extension.MapPromiseService;
import org.flowr.framework.core.service.extension.PhasedPromiseService;
import org.flowr.framework.core.service.extension.PromiseService;
import org.flowr.framework.core.service.extension.ScheduledPromiseService;
import org.flowr.framework.core.service.extension.StagePromiseService;
import org.flowr.framework.core.service.extension.StreamPromiseService;
import org.flowr.framework.core.service.internal.AdministrationService;
import org.flowr.framework.core.service.internal.ConfigurationService;
import org.flowr.framework.core.service.internal.EventService;
import org.flowr.framework.core.service.internal.HighAvailabilityService;
import org.flowr.framework.core.service.internal.ManagedService;
import org.flowr.framework.core.service.internal.NodeService;
import org.flowr.framework.core.service.internal.NotificationService;
import org.flowr.framework.core.service.internal.RegistryService;
import org.flowr.framework.core.service.internal.RoutingService;
import org.flowr.framework.core.service.internal.SecurityService;
import org.flowr.framework.core.service.internal.SubscriptionService;

public interface Catalog {

    EventService getEventService();

    NotificationService getNotificationService();

    PromiseService getPromiseService();
    
    DefferedPromiseService getDefferedPromiseService();

    PhasedPromiseService getPhasedPromiseService();

    ScheduledPromiseService getScheduledPromiseService();

    StagePromiseService getStagedPromiseService();
    
    StreamPromiseService getStreamPromiseService();

    RegistryService getRegistryService();

    RoutingService getRoutingService();

    SubscriptionService getSubscriptionService();

    ManagedService getManagedService();
    
    ConfigurationService getConfigurationService() ;

    AdministrationService getAdministrationService();
    
    SecurityService getSecurityService();

    MapPromiseService getMapPromiseService();
    
    HighAvailabilityService getHighAvailabilityService(); 
    
    NodeService getNodeService();
    
    Catalog postProcess(ServiceFramework serviceFramework);
    
    List<ServiceFrameworkComponent> getServiceList();
    
    List<ServiceFrameworkComponent> getFrameworkServiceList();
    
    public abstract class AbstractFrameworkCatalog implements Catalog{

        private EventService eventService                       = EventService.getInstance(); 
        private NotificationService notificationService         = NotificationService.getInstance();
        private RegistryService registryService                 = RegistryService.getInstance();    
        private RoutingService routingService                   = RoutingService.getInstance();
        private SubscriptionService subscriptionService         = SubscriptionService.getInstance();
        private SecurityService securityService                 = SecurityService.getInstance();
        private ManagedService managedService                   = ManagedService.getInstance();
        private ConfigurationService configService              = ConfigurationService.getInstance();
        private AdministrationService adminService              = AdministrationService.getInstance();
        private HighAvailabilityService highAvailabilityService = HighAvailabilityService.getInstance();
        private NodeService nodeService                         = NodeService.getInstance();
        
        private List<ServiceFrameworkComponent> serviceList     = new ArrayList<>();  
        
        public AbstractFrameworkCatalog() {
            
            this.serviceList.add(eventService);
            this.serviceList.add(notificationService);
            this.serviceList.add(registryService);
            this.serviceList.add(routingService);
            this.serviceList.add(subscriptionService);
            this.serviceList.add(managedService);
            this.serviceList.add(securityService);
            this.serviceList.add(configService);
            this.serviceList.add(adminService);
            this.serviceList.add(highAvailabilityService);
            this.serviceList.add(nodeService);       
        }
        
       @Override
       public Catalog postProcess(ServiceFramework serviceFramework) {
            
            this.serviceList.forEach(
                   
                (ServiceFrameworkComponent s) ->  s.setServiceFramework(serviceFramework)
                     
            );  
            
            return this;
        }
       
       @Override
       public EventService getEventService() {
           return this.eventService;
       }

       @Override
       public NotificationService getNotificationService() {
           return this.notificationService;
       }
       @Override
       public RegistryService getRegistryService() {
           return this.registryService;
       }

       @Override
       public RoutingService getRoutingService() {
           return this.routingService;
       }
       
       @Override
       public SubscriptionService getSubscriptionService() {
           return this.subscriptionService;
       }

       @Override
       public ManagedService getManagedService() {
           return this.managedService;
       }
       
       @Override
       public SecurityService getSecurityService() {
           return this.securityService;
       }

       @Override
       public ConfigurationService getConfigurationService() {
           return this.configService;
       }

       @Override
       public AdministrationService getAdministrationService() {
           return this.adminService;
       }

       @Override
       public HighAvailabilityService getHighAvailabilityService() {
           return this.highAvailabilityService;
       }
       
       @Override
       public NodeService getNodeService() {
           return this.nodeService;
       }
       
       @Override
       public List<ServiceFrameworkComponent> getFrameworkServiceList(){
           
           return this.serviceList;
       }
       
       public String toString(){
           
           StringBuilder builder = new StringBuilder();
           
           serviceList.forEach(
                  
                ( ServiceFrameworkComponent s) -> builder.append("\n"+s.getServiceConfig().getServiceName())   
           );
           
            return "FrameworkCatalog{"+
                    "\n serviceList : "+serviceList.size()+builder.toString()+
                    "\n}\n";
        }
    }

}
