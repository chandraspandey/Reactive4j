
/**
 * Provides Analytics instrumentation that can be integrated with GUI/console
 * for Administrative functionality. Contains encapsulated list of progress
 * intervals for expanded view or graphical representation of scale vs time.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.promise;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.flowr.framework.core.model.Model;
import org.flowr.framework.core.promise.Scale.ScaleOption;

public class Timeline implements Model{

    private Timestamp startTime;
    private Timestamp completionTime;
    private List<ListenerTimeUnit> progressIntervalList = new ArrayList<>();
    private double interval;
    private TimeUnit progressTimeUnit;
    private double scaleUnit;
    private double now;
    
    
    /**
     * At the time of instantiation initializes internal variables based on 
     * ProgressScale
     * @param progressScale
     */
    public Timeline(ScaleOption scaleOption){
        
        Timestamp startTimestamp=  new Timestamp(Instant.now().toEpochMilli());
        startTime               = startTimestamp;
        progressTimeUnit        = scaleOption.getProgressTimeUnit();
        interval                = scaleOption.getInterval();
    }
    
    /**
     * Optional method to override default initialization for use cases where 
     * there can be time lag between intermediate calls before timeline is used
     * in a program. At the time of call reinitializes internal variables based  
     * on ProgressScale
     * @param progressScale
     */
    public void initTimeline(Scale progressScale){
        
        Timestamp startTimestamp =  new Timestamp(Instant.now().toEpochMilli());
        startTime = startTimestamp;
        progressTimeUnit = progressScale.getScaleOption().getProgressTimeUnit();
        interval= progressScale.getScaleOption().getInterval();
    }
    
    /**
     * Updates timeline by adding a ProgressTimeUnit based on progress
     * represented by now. Updates the internally held value of now for 
     * convinient lookup in case of phased or multiple responses for external
     * instrumentation.
     * @param now
     */
    public void addToTimeline(double now){
        
        Timestamp currentTimestamp =  new Timestamp(Instant.now().toEpochMilli());
        
        ListenerTimeUnit pTimeUnit = new ListenerTimeUnit();
        pTimeUnit.setOccurenceTimestamp(currentTimestamp);
        pTimeUnit.setProgress(now);
        progressIntervalList.add(pTimeUnit);
                
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
