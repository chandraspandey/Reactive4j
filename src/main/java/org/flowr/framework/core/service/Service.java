
/**
 * Marker interface for service definition, concrete implementation provides the behavior for service operation.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.service;

import org.flowr.framework.core.service.dependency.Dependency;

public interface Service extends ServiceLifecycle{
    
    public enum ServiceType{        
        ADMINISTRATION,
        BUS,
        CONFIGURATION,
        EVENT,
        FRAMEWORK,
        NOTIFICATION,
        PROMISE,
        HEALTH,
        PROMISE_PHASED,
        PROMISE_SCHEDULED,
        PROMISE_STAGE,
        PROMISE_MAP,
        PROMISE_STREAM,
        PROVIDER,
        PROCESS,
        REGISTRY,
        ROUTING,
        SERVER,
        NODE,
        MANAGEMENT,
        SUBSCRIPTION,
        SECURITY,
        API,
        NETWORK
    }
        
    public enum ServiceState{
        INITIALIZING,
        STARTING,
        STOPPING
    }
    
    public enum ServiceStatus{
        UNUSED,
        STARTED,
        SUSPENDED,
        RUNNING,
        RESUMED,
        STOPPED,
        ERROR
    }
    
    public enum ServiceMode{
        EMBEDDED,
        CONTAINER,
        SERVER
    }
    
    public enum ServiceUnit{
        SINGELTON,
        POOL,
        REGISTRY,
        PROVIDER,
        BUS
    }
    
    ServiceConfig getServiceConfig();
    
    public class ServiceConfig implements Dependency{
        
        private boolean isEnabled; 
        private String dependencyName;
        private DependencyType dependencyType;
        private ServiceUnit serviceUnit;
        private String serviceName;
        private ServiceType serviceType;
        private ServiceStatus serviceStatus;
     
        public ServiceConfig(boolean isEnabled, ServiceUnit serviceUnit, String serviceName, ServiceType serviceType,
                 ServiceStatus serviceStatus,String dependencyName, DependencyType dependencyType
                ) {

            this.isEnabled      = isEnabled;
            this.serviceUnit    = serviceUnit;
            this.serviceName    = serviceName;
            this.serviceType    = serviceType;
            this.serviceStatus  = serviceStatus;
            this.dependencyName = dependencyName;
            this.dependencyType = dependencyType;
            
        }

        public ServiceUnit getServiceUnit() {
            return serviceUnit;
        }

        public DependencyType getDependencyType() {
            return dependencyType;
        }

        public String getServiceName() {
            return serviceName;
        }

        public ServiceType getServiceType() {
            return serviceType;
        }

        public String getDependencyName() {
            return dependencyName;
        }

        public boolean isEnabled() {
            return isEnabled;
        }
        
        public ServiceStatus getServiceStatus() {
            return serviceStatus;
        }

        public void setServiceStatus(ServiceStatus serviceStatus) {
            this.serviceStatus = serviceStatus;
        }
        
        public void setEnabled(boolean isEnabled) {
            this.isEnabled = isEnabled;
        }
        
        public String toString(){
            
            return "\n\t ServiceConfig{"+
                    "\n\t isEnabled : "+isEnabled+
                    "\n\t serviceUnit : "+serviceUnit+
                    "\n\t serviceType : "+serviceType+
                    "\n\t serviceName : "+serviceName+      
                    "\n\t serviceStatus : "+serviceStatus+
                    "\n\t dependencyType : "+dependencyType+
                    "\n\t dependencyName : "+dependencyName+
                    "\n\t}";
        } 
    }
    
}
