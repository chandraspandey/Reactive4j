
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.constants;

import java.util.concurrent.TimeUnit;

public interface Constant {

    public final class FrameworkConstants {

        public static final String FRAMEWORK_SERVICE                    = "FRAMEWORK_SERVICE";
        public static final String FRAMEWORK_SERVICE_PROVIDER           = "FRAMEWORK_SERVICE_PROVIDER";
        public static final String FRAMEWORK_SERVICE_EVENT              = "FRAMEWORK_SERVICE_EVENT";
        public static final String FRAMEWORK_SERVICE_NOTIFICATION       = "FRAMEWORK_SERVICE_NOTIFICATION";
        public static final String FRAMEWORK_SERVICE_PROMISE            = "FRAMEWORK_SERVICE_PROMISE";
        public static final String FRAMEWORK_SERVICE_PROMISE_DEFFERED   = "FRAMEWORK_SERVICE_PROMISE_DEFFERED";
        public static final String FRAMEWORK_SERVICE_PROMISE_PHASED     = "FRAMEWORK_SERVICE_PROMISE_PHASED";
        public static final String FRAMEWORK_SERVICE_PROMISE_SCHEDULED  = "FRAMEWORK_SERVICE_PROMISE_SCHEDULED";
        public static final String FRAMEWORK_SERVICE_PROMISE_STAGE      = "FRAMEWORK_SERVICE_PROMISE_STAGE";
        public static final String FRAMEWORK_SERVICE_PROMISE_STREAM     = "FRAMEWORK_SERVICE_PROMISE_STREAM";
        public static final String FRAMEWORK_SERVICE_PROMISE_MAP        = "FRAMEWORK_SERVICE_PROMISE_MAP";
        public static final String FRAMEWORK_SERVICE_REGISTRY           = "FRAMEWORK_SERVICE_REGISTRY";
        public static final String FRAMEWORK_SERVICE_ROUTING            = "FRAMEWORK_SERVICE_ROUTING";
        public static final String FRAMEWORK_SERVICE_SUBSCRIPTION       = "FRAMEWORK_SERVICE_SUBSCRIPTION";
        public static final String FRAMEWORK_SERVICE_MANAGEMENT         = "FRAMEWORK_SERVICE_MANAGEMENT";
        public static final String FRAMEWORK_SERVICE_CONFIGURATION      = "FRAMEWORK_SERVICE_CONFIGURATION";
        public static final String FRAMEWORK_SERVICE_ADMINISTRATION     = "FRAMEWORK_SERVICE_ADMINISTRATION";
        public static final String FRAMEWORK_SERVICE_SECURITY           = "FRAMEWORK_SERVICE_SECURITY";
        public static final String FRAMEWORK_SERVICE_NODE               = "FRAMEWORK_SERVICE_NODE";
        public static final String FRAMEWORK_SERVICE_HEALTH             = "FRAMEWORK_SERVICE_HEALTH";
        
        public static final String FRAMEWORK_PIPELINE_ALL               = "FRAMEWORK_PIPELINE_ALL";
        public static final String FRAMEWORK_PIPELINE_PROMISE           = "FRAMEWORK_PIPELINE_PROMISE";
        public static final String FRAMEWORK_PIPELINE_PROMISE_DEFFERED  = "FRAMEWORK_PIPELINE_PROMISE_DEFFERED";
        public static final String FRAMEWORK_PIPELINE_PROMISE_PHASED    = "FRAMEWORK_PIPELINE_PROMISE_PHASED";
        public static final String FRAMEWORK_PIPELINE_PROMISE_SCHEDULED = "FRAMEWORK_PIPELINE_PROMISE_SCHEDULED";
        public static final String FRAMEWORK_PIPELINE_PROMISE_STAGE     = "FRAMEWORK_PIPELINE_PROMISE_STAGE";
        public static final String FRAMEWORK_PIPELINE_PROMISE_MAP       = "FRAMEWORK_PIPELINE_PROMISE_MAP";
        public static final String FRAMEWORK_PIPELINE_PROMISE_STREAM    = "FRAMEWORK_PIPELINE_PROMISE_STREAM";
        public static final String FRAMEWORK_PIPELINE_MANAGEMENT        = "FRAMEWORK_PIPELINE_MANAGEMENT";
        public static final String FRAMEWORK_PIPELINE_NOTIFICATION      = "FRAMEWORK_PIPELINE_NOTIFICATION";
        public static final String FRAMEWORK_PIPELINE_PUSH_NOTIFICATION = "FRAMEWORK_PIPELINE_PUSH_NOTIFICATION";
        public static final String FRAMEWORK_PIPELINE_HEALTH            = "FRAMEWORK_PIPELINE_HEALTH";
        public static final String FRAMEWORK_PIPELINE_SMS               = "FRAMEWORK_PIPELINE_SMS";
        public static final String FRAMEWORK_PIPELINE_EMAIL             = "FRAMEWORK_PIPELINE_EMAIL";
        
        public static final String FRAMEWORK_PIPELINE_INTEGRATION       = "FRAMEWORK_PIPELINE_INTEGRATION";
        public static final String FRAMEWORK_PIPELINE_USECASE           = "FRAMEWORK_PIPELINE_USECASE";
        public static final String FRAMEWORK_PIPELINE_NETWORK_SERVER    = "FRAMEWORK_PIPELINE_NETWORK_SERVER";
        public static final String FRAMEWORK_PIPELINE_NETWORK_CLIENT    = "FRAMEWORK_PIPELINE_NETWORK_CLIENT";
        
        public static final String FRAMEWORK_PIPELINE_SERVICE           = "FRAMEWORK_PIPELINE_SERVICE";
        
        public static final String FRAMEWORK_CONFIG_PERSISTENCE         = "flowr.node.provider.persistence";
        public static final String FRAMEWORK_CONFIG_DATASOURCE          = "flowr.node.provider.datasource";
        public static final String FRAMEWORK_CONFIG_SERVICE             = "flowr.node.provider.service";
        public static final String FRAMEWORK_CONFIG_SECURITY            = "flowr.node.provider.security";
        public static final String FRAMEWORK_CONFIG_CLIENT              = "flowr.node.provider.client";
        public static final String FRAMEWORK_CONFIG_SERVER              = "flowr.node.provider.server";
        public static final String FRAMEWORK_CONFIG_FRAMEWORK           = "flowr.node.provider.framework";
     
        
        public static final String FRAMEWORK_PIPELINE_DEFAULT_NAME      = "DEFAULT";
        public static final String FRAMEWORK_SUBSCRIPTION_DEFAULT_ID    = "DEFAULT";
        public static final boolean FRAMEWORK_PIPELINE_BATCH_DEFAULT_MODE= false;
        public static final int FRAMEWORK_PIPELINE_BATCH_DEFAULT_MIN    = 0;
        public static final int FRAMEWORK_PIPELINE_DEFAULT_MAX_ELEMENTS = 10000;
        
        public static final int FRAMEWORK_PIPELINE_NOTIFICATION_TIME_UNIT= 500;
        public static final int FRAMEWORK_NETWORK_IO_BUFFER_UNIT        = 1024;
        public static final int FRAMEWORK_NETWORK_IO_BUFFER_BLOCK       = 5120;
        public static final String FRAMEWORK_NETWORK_IO_PING            = "ECHO";
        
        public static final int FRAMEWORK_BYTE_FIELD_DEFAULT            = 0;
        public static final long FRAMEWORK_SLEEP_INTERVAL               = 10000;
        
        public static final int FRAMEWORK_VERSION_ID                    = 555555555;
        public static final  String CONTENT_TAB_SPACE = "                                         | ";
        
        public static final String FRAMEWORK_CONFIG_PATH                = "FRAMEWORK_CONFIG_PATH";

        private FrameworkConstants(){
            
        }
    }

    public final class NodeConstants {
        
    public static final long NODE_PIPELINE_CLIENT_SLEEP_INTERVAL           = 6000;
    public static final long NODE_PIPELINE_CLIENT_SEEK_TIMEOUT             = 6000;
    public static final TimeUnit NODE_PIPELINE_CLIENT_SEEK_TIMEUNIT        = TimeUnit.MILLISECONDS;
    public static final long NODE_PIPELINE_SERVER_SLEEP_INTERVAL           = 6000;
    public static final int NODE_PIPELINE_RESPONSE_LIMIT_MIN               = 20;
    public static final int NODE_PIPELINE_RESPONSE_LIMIT_MAX               = 20;
            
        private NodeConstants(){
            
        }
    }

    public final class DataSourceConstants {
        
    
        public static final int DS_CONFIG_MIN                               = 1;        
        public static final int SEQUENCE_CHARACTER_START                    = 64;
        public static final int SEQUENCE_CHARACTER_END                      = 90;
        public static final int SEQUENCE_NUMBER_START                       = 49;
        public static final int SEQUENCE_NUMBER_END                         = 57;

        
        private DataSourceConstants() {
            
        }
    }
    
    public final class ClientConstants {

      
        
        // supports up to 100 nodes
        public static final int CLIENT_PIPELINE_ENDPOINT_MIN                = 1;
        public static final int CLIENT_PIPELINE_ENDPOINT_MAX                = 100;
        // supports up to 100 pipelines
        public static final int CLIENT_PIPELINE_MIN                         = 1;
        public static final int CLIENT_PIPELINE_MAX                         = 100;
 
        private ClientConstants() {
            
        }
    }
    
    public final class ServerConstants {
        
        public static final String SERVER_EVENT_BUS_NAME                     = "EventBus";
        public static final String SERVER_NETWORK_BUS_NAME                   = "NetworkBus";

        // supports up to 100 nodes
        public static final int SERVER_PIPELINE_ENDPOINT_MIN                 = 0;
        public static final int SERVER_PIPELINE_ENDPOINT_MAX                 = 100;

        
        // supports up to 100 pipelines
        public static final int SERVER_PIPELINE_MIN                          = 0;
        public static final int SERVER_PIPELINE_MAX                          = 100;
        
        
        private ServerConstants() {
            
        }
    }
    
}

