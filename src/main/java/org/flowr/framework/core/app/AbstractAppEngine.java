
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.app;

import java.nio.charset.Charset;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.flowr.framework.core.app.AppBuilder.AppEngineBuilder;
import org.flowr.framework.core.config.Configuration.ConfigurationType;
import org.flowr.framework.core.constants.Constant.FrameworkConstants;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.NodeManager.NodePipelineConfig;
import org.flowr.framework.core.node.io.endpoint.NodePipelineClient;
import org.flowr.framework.core.node.io.endpoint.NodePipelineServer;
import org.flowr.framework.core.node.io.network.ServiceMesh.ServiceTopologyMode;
import org.flowr.framework.core.promise.Promise.PromiseConfig;
import org.flowr.framework.core.promise.PromiseTypeClient;
import org.flowr.framework.core.service.Service.ServiceStatus;
import org.flowr.framework.core.service.ServiceFramework;
import org.flowr.framework.core.service.internal.ConfigurationService;

public abstract class AbstractAppEngine implements AppEngineBuilder{

    private ExecutorService executorService = Executors.newCachedThreadPool();
    protected AppNodePipelineBus bus        = new AppNodePipelineBus();
    
    @Override
    public void configure() throws ConfigurationException {
               
        bus.registerMetricMapperHandler();
    }
    
    @Override
    public PromiseTypeClient createPromiseTypeClient(PromiseConfig promiseConfig) throws ConfigurationException{

        return   
            AppBuilder.getPromiseBuilder()
            .withPromiseTypeClientAs(promiseConfig.getPromiseTypeClient())
            .withOperationConfigAs(
                    
                AppBuilder.getConfigBuilder()
                .withRequestModelAs(promiseConfig.getRequestModel())
                .withResponseModelAs(promiseConfig.getResponseModel())
                .withClientNotificationConfigAs(
                        
                    AppBuilder.getNotificationConfigBuilder()
                        .withNotificationAdapterAs(
                                
                            AppBuilder.getNotificationAdapterBuilder()
                            .withNotificationServiceAdapterAs(promiseConfig.getClientAdapter())
                            .withRuntimePropertiesAs(promiseConfig.getClientAdapterProperties())
                            .build()
                         )
                        .withSubscriptionMapAs(promiseConfig.getClientSubscriptionMap())
                        .withNotificationTaskAs(
                                AppBuilder.getNotificationTaskBuilder()
                                .withNotificationTaskAs(promiseConfig.getClientNotificationTask())
                                .withServiceTopologyModeAs(ServiceTopologyMode.DISTRIBUTED)
                                .withTopologyPropertiesAs(promiseConfig.getClientTaskTopologyProperties())
                                .build()
                        ).build()       
                        
                )
                .withServerNotificationConfigAs(
                       
                        AppBuilder.getNotificationConfigBuilder()
                        .withNotificationAdapterAs(
                                
                            AppBuilder.getNotificationAdapterBuilder()
                            .withNotificationServiceAdapterAs(promiseConfig.getServerAdapter())
                            .withRuntimePropertiesAs(promiseConfig.getServerAdapterProperties())
                            .build()
                         )
                        .withSubscriptionMapAs(promiseConfig.getServerSubscriptionMap())
                        .withNotificationTaskAs(
                                
                                AppBuilder.getNotificationTaskBuilder()
                                .withNotificationTaskAs(promiseConfig.getServerNotificationTask())
                                .withServiceTopologyModeAs(ServiceTopologyMode.LOCAL)
                                .withTopologyPropertiesAs(promiseConfig.getServerTaskTopologyProperties())
                                .build()
                        ).build()          
                        
                ).build()
                    
            ).build();                                        

    }
    
    @Override   
    public SimpleEntry<NodePipelineServer,NodePipelineClient> configurePipeline( 
          SimpleEntry<NodePipelineConfig,NodePipelineConfig> serverClientConfigEntry) throws ConfigurationException {
 
        SimpleEntry<NodePipelineServer,NodePipelineClient> entrySet = null;
        
        SimpleEntry<NodePipelineServer,List<NodePipelineClient>> entry = bus.configure(serverClientConfigEntry);
        
        if(entry != null) {
        
            NodePipelineServer server = entry.getKey();      
            
            server.init(serverClientConfigEntry.getKey().getIdentityConfig());
            
            List<NodePipelineClient> clientList = entry.getValue();
            
            for(int index = 0;index < clientList.size(); index++) {
                
                NodePipelineClient client = clientList.get(index);
                client.init(serverClientConfigEntry.getValue().getIdentityConfig());
                
                if(index == 0) {
                    
                    SimpleEntry<Thread, Thread> serverThreadHooks = bus.startInboundNetworkPipeline(server, client);
                    
                    
                    bus.clientToServer(FrameworkConstants.FRAMEWORK_NETWORK_IO_PING.getBytes(
                            Charset.defaultCharset()), client,server);
                    
                    bus.addToNetworkThreadPool(serverThreadHooks);
                    
                    SimpleEntry<Thread, Thread> clientThreadHooks = bus.startOutboundNetworkPipeline(
                            client,server );
            
                    bus.addToNetworkThreadPool(clientThreadHooks);
                    
                    entrySet = new SimpleEntry<>(server,client);
                }
                
                bus.serverToClient(FrameworkConstants.FRAMEWORK_NETWORK_IO_PING.getBytes(
                        Charset.defaultCharset()), server,client);   
                
                Logger.getRootLogger().info("AppEngine : server : "+server);
                
                Logger.getRootLogger().info("AppEngine : client : "+client);
            }            
        }
            
        return entrySet;
    }
    
    
      
    @Override
    public ServiceStatus startup(Optional<Properties> propertiesOption) throws ConfigurationException{

        //  -D<property-name>=<value> i.e. -DFRAMEWORK_CONFIG_PATH=<Install_HOME>/../../framework.properties
        Logger.getRootLogger().info(FrameworkConstants.FRAMEWORK_CONFIG_PATH+ " : "+ 
        System.getProperty(FrameworkConstants.FRAMEWORK_CONFIG_PATH));

        String configurationFilePath = System.getProperty(FrameworkConstants.FRAMEWORK_CONFIG_PATH);

        ServiceFramework.getInstance().configure(configurationFilePath);

        ConfigurationService configurationService = ServiceFramework.getInstance().getCatalog()
                .getConfigurationService();

        Logger.getRootLogger().info("AbstractAppEngine : configurationService : "+configurationService);        

        ServiceStatus serviceStatus = ServiceFramework.getInstance().startup(
                Optional.of(configurationService.getServiceConfiguration(ConfigurationType.SERVER)
                        .getConfigAsProperties()));
        
        
       
        configureProviders();
        
        startAppNodePipelineBus();

        return serviceStatus;
    }
    

    public ServiceStatus startAppNodePipelineBus() throws ConfigurationException{
        
        return  bus.startup();
    }
    
    public ServiceStatus shutdownAppNodePipelineBus() throws ConfigurationException{
        
        return  bus.shutdown();
    }

    @Override
    public ServiceStatus shutdown(Optional<Properties> propertiesOption) throws ConfigurationException{

        ConfigurationService configurationService = ServiceFramework.getInstance().getCatalog()
                .getConfigurationService();

        bus.closeNetworkBusExecutor(Optional.empty());

        ServiceStatus serviceStatus = ServiceFramework.getInstance().shutdown(
                Optional.of(configurationService.getServiceConfiguration(ConfigurationType.SERVER)
                        .getConfigAsProperties()));

        executorService.shutdown();
        
        shutdownAppNodePipelineBus();

        return serviceStatus;
    }



}

