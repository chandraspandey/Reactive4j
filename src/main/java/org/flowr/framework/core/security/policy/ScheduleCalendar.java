
/**
 * Defines ScheduleCalendar as a configurable model which wraps the Calendar
 * functionality.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
package org.flowr.framework.core.security.policy;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

public class ScheduleCalendar {

    private ZonedDateTime zonedDateTime = ZonedDateTime.now();
    private int[] scheduleDays          = new int[Calendar.DAY_OF_WEEK];
    private ScheduleFrequency scheduleOccurence;
    private int retryonError;
    private Date startTime; 
    private Date endTime;   
      
    public enum ScheduleFrequency {
        ONCE,
        DAILY,
        WEEKLY,
        MONTHLY
    }    
    
    public void setScheduleFrequency(ScheduleFrequency scheduleOccurence){
        this.scheduleOccurence = scheduleOccurence;
    }
        
    public ScheduleFrequency getScheduleFrequency(){
        
        return this.scheduleOccurence;
    }

    public int[] getScheduleDays() {
        return scheduleDays;
    }

    public void setScheduleDays(int[] scheduleDays) {
        this.scheduleDays = scheduleDays;
    }
    
    public int getRetryonError() {
        return retryonError;
    }

    public void setRetryonError(int retryonError) {
        this.retryonError = retryonError;
    }
    
    public Date getStartTime() {
        return startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    public Date getEndTime() {
        return endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    public String toString(){
        
        return "ScheduleCalendar {"+
                "\n zonedDateTime : "+zonedDateTime+
                "\n startTime : "+startTime+    
                "\n endTime : "+endTime+                
                "\n scheduleOccurence : "+scheduleOccurence+
                "\n scheduleDays : "+scheduleDays+
                "\n retryonError : "+retryonError+
                "\n}\n";
    }
}
