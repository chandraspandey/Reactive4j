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
import org.flowr.framework.core.promise.phase.PhasedProgressScale;
import org.flowr.framework.core.promise.phase.PhasedService.ServicePhase;
import org.flowr.framework.core.service.ServiceFramework;
import org.flowr.framework.core.target.ReactiveTarget;

import example.flowr.mock.Mock.MOCK_CHUNKED_RESPONSE;
import example.flowr.mock.Mock.PHASED_PROMISE_MOCK_REQUEST;
import example.flowr.mock.Mock.PHASED_PROMISE_MOCK_RESPONSE;

public class PhasedPromiseServer implements PromiseTypeServer<PHASED_PROMISE_MOCK_REQUEST,PHASED_PROMISE_MOCK_RESPONSE>{

	private double artificialNow 						= 0.0;
	private UUID acknowledgementId 						= UUID.randomUUID();	
	private long artificialDelay 						= 3000; 
	private String serverIdentifier 					= PromiseTypeServer.ServerIdentifier();
	private PhasedProgressScale phasedProgressScale 	= null;
	private PromisableType promisableType 				= PromisableType.PROMISE_PHASED;
	//private long timeout 								= 11000;
	private boolean isNegotiated 						= false;
	
	@Override
	public PromisableType getPromisableType() {
		return promisableType;
	}
	
	@Override
	public Scale buildProgressScale(PromisableType promisableType,double now) throws ConfigurationException{
		
		if(phasedProgressScale == null && now == 0.0){
		
			phasedProgressScale =  (PhasedProgressScale) ServiceFramework.getInstance().getConfigurationService().getProgressScale(
					promisableType, serverIdentifier);			
		}else{
			phasedProgressScale.setNow(now);
		}
		
		//System.out.println("PhasedPromiseServer : serviceConfiguration : "+phasedProgressScale);
		
		return phasedProgressScale;
	}
	
	
	@Override
	public Scale invokeAndReturn(PHASED_PROMISE_MOCK_REQUEST request, RequestScale requestScale) throws PromiseException, 
		ConfigurationException {
		
		PhasedProgressScale scale = (PhasedProgressScale) 
				buildProgressScale(promisableType,artificialNow+=0.0);
		
		scale.setPromiseState(PromiseState.ACKNOWLEDGED);
		scale.setPromiseStatus(PromiseStatus.INITIATED);
		scale.setAcknowledgmentIdentifier(acknowledgementId.toString());
		scale.setServicePhase(ServicePhase.DISCOVERY); 
		scale.setSubscriptionClientId(requestScale.getSubscriptionClientId());
		scale.setPriorityScale(new PriorityScale(Priority.HIGH, 75));
		scale.setSeverityScale(new SeverityScale(Severity.LOW,25));
				
		try {
			Thread.sleep(artificialDelay);
			//Thread.sleep(timeout);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
				
		System.out.println("PhasedResponseServer : invokeAndReturn : "+scale);
		
		return scale;
	}

	@Override
	public Scale invokeForProgress(String acknowledgmentIdentifier) throws ConfigurationException {
		
		
		MOCK_CHUNKED_RESPONSE chunkBuffer 	= new MOCK_CHUNKED_RESPONSE();
		
		for(double index = artificialNow; index < artificialNow+20 ; index++){
			String val = "10.101.241."+((int)index)+",";
			chunkBuffer.appendChunk(val.getBytes());
		}
				
		
		PhasedProgressScale scale = (PhasedProgressScale)
					buildProgressScale(promisableType,artificialNow+=20.0);	
			
		scale.setChunkBuffer(chunkBuffer);
		
		if(artificialNow < 100.0){
			
			scale.setPromiseState(PromiseState.ASSURED);
			scale.setPromiseStatus(PromiseStatus.PROCESSING);
			scale.setServicePhase(ServicePhase.AGGREGATION); 
			scale.setPriorityScale(new PriorityScale(Priority.LOW, 25));
			scale.setSeverityScale(new SeverityScale(Severity.LOW,25));
		}else{
			scale.setPromiseState(PromiseState.FULFILLED);
			scale.setPromiseStatus(PromiseStatus.COMPLETED);	
			scale.setServicePhase(ServicePhase.COMPLETE); 
			scale.setPriorityScale(new PriorityScale(Priority.HIGH, 25));
			scale.setSeverityScale(new SeverityScale(Severity.LOW,25));
		}
		
		
		try {
			Thread.sleep(artificialDelay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		scale.setAcknowledgmentIdentifier(acknowledgmentIdentifier);
		
		System.out.println("PhasedResponseServer : invokeForProgress : "+scale);
		
		return scale;
	}
	
	@Override
	public String getServerIdentifier(){
	
		return this.serverIdentifier;
	}
	

	@Override
	public PHASED_PROMISE_MOCK_RESPONSE invokeWhenComplete(String acknowledgmentIdentifier) throws PromiseException,ConfigurationException{
		
		PHASED_PROMISE_MOCK_RESPONSE res = new PHASED_PROMISE_MOCK_RESPONSE();
		
		try {
			Thread.sleep(artificialDelay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("PhasedPromiseServer : invokeWhenComplete : res : "+
				res);
		
		return (PHASED_PROMISE_MOCK_RESPONSE) res;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public ReactiveTarget<PHASED_PROMISE_MOCK_REQUEST, PHASED_PROMISE_MOCK_RESPONSE> getReactiveTarget() {
		return (ReactiveTarget<PHASED_PROMISE_MOCK_REQUEST, PHASED_PROMISE_MOCK_RESPONSE>) this;
	}


	@Override
	public Scale negotiate(RequestScale requestScale) throws ConfigurationException {
		
		PhasedProgressScale scale = (PhasedProgressScale) 
				buildProgressScale(promisableType,artificialNow+=0.0);
		scale.acceptIfApplicable(requestScale);
		scale.setPromiseState(PromiseState.NEGOTIATED);
		scale.setPriorityScale(new PriorityScale(Priority.HIGH, 100));
		scale.setSeverityScale(new SeverityScale(Severity.LOW,25));
		isNegotiated = true;
		return scale;
	}

	@Override
	public boolean isNegotiated() {
		return isNegotiated;
	}

}
