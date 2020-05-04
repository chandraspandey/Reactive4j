
/**
 * Defines HA as deployment of an integrated Circuit for facilitating automatic fallback & fallforward features
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.node.ha;

import java.util.Collection;

import org.flowr.framework.core.config.Configuration.ConfigurationType;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.EndPoint.EndPointStatus;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.service.ServiceEndPoint;
import org.flowr.framework.core.service.dependency.Dependency;
import org.flowr.framework.core.service.dependency.DependencyLoop;

public interface Circuit extends Dependency, DependencyLoop{

    public enum CircuitStatus{
        AVAILABLE,
        UNAVAILABLE
    }
    
    EndPointStatus addServiceEndpoint(ServiceEndPoint serviceEndPoint) throws ConfigurationException;
        
    EndPointStatus removeServiceEndpoint(ServiceEndPoint serviceEndPoint) throws ConfigurationException;    
    
    void buildCircuit(ConfigurationType configurationType) throws ConfigurationException;
    
    EndPointStatus handleEndPoint(ServiceEndPoint serviceEndPoint) throws ConfigurationException;
    
    void heartbeat() throws ConfigurationException;
    
    void shutdownCircuit();

    Collection<ServiceEndPoint> getAvailableServiceEndPoints(NotificationProtocolType notificationProtocolType);
    
    CircuitStatus getCircuitStatus();
    
    void setCircuitStatus(CircuitStatus circuitStatus);

    Collection<ServiceEndPoint> getAllServiceEndPoints();

    Collection<ServiceEndPoint> getAllAvailableServiceEndPoints();

    Collection<ServiceEndPoint> getAvailableServiceEndPoints(NotificationProtocolType notificationProtocolType,
            PipelineFunctionType pipelineFunctionType);
}
