
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
     
        
        public static final String FRAMEWORK_PIPELINE_DEFAULT_NAME      = "DEFAULT";
        public static final String FRAMEWORK_SUBSCRIPTION_DEFAULT_ID    = "DEFAULT";
        public static final boolean FRAMEWORK_PIPELINE_BATCH_DEFAULT_MODE= false;
        public static final int FRAMEWORK_PIPELINE_BATCH_DEFAULT_MIN    = 0;
        public static final int FRAMEWORK_PIPELINE_DEFAULT_MAX_ELEMENTS = 10000;
        
        public static final int FRAMEWORK_PIPELINE_NOTIFICATION_TIME_UNIT= 500;
        public static final int FRAMEWORK_NETWORK_IO_BUFFER_UNIT        = 1024;
        public static final int FRAMEWORK_NETWORK_IO_BUFFER_BLOCK       = 5120;
        
        public static final int FRAMEWORK_BYTE_FIELD_DEFAULT            = 0;
        public static final long FRAMEWORK_SLEEP_INTERVAL               = 10000;
        
        public static final int FRAMEWORK_VERSION_ID                    = 555555555;
        public static final  String CONTENT_TAB_SPACE = "                                         | ";
        
        public static final String FRAMEWORK_CONFIG_PATH                = "FRAMEWORK_CONFIG_PATH";
        public static final String FRAMEWORK_CONFIG_PERSISTENCE         = "flowr.node.provider.persistence";
        public static final String FRAMEWORK_CONFIG_DATASOURCE          = "flowr.node.provider.datasource";
        public static final String FRAMEWORK_CONFIG_SERVICE             = "flowr.node.provider.service";
        public static final String FRAMEWORK_CONFIG_SECURITY            = "flowr.node.provider.security";
        public static final String FRAMEWORK_CONFIG_CLIENT              = "flowr.node.provider.client";
        public static final String FRAMEWORK_CONFIG_SERVER              = "flowr.node.provider.server";
        public static final String FRAMEWORK_CONFIG_FRAMEWORK           = "flowr.node.provider.framework";

        private FrameworkConstants(){
            
        }
    }

    public final class NodeConstants {
        
    public static final long NODE_PIPELINE_CLIENT_SLEEP_INTERVAL                = 6000;
    public static final long NODE_PIPELINE_CLIENT_SEEK_TIMEOUT                  = 6000;
    public static final TimeUnit NODE_PIPELINE_CLIENT_SEEK_TIMEUNIT             = TimeUnit.MILLISECONDS;
    public static final long NODE_PIPELINE_SERVER_SLEEP_INTERVAL                = 6000;
    public static final int NODE_PIPELINE_RESPONSE_LIMIT_MIN                    = 20;
    public static final int NODE_PIPELINE_RESPONSE_LIMIT_MAX                    = 20;
    
    private static final String BASE                                            = "flowr.node.pipeline";
    
    // supports up to 100 nodes
    public static final String NODE_PIPELINE_MIN                         = BASE+".min";
    public static final String NODE_PIPELINE_MAX                         = BASE+".max";
    public static final String NODE_PIPELINE_ENDPOINT_MIN                = BASE+".endpoint.min";
    public static final String NODE_PIPELINE_ENDPOINT_MAX                = BASE+".endpoint.max";

    public static final String NODE_PIPELINE_THREADS_MIN                 = BASE+".endpoint.thread.min";
    public static final String NODE_PIPELINE_THREADS_MAX                 = BASE+".endpoint.thread.max";
    public static final String NODE_PIPELINE_TIMEOUT                     = BASE+".endpoint.timeout";
    public static final String NODE_PIPELINE_TIMEOUT_UNIT                = BASE+".endpoint.timeout.unit";
    public static final String NODE_PIPELINE_NOTIFICATION_ENDPOINT     = BASE+".endpoint.notification.endpoint"; 
    public static final String NODE_PIPELINE_ENDPOINT_HEARTBEAT          = BASE+".endpoint.hearbeat.interval";
    public static final String NODE_PIPELINE_ENDPOINT_HEARTBEAT_UNIT     = BASE+".endpoint.hearbeat.unit"; 
    
    // In bound
    public static final String NODE_PIPELINE_INBOUND_SERVER_NAME         = BASE+".inbound.server.name";
    public static final String NODE_PIPELINE_INBOUND_SERVER_CHANNEL_NAME = BASE+".inbound.server.channel.name";
    public static final String NODE_PIPELINE_INBOUND_SERVER_HOST_NAME    = BASE+".inbound.server.host.name";
    public static final String NODE_PIPELINE_INBOUND_SERVER_HOST_PORT    = BASE+".inbound.server.host.port";
    public static final String NODE_PIPELINE_INBOUND_POLICY_ELEMENT      = BASE+".inbound.policy.element";
    public static final String NODE_PIPELINE_INBOUND_BATCH_MODE          = BASE+".inbound.dispatch.batch.mode";
    public static final String NODE_PIPELINE_INBOUND_BATCH_SIZE          = BASE+".inbound.dispatch.batch.size";
    public static final String NODE_PIPELINE_INBOUND_ELEMENT_MAX         = BASE+".inbound.elements.max";
    public static final String NODE_PIPELINE_INBOUND_FUNCTION_TYPE       = BASE+".inbound.endpoint.functiontype";
    public static final String NODE_PIPELINE_INBOUND_ENDPOINT_TYPE       = BASE+".inbound.endpoint.type";
    public static final String NODE_PIPELINE_INBOUND_CLIENT_NAME         = BASE+".inbound.client.name";
    public static final String NODE_PIPELINE_INBOUND_CLIENT_MAX          = BASE+".inbound.client.max";
    public static final String NODE_PIPELINE_INBOUND_CLIENT_HOST_NAME    = BASE+".inbound.client.host.name";
    public static final String NODE_PIPELINE_INBOUND_CLIENT_HOST_PORT    = BASE+".inbound.client.host.port";
    
    
    // Out bound
    public static final String NODE_PIPELINE_OUTBOUND_SERVER_NAME        = BASE+".outbound.server.name";
    public static final String NODE_PIPELINE_OUTBOUND_SERVER_CHANNEL_NAME= BASE+".outbound.server.channel.name";
    public static final String NODE_PIPELINE_OUTBOUND_SERVER_HOST_NAME   = BASE+".outbound.server.host.name";
    public static final String NODE_PIPELINE_OUTBOUND_SERVER_HOST_PORT   = BASE+".outbound.server.host.port";
    public static final String NODE_PIPELINE_OUTBOUND_POLICY_ELEMENT     = BASE+".outbound.policy.element";
    public static final String NODE_PIPELINE_OUTBOUND_BATCH_MODE         = BASE+".outbound.dispatch.batch.mode";
    public static final String NODE_PIPELINE_OUTBOUND_BATCH_SIZE         = BASE+".outbound.dispatch.batch.size";
    public static final String NODE_PIPELINE_OUTBOUND_ELEMENT_MAX        = BASE+".outbound.elements.max";
    public static final String NODE_PIPELINE_OUTBOUND_FUNCTION_TYPE    = BASE+".outbound.endpoint.functiontype";
    public static final String NODE_PIPELINE_OUTBOUND_ENDPOINT_TYPE      = BASE+".outbound.endpoint.type";
    public static final String NODE_PIPELINE_OUTBOUND_CLIENT_NAME        = BASE+".outbound.client.name";
    public static final String NODE_PIPELINE_OUTBOUND_CLIENT_MAX         = BASE+".outbound.client.max";
    public static final String NODE_PIPELINE_OUTBOUND_CLIENT_HOST_NAME   = BASE+".outbound.client.host.name";
    public static final String NODE_PIPELINE_OUTBOUND_CLIENT_HOST_PORT   = BASE+".outbound.client.host.port";

        
        private NodeConstants(){
            
        }
    }

    public final class DataSourceConstants {
        
        public static final String DS_COUNT                         = "flowr.datasource.count";
        public static final String DS_NAME                          = "flowr.datasource.name";  
        public static final String DS_JPA_VERSION                   = "flowr.datasource.jpa.version";   
        public static final String DS_DB                            = "flowr.datasource.provider.db";
        public static final String DS_PROVIDER                      = "flowr.datasource.provider";
        public static final String DS_DIALECT                       = "flowr.datasource.dialect";
        public static final String DS_CONNECTION_DRIVER             = "flowr.datasource.connection.driver";
        public static final String DS_CONNECTION_USERNAME           = "flowr.datasource.connection.username";
        public static final String DS_CONNECTION_URL                = "flowr.datasource.connection.url";
        public static final String DS_CONNECTION_CRED               = "flowr.datasource.connection.cred";
        public static final String DS_CONNECTION_POOL_SIZE          = "flowr.datasource.connection.pool_size";
        public static final String DS_QUERY_SHOW                    = "flowr.datasource.query.show";
        public static final String DS_QUERY_FORMAT                  = "flowr.datasource.query.format";
        public static final String DS_QUERY_CACHE                   = "flowr.datasource.query.cache";   
        
        public static final String DS_QUERY_CACHE_EXTERNAL          = "flowr.datasource.query.cache.external";
        public static final String DS_QUERY_CACHE_PROVIDER          = "flowr.datasource.query.cache.provider";
        public static final String DS_QUERY_CACHE_PROVIDER_FACTORY  
        = "flowr.datasource.query.cache.provider.factory";
        public static final String DS_QUERY_CACHE_DEFAULT           = "flowr.datasource.query.cache.default";
        public static final String DS_QUERY_CACHE_TIMESTAMP         = "flowr.datasource.query.cache.timestamp"; 
        
        public static final String DS_MAPPING_ENTITY                = "flowr.datasource.mapping.entity";
        public static final String DS_MAPPING_ENTITY_COUNT          = "flowr.datasource.mapping.count";
        
        public static final String DS_CACHE_ETERNAL                 = "flowr.datasource.cache.eternal";
        public static final String DS_CACHE_NAME                    = "flowr.datasource.cache.name";
        public static final String DS_CACHE_STRATEGY                = "flowr.datasource.cache.strategy";
        public static final String DS_CACHE_PATH                    = "flowr.datasource.cache.path";
        public static final String DS_CACHE_DISK_MAX                = "flowr.datasource.cache.disk.store.max";  
        public static final String DS_CACHE_DISK_OVERFLOW           = "flowr.datasource.cache.disk.overflow";
        public static final String DS_CACHE_DISK_SPOOL              = "flowr.datasource.cache.disk.spool";
        public static final String DS_CACHE_POLICY                  = "flowr.datasource.cache.policy";
        public static final String DS_CACHE_STATISTICS              = "flowr.datasource.cache.statistics";
        public static final String DS_CACHE_ELEMENTS_MEMORY_MAX     = "flowr.datasource.cache.elements.memory.max";
        public static final String DS_CACHE_ELEMENTS_HEAP_MAX       = "flowr.datasource.cache.elements.heap.max";
        public static final String DS_CACHE_ELEMENTS_DISK_MAX       = "flowr.datasource.cache.elements.disk.max";
        public static final String DS_CACHE_ELEMENTS_TIME_TO_IDLE   = "flowr.datasource.cache.elements.timeToIdle";
        public static final String DS_CACHE_ELEMENTS_TIME_TO_LIVE   = "flowr.datasource.cache.elements.timeToLive";
        public static final String DS_CACHE_ELEMENTS_EXPIRY_DISK    = "flowr.datasource.cache.elements.disk.expiry";

        
        public static final int DS_CONFIG_MIN                       = 1;
        
        public static final int SEQUENCE_CHARACTER_START                    = 64;
        public static final int SEQUENCE_CHARACTER_END                      = 90;
        public static final int SEQUENCE_NUMBER_START                       = 49;
        public static final int SEQUENCE_NUMBER_END                         = 57;

        
        private DataSourceConstants() {
            
        }
    }
    
    public final class ClientConstants {

        public static final String CLIENT_SERVICE_SERVER_NAME     = "flowr.client.service.server.name";
        public static final String CLIENT_SERVICE_SERVER_PORT     = "flowr.client.service.server.port";
        public static final String CLIENT_THREADS_MIN             = "flowr.client.service.thread.min";
        public static final String CLIENT_THREADS_MAX             = "flowr.client.service.thread.max";
        public static final String CLIENT_TIMEOUT                 = "flowr.client.service.timeout";
        public static final String CLIENT_TIMEOUT_UNIT            = "flowr.client.service.timeout.unit";
        public static final String CLIENT_NOTIFICATION_ENDPOINT   = "flowr.client.service.notification.endpoint";
        
        public static final String CLIENT_REQUEST_SCALE_MIN       = "flowr.client.request.scale.min";
        public static final String CLIENT_REQUEST_SCALE_MAX       = "flowr.client.request.scale.max";
        public static final String CLIENT_REQUEST_SCALE_RETRY     = "flowr.client.request.scale.retry";
        public static final String CLIENT_REQUEST_SCALE_TIMEOUT   = "flowr.client.request.scale.timeout";
        public static final String CLIENT_REQUEST_SCALE_UNIT      = "flowr.client.request.scale.unit";
        public static final String CLIENT_REQUEST_SCALE_INTERVAL  = "flowr.client.request.scale.interval";

        public static final String CLIENT_REQUEST_SCALE_TIMEOUT_UNIT  = "flowr.client.request.scale.timeout.unit";
        public static final String CLIENT_REQUEST_SCALE_PROGRESS_UNIT = "flowr.client.request.scale.progress.unit";
        public static final String CLIENT_REQUEST_RETRY_INTERVAL      = "flowr.client.request.scale.retry.interval";
        public static final String CLIENT_REQUEST_NOTIFICATION_DELIVERY_TYPE 
            = "flowr.client.request.scale.notification.delivery.type";  
        
        
        // supports up to 100 nodes
        public static final int CLIENT_PIPELINE_ENDPOINT_MIN      = 1;
        public static final int CLIENT_PIPELINE_ENDPOINT_MAX      = 100;
        public static final String CLIENT_PIPELINE_ENDPOINT_NAME  = "flowr.client.pipeline.endpoint.name";
        public static final String CLIENT_PIPELINE_ENDPOINT_URL   = "flowr.client.pipeline.endpoint.url";
        public static final String CLIENT_PIPELINE_FUNCTION_TYPE  = "flowr.client.pipeline.endpoint.functiontype";
        public static final String CLIENT_PIPELINE_ENDPOINT_TYPE  = "flowr.client.pipeline.endpoint.type";
        public static final String CLIENT_PIPELINE_ENDPOINT_TIMEOUT          
            = "flowr.client.pipeline.endpoint.timeout.interval";
        public static final String CLIENT_PIPELINE_ENDPOINT_TIMEOUT_UNIT     
            = "flowr.client.pipeline.endpoint.timeout.unit";
        public static final String CLIENT_PIPELINE_ENDPOINT_HEARTBEAT        
            = "flowr.client.pipeline.endpoint.hearbeat.interval";
        public static final String CLIENT_PIPELINE_ENDPOINT_HEARTBEAT_UNIT   
            = "flowr.client.pipeline.endpoint.hearbeat.unit";

        // supports up to 100 pipelines
        public static final int CLIENT_PIPELINE_MIN               = 1;
        public static final int CLIENT_PIPELINE_MAX               = 100;
        public static final String CLIENT_PIPELINE_NAME           = "flowr.client.pipeline.name";
        public static final String CLIENT_PIPELINE_POLICY_ELEMENT = "flowr.client.pipeline.policy.element";
        public static final String CLIENT_PIPELINE_BATCH_MODE     = "flowr.client.pipeline.dispatch.batch.mode";
        public static final String CLIENT_PIPELINE_BATCH_SIZE     = "flowr.client.pipeline.dispatch.batch.size";
        public static final String CLIENT_PIPELINE_ELEMENT_MAX    = "flowr.client.pipeline.elements.max";

        private ClientConstants() {
            
        }
    }
    
    public final class ServerConstants {

        private static final String BASE                                       = "flowr.server";
        private static final String BASE_ENDPOINT                              = "flowr.server.pipeline.endpoint";
        private static final String BASE_SCALE                                 = "flowr.server.response.progress.scale";
        
        public static final String SERVER_NAME                               = BASE+".name";
        public static final String SERVER_PORT                               = BASE+".port";
        public static final String SERVER_THREADS_MIN                        = BASE+".thread.min";
        public static final String SERVER_THREADS_MAX                        = BASE+".thread.max";
        public static final String SERVER_TIMEOUT                            = BASE+".timeout";
        public static final String SERVER_TIMEOUT_UNIT                       = BASE+".timeout.unit";
        public static final String SERVER_NOTIFICATION_ENDPOINT              = BASE+".notification.endpoint";
        public static final String SERVER_RESPONSE_PROGRESS_SCALE_MIN        = BASE_SCALE+".min";
        public static final String SERVER_RESPONSE_PROGRESS_SCALE_MAX        = BASE_SCALE+".max";
        public static final String SERVER_RESPONSE_PROGRESS_SCALE_TIME_UNIT  = BASE_SCALE+".time.unit";
        public static final String SERVER_RESPONSE_PROGRESS_SCALE_UNIT       = BASE_SCALE+".unit";
        public static final String SERVER_RESPONSE_PROGRESS_SCALE_INTERVAL   = BASE_SCALE+".interval";
        public static final String SERVER_RESPONSE_PROGRESS_NOTIFICATION_DELIVERY_TYPE
            = BASE+".response.progress.scale.notification.delivery.type";
        
        public static final String SERVER_EVENT_BUS_NAME                     = "EventBus";
        public static final String SERVER_NETWORK_BUS_NAME                   = "NetworkBus";

        // supports up to 100 nodes
        public static final int SERVER_PIPELINE_ENDPOINT_MIN                 = 0;
        public static final int SERVER_PIPELINE_ENDPOINT_MAX                 = 100;
        public static final String SERVER_PIPELINE_ENDPOINT_NAME             = BASE_ENDPOINT+".name";
        public static final String SERVER_PIPELINE_FUNCTION_TYPE             = BASE_ENDPOINT+".functiontype";
        public static final String SERVER_PIPELINE_ENDPOINT_URL              = BASE_ENDPOINT+".url";
        public static final String SERVER_PIPELINE_ENDPOINT_TYPE             = BASE_ENDPOINT+".type";
        public static final String SERVER_PIPELINE_ENDPOINT_TIMEOUT          = BASE_ENDPOINT+".timeout.interval";
        public static final String SERVER_PIPELINE_ENDPOINT_TIMEOUT_UNIT     = BASE_ENDPOINT+".timeout.unit";
        public static final String SERVER_PIPELINE_ENDPOINT_HEARTBEAT        = BASE_ENDPOINT+".hearbeat.interval";
        public static final String SERVER_PIPELINE_ENDPOINT_HEARTBEAT_UNIT   = BASE_ENDPOINT+".hearbeat.unit";

        
        // supports up to 100 pipelines
        public static final int SERVER_PIPELINE_MIN                          = 0;
        public static final int SERVER_PIPELINE_MAX                          = 100;
        public static final String SERVER_PIPELINE_NAME                      = BASE+".pipeline.name";
        public static final String SERVER_PIPELINE_POLICY_ELEMENT            = BASE+".pipeline.policy.element";
        public static final String SERVER_PIPELINE_BATCH_MODE                = BASE+".pipeline.dispatch.batch.mode";
        public static final String SERVER_PIPELINE_BATCH_SIZE                = BASE+".pipeline.dispatch.batch.size";
        public static final String SERVER_PIPELINE_ELEMENT_MAX               = BASE+".pipeline.elements.max";
        
        
        private ServerConstants() {
            
        }
    }
    
}

