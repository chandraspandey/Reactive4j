package org.flowr.framework.core.service;

import static org.flowr.framework.core.constants.ExceptionConstants.ERR_CONFIG;
import static org.flowr.framework.core.constants.ExceptionConstants.ERR_CONFIG_INVALID_FORMAT;
import static org.flowr.framework.core.constants.ExceptionConstants.ERR_REQUEST_INVALID;
import static org.flowr.framework.core.constants.ExceptionConstants.ERR_ROUTE_MAPPING_NOT_FOUND;
import static org.flowr.framework.core.constants.ExceptionMessages.MSG_CONFIG;
import static org.flowr.framework.core.constants.ExceptionMessages.MSG_CONFIG_INVALID;
import static org.flowr.framework.core.constants.ExceptionMessages.MSG_REQUEST_INVALID;
import static org.flowr.framework.core.constants.ExceptionMessages.MSG_REQUEST_TYPE_NOT_FOUND;
import static org.flowr.framework.core.constants.ExceptionMessages.MSG_ROUTE_MAPPING_NOT_FOUND;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.flowr.framework.core.config.ServiceConfiguration;
import org.flowr.framework.core.context.PromiseContext;
import org.flowr.framework.core.context.ServerContext;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.exception.PromiseException;
import org.flowr.framework.core.notification.Notification.ServerNotificationProtocolType;
import org.flowr.framework.core.promise.PromiseRequest;
import org.flowr.framework.core.promise.PromiseResponse;
import org.flowr.framework.core.promise.phase.PhasedProgressScale;
import org.flowr.framework.core.promise.phase.PhasedService.ServicePhase;
import org.flowr.framework.core.target.ReactiveTarget;

public abstract class ServiceFrameworkProvider<REQUEST,RESPONSE> extends ServiceBus<REQUEST,RESPONSE> {

	//private ServiceConfiguration serviceConfiguration   	= null;
	private static ServerContext serverContext 				= new ServerContext();
	
	public void setServerSubscriptionIdentifier(String subscriptionIdentifier) {
		
		serverContext.setSubscriptionClientId(subscriptionIdentifier);
	}

	@Override
	public PromiseResponse<RESPONSE> service(PromiseRequest<REQUEST,RESPONSE> promiseRequest) throws PromiseException,
		ConfigurationException{
		
		PromiseResponse<RESPONSE> response = null;		
		
		try {
		
			if(promiseRequest != null && promiseRequest.getRequestType() != null){
				
				PromiseContext<REQUEST> promiseContext = promiseRequest.getPromiseContext();
				
				if( 	promiseContext != null && promiseContext.getRequestScale() != null &&
						promiseContext.getRequestScale().getSubscriptionClientId() != null){
				
					Class<? extends ServiceResponse> responseHandler = this.getRoutingService().getServiceRoute(promiseRequest);
					
						if(responseHandler != null){
								
							@SuppressWarnings("unchecked")
							Class<? extends ServiceResponse> klass = 
									(Class<? extends ServiceResponse>) Class.forName(responseHandler.getCanonicalName());
							
							ReactiveTarget<REQUEST, RESPONSE> reactiveTarget = ((ServiceResponse) klass.getDeclaredConstructor().newInstance(new Object[0])).getReactiveTarget();

							System.out.println(" ServiceFrameworkProvider : "+
									"responseHandler : "+ responseHandler.getSimpleName()+
									" | reactiveTarget : "+reactiveTarget.getClass().getSimpleName()+
									" | RequestType : "+promiseRequest.getRequestType()+
									" | ClientIdentity "+promiseRequest.getClientIdentity()
							);
							
							
							switch(promiseRequest.getRequestType()){
								
								case PROMISE:{
									this.getPromiseService().getPromise().setReactiveTarget(reactiveTarget);
									response = this.getPromiseService().getPromise().handle(promiseRequest);
									break;
								}case PROMISE_DEFFERED:{
									this.getDefferedPromiseService().getPromise().setReactiveTarget(reactiveTarget);
									response = this.getDefferedPromiseService().getPromise().handle(promiseRequest);
									break;
								}case PROMISE_PHASED:{
									
									this.getPhasedPromiseService().getPromise().setReactiveTarget(reactiveTarget);
									response = this.getPhasedPromiseService().getPromise().handle(promiseRequest);
									PhasedProgressScale phasedProgressScale = (PhasedProgressScale) 
											response.getProgressScale();
									
									if(phasedProgressScale.getServicePhase() == ServicePhase.COMPLETE){
										response.setStreamValues(this.getPhasedPromiseService().getPromise().done());
										
									}
									break;
								}case PROMISE_SCHEDULED:{
									this.getScheduledPromiseService().getPromise().setReactiveTarget(reactiveTarget);
									response = this.getScheduledPromiseService().getPromise().handle(promiseRequest);
									break;
								}case PROMISE_STAGED:{
									break;
								}default:{
									this.getPromiseService().getPromise().setReactiveTarget(reactiveTarget);
									response = this.getPromiseService().getPromise().handle(promiseRequest);
									break;
								}
							}
							
							response.setStatus(HttpServletResponse.SC_OK);
						
						}else{
							
							response = new PromiseResponse<RESPONSE>(); 				
							response.setStatus(HttpServletResponse.SC_NOT_FOUND);				
							response.setErrorMessage(MSG_ROUTE_MAPPING_NOT_FOUND+
								"Configuration mapping for request class "+promiseRequest.getClass().getName()+" does not exists.");				
						}
				}else{
					
					throw new ConfigurationException(ERR_REQUEST_INVALID,MSG_REQUEST_INVALID,
							"Invalid PromiseContext, RequestScale or Subscription id : "+promiseContext);
				}
			}else{
				
				response = new PromiseResponse<RESPONSE>(); 				
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);				
				response.setErrorMessage(MSG_REQUEST_TYPE_NOT_FOUND+
					"Request Type not provided for the request."+promiseRequest.getClass().getName());	
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchMethodException e) {
			e.printStackTrace();
			throw new ConfigurationException(ERR_ROUTE_MAPPING_NOT_FOUND,MSG_ROUTE_MAPPING_NOT_FOUND, 
					"Unable to instantiate mapping classes.");
		}
		
		return response;
	}
	
	@Override
	public ServiceConfiguration loadConfiguration(Properties configProperties) throws ConfigurationException {
		
		ServiceConfiguration serviceConfiguration = new ServiceConfiguration();		
		
		if(configProperties != null ){
			
			String serverName 	= configProperties.getProperty("reactive.server.name");
			String serverPort 	= configProperties.getProperty("reactive.server.port");
			String timeout	 	= configProperties.getProperty("reactive.server.timeout");
			String minThread 	= configProperties.getProperty("reactive.server.thread.min","1");
			String maxThread 	= configProperties.getProperty("reactive.server.thread.max","1");
			String notification = configProperties.getProperty("reactive.server.notification.endpoint");
			
			if(serverName != null && serverPort != null && minThread != null && maxThread != null){
				
				serviceConfiguration.setServerName(serverName);		
				serviceConfiguration.setTimeout(Integer.parseInt(timeout));
				serviceConfiguration.setServerPort(Integer.parseInt(serverPort));					
				serviceConfiguration.setMIN_THREADS(Integer.parseInt(minThread));
				serviceConfiguration.setMAX_THREADS(Integer.parseInt(maxThread));
				serviceConfiguration.setNotificationEndPoint(notification);
			}else{
				throw new ConfigurationException(ERR_CONFIG_INVALID_FORMAT,	MSG_CONFIG_INVALID, 
					configProperties.toString());
			}
			
		}else{
			
			throw new ConfigurationException(ERR_CONFIG,MSG_CONFIG, 
					"Configuration Properties not provided for execution.");
		}

		return serviceConfiguration;
	}

	public ServiceStatus startup(Properties configProperties){
		
		System.out.println("ServerEngine : startup :");
		
		super.getNotificationService().startup(configProperties);

		serverContext.setServiceMode(ServiceMode.SERVER);
		serverContext.setServiceState(ServiceState.INITIALIZING);

		serverContext.setNotificationProtocolType(ServerNotificationProtocolType.INFORMATION); 
		
		
		try {

				loadConfiguration(configProperties);
			
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		serverContext.setServiceState(ServiceState.STARTING);
		serverContext.setServiceStatus(ServiceStatus.STARTED);
		
		return ServiceStatus.STARTED;
	}
	
	@Override
	public ServiceStatus shutdown(Properties configProperties) {
				
		serverContext.setServiceMode(ServiceMode.SERVER);
		serverContext.setServiceState(ServiceState.STOPPING);
		serverContext.setNotificationProtocolType(ServerNotificationProtocolType.INFORMATION);
		
		
		
		this.getPromiseService().shutdown(configProperties);
		this.getPhasedPromiseService().getPromise().shutdown();
		this.getScheduledPromiseService().getPromise().shutdown();		


		serverContext.setServiceStatus(ServiceStatus.STOPPED);
		
		super.getNotificationService().shutdown(configProperties);
		
		

		return ServiceStatus.STOPPED;
	}
	
	/*public void update( Object change) {
		
		System.out.println("ProcessServerProvider |  change : "+
		change.getClass().getSimpleName()+" | update recieved : "+change);

		EventContext eventContext = new EventContext();
		
		eventContext.setSubscriptionClientId(serverContext.getSubscriptionClientId());		
		
		eventContext.setChange(change);		
		
		// Need mechanism for retrieving MetaData
		eventContext.setReactiveMetaData(null);
		
		try {
			super.getNotificationService().notify(eventContext);
		} catch (ClientException clientException) {
			System.out.println("ServerEngine : Unable to notify client :"+clientException.getContextMessage());
			clientException.printStackTrace();
		}		
		
		this.getEventService().process();
	}*/

}
