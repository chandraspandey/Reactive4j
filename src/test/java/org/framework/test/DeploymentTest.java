
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.framework.test;


import org.apache.log4j.Logger;
import org.flowr.framework.core.config.Configuration;
import org.flowr.framework.core.config.xml.Config;
import org.flowr.framework.core.exception.ConfigurationException;
import org.junit.Assert;
import org.junit.Test;

public class DeploymentTest extends FrameworkTest{

    @Test
    public void testConfig() throws ConfigurationException{
        
        Config config = Configuration.DefaultConfigReader.getInstance().read();

        Logger.getRootLogger().info(config);
        
        Assert.assertTrue("Config should not be null.", (config != null)); 


     }
   
}
