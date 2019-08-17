package example.flowr.promise.server;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;

import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.exception.PromiseException;
import org.flowr.framework.core.promise.Promisable.PromisableType;
import org.flowr.framework.core.promise.Promise.PromiseState;
import org.flowr.framework.core.promise.Promise.PromiseStatus;
import org.flowr.framework.core.promise.Promise.ScheduleStatus;
import org.flowr.framework.core.promise.PromiseTypeServer;
import org.flowr.framework.core.promise.RequestScale;
import org.flowr.framework.core.promise.Scale;
import org.flowr.framework.core.promise.Scale.Priority;
import org.flowr.framework.core.promise.Scale.PriorityScale;
import org.flowr.framework.core.promise.Scale.Severity;
import org.flowr.framework.core.promise.Scale.SeverityScale;
import org.flowr.framework.core.promise.scheduled.ScheduledProgressScale;
import org.flowr.framework.core.service.ServiceFramework;
import org.flowr.framework.core.target.ReactiveTarget;

import example.flowr.mock.Mock.SCHEDULED_PROMISE_MOCK_REQUEST;
import example.flowr.mock.Mock.SCHEDULED_PROMISE_MOCK_RESPONSE;

public class ScheduledPromiseServer implements PromiseTypeServer<SCHEDULED_PROMISE_MOCK_REQUEST,SCHEDULED_PROMISE_MOCK_RESPONSE>{

	private double artificialNow 							= 0.0;
	private UUID acknowledgementId 							= UUID.randomUUID();	
	private long artificialDelay 							= 5000; 
	private String serverIdentifier 						= PromiseTypeServer.ServerIdentifier();
	private ScheduledProgressScale scheduledProgressScale 	= null;
	
	private Timestamp scheduledTimestamp;	
	private Timestamp deferredTimestamp;
	private boolean isDeferred 								= false;
	private PromisableType promisableType 					= PromisableType.PROMISE_SCHEDULED;
	//private long timeout 									= 11000;
	private boolean isNegotiated 							= false;
	
	@Override
	public Scale buildProgressScale(PromisableType promisableType,double now) throws ConfigurationException{
		
		if(scheduledProgressScale == null && now == 0.0){
		
			scheduledProgressScale = (ScheduledProgressScale) ServiceFramework.getInstance().getConfigurationService().getProgressScale(
					promisableType, serverIdentifier);				
			
		}else{
			scheduledProgressScale.setNow(now);
		}
		
		//System.out.println("ScheduledPromiseServer : serviceConfiguration : "+scheduledProgressScale);
		
		return scheduledProgressScale;
	}
	
	@Override
	public PromisableType getPromisableType() {
		return promisableType;
	}

	@Override
	public Scale invokeAndReturn(SCHEDULED_PROMISE_MOCK_REQUEST request, RequestScale requestScale)
			throws PromiseException, ConfigurationException {
		
		ScheduledProgressScale scale = (ScheduledProgressScale) buildProgressScale(promisableType,artificialNow+=0.0);
		
		scale.setPromiseState(PromiseState.ACKNOWLEDGED);
		scale.setPromiseStatus(PromiseStatus.INITIATED);
		scale.setAcknowledgmentIdentifier(acknowledgementId.toString());
		scale.setScheduleStatus(ScheduleStatus.REGISTERED);
		scale.setSubscriptionClientId(requestScale.getSubscriptionClientId());
		scale.setPriorityScale(new PriorityScale(Priority.HIGH, 75));
		scale.setSeverityScale(new SeverityScale(Severity.LOW,25));
		
		if(scheduledTimestamp == null){
			
			scheduledTimestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
			((ScheduledProgressScale)scale).setScheduledTimestamp(scheduledTimestamp);
		}
				
		try {
			Thread.sleep(artificialDelay);
			//Thread.sleep(timeout);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("ScheduledEventServer : invokeAndReturn : "+scale);
		
		return scale;
	}

	@Override
	public Scale invokeForProgress(String acknowledgmentIdentifier) throws PromiseException, 
		ConfigurationException {

		ScheduledProgressScale scale = null;
		
		if(isDeferred == false && artificialNow == 0){
			
			// Defer by 30 milli seconds
			deferredTimestamp = new Timestamp(
							Calendar.getInstance().getTimeInMillis()+artificialDelay);	
			scale = (ScheduledProgressScale) buildProgressScale(promisableType,artificialNow);	
			scale.setDeferredTimestamp(deferredTimestamp);
			scale.setPriorityScale(new PriorityScale(Priority.LOW, 25));
			scale.setSeverityScale(new SeverityScale(Severity.LOW,25));
			
			isDeferred = true;
			
			System.out.println("ScheduledPromiseServer : Execution scheduledTimestamp at  : "+scheduledTimestamp);	
			System.out.println("ScheduledPromiseServer : Execution Deffered for  : "+deferredTimestamp);				
			
			System.out.println("ScheduledPromiseServer : Expected time lag  : "+
					(deferredTimestamp.getTime()-scheduledTimestamp.getTime()));
		}
		
		if(isDeferred){
			
			scale.setScheduleStatus(ScheduleStatus.DEFFERED);
			scale.setPromiseState(PromiseState.ASSURED);
			scale.setPromiseStatus(PromiseStatus.INITIATED);
			scale.setPriorityScale(new PriorityScale(Priority.LOW, 25));
			scale.setSeverityScale(new SeverityScale(Severity.LOW,25));
			
			// mask
			artificialNow+=20.0;
			isDeferred = false;
		}else{
			
			scale = (ScheduledProgressScale) buildProgressScale(promisableType,artificialNow+=20.0);	
			
			if(artificialNow < 100.0){
				
				scale.setPromiseState(PromiseState.ASSURED);
				scale.setPromiseStatus(PromiseStatus.PROCESSING);
				scale.setPriorityScale(new PriorityScale(Priority.LOW, 25));
				scale.setSeverityScale(new SeverityScale(Severity.LOW,25));
				
			}else{
				scale.setPromiseState(PromiseState.FULFILLED);
				scale.setPromiseStatus(PromiseStatus.COMPLETED);	
				scale.setScheduleStatus(ScheduleStatus.OVER);
				scale.setPriorityScale(new PriorityScale(Priority.HIGH, 75));
				scale.setSeverityScale(new SeverityScale(Severity.LOW,25));
			}
			
		}
	
		scale.setAcknowledgmentIdentifier(acknowledgmentIdentifier);
		
		System.out.println("ScheduledPromiseServer : invokeForProgress : "+scale);
		
		return scale;
	}

	@Override
	public String getServerIdentifier(){
	
		return this.serverIdentifier;
	}
	

	@Override
	public SCHEDULED_PROMISE_MOCK_RESPONSE invokeWhenComplete(String acknowledgmentIdentifier) throws PromiseException,ConfigurationException{
		
		SCHEDULED_PROMISE_MOCK_RESPONSE res = new SCHEDULED_PROMISE_MOCK_RESPONSE();
		
		try {
			Thread.sleep(artificialDelay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("ScheduledPromiseServer : invokeWhenComplete : res : "+
				res);
		
		return (SCHEDULED_PROMISE_MOCK_RESPONSE) res;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public ReactiveTarget<SCHEDULED_PROMISE_MOCK_REQUEST, SCHEDULED_PROMISE_MOCK_RESPONSE> getReactiveTarget() {
		return (ReactiveTarget<SCHEDULED_PROMISE_MOCK_REQUEST, SCHEDULED_PROMISE_MOCK_RESPONSE>) this;
	}
	
	@Override
	public Scale negotiate(RequestScale requestScale) throws ConfigurationException {
		
		ScheduledProgressScale scale = (ScheduledProgressScale) 
				buildProgressScale(promisableType,artificialNow+=0.0);
		scale.acceptIfApplicable(requestScale);
		scale.setPromiseState(PromiseState.NEGOTIATED);
		scale.setPriorityScale(new PriorityScale(Priority.HIGH, 75));
		scale.setSeverityScale(new SeverityScale(Severity.LOW,25));
		isNegotiated = true;
		return scale;
	}

	@Override
	public boolean isNegotiated() {
		
		return isNegotiated;
	}

}
