
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.node.io.pipeline;

import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.flowr.framework.core.service.Service.ServiceStatus;

public class NetworkPipelineBusExecutor implements NetworkBusExecutor {

    private ExecutorService service     = Executors.newCachedThreadPool();
    private ServiceStatus serviceStatus = ServiceStatus.UNUSED;
    private NetworkBus  networkBus;
    
    public NetworkPipelineBusExecutor(NetworkBus networkBus) {
        this.networkBus = networkBus; 
    }

    @Override
    public ServiceStatus startup(Optional<Properties> configProperties) {
        
        service.execute(this);
        this.serviceStatus = ServiceStatus.STARTED;
        return serviceStatus;
    }

    @Override
    public ServiceStatus shutdown(Optional<Properties> configProperties) {
        service.shutdown();
        this.serviceStatus = ServiceStatus.STOPPED;
        return serviceStatus;   
    }

    @Override
    public void process() {
                
        networkBus.execute();
    }
    
    @Override
    public void run() {
        
        while(serviceStatus != ServiceStatus.STOPPED) {
            
            try {
                
                process();
                
                Thread.sleep(1000);
            } catch ( InterruptedException e) {
                Logger.getRootLogger().error(e);
                Thread.currentThread().interrupt();
            }
        }
    }

    
}
