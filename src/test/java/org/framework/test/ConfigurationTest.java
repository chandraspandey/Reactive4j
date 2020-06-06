
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.framework.test;


import java.util.UUID;

import org.apache.log4j.Logger;
import org.flowr.framework.core.config.Configuration;
import org.flowr.framework.core.config.DataSourceConfiguration;
import org.flowr.framework.core.config.NodeServiceConfiguration;
import org.flowr.framework.core.config.QueryCacheConfiguration;
import org.flowr.framework.core.config.ServiceConfiguration;
import org.flowr.framework.core.config.xml.Config;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.promise.RequestScale;
import org.flowr.framework.core.promise.Scale.ScaleOption;
import org.flowr.framework.core.promise.deferred.ProgressScale;
import org.flowr.framework.core.promise.phase.PhasedProgressScale;
import org.flowr.framework.core.promise.scheduled.ScheduledProgressScale;
import org.junit.Assert;
import org.junit.Test;

public class ConfigurationTest extends FrameworkTest{

    @Test
    public void testConfig() throws ConfigurationException{
        
        Config config = Configuration.DefaultConfigReader.getInstance().read();

        Logger.getRootLogger().info(config);
        
        Assert.assertTrue("Config should not be null.", (config != null)); 
        Assert.assertTrue("DataSourceConfig should not be null.", (config.getDataSourceConfig() != null)); 
        Assert.assertTrue("ClientConfiguration should not be null.", (config.getClientConfiguration() != null)); 
        Assert.assertTrue("ServerConfiguration should not be null.", (config.getServerConfiguration() != null)); 
        Assert.assertTrue("NodeConfiguration should not be null.", (config.getNodeConfiguration() != null));  

     }
    
    @Test
    public void testClientConfig() throws ConfigurationException{
        
        Assert.assertTrue("ClientPipelineConfiguration should not be null.", 
                Configuration.getClientPipelineConfiguration() != null);
        Assert.assertTrue("ClientPipelineConfiguration should be more more than 0.", 
                !Configuration.getClientPipelineConfiguration().isEmpty());
        
        Assert.assertTrue("ClientEndPointConfiguration should not be null.", 
                Configuration.getClientEndPointConfiguration()!= null);
        
        RequestScale requestScale = Configuration.getRequestScale(UUID.randomUUID().toString());
        
        Assert.assertTrue("RequestScale should not be null.", requestScale != null);
        
        Assert.assertTrue("RequestScale should be valid.", requestScale.isValid());
        
        ScaleOption scaleOption = requestScale.getScaleOption();
        
        Assert.assertTrue("ScaleOption should not be null.", scaleOption != null);
        Assert.assertTrue("ScaleOption should be valid.", validateScaleOption(scaleOption));
                
        ServiceConfiguration serviceConfiguration = Configuration.getClientConfiguration();
        
        Assert.assertTrue("Client ServiceConfiguration should not be null.", serviceConfiguration != null);
        
        Logger.getRootLogger().info(serviceConfiguration);
        Assert.assertTrue("Client ServiceConfiguration should be valid.", serviceConfiguration.isValid());
     }
    
    @Test
    public void testServerConfig() throws ConfigurationException{
        
        Assert.assertTrue("ServerPipelineConfiguration should not be null .", 
                Configuration.getServerPipelineConfiguration() != null);
        Assert.assertTrue("ServerPipelineConfiguration should be more more than 0.", 
                !Configuration.getServerPipelineConfiguration().isEmpty());
        
        Assert.assertTrue("ClientEndPointConfiguration should not be null.", 
                Configuration.getServerEndPointConfiguration()!= null);
        
        ProgressScale progressScale = (ProgressScale)Configuration.getProgressScale(UUID.randomUUID().toString());
        
        Assert.assertTrue("RequestScale should not be null.", progressScale != null);
        
        Assert.assertTrue("RequestScale should be valid.", progressScale.isValid());
        
        ScaleOption scaleOption = progressScale.getScaleOption();
        
        Assert.assertTrue("ScaleOption should not be null.", scaleOption != null);
        Assert.assertTrue("ScaleOption should be valid.", validateScaleOption(scaleOption));
        
        PhasedProgressScale phasedProgressScale = (PhasedProgressScale)Configuration.getPhasedProgressScale(
                UUID.randomUUID().toString());
        
        Assert.assertTrue("PhasedProgressScale should not be null.", phasedProgressScale != null);
        
        Assert.assertTrue("PhasedProgressScale should be valid.", phasedProgressScale.isValid());
        
        scaleOption = phasedProgressScale.getScaleOption();
        
        Assert.assertTrue("ScaleOption should not be null.", scaleOption != null);
        Assert.assertTrue("ScaleOption should be valid.", validateScaleOption(scaleOption));
        
        ScheduledProgressScale scheduledProgressScale = (ScheduledProgressScale)Configuration
                .getScheduledProgressScale(UUID.randomUUID().toString());
        
        Assert.assertTrue("ScheduledProgressScale should not be null.", scheduledProgressScale != null);
        
        Assert.assertTrue("ScheduledProgressScale should be valid.", scheduledProgressScale.isValid());
        
        scaleOption = scheduledProgressScale.getScaleOption();
        
        Assert.assertTrue("ScaleOption should not be null.", scaleOption != null);
        Assert.assertTrue("ScaleOption should be valid.", validateScaleOption(scaleOption));
        
        ServiceConfiguration serviceConfiguration = Configuration.getServerConfiguration();
        
        Assert.assertTrue("Server ServiceConfiguration should not be null.", serviceConfiguration != null);       
        
        Logger.getRootLogger().info(serviceConfiguration);
        Assert.assertTrue("Server ServiceConfiguration should be valid.", serviceConfiguration.isValid());
        
     }
    
    @Test
    public void testNodeInboundConfig() throws ConfigurationException{
        
        Assert.assertTrue("NodeInboundEndPointConfiguration should not be null .", 
                Configuration.getNodeInboundEndPointConfiguration() != null);
        Assert.assertTrue("NodeInboundEndPointConfiguration should be more more than 0.", 
                !Configuration.getNodeInboundEndPointConfiguration().isEmpty());
        
        Configuration.getNodeInboundEndPointConfiguration().forEach(
                
               (NodeServiceConfiguration n) ->  
                   Assert.assertTrue("NodeInboundEndPointConfiguration should be valid.", 
                           validateNodeServiceConfiguration(n))
               
         );
        
        Assert.assertTrue("NodeOutboundEndPointConfiguration should not be null .", 
                Configuration.getNodeOutboundEndPointConfiguration() != null);
        Assert.assertTrue("NodeOutboundEndPointConfiguration should be more more than 0.", 
                !Configuration.getNodeOutboundEndPointConfiguration().isEmpty());
        
        Configuration.getNodeOutboundEndPointConfiguration().forEach(
                
                (NodeServiceConfiguration n) ->  {
                    Assert.assertTrue("NodeOutboundEndPointConfiguration should be valid.", 
                            validateNodeServiceConfiguration(n));
                    
                    Assert.assertTrue("NodeOutboundEndPointConfiguration should not have client & server port for"
                            + "socket creation.", 
                            n.getClientHostPort() != n.getServerHostPort());
                    
                }
          );
        
    }
    
    @Test
    public void testDataSourceConfig() throws ConfigurationException{
        
        Assert.assertTrue("DataSourceConfiguration should not be null .", 
                Configuration.getDataSourceConfiguration() != null);
        Assert.assertTrue("DataSourceConfiguration should be more more than 0.", 
                !Configuration.getDataSourceConfiguration().isEmpty());
        
        Configuration.getDataSourceConfiguration().forEach(
                
               (DataSourceConfiguration d) ->  {
                   
                   Assert.assertTrue("DataSourceConfiguration should be valid.", 
                           d.isValid());
                   
                   Assert.assertTrue("MappingEntityClassNames should not be null.", 
                           d.getMappingEntityClassNames() != null && !d.getMappingEntityClassNames().isEmpty());
                                       
                   Assert.assertTrue("ConnectionConfiguration should be valid.", 
                           d.getConnectionConfiguration().isValid());
                 
                   QueryCacheConfiguration queryCacheConfiguration = d.getQueryCacheConfiguration();
                    
                   if(queryCacheConfiguration.isCacheQuery()) {
                   
                       Assert.assertTrue("QueryCacheConfiguration should be valid.", 
                               queryCacheConfiguration.isValid());
                       
                       if(queryCacheConfiguration.isCacheExternal()) {
                           
                           Assert.assertTrue("QueryCacheConfiguration should be valid.", 
                                   queryCacheConfiguration.hasValidProvider());
                       }
                   }
                   
                   Assert.assertTrue("CacheConfiguration should not be null.", 
                           d.getCacheConfiguration() != null && d.getCacheConfiguration().isValid());
                   
               }
         );
    }
        
    private static boolean validateNodeServiceConfiguration(NodeServiceConfiguration config) {
        
        return (
                    config.isValid()  && config.getClientEndPointList() != null &&
                    !config.getClientEndPointList().isEmpty()
                );
     }
    
    private static boolean validateScaleOption(ScaleOption scaleOption) {
        
       return (
                   scaleOption.getMinScale() >= 0 && scaleOption.getMaxScale() <=100 &&
                   scaleOption.getProgressTimeUnit() != null && scaleOption.getScaleUnit() > 0
               );
    }
    
   
}
