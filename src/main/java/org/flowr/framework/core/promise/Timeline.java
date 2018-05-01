package org.flowr.framework.core.promise;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.flowr.framework.core.model.Model;
import org.flowr.framework.core.promise.deferred.ProgressScale;



/**
 * Provides Analytics instrumentation that can be integrated with GUI/console
 * for Administrative functionality. Contains encapsulated list of progress
 * intervals for expanded view or graphical representation of scale vs time.
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class Timeline implements Model{

	private Timestamp startTime;
	private Timestamp completionTime;
	private List<ListenerTimeUnit> progressIntervalList = 
			new ArrayList<ListenerTimeUnit>();
	private double interval;
	private TimeUnit progressTimeUnit;
	private double scaleUnit;
	private double now;
	
	
	/**
	 * At the time of instantiation initializes internal variables based on 
	 * ProgressScale
	 * @param progressScale
	 */
	public Timeline(Scale progressScale){
		
		Timestamp startTimestamp =  new Timestamp(new Date().getTime());
		startTime = startTimestamp;
		progressTimeUnit = progressScale.getProgressTimeUnit();
		interval= progressScale.getINTERVAL();
	}
	
	/**
	 * Optional method to override default initialization for use cases where 
	 * there can be time lag between intermediate calls before timeline is used
	 * in a program. At the time of call reinitializes internal variables based  
	 * on ProgressScale
	 * @param progressScale
	 */
	public void initTimeline(ProgressScale progressScale){
		
		Timestamp startTimestamp =  new Timestamp(new Date().getTime());
		startTime = startTimestamp;
		progressTimeUnit = progressScale.getProgressTimeUnit();
		interval= progressScale.getINTERVAL();
	}
	
	/**
	 * Updates timeline by adding a ProgressTimeUnit based on progress
	 * represented by now. Updates the internally held value of now for 
	 * convinient lookup in case of phased or multiple responses for external
	 * instrumentation.
	 * @param now
	 */
	public void addToTimeline(double now){
		
		Timestamp currentTimestamp =  new Timestamp(new Date().getTime());
		
		ListenerTimeUnit progressTimeUnit = new ListenerTimeUnit();
		progressTimeUnit.setOccurenceTimestamp(currentTimestamp);
		progressTimeUnit.setProgress(now);
		progressIntervalList.add(progressTimeUnit);
				
		if(now == 100){
			completionTime = currentTimestamp;
		}
		
		// update now
		this.now = now;
	}
	
	public double getInterval() {
		return interval;
	}
	public void setInterval(double interval) {
		this.interval = interval;
	}
	public TimeUnit getProgressTimeUnit() {
		return progressTimeUnit;
	}
	public void setProgressTimeUnit(TimeUnit progressTimeUnit) {
		this.progressTimeUnit = progressTimeUnit;
	}
	public double getScaleUnit() {
		return scaleUnit;
	}
	public void setScaleUnit(double scaleUnit) {
		this.scaleUnit = scaleUnit;
	}
	
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getCompletionTime() {
		return completionTime;
	}
	public void setCompletionTime(Timestamp completionTime) {
		this.completionTime = completionTime;
	}
	
	public List<ListenerTimeUnit> getProgressList(){
		return progressIntervalList;
	}

	public double getNow() {
		return now;
	}
	
	public String toString(){
		
		return " Timeline{"+
				"startTime : "+startTime+
				" | completionTime : "+completionTime+		
				" | scaleUnit : "+scaleUnit+
				" | interval : "+interval+
				" | progressIntervalList : "+progressIntervalList+
				" | now : "+now+
				"}";
	}


}
