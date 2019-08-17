package example.flowr.promise.server;

import java.util.UUID;

import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.exception.PromiseException;
import org.flowr.framework.core.promise.Promisable.PromisableType;
import org.flowr.framework.core.promise.Promise.PromiseState;
import org.flowr.framework.core.promise.Promise.PromiseStatus;
import org.flowr.framework.core.promise.Scale.Priority;
import org.flowr.framework.core.promise.Scale.PriorityScale;
import org.flowr.framework.core.promise.Scale.Severity;
import org.flowr.framework.core.promise.Scale.SeverityScale;
import org.flowr.framework.core.promise.PromiseTypeServer;
import org.flowr.framework.core.promise.RequestScale;
import org.flowr.framework.core.promise.Scale;
import org.flowr.framework.core.promise.deferred.ProgressScale;
import org.flowr.framework.core.service.ServiceFramework;
import org.flowr.framework.core.target.ReactiveTarget;

import example.flowr.mock.Mock.PROMISE_MOCK_REQUEST;
import example.flowr.mock.Mock.PROMISE_MOCK_RESPONSE;

public class DeferredPromiseServer implements PromiseTypeServer<PROMISE_MOCK_REQUEST,PROMISE_MOCK_RESPONSE>{

	private double artificialNow 							= 0.0;
	private UUID acknowledgementId 							= UUID.randomUUID();	
	private long artificialDelay 							= 500; 
	private String serverIdentifier 						= PromiseTypeServer.ServerIdentifier();
	private ProgressScale progressScale 					= null;
	private PromisableType promisableType 					= PromisableType.PROMISE_DEFFERED;
	//private long timeout 									= 11000;
	private boolean isNegotiated 							= false;

	
	@Override
	public ProgressScale buildProgressScale(PromisableType promisableType,double now) throws ConfigurationException{
		
		if(progressScale == null && now == 0.0){
		
			progressScale = (ProgressScale) ServiceFramework.getInstance().getConfigurationService().getProgressScale(
					promisableType, serverIdentifier);
			
		}else{
			progressScale.setNow(now);
		}
		
		//System.out.println("PromiseServer : serviceConfiguration : "+progressScale);
		
		return progressScale;
	}
	
	
	@Override
	public ProgressScale invokeAndReturn(PROMISE_MOCK_REQUEST request, RequestScale requestScale)
			throws PromiseException,ConfigurationException {
		
		ProgressScale scale = (ProgressScale) buildProgressScale(promisableType,artificialNow+=0.0);
		
		scale.setPromiseState(PromiseState.ACKNOWLEDGED);
		scale.setPromiseStatus(PromiseStatus.INITIATED);
		scale.setAcknowledgmentIdentifier(acknowledgementId.toString());
		scale.setPriorityScale(new PriorityScale(Priority.HIGH, 75));
		scale.setSeverityScale(new SeverityScale(Severity.LOW,25));
				
		try {
			Thread.sleep(artificialDelay);
			//Thread.sleep(timeout);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println("PromiseServer : invokeAndReturn : "+scale);
		
		return scale;
	}

	@Override
	public ProgressScale invokeForProgress(String acknowledgmentIdentifier) throws PromiseException,ConfigurationException {

		ProgressScale scale = buildProgressScale(promisableType,artificialNow+=10.0);			
			
		scale.setAcknowledgmentIdentifier(acknowledgmentIdentifier);
		if(artificialNow < 100.0){
		
			scale.setPromiseState(PromiseState.ASSURED);
			scale.setPromiseStatus(PromiseStatus.PROCESSING);
			scale.setPriorityScale(new PriorityScale(Priority.LOW, 25));
			scale.setSeverityScale(new SeverityScale(Severity.LOW,25));
		}else{
			scale.setPromiseState(PromiseState.FULFILLED);
			scale.setPromiseStatus(PromiseStatus.COMPLETED);	
			scale.setPriorityScale(new PriorityScale(Priority.LOW, 25));
			scale.setSeverityScale(new SeverityScale(Severity.LOW,25));
		}
		
		try {
			Thread.sleep(artificialDelay);
			//Thread.sleep(timeout);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println("PromiseServer : invokeForProgress : "+scale);
		
		return scale;
	}

	@Override
	public PromisableType getPromisableType() {
		return promisableType;
	}

	@Override
	public String getServerIdentifier(){
	
		return this.serverIdentifier;
	}
	

	@Override
	public PROMISE_MOCK_RESPONSE invokeWhenComplete(String acknowledgmentIdentifier) throws PromiseException,ConfigurationException{
		
		PROMISE_MOCK_RESPONSE res = new PROMISE_MOCK_RESPONSE();
		
		try {
			Thread.sleep(artificialDelay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("PromiseServer : invokeWhenComplete : res : "+
				res);
		
		return (PROMISE_MOCK_RESPONSE) res;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public ReactiveTarget<PROMISE_MOCK_REQUEST, PROMISE_MOCK_RESPONSE> getReactiveTarget() {
		return (ReactiveTarget<PROMISE_MOCK_REQUEST, PROMISE_MOCK_RESPONSE>) this;
	}


	@Override
	public Scale negotiate(RequestScale requestScale) throws ConfigurationException {
		
		ProgressScale scale = buildProgressScale(promisableType,artificialNow+=0.0);
		scale.acceptIfApplicable(requestScale);
		scale.setPromiseState(PromiseState.NEGOTIATED);
		scale.setPriorityScale(new PriorityScale(Priority.HIGH,75));
		scale.setSeverityScale(new SeverityScale(Severity.LOW,25));
		isNegotiated = true;
		return scale;
	}


	@Override
	public boolean isNegotiated() {
		return isNegotiated;
	}

}
