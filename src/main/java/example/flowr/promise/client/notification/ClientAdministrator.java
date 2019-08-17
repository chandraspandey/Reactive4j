package example.flowr.promise.client.notification;

import java.util.concurrent.Flow.Subscription;

import org.flowr.framework.core.event.Event;
import org.flowr.framework.core.flow.EventFlow.FlowSubscriberType;
import org.flowr.framework.core.flow.EventSubscriber;
import org.flowr.framework.core.model.EventModel;

public class ClientAdministrator implements EventSubscriber{

	private FlowSubscriberType flowSubscriberType = FlowSubscriberType.FLOW_SUBSCRIBER_CLIENT;
	
	//@Override
	public void onComplete() {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void onError(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void onNext(Event<EventModel> event) {
		// TODO Auto-generated method stub
		System.out.println("ClientAdministrator : "+event);
	}

	//@Override
	public void onSubscribe(Subscription subscription) {
		// TODO Auto-generated method stub
	}

	//@Override
	public void setEventSubscriberType(FlowSubscriberType flowSubscriberType) {
		this.flowSubscriberType = flowSubscriberType;
	}

	//@Override
	public FlowSubscriberType getEventSubscriberType() {
		return this.flowSubscriberType;
	}

}
