
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package example.flowr;

import java.util.AbstractMap.SimpleEntry;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.flowr.framework.api.Provider;
import org.flowr.framework.api.Provider.ProviderType;
import org.flowr.framework.api.ProviderFactory;
import org.flowr.framework.concurrent.PromiseCoRoutine;
import org.flowr.framework.core.app.AbstractAppEngine;
import org.flowr.framework.core.app.AppBuilder.PromiseConfigBuilder;
import org.flowr.framework.core.app.AppNodeBuilder.NodePipelineConfigBuilder;
import org.flowr.framework.core.config.Configuration.ConfigurationType;
import org.flowr.framework.core.config.NodeServiceConfiguration;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.NodeManager.NodePipelineConfig;
import org.flowr.framework.core.node.NodeManager.ProtocolConfig;
import org.flowr.framework.core.node.io.endpoint.NodePipelineClient;
import org.flowr.framework.core.node.io.endpoint.NodePipelineServer;
import org.flowr.framework.core.notification.Notification.ClientNotificationProtocolType;
import org.flowr.framework.core.notification.Notification.ServerNotificationProtocolType;
import org.flowr.framework.core.promise.PromiseTypeClient;
import org.flowr.framework.core.security.Identity.IdentityType;
import org.flowr.framework.core.security.SecurityBuilder.IdentityBuilder;
import org.flowr.framework.core.security.access.Entitlements.DefaultEntitlements;
import org.flowr.framework.core.security.identity.IdentityData;
import org.flowr.framework.core.service.ServiceFramework;

import example.flowr.mock.Mock.MockSettings;
import example.flowr.mock.Mock.PhasedPromiseMockRequest;
import example.flowr.mock.Mock.PhasedPromiseMockResponse;
import example.flowr.mock.Mock.PromiseMockRequest;
import example.flowr.mock.Mock.PromiseMockResponse;
import example.flowr.mock.Mock.ScheduledPromiseMockRequest;
import example.flowr.mock.Mock.ScheduledPromiseMockResponse;
import example.flowr.node.integration.NodeProviderFactory;
import example.flowr.node.integration.ProviderIntegrationBridge;
import example.flowr.promise.client.DeferredPromiseClient;
import example.flowr.promise.client.PhasedPromiseClient;
import example.flowr.promise.client.PromiseClient;
import example.flowr.promise.client.ScheduledPromiseClient;
import example.flowr.promise.client.notification.ClientNotificationAdapter;
import example.flowr.promise.client.notification.ClientNotificationAdapterTask;
import example.flowr.promise.server.notification.ServerNotificationAdapter;
import example.flowr.promise.server.notification.ServerNotificationAdapterTask;

public class FlowR extends AbstractAppEngine{

    
    public PromiseTypeClient promiseClient() throws ConfigurationException{
        
        return createPromiseTypeClient(
                
            new PromiseConfigBuilder()
                .withPromiseTypeClientAs(new PromiseClient())
                .withRequestModelAs(new PromiseMockRequest())
                .withResponseModelAs(new PromiseMockResponse())
                .withClientNotificationServiceAdapterAs(new ClientNotificationAdapter())
                .withClientAdapterPropertiesAs(new Properties())
                .withClientNotificationTaskAs(new ClientNotificationAdapterTask())
                .withClientTaskTopologyPropertiesAs(new Properties())
                .withClientSubscriptionMapAs(MockSettings.clientSubscriptionMap())
                .withServerNotificationServiceAdapterAs(new ServerNotificationAdapter())
                .withServerAdapterPropertiesAs(new Properties())
                .withServerNotificationTaskAs(new ServerNotificationAdapterTask())
                .withServerTaskTopologyPropertiesAs(new Properties())
                .withServerSubscriptionMapAs(MockSettings.serverSubscriptionMap())
                .build()                
           );
    }  

    public PromiseTypeClient defferedPromiseClient() throws ConfigurationException{
        
        return createPromiseTypeClient(
                
            new PromiseConfigBuilder()
            .withPromiseTypeClientAs(new DeferredPromiseClient())
            .withRequestModelAs(new PromiseMockRequest())
            .withResponseModelAs(new PromiseMockResponse())
            .withClientNotificationServiceAdapterAs(new ClientNotificationAdapter())
            .withClientAdapterPropertiesAs(new Properties())
            .withClientNotificationTaskAs(new ClientNotificationAdapterTask())
            .withClientTaskTopologyPropertiesAs(new Properties())
            .withClientSubscriptionMapAs(MockSettings.clientSubscriptionMap())
            .withServerNotificationServiceAdapterAs(new ServerNotificationAdapter())
            .withServerAdapterPropertiesAs(new Properties())
            .withServerNotificationTaskAs(new ServerNotificationAdapterTask())
            .withServerTaskTopologyPropertiesAs(new Properties())
            .withServerSubscriptionMapAs(MockSettings.serverSubscriptionMap())
            .build()                
       );
    }

    public PromiseTypeClient phasedPromiseClient() throws ConfigurationException{
        
        return createPromiseTypeClient(
                
                new PromiseConfigBuilder()
                .withPromiseTypeClientAs(new PhasedPromiseClient())
                .withRequestModelAs(new PhasedPromiseMockRequest())
                .withResponseModelAs(new PhasedPromiseMockResponse())
                .withClientNotificationServiceAdapterAs(new ClientNotificationAdapter())
                .withClientAdapterPropertiesAs(new Properties())
                .withClientNotificationTaskAs(new ClientNotificationAdapterTask())
                .withClientTaskTopologyPropertiesAs(new Properties())
                .withClientSubscriptionMapAs(MockSettings.clientSubscriptionMap())
                .withServerNotificationServiceAdapterAs(new ServerNotificationAdapter())
                .withServerAdapterPropertiesAs(new Properties())
                .withServerNotificationTaskAs(new ServerNotificationAdapterTask())
                .withServerTaskTopologyPropertiesAs(new Properties())
                .withServerSubscriptionMapAs(MockSettings.serverSubscriptionMap())
                .build()                
           );
    }

    public PromiseTypeClient scheduledPromiseClient() throws ConfigurationException{
        
        return createPromiseTypeClient(
                
                new PromiseConfigBuilder()
                .withPromiseTypeClientAs(new ScheduledPromiseClient())
                .withRequestModelAs(new ScheduledPromiseMockRequest())
                .withResponseModelAs(new ScheduledPromiseMockResponse())
                .withClientNotificationServiceAdapterAs(new ClientNotificationAdapter())
                .withClientAdapterPropertiesAs(new Properties())
                .withClientNotificationTaskAs(new ClientNotificationAdapterTask())
                .withClientTaskTopologyPropertiesAs(new Properties())
                .withClientSubscriptionMapAs(MockSettings.clientSubscriptionMap())
                .withServerNotificationServiceAdapterAs(new ServerNotificationAdapter())
                .withServerAdapterPropertiesAs(new Properties())
                .withServerNotificationTaskAs(new ServerNotificationAdapterTask())
                .withServerTaskTopologyPropertiesAs(new Properties())
                .withServerSubscriptionMapAs(MockSettings.serverSubscriptionMap())
                .build()                
           );

    }
      
    @Override 
    public void configureProviders() {
        
        ProviderFactory providerFactory = new NodeProviderFactory();        
        
        Map<ProviderType,Provider> providerMap = new EnumMap<>(ProviderType.class);
        
        providerMap.put(ProviderType.SECURITY, providerFactory.instantiate(ProviderType.SECURITY));
        providerMap.put(ProviderType.SERVICE, providerFactory.instantiate(ProviderType.SERVICE));
        providerMap.put(ProviderType.PERSISTENCE, providerFactory.instantiate(ProviderType.PERSISTENCE));
        providerMap.put(ProviderType.DATASOURCE, providerFactory.instantiate(ProviderType.DATASOURCE));
        
        ServiceFramework.getInstance().getCatalog()
                .getConfigurationService().loadProviderConfiguration(providerMap);
        
        Logger.getRootLogger().info("FlowR : configurationService : "+ServiceFramework.getInstance().getCatalog()
                .getConfigurationService());

    }
    
    @Override 
    public SimpleEntry<NodePipelineServer,NodePipelineClient> configureApp() throws ConfigurationException {
        
        super.configure();
        
        return configureNodePipeline();
    }
    
    @Override 
    public SimpleEntry<NodePipelineServer,NodePipelineClient> configureNodePipeline() throws 
        ConfigurationException{

        List<NodeServiceConfiguration> nodeInboundConfiguration = 
                ServiceFramework.getInstance().getCatalog().getConfigurationService()
                .getIntegrationServiceConfiguration(
                        ConfigurationType.INTEGRATION_INBOUND);

        List<NodeServiceConfiguration> nodeOutboundConfiguration = 
                ServiceFramework.getInstance().getCatalog().getConfigurationService()
                .getIntegrationServiceConfiguration(
                        ConfigurationType.INTEGRATION_OUTBOUND);
        
        NodeServiceConfiguration inboundConfiguration = nodeInboundConfiguration.get(0);        
        NodeServiceConfiguration outboundConfiguration = nodeOutboundConfiguration.get(0);
        
        Properties serverProperties = new Properties();
        serverProperties.put("id", "server-101");
        
        ProtocolConfig serverProtocolConfig = new ProtocolConfig(
                                                ServerNotificationProtocolType.SERVER_INTEGRATION,
                                                PipelineFunctionType.PIPELINE_SERVER_SERVICE, 
                                                PipelineFunctionType.PIPELINE_SERVER_SERVICE.name()
                                              );
        
        NodePipelineConfig serverConfig = new NodePipelineConfigBuilder()
                                            .withInboundBridgeAs(new ProviderIntegrationBridge())
                                            .withOutboundBridgeAs(new ProviderIntegrationBridge())
                                            .withInboundConfigurationAs(inboundConfiguration)
                                            .withOutboundConfigurationAs(outboundConfiguration)
                                            .withProcessOptionAs(Optional.of(new SimpleEntry<Integer,Integer>(1,1)))
                                            .withPropertiesOptionAs(Optional.of(serverProperties))
                                            .withProtocolConfigAs(serverProtocolConfig)
                                            .withIdentityConfigAs(
                                                new IdentityBuilder()
                                                .withIdentityNameAs(inboundConfiguration.getServerHostName()+":"+
                                                        inboundConfiguration.getServerHostPort())
                                                .withEntitlementsAs(new DefaultEntitlements())
                                                .withIdentityDataAs(new IdentityData())
                                                .withIdentityTypeAs(IdentityType.LOGICAL)
                                                .build()    
                                                    
                                            ).build();
        
        Properties clientProperties = new Properties();
        clientProperties.put("id", "client-101");
        
        // Flip outboundConfiguration & inboundConfiguration Here, need to have validate method overall 
        
        ProtocolConfig clientProtocolConfig = new ProtocolConfig(
                                                ClientNotificationProtocolType.CLIENT_INTEGRATION,
                                                PipelineFunctionType.PIPELINE_CLIENT_SERVICE, 
                                                PipelineFunctionType.PIPELINE_CLIENT_SERVICE.name()
                                              );

       
        NodePipelineConfig clientConfig = new NodePipelineConfigBuilder()
                                            .withInboundBridgeAs(new ProviderIntegrationBridge())
                                            .withOutboundBridgeAs(new ProviderIntegrationBridge())
                                            .withInboundConfigurationAs(inboundConfiguration)
                                            .withOutboundConfigurationAs(outboundConfiguration)
                                            .withProcessOptionAs(Optional.of(new SimpleEntry<Integer,Integer>(1,1)))
                                            .withPropertiesOptionAs(Optional.of(clientProperties))
                                            .withProtocolConfigAs(clientProtocolConfig)
                                            .withIdentityConfigAs(
                                                new IdentityBuilder()
                                                .withIdentityNameAs(outboundConfiguration.getServerHostName()+":"+
                                                        outboundConfiguration.getServerHostPort())
                                                .withEntitlementsAs(new DefaultEntitlements())
                                                .withIdentityDataAs(new IdentityData())
                                                .withIdentityTypeAs(IdentityType.LOGICAL)
                                                .build()
                                            ).build();
        
        return super.configurePipeline(new SimpleEntry<>(serverConfig,clientConfig));
    }
    
    @Override 
    public void runWithNodeOperation() throws ConfigurationException{
        
        SimpleEntry<NodePipelineServer,NodePipelineClient> serverClientConfigEntry = configureApp();
        Logger.getRootLogger().info("FlowR : runWithNodeOperation : "+serverClientConfigEntry);
    }
    
    @Override 
    public void runWithPromise(PromiseTypeClient promiseTypeClient){
        
        PromiseCoRoutine promiseThread = new PromiseCoRoutine(promiseTypeClient);      
        
        promiseThread.start();
        
    }  

    public void test() throws ConfigurationException{
        
        //runWithPromise(promiseClient());
        
        //runWithPromise(defferedPromiseClient());
        runWithPromise(scheduledPromiseClient());
        //runWithPromise(phasedPromiseClient());

    }  
     
    private static void printUsage() {
        
        Logger.getRootLogger().info("USAGE : Reguires FRAMEWORK_CONFIG_PATH as key for loading flowr.xml"
                + " in classpath settings for running.");
        Logger.getRootLogger().info("USAGE : It can be provided as part of VM of program arguments for execution.");
        Logger.getRootLogger().info("USAGE : -D<property-name>=<value> i.e. "
                + "-DFRAMEWORK_CONFIG_PATH=<Install_HOME>/../../flowr.xml");
    }

    public static void main(String[] args) {

        printUsage();
        
        FlowR flowr = new FlowR();

        try{
        
            flowr.startup(Optional.empty());
    
            //flowr.test(); 
    
            flowr.runWithNodeOperation();
    
            //flowr.shutdown(Optional.empty());
        
      } catch (ConfigurationException e) {
            
            Logger.getRootLogger().error(e);
            Thread.currentThread().interrupt();          
        }   
    }



}
