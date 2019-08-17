package org.flowr.framework.core.promise;

import org.flowr.framework.core.exception.PromiseException;
import org.flowr.framework.core.process.management.ProcessHandler;
import org.flowr.framework.core.target.ReactiveTarget;

/**
 * Provides a wrapper analogy of IF-THEN-WHAT for asynchronous call using 
 * Future<PromiseResponse>
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public interface Promise<REQUEST,RESPONSE> {

	/** Marks the states that a promise can hold as a state change machine. 
	 * DEFAULT : The default state of a promise without assignment.
	 * NEGOTIATED : The Server has agreed for scale to be used.
	 * ACKNOWLEDGED : The client has made a request for making a Promise.
	 * However due to external dependencies involved it can move to ASSURED or
	 * ERROR state based on resource/processing availability.
	 * ASSURED 		: The processing for fulfillment has started successfully to be
	 * assured to be successfull in normal course, however can move to ERROR
	 * state in case of any disruption or time out or run time error 
	 * FULFILLED 	: The fullfillment has completed successfully
	 * TIMEOUT 		: Marks a state where the promise can not be honoured as the
	 * response generation has timed out. Relevant for Wide scope search,
	 * parse or 1:N relationship processing on server side 
	 * ERROR 		: Marks a state where the promise can not be honoured. 
	 * 
	 */
	public enum PromiseState{
		DEFAULT,
		NEGOTIATED,
		ACKNOWLEDGED,
		ASSURED,
		FULFILLED,
		TIMEOUT,
		CANCEL,
		ERROR
	}
	
	/**
	 * INITIATED 	: When the processing has been started sucessfully & the state
	 * has been changed to ASSURED.	 *  
	 * PROCESSING 	: The client can make an assumption that promise would held 
	 * good & the state as FULFILLED should happen in normal course.
	 * COMPLETED 	: The client can make definitive conclusion of processing
	 * completion & no more state change can occur. Pairing with FULFILLED or
	 * ERROR state is valid existence. 
	 */
	public enum PromiseStatus{
		INITIATED,
		PROCESSING,
		COMPLETED
	}
	
	/*
	 * REGISTERED 	: Registration for Future occurence is acknowledged.
	 * SCHEDULED	: Should happen in normal course unless run time error happens
	 * DEFFERED		: The occurence is defered to new time
	 * OVER			: The occurence is marked to be complete. 
	 */
	public enum ScheduleStatus{
		REGISTERED,
		SCHEDULED,
		DEFFERED,
		OVER
	}
	
	/**
	 * Default implementation for handling the flow for fullfillment of promise.
	 * Subclasses can override this method to define alternate control flow. 
	 * @param promiseRequest
	 * @return
	 * @throws PromiseException
	 */
	public PromiseResponse<RESPONSE> handle(PromiseRequest<REQUEST>
		promiseRequest) throws PromiseException;
	
	/**
	 * Validates if the PromiseRequest meets the preconditions for execution
	 * that it provides ProgressScale as input and is of type Promisable that
	 * can support generate PromiseResponse 
	 * @param promiseRequest
	 * @return boolean
	 * @throws PromiseException
	 */
	public boolean ifValid(PromiseRequest<REQUEST> promiseRequest) throws 
		PromiseException;
	
	
	/**
	 * Determines resources required for delegation to fulfill the request.
	 * For non distributed processing, implementation can include What as an
	 * offering 
	 * @param promiseRequest
	 * @return PromiseResponse
	 * @throws PromiseException
	 */
	public PromiseResponse<RESPONSE> then(PromiseRequest<REQUEST> 
		promiseRequest) throws PromiseException;
	
	/**
	 * Processes the Request for generating a valid response. Additional 
	 * option for stateless distributed computing such as micro services, where
	 * request is processing agnostic can be delegated to any of the backend 
	 * systems  
	 * @param promiseRequest
	 * @return PromiseResponse
	 * @throws PromiseException
	 */
	public PromiseResponse<RESPONSE> what(PromiseRequest<REQUEST> 
		promiseRequest) throws PromiseException;
	
	/**
	 * Processes the progress and updates the Timeline accordingly 
	 * @param timeline
	 * @param snapshotScale
	 * @return
	 */
	public Timeline processTimeline(Timeline timeline, Scale snapshotScale);
	
	
	/**
	 * Returns the Reactive Target that services the request
	 * @return
	 */
	public ReactiveTarget<REQUEST, RESPONSE> getReactiveTarget();
	
	/**
	 * Sets the Reactive Target that services the request
	 * @param reactiveTarget
	 */
	public void setReactiveTarget(ReactiveTarget<REQUEST, RESPONSE> reactiveTarget) ;
	
	/**
	 * Associate manageability process handler.
	 * @param processHandler
	 */
	public void associateProcessHandler(ProcessHandler processHandler);
	
	public ProcessHandler getAssociatedProcessHandler();
	
	public void startup();
	
	public void shutdown();
}
