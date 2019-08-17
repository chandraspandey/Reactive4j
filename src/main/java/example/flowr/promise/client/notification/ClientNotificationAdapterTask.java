package example.flowr.promise.client.notification;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.Delayed;

import org.flowr.framework.core.event.ChangeEvent;
import org.flowr.framework.core.model.EventModel;
import org.flowr.framework.core.node.EndPointDispatcher;
import org.flowr.framework.core.node.io.network.ServiceMesh.ServiceTopologyMode;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.NotificationBufferQueue;
import org.flowr.framework.core.notification.NotificationTask;
import org.flowr.framework.core.notification.subscription.NotificationSubscription;
import org.flowr.framework.core.service.ServiceFramework;
import org.flowr.framework.core.service.internal.NotificationService.NotificationServiceStatus;



public class ClientNotificationAdapterTask implements NotificationTask {

	private HashMap<NotificationProtocolType,EndPointDispatcher> dispatcherMap 	= null;
	private ServiceTopologyMode serviceTopologyMode = ServiceTopologyMode.DISTRIBUTED;
	private NotificationBufferQueue notificationBufferQueue = null;

	@Override
	public void configure(HashMap<NotificationProtocolType,EndPointDispatcher> dispatcherMap) {
		
		this.dispatcherMap 	= dispatcherMap;
	}
	
	public ClientNotificationAdapterTask(){
		
		if(serviceTopologyMode == ServiceTopologyMode.DISTRIBUTED){			

		}else if(serviceTopologyMode == ServiceTopologyMode.LOCAL) {
			
		}
	}
	
	@Override
	public HashMap<NotificationProtocolType, NotificationServiceStatus> call() throws Exception {

		return dispatch(notificationBufferQueue);
	}
	
	
	@Override
	public HashMap<NotificationProtocolType, NotificationServiceStatus> dispatch(NotificationBufferQueue notificationBufferQueue) {

		HashMap<NotificationProtocolType, NotificationServiceStatus> statusMap = new HashMap<NotificationProtocolType, NotificationServiceStatus>();
		
		NotificationServiceStatus notificationServiceStatus = NotificationServiceStatus.QUEUED;
		
		System.out.println(" ClientNotificationAdapterTask : notificationBufferQueue : " +notificationBufferQueue);
		
		//QueueType queueType =notificationBufferQueue.getQueueType();
		
		//EventType eventType = notificationBufferQueue.getEventType();
		
		if(!notificationBufferQueue.isEmpty()) {
			
			Iterator<Delayed> iter = notificationBufferQueue.iterator();
			
			while(iter.hasNext()) {
				
				@SuppressWarnings("unchecked")
				ChangeEvent<EventModel> event = (ChangeEvent<EventModel>) iter.next();
				
				NotificationSubscription notificationSubscription = ServiceFramework.getInstance().getSubscriptionService().lookup(event.getSubscriptionClientId());
					
				if(notificationSubscription != null) {					
				
					NotificationProtocolType notificationProtocolType = notificationSubscription.getNotificationProtocolType();
					
					notificationServiceStatus = dispatcherMap.get(notificationProtocolType).dispatch(event);
					
					statusMap.put(notificationProtocolType, notificationServiceStatus);
					
					if(notificationServiceStatus == NotificationServiceStatus.EXECUTED) {
						iter.remove();
					}else {
						System.err.println("NotificationDispath Failed with status : " +notificationServiceStatus);
					}
				}else {
					
					System.err.println("Unable to find NotificationSubscription for event : " +event);
				}
			}

		}
		
		return statusMap;
	}
	
	

	@Override
	public void setServiceMode(ServiceTopologyMode serviceTopologyMode) {
		this.serviceTopologyMode = serviceTopologyMode;
	}

	@Override
	public ServiceTopologyMode getServiceTopologyMode() {
		return this.serviceTopologyMode;
	}

	public void setNotificationBufferQueue(NotificationBufferQueue notificationBufferQueue) {
		this.notificationBufferQueue = notificationBufferQueue;
	}

}
