package org.flowr.framework.core.notification;

import static org.flowr.framework.core.constants.ExceptionConstants.ERR_CONFIG;
import static org.flowr.framework.core.constants.ExceptionMessages.MSG_CONFIG;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import org.flowr.framework.core.config.Configuration.ConfigurationType;
import org.flowr.framework.core.constants.FrameworkConstants;
import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.event.pipeline.EventBus;
import org.flowr.framework.core.event.pipeline.EventPipeline;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineType;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.flow.BatchEventPublisher;
import org.flowr.framework.core.node.Circuit;
import org.flowr.framework.core.node.CircuitBreaker;
import org.flowr.framework.core.node.EndPointDispatcher;
import org.flowr.framework.core.notification.Notification.ClientNotificationProtocolType;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.Notification.ServerNotificationProtocolType;
import org.flowr.framework.core.service.ServiceEndPoint;
import org.flowr.framework.core.service.ServiceLifecycle;

/**
 * Extends EventPublisher to provide Notification Adapter configuration  required for notification.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface NotificationServiceAdapter extends BatchEventPublisher, ServiceLifecycle,CircuitBreaker{

	/*
	 * Provides Event handling integration capability based on the Event type qualified as CLIENT or SERVER
	 * CLIENT : Defined for discrete implementation of Notification servicing client side notification
	 * SERVER : Defined for discrete implementation of Notification servicing server side notification
	 */
	public enum NotificationServiceAdapterType{
		CLIENT,
		SERVER;
		
		public boolean equals(EventType eventType){
				
			boolean isEqual = false;
			
			if(this.name().equals(eventType.toString())){
				isEqual = true;
			}
			
			return isEqual;
		}
	}
	
	public void setNotificationServiceAdapterType(NotificationServiceAdapterType notificationServiceAdapterType);
	
	public NotificationServiceAdapterType getNotificationServiceAdapterType();
	
	public void setNotificationServiceAdapterName(String notificationServiceAdapterName);
	
	public String getNotificationServiceAdapterName();
	
	public HashMap<NotificationProtocolType,EndPointDispatcher> buildPipelineDispatcher(
			Circuit circuit,
			EventBus eventBus, ConfigurationType configurationType,
			Class<? extends EndPointDispatcher> dispatcher ) throws ConfigurationException;	
	
	/**
	 * Weaves NotificationTask to the adapter to address different NotificationProtocolTypes
	 * @param notificationTask
	 */
	public void configure(NotificationTask notificationTask);
	
	public abstract class DefaultAdapter implements NotificationServiceAdapter{

		@Override
		public HashMap<NotificationProtocolType,EndPointDispatcher> buildPipelineDispatcher(
			Circuit circuit,
			EventBus eventBus, ConfigurationType configurationType,
			Class<? extends EndPointDispatcher> dispatcher ) throws ConfigurationException{

			HashMap<NotificationProtocolType,EndPointDispatcher> dispatcherMap 	= null;
			
			if(configurationType == ConfigurationType.CLIENT) {
				
				dispatcherMap 	=  buildClientPipelineDispatcher(circuit,eventBus,dispatcher);
			}else if(configurationType == ConfigurationType.SERVER) {
				dispatcherMap 	=  buildServerPipelineDispatcher(circuit,eventBus,dispatcher);
			}
			
			return dispatcherMap;
		}
		
		private HashMap<NotificationProtocolType,EndPointDispatcher> buildClientPipelineDispatcher(Circuit clientCircuit,
			EventBus eventBus,Class<? extends EndPointDispatcher> notificationProtocolDispatcher ) throws ConfigurationException{
			
			
			HashMap<NotificationProtocolType,EndPointDispatcher> dispatcherMap 	= 
					new HashMap<NotificationProtocolType,EndPointDispatcher>();

			if(clientCircuit != null && eventBus != null) {
					
				Arrays.asList(ClientNotificationProtocolType.values()).forEach(
											
					(protocolType) ->{
							
						try {	
							
							switch(protocolType) {
															
								case CLIENT_EMAIL:{
									EventPipeline integrationPipeline  =  new EventPipeline();
									integrationPipeline.setPipelineName("PIPELINE_EMAIL");
									integrationPipeline.setPipelineType(PipelineType.TRANSFER);
									integrationPipeline.setPipelineFunctionType(PipelineFunctionType.USECASE);	
									integrationPipeline.setEventType(EventType.CLIENT);
									
									Collection<ServiceEndPoint> serviceEndPointList = clientCircuit.getAvailableServiceEndPoints(
											protocolType);
									EndPointDispatcher dispatcher = notificationProtocolDispatcher.getDeclaredConstructor().newInstance(new Object[0]);
									dispatcher.configure(protocolType, serviceEndPointList,integrationPipeline);
									
									dispatcherMap.put(protocolType, dispatcher);
									eventBus.addEventPipeline(integrationPipeline);
								}case CLIENT_HEALTHCHECK:{
									EventPipeline integrationPipeline  =  new EventPipeline();
									integrationPipeline.setPipelineName(FrameworkConstants.FRAMEWORK_PIPELINE_HEALTH);
									integrationPipeline.setPipelineType(PipelineType.TRANSFER);
									integrationPipeline.setPipelineFunctionType(PipelineFunctionType.HEALTH);	
									integrationPipeline.setEventType(EventType.CLIENT);
									
									Collection<ServiceEndPoint> serviceEndPointList = clientCircuit.getAvailableServiceEndPoints(
											protocolType);
									EndPointDispatcher dispatcher = notificationProtocolDispatcher.getDeclaredConstructor().newInstance(new Object[0]);
									dispatcher.configure(protocolType, serviceEndPointList,integrationPipeline);
									
									dispatcherMap.put(protocolType, dispatcher);
									eventBus.addEventPipeline(integrationPipeline);
									break;
								}case CLIENT_INTEGRATION:{
									
									EventPipeline integrationPipeline  =  new EventPipeline();
									integrationPipeline.setPipelineName(FrameworkConstants.FRAMEWORK_PIPELINE_SERVICE);
									integrationPipeline.setPipelineType(PipelineType.TRANSFER);
									integrationPipeline.setPipelineFunctionType(PipelineFunctionType.PIPELINE_CLIENT_SERVICE);	
									integrationPipeline.setEventType(EventType.CLIENT);
									
									Collection<ServiceEndPoint> serviceEndPointList = clientCircuit.getAvailableServiceEndPoints(
											protocolType);
									EndPointDispatcher dispatcher = notificationProtocolDispatcher.getDeclaredConstructor().newInstance(new Object[0]);
									dispatcher.configure(protocolType, serviceEndPointList,integrationPipeline);
									
									dispatcherMap.put(protocolType, dispatcher);
									eventBus.addEventPipeline(integrationPipeline);
									
								}case CLIENT_INTEGRATION_PIPELINE_NOTIFICATION_EVENT:{
									EventPipeline integrationPipeline  =  new EventPipeline();
									integrationPipeline.setPipelineName(FrameworkConstants.FRAMEWORK_PIPELINE_NOTIFICATION);
									integrationPipeline.setPipelineType(PipelineType.TRANSFER);
									integrationPipeline.setPipelineFunctionType(PipelineFunctionType.NOTIFICATION);	
									integrationPipeline.setEventType(EventType.CLIENT);
									
									Collection<ServiceEndPoint> serviceEndPointList = clientCircuit.getAvailableServiceEndPoints(
											protocolType);
									EndPointDispatcher dispatcher = notificationProtocolDispatcher.getDeclaredConstructor().newInstance(new Object[0]);
									dispatcher.configure(protocolType, serviceEndPointList,integrationPipeline);
									
									dispatcherMap.put(protocolType, dispatcher);
									eventBus.addEventPipeline(integrationPipeline);
									break;
								}case CLIENT_INTEGRATION_PIPELINE_PROMISE_DEFFERED_EVENT:{
									EventPipeline integrationPipeline  =  new EventPipeline();
									integrationPipeline.setPipelineName(FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE_DEFFERED);
									integrationPipeline.setPipelineType(PipelineType.TRANSFER);
									integrationPipeline.setPipelineFunctionType(PipelineFunctionType.PIPELINE_PROMISE_DEFFERED_EVENT);	
									integrationPipeline.setEventType(EventType.CLIENT);
									
									Collection<ServiceEndPoint> serviceEndPointList = clientCircuit.getAvailableServiceEndPoints(
											protocolType);
									EndPointDispatcher dispatcher = notificationProtocolDispatcher.getDeclaredConstructor().newInstance(new Object[0]);
									dispatcher.configure(protocolType, serviceEndPointList,integrationPipeline);
									
									dispatcherMap.put(protocolType, dispatcher);
									eventBus.addEventPipeline(integrationPipeline);
									break;
								}case CLIENT_INTEGRATION_PIPELINE_PROMISE_EVENT:{
									EventPipeline integrationPipeline  =  new EventPipeline();
									integrationPipeline.setPipelineName(FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE);
									integrationPipeline.setPipelineType(PipelineType.TRANSFER);
									integrationPipeline.setPipelineFunctionType(PipelineFunctionType.PIPELINE_PROMISE_EVENT);	
									integrationPipeline.setEventType(EventType.CLIENT);
									
									Collection<ServiceEndPoint> serviceEndPointList = clientCircuit.getAvailableServiceEndPoints(
											protocolType);
									EndPointDispatcher dispatcher = notificationProtocolDispatcher.getDeclaredConstructor().newInstance(new Object[0]);
									dispatcher.configure(protocolType, serviceEndPointList,integrationPipeline);
									
									dispatcherMap.put(protocolType, dispatcher);
									eventBus.addEventPipeline(integrationPipeline);
									break;
								}case CLIENT_INTEGRATION_PIPELINE_PROMISE_MAP_EVENT:{
									EventPipeline integrationPipeline  =  new EventPipeline();
									integrationPipeline.setPipelineName(FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE_MAP);
									integrationPipeline.setPipelineType(PipelineType.TRANSFER);
									integrationPipeline.setPipelineFunctionType(PipelineFunctionType.PIPELINE_PROMISE_MAP_EVENT);	
									integrationPipeline.setEventType(EventType.CLIENT);
									
									Collection<ServiceEndPoint> serviceEndPointList = clientCircuit.getAvailableServiceEndPoints(
											protocolType);
									EndPointDispatcher dispatcher = notificationProtocolDispatcher.getDeclaredConstructor().newInstance(new Object[0]);
									dispatcher.configure(protocolType, serviceEndPointList,integrationPipeline);
									
									dispatcherMap.put(protocolType, dispatcher);
									eventBus.addEventPipeline(integrationPipeline);
									break;
								}case CLIENT_INTEGRATION_PIPELINE_PROMISE_PHASED_EVENT:{
									EventPipeline integrationPipeline  =  new EventPipeline();
									integrationPipeline.setPipelineName(FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE_PHASED);
									integrationPipeline.setPipelineType(PipelineType.TRANSFER);
									integrationPipeline.setPipelineFunctionType(PipelineFunctionType.PIPELINE_PROMISE_PHASED_EVENT);	
									integrationPipeline.setEventType(EventType.CLIENT);
									
									Collection<ServiceEndPoint> serviceEndPointList = clientCircuit.getAvailableServiceEndPoints(
											protocolType);
									EndPointDispatcher dispatcher = notificationProtocolDispatcher.getDeclaredConstructor().newInstance(new Object[0]);
									dispatcher.configure(protocolType, serviceEndPointList,integrationPipeline);
									
									dispatcherMap.put(protocolType, dispatcher);
									eventBus.addEventPipeline(integrationPipeline);
									break;
								}case CLIENT_INTEGRATION_PIPELINE_PROMISE_SCHEDULED_EVENT:{
									EventPipeline integrationPipeline  =  new EventPipeline();
									integrationPipeline.setPipelineName(FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE_SCHEDULED);
									integrationPipeline.setPipelineType(PipelineType.TRANSFER);
									integrationPipeline.setPipelineFunctionType(PipelineFunctionType.PIPELINE_PROMISE_SCHEDULED_EVENT);	
									integrationPipeline.setEventType(EventType.CLIENT);
									
									Collection<ServiceEndPoint> serviceEndPointList = clientCircuit.getAvailableServiceEndPoints(
											protocolType);
									EndPointDispatcher dispatcher = notificationProtocolDispatcher.getDeclaredConstructor().newInstance(new Object[0]);
									dispatcher.configure(protocolType, serviceEndPointList,integrationPipeline);
									
									dispatcherMap.put(protocolType, dispatcher);
									eventBus.addEventPipeline(integrationPipeline);
									break;
								}case CLIENT_INTEGRATION_PIPELINE_PROMISE_STAGE_EVENT:{
									EventPipeline integrationPipeline  =  new EventPipeline();
									integrationPipeline.setPipelineName(FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE_STAGE);
									integrationPipeline.setPipelineType(PipelineType.TRANSFER);
									integrationPipeline.setPipelineFunctionType(PipelineFunctionType.PIPELINE_PROMISE_STAGE_EVENT);	
									integrationPipeline.setEventType(EventType.CLIENT);
									
									Collection<ServiceEndPoint> serviceEndPointList = clientCircuit.getAvailableServiceEndPoints(
											protocolType);
									EndPointDispatcher dispatcher = notificationProtocolDispatcher.getDeclaredConstructor().newInstance(new Object[0]);
									dispatcher.configure(protocolType, serviceEndPointList,integrationPipeline);
									
									dispatcherMap.put(protocolType, dispatcher);
									eventBus.addEventPipeline(integrationPipeline);
									break;
								}case CLIENT_INTEGRATION_PIPELINE_PROMISE_STREAM_EVENT:{
									EventPipeline integrationPipeline  =  new EventPipeline();
									integrationPipeline.setPipelineName(FrameworkConstants.FRAMEWORK_PIPELINE_PROMISE_STREAM);
									integrationPipeline.setPipelineType(PipelineType.TRANSFER);
									integrationPipeline.setPipelineFunctionType(PipelineFunctionType.PIPELINE_PROMISE_STREAM_EVENT);	
									integrationPipeline.setEventType(EventType.CLIENT);
									
									Collection<ServiceEndPoint> serviceEndPointList = clientCircuit.getAvailableServiceEndPoints(
											protocolType);
									EndPointDispatcher dispatcher = notificationProtocolDispatcher.getDeclaredConstructor().newInstance(new Object[0]);
									dispatcher.configure(protocolType, serviceEndPointList,integrationPipeline);
									
									dispatcherMap.put(protocolType, dispatcher);
									eventBus.addEventPipeline(integrationPipeline);
									break;
								}case CLIENT_PUSH_NOTIFICATION:{
									EventPipeline integrationPipeline  =  new EventPipeline();
									integrationPipeline.setPipelineName("PIPELINE_PUSH_NOTIFICATION");
									integrationPipeline.setPipelineType(PipelineType.TRANSFER);
									integrationPipeline.setPipelineFunctionType(PipelineFunctionType.USECASE);	
									integrationPipeline.setEventType(EventType.CLIENT);
									
									Collection<ServiceEndPoint> serviceEndPointList = clientCircuit.getAvailableServiceEndPoints(
											protocolType);
									EndPointDispatcher dispatcher = notificationProtocolDispatcher.getDeclaredConstructor().newInstance(new Object[0]);
									dispatcher.configure(protocolType, serviceEndPointList,integrationPipeline);
									
									dispatcherMap.put(protocolType, dispatcher);
									eventBus.addEventPipeline(integrationPipeline);
									break;
								}case CLIENT_SMS:{
									EventPipeline integrationPipeline  =  new EventPipeline();
									integrationPipeline.setPipelineName("PIPELINE_SMS");
									integrationPipeline.setPipelineType(PipelineType.TRANSFER);
									integrationPipeline.setPipelineFunctionType(PipelineFunctionType.USECASE);	
									integrationPipeline.setEventType(EventType.CLIENT);
									
									Collection<ServiceEndPoint> serviceEndPointList = clientCircuit.getAvailableServiceEndPoints(
											protocolType);
									EndPointDispatcher dispatcher = notificationProtocolDispatcher.getDeclaredConstructor().newInstance(new Object[0]);
									dispatcher.configure(protocolType, serviceEndPointList,integrationPipeline);
									
									dispatcherMap.put(protocolType, dispatcher);
									eventBus.addEventPipeline(integrationPipeline);
									break;
			
								}case CLIENT_SERVICE:{
									EventPipeline integrationPipeline  =  new EventPipeline();
									integrationPipeline.setPipelineName(FrameworkConstants.FRAMEWORK_PIPELINE_SERVICE);
									integrationPipeline.setPipelineType(PipelineType.TRANSFER);
									integrationPipeline.setPipelineFunctionType(PipelineFunctionType.PIPELINE_CLIENT_SERVICE);	
									integrationPipeline.setEventType(EventType.CLIENT);
									
									Collection<ServiceEndPoint> serviceEndPointList = clientCircuit.getAvailableServiceEndPoints(
											protocolType);
									EndPointDispatcher dispatcher = notificationProtocolDispatcher.getDeclaredConstructor().newInstance(new Object[0]);
									dispatcher.configure(protocolType, serviceEndPointList,integrationPipeline);
									
									dispatcherMap.put(protocolType, dispatcher);
									eventBus.addEventPipeline(integrationPipeline);
									break;
			
								}default:{
									break;
								}
							}
						}catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
								| NoSuchMethodException | SecurityException e) {
							e.printStackTrace();
						}
							
					}
				);
			}else {
				throw new ConfigurationException(
						ERR_CONFIG,
						MSG_CONFIG, 
						"Circuit & EventBus not configured for pipeline creation.");
			}
			return dispatcherMap;
			
		}
		
		
		public HashMap<NotificationProtocolType, EndPointDispatcher> buildServerPipelineDispatcher(Circuit serverCircuit,
			EventBus eventBus,Class<? extends EndPointDispatcher> notificationProtocolDispatcher ) 	throws ConfigurationException {
			
			HashMap<NotificationProtocolType,EndPointDispatcher> dispatcherMap 	= 
					new HashMap<NotificationProtocolType,EndPointDispatcher>();

			if(serverCircuit != null && eventBus != null) {
			
				Arrays.asList(ServerNotificationProtocolType.values()).forEach(
						
					(protocolType) ->{
						
						try {
						
						switch(protocolType) {
												
							case SERVER_ALL:{
								EventPipeline integrationPipeline  =  new EventPipeline();
								integrationPipeline.setPipelineName(FrameworkConstants.FRAMEWORK_PIPELINE_MANAGEMENT);
								integrationPipeline.setPipelineType(PipelineType.TRANSFER);
								integrationPipeline.setPipelineFunctionType(PipelineFunctionType.ALL);	
								integrationPipeline.setEventType(EventType.SERVER);
								
								Collection<ServiceEndPoint> serviceEndPointList = serverCircuit.getAvailableServiceEndPoints(
										protocolType);
								EndPointDispatcher dispatcher = notificationProtocolDispatcher.getDeclaredConstructor().newInstance(new Object[0]);

								dispatcher.configure(protocolType, serviceEndPointList,integrationPipeline);
								
								dispatcherMap.put(protocolType, dispatcher);
								eventBus.addEventPipeline(integrationPipeline);
								break;
							}case SERVER_HEALTHCHECK:{
								EventPipeline integrationPipeline  =  new EventPipeline();
								integrationPipeline.setPipelineName(FrameworkConstants.FRAMEWORK_PIPELINE_HEALTH);
								integrationPipeline.setPipelineType(PipelineType.TRANSFER);
								integrationPipeline.setPipelineFunctionType(PipelineFunctionType.HEALTH);	
								integrationPipeline.setEventType(EventType.HEALTHCHECK);
								
								Collection<ServiceEndPoint> serviceEndPointList = serverCircuit.getAvailableServiceEndPoints(
										protocolType);
								EndPointDispatcher dispatcher = notificationProtocolDispatcher.getDeclaredConstructor().newInstance(new Object[0]);
								dispatcher.configure(protocolType, serviceEndPointList,integrationPipeline);
								
								dispatcherMap.put(protocolType, dispatcher);
								eventBus.addEventPipeline(integrationPipeline);
								break;
							}case SERVER_INFORMATION:{
								EventPipeline integrationPipeline  =  new EventPipeline();
								integrationPipeline.setPipelineName(FrameworkConstants.FRAMEWORK_SERVICE_EVENT);
								integrationPipeline.setPipelineType(PipelineType.TRANSFER);
								integrationPipeline.setPipelineFunctionType(PipelineFunctionType.PIPELINE_SERVER_SERVICE);	
								integrationPipeline.setEventType(EventType.SERVER);
								
								Collection<ServiceEndPoint> serviceEndPointList = serverCircuit.getAvailableServiceEndPoints(
										protocolType);
								EndPointDispatcher dispatcher = notificationProtocolDispatcher.getDeclaredConstructor().newInstance(new Object[0]);
								dispatcher.configure(protocolType, serviceEndPointList,integrationPipeline);
								
								dispatcherMap.put(protocolType, dispatcher);
								eventBus.addEventPipeline(integrationPipeline);
								break;
							}case SERVER_INTEGRATION:{
								EventPipeline integrationPipeline  =  new EventPipeline();
								integrationPipeline.setPipelineName(FrameworkConstants.FRAMEWORK_SERVICE_MANAGEMENT);
								integrationPipeline.setPipelineType(PipelineType.TRANSFER);
								integrationPipeline.setPipelineFunctionType(PipelineFunctionType.PIPELINE_SERVER_INTEGRATION);	
								integrationPipeline.setEventType(EventType.SERVER);
								
								Collection<ServiceEndPoint> serviceEndPointList = serverCircuit.getAvailableServiceEndPoints(
										protocolType);
								EndPointDispatcher dispatcher = notificationProtocolDispatcher.getDeclaredConstructor().newInstance(new Object[0]);
								dispatcher.configure(protocolType, serviceEndPointList,integrationPipeline);
								
								dispatcherMap.put(protocolType, dispatcher);
								eventBus.addEventPipeline(integrationPipeline);
								break;
							}default:
								break;
							
							}
						}catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
								| NoSuchMethodException | SecurityException e) {
							e.printStackTrace();
						}
					}
				);
			}else {
				throw new ConfigurationException(
						ERR_CONFIG,
						MSG_CONFIG, 
						"Circuit & EventBus not configured for pipeline creation.");

			}
			
			return dispatcherMap;
		}
	}
}
