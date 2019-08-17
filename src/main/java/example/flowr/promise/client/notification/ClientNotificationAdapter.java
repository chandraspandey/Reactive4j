package example.flowr.promise.client.notification;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.flowr.framework.core.config.Configuration.ConfigurationType;
import org.flowr.framework.core.event.Event;
import org.flowr.framework.core.event.pipeline.EventBus;
import org.flowr.framework.core.event.pipeline.EventPipelineBus;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.flow.EventPublisher;
import org.flowr.framework.core.model.EventModel;
import org.flowr.framework.core.node.Autonomic.ResponseCode;
import org.flowr.framework.core.node.EndPoint.EndPointStatus;
import org.flowr.framework.core.node.EndPointDispatcher;
import org.flowr.framework.core.node.ha.Circuit;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.NotificationBufferQueue;
import org.flowr.framework.core.notification.NotificationServiceAdapter.DefaultAdapter;
import org.flowr.framework.core.notification.NotificationTask;
import org.flowr.framework.core.service.Service.ServiceState;
import org.flowr.framework.core.service.Service.ServiceStatus;
import org.flowr.framework.core.service.ServiceEndPoint;
import org.flowr.framework.core.service.ServiceFramework;
import org.flowr.framework.core.service.internal.NotificationService.NotificationServiceMode;
import org.flowr.framework.core.service.internal.NotificationService.NotificationServiceStatus;

import example.flowr.node.NodeProvider;
import example.flowr.notification.NotificationProtocolDispatcher;


public class ClientNotificationAdapter extends DefaultAdapter {

	private String flowName 								= ClientNotificationAdapter.class.getSimpleName();
	private boolean isEnabled								= true;
	private FlowPublisherType flowPublisherType				= FlowPublisherType.FLOW_PUBLISHER_SERVER;
	private FlowFunctionType flowFunctionType 				= FlowFunctionType.DISSEMINATOR;  
	private FlowType flowType								= FlowType.ENDPOINT;
	private Logger logger 									= Logger.getLogger(ClientNotificationAdapter.class);
	private static ExecutorService service 					= Executors.newSingleThreadScheduledExecutor();
	private NotificationServiceMode mode 					= NotificationServiceMode.CENTRAL;
	private NotificationTask notificationTask				= null;
	private String notificationServiceAdapterName			= null;
	private ServiceStatus serviceStatus 					= null;
	private static Circuit clientCircuit					= null;
	private NodeProvider nodeProvider 						= new NodeProvider();
	private static EventBus eventBus						= new EventPipelineBus();
	@SuppressWarnings("unchecked")
	private Subscriber<? super Event<EventModel>> subscriber=  (Subscriber<? super Event<EventModel>>) new ClientAdministrator() ;
	private HashMap<NotificationProtocolType,EndPointDispatcher> dispatcherMap 	= null;
	private NotificationServiceAdapterType notificationServiceAdapterType = NotificationServiceAdapterType.CLIENT;
	private EventPublisher serviceListener					= this;


	@Override
	public void configure(NotificationTask notificationTask) {
		
		try {
			clientCircuit	= ServiceFramework.getInstance().getHighAvailabilityService().getCircuit(ConfigurationType.CLIENT);
			dispatcherMap 	= super.buildPipelineDispatcher(clientCircuit, eventBus, ConfigurationType.CLIENT,
					NotificationProtocolDispatcher.class);
			this.notificationTask = notificationTask;
			this.notificationTask.configure(dispatcherMap);
		} catch (ConfigurationException e) {
			
			System.err.println(e.getLocalizedMessage());
			//e.printStackTrace();
		}
	}
	
	@Override
	public void publishEvent(NotificationBufferQueue notificationBufferQueue) {
				
		notificationTask.setNotificationBufferQueue(notificationBufferQueue);
		
		//System.out.println("ClientNotificationAdapter : notificationTask : "+notificationBufferQueue);
		
		//System.out.println("ServerNotificationAdapter : service : "+service);
		
		Future<HashMap<NotificationProtocolType, NotificationServiceStatus>> notificationServiceFuture = service.submit(notificationTask);
		
		HashMap<NotificationProtocolType, NotificationServiceStatus> statusMap = processNotification(notificationServiceFuture);
		
		//System.out.println("NotificationAdapter : Event<EventModel> :"+event);
		//logger.info("NotificationAdapter : Notification published : "+event);
		
		if( 	statusMap != null && ! statusMap.isEmpty() &&
				(
						statusMap.values().contains(NotificationServiceStatus.ERROR) ||
						statusMap.values().contains(NotificationServiceStatus.TIMEOUT)
				)
		){
			handleError(statusMap);
		}
	}
	
	public void handleError(HashMap<NotificationProtocolType, NotificationServiceStatus> statusMap){
		
		@SuppressWarnings("unused")
		ResponseCode responseCode;
		
		statusMap.forEach(
			
			(k,v) -> {
				
				if(
						v == NotificationServiceStatus.ERROR ||
						v == NotificationServiceStatus.TIMEOUT
				){
										
					Collection<ServiceEndPoint> endPointList = clientCircuit.getAvailableServiceEndPoints(k);
					
					Iterator<ServiceEndPoint> endPointIterator = endPointList.iterator();
					
					while(endPointIterator.hasNext()){
						
						ServiceEndPoint serviceEndPoint = endPointIterator.next();
						
						if(serviceEndPoint.getEndPointStatus() == EndPointStatus.UNREACHABLE ){
							
							try {
								suspendOperationOn(serviceEndPoint, serviceEndPoint.getEndPointStatus(),v);
							} catch (ConfigurationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
		);
		
		
			
	}
	
	public HashMap<NotificationProtocolType, NotificationServiceStatus> processNotification(
			Future<HashMap<NotificationProtocolType, NotificationServiceStatus>> notificationServiceFuture){
		
		HashMap<NotificationProtocolType, NotificationServiceStatus> statusMap = null;
		
		//System.out.println("NotificationAdapter : processNotification :");
		
		while(!notificationServiceFuture.isDone()){
			
			try {
					Thread.sleep(1000);
				
					statusMap = notificationServiceFuture.get();
				
				System.out.println("ClientNotificationAdapter : notificationServiceStatus :"+statusMap);
				//logger.info("ClientNotificationAdapter : NotificationServiceStatus : "+notificationServiceStatus);
			} catch (InterruptedException | ExecutionException e) {
				logger.error("ClientNotificationAdapter : Service Unreachable : ", e);
				e.printStackTrace();
			}
		}
		
		return statusMap;
	}

	public NotificationServiceMode getNotificationServiceMode() {
		return mode;
	}

	public void setNotificationServiceMode(NotificationServiceMode notificationServiceMode) {
		this.mode = notificationServiceMode;
	}
	
	public ServiceStatus startup(Optional<Properties> configProperties) {
		
		System.out.println("ClientNotificationAdapter : startup : "+configProperties);
		
		if(mode == NotificationServiceMode.CENTRAL){
			
			service = Executors.newSingleThreadExecutor();
		}else{
			service = Executors.newCachedThreadPool();
		}
		
		serviceStatus = ServiceStatus.STARTED;
		return serviceStatus;
	}
	
	public ServiceStatus shutdown(Optional<Properties> configProperties) {
		
		System.out.println("ClientNotificationAdapter : shutdown : "+configProperties);
		//service.shutdown();
		serviceStatus = ServiceStatus.STARTED;
		return serviceStatus;
	}

	@Override
	public void addServiceListener(EventPublisher serviceListener) {
		this.serviceListener = serviceListener;
	}

	public void setNotificationServiceAdapterName(String notificationServiceAdapterName){
		this.notificationServiceAdapterName = notificationServiceAdapterName;
	}
	
	public String getNotificationServiceAdapterName(){
		
		return this.notificationServiceAdapterName;
	}


	@Override
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	@Override
	public String getFlowName() {
		return this.flowName;
	}

	@Override
	public void setFlowType(FlowType flowType) {
		this.flowType = flowType;
	}

	@Override
	public FlowType getFlowType() {
		return this.flowType;
	}

	@Override
	public void setFlowFunctionType(FlowFunctionType flowFunctionType) {
		this.flowFunctionType = flowFunctionType;
	}

	@Override
	public FlowFunctionType getFlowFunctionType() {
		return this.flowFunctionType;
	}

	@Override
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Override
	public boolean isEnabled() {
		return this.isEnabled;
	}

	@Override
	public void subscribe(Subscriber<? super Event<EventModel>> subscriber) {
		this.subscriber = subscriber;
	}

	@Override
	public boolean isSubscribed() {
		
		return (this.subscriber != null);
	}
	
	@Override
	public void setFlowPublisherType(FlowPublisherType flowPublisherType) {
		this.flowPublisherType = flowPublisherType;
	}

	@Override
	public FlowPublisherType getFlowPublisherType() {
		return this.flowPublisherType;
	}
	
	@Override
	public void setNotificationServiceAdapterType(NotificationServiceAdapterType notificationServiceAdapterType) {
		this.notificationServiceAdapterType = notificationServiceAdapterType;
	}

	@Override
	public NotificationServiceAdapterType getNotificationServiceAdapterType() {
		return this.notificationServiceAdapterType;
	}

	@Override
	public void suspendOperationOn(ServiceEndPoint serviceEndPoint,EndPointStatus health, NotificationServiceStatus trigger) 
			throws ConfigurationException {
		
		
		clientCircuit.removeServiceEndpoint(serviceEndPoint);
		subscriber.onNext(onServiceChange(serviceEndPoint,ServiceState.STOPPING));
	}

	@Override
	public void resumeOperationOn(ServiceEndPoint serviceEndPoint,EndPointStatus health, NotificationServiceStatus trigger) 
			throws ConfigurationException {

		clientCircuit.addServiceEndpoint(serviceEndPoint);
		subscriber.onNext(onServiceChange(serviceEndPoint,ServiceState.STARTING));
	}

	
	public String toString(){
		return " ClientNotificationAdapter { notificationServiceAapterName "+notificationServiceAdapterName+
				" | notificationServiceAdapterType : "+notificationServiceAdapterType+"}";
	}



}