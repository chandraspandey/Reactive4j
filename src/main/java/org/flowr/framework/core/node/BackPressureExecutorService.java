package org.flowr.framework.core.node;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.flowr.framework.core.node.Autonomic.ResponseCode;
import org.flowr.framework.core.promise.RequestScale;

public class BackPressureExecutorService implements ExecutorService, BackPressure, Failsafe{

	// Chached Thread pool for executing async tasks 
	private ExecutorService executorService = Executors.newCachedThreadPool();
	private Timestamp startTime;
	private long timeout;
	private int retryCount;
	private long retryInterval;
	private boolean isConfigured;
	
	public void configureBackPressure(RequestScale requestScale){
		this.retryInterval	= requestScale.getRetryInterval();
		this.timeout 		= requestScale.getTimeout();
		this.retryCount 	= requestScale.getRetryCount();	
		this.startTime 		=  Timestamp.from(Instant.now());
		this.isConfigured	= true;
	}
	
	
	@Override
	public <V> V react(FailsafeCallable<V> callable) throws InterruptedException, ExecutionException{
						
		V v = null;
				
		Future<V>  future = submit(callable);	
		
		while(!future.isDone()){
			
			if(this.isTimedOut()){
		
				future.cancel(true);				
				
			}else{
				
				// SleepBreak;
				Thread.sleep(retryInterval);
				
				if(future.isDone()){
					
					v =  future.get();	
					break;
				}
				/*System.out.println("BackPressureExecutorService : react : retryInterval : "+ retryInterval+
						": t : "+Timestamp.from(Instant.now()).getTime());*/
			}
		}	
		
		return v;
	}
	
	public void resetTimeout(){
		this.startTime 		=  Timestamp.from(Instant.now());
	}
	
	@Override
	public boolean isTimedOut(){
		
		boolean isTimedOut = true;
		
		if( ( Timestamp.from(Instant.now()).getTime() - this.startTime.getTime())   > timeout ){
			isTimedOut = true;
		}else{
			isTimedOut = false;
		}
		
		/*System.out.println("BackPressureExecutorService : isTimedOut : "+isTimedOut+" | "+timeout+" > "+ 
				( Timestamp.from(Instant.now()).getTime() - this.startTime.getTime()));*/
		
		return isTimedOut;
	}
	
	@Override
	public <V> V fallforward(FailsafeCallable<V> callable) throws InterruptedException, ExecutionException {
		
		V v = null;
		
		for(int index = 0; index < retryCount; index++){
						
			v = react(callable);
			
			if(v != null){	
				break;
			}else{
				resetTimeout();
			}
			System.out.println("BackPressureExecutorService : fallforward | retryCount : "+index+" | v : "+v);
		}
		
		return v;
	}

	@Override
	public <V> V fallback(FailsafeCallable<V> callable,ResponseCode responseCode) {
		
		V v = (V) callable.handlePromiseError(responseCode);
		
		return v;
	}

	@Override
	public void execute(Runnable command) {
		executorService.execute(command);
	}

	@Override
	public void shutdown() {
		executorService.shutdown();
	}

	@Override
	public List<Runnable> shutdownNow() {
		return executorService.shutdownNow();
	}

	@Override
	public boolean isShutdown() {
		return executorService.isShutdown();
	}

	@Override
	public boolean isTerminated() {
		return executorService.isTerminated();
	}

	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		return executorService.awaitTermination(timeout, unit);
	}

	@Override
	public <T> Future<T> submit(Callable<T> task) {
		return executorService.submit(task);
	}

	@Override
	public <T> Future<T> submit(Runnable task, T result) {
		return executorService.submit(task, result);
	}

	@Override
	public Future<?> submit(Runnable task) {
		return executorService.submit(task);
	}

	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
		return executorService.invokeAll(tasks);
	}

	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
			throws InterruptedException {
		return executorService.invokeAll(tasks, timeout, unit);
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
		return executorService.invokeAny(tasks);
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException {
		return executorService.invokeAny(tasks, timeout, unit);
	}

	public boolean isConfigured(){
		
		return this.isConfigured;
	}

}
