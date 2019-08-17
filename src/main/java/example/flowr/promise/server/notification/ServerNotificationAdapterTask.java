package example.flowr.promise.server.notification;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.Delayed;

import org.flowr.framework.core.context.Context;
import org.flowr.framework.core.context.EventContext;
import org.flowr.framework.core.context.ProcessContext;
import org.flowr.framework.core.context.ServerContext;
import org.flowr.framework.core.context.ServiceContext;
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

public class ServerNotificationAdapterTask implements NotificationTask {

	private ServiceTopologyMode serviceTopologyMode = ServiceTopologyMode.DISTRIBUTED;
	private NotificationBufferQueue notificationBufferQueue = null;
	private HashMap<NotificationProtocolType,EndPointDispatcher> dispatcherMap 	= null;

	public ServerNotificationAdapterTask() {
		
		if(serviceTopologyMode == ServiceTopologyMode.DISTRIBUTED){			
	
		}else if(serviceTopologyMode == ServiceTopologyMode.LOCAL) {
			
		}
	}
	
	@Override
	public void configure(HashMap<NotificationProtocolType,EndPointDispatcher> dispatcherMap) {

		this.dispatcherMap 	= dispatcherMap;
	}

	@Override
	public HashMap<NotificationProtocolType, NotificationServiceStatus> call() throws Exception {

		return dispatch(notificationBufferQueue);
	}
	
	@Override
	public HashMap<NotificationProtocolType, NotificationServiceStatus> dispatch(NotificationBufferQueue notificationBufferQueue) {
		
		HashMap<NotificationProtocolType, NotificationServiceStatus> statusMap = new HashMap<NotificationProtocolType, NotificationServiceStatus>();
				
		NotificationServiceStatus notificationServiceStatus = NotificationServiceStatus.QUEUED;
		
		//System.out.println(" ServerNotificationAdapterTask NotificationServiceStatus : " +status);
		
		System.out.println(" ServerNotificationAdapterTask : notificationBufferQueue : "+notificationBufferQueue);
		
		// Call for external interface based on type for endPointList
		
		if(!notificationBufferQueue.isEmpty()) {
			
			Iterator<Delayed> iter = notificationBufferQueue.iterator();
			
			while(iter.hasNext()) {
				
				@SuppressWarnings("unchecked")
				ChangeEvent<EventModel> event = (ChangeEvent<EventModel>) iter.next();
				
				String hostName = event.getSubscriptionClientId();
				
				String subscriptionId = null;
				
				Context context = event.getChangedModel().getContext();
				
				if(context instanceof ServerContext) {
					
					subscriptionId = ((ServerContext)context).getSubscriptionClientId();
				}else if(context instanceof EventContext) {
					subscriptionId = ((EventContext)context).getSubscriptionClientId();
				}else if(context instanceof ProcessContext) {
					
				}else if(context instanceof ServiceContext) {
					
				}
				
				if(subscriptionId != null && !subscriptionId.isEmpty()) {
					
					NotificationSubscription notificationSubscription = ServiceFramework.getInstance().getSubscriptionService().lookup(subscriptionId);
					
					if(notificationSubscription != null) {
						
						NotificationProtocolType notificationProtocolType = notificationSubscription.getNotificationProtocolType();
						
						System.out.println("NotificationDispath Failed with status : " +dispatcherMap);
						
						notificationServiceStatus = dispatcherMap.get(notificationProtocolType).dispatch(event);
						
						statusMap.put(notificationProtocolType, notificationServiceStatus);
						
						if(notificationServiceStatus == NotificationServiceStatus.EXECUTED) {
							iter.remove();
						}else {
							System.err.println("NotificationDispath Failed with status : " +notificationServiceStatus);
						}
					}else {
						
						System.err.println("Unable to find NotificationSubscription for "+hostName+" for event : "
								+event);
					}
				}
			}
		}
		
		//System.out.println(" ServerNotificationAdapterTask NotificationServiceStatus : " +status);
		
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
