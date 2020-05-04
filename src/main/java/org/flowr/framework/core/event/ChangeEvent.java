
/**
 * Marker interface for Event. Event Type, mechanism & event details are provided by concrete implementation classes.
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.event;

import org.flowr.framework.core.context.Context;
import org.flowr.framework.core.context.Context.EventContext;
import org.flowr.framework.core.context.Context.ProcessContext;
import org.flowr.framework.core.context.Context.ServerContext;
import org.flowr.framework.core.context.Context.ServiceContext;
import org.flowr.framework.core.model.EventModel;
import org.flowr.framework.core.promise.Scale;
import org.flowr.framework.core.promise.Scale.Priority;
import org.flowr.framework.core.promise.Scale.PriorityScale;
import org.flowr.framework.core.promise.Scale.Severity;
import org.flowr.framework.core.promise.Scale.SeverityScale;

public interface ChangeEvent<T> extends Event<T>{
    
    String getSubscriptionClientId();
    void setSubscriptionClientId(String subscriptionClientId);
    String getSource();
    void setSource(String source) ;
    String getDestination();
    void setDestination(String destination);
    T getChangedModel() ;
    void setChangedModel(T changedModel);
    
    static String deriveSubscriptionContext(ChangeEvent<EventModel> event) {
        
        String subscriptionId = null;
        
        Context context = event.getChangedModel().getContext();
        
        if(context instanceof ServerContext) {
            
            subscriptionId = ((ServerContext)context).getSubscriptionClientId();
        }
        
        if(context instanceof EventContext) {
            subscriptionId = ((EventContext)context).getSubscriptionClientId();
        }
        
        if(context instanceof ProcessContext) {
            subscriptionId = ((ProcessContext)context).getProcessIdentifier();
        }
        
        if(context instanceof ServiceContext) {
            subscriptionId = ((ServiceContext)context).getSubscriptionClientId();
        }
        
        return subscriptionId;
    }
    
    private static int compareValue(double scale1, double scale2) {
        
        int status;
        
        if(scale1 < scale2) {
            status = -1;
        }else if(scale1 == scale2) {
            status = 0;
        }else {
            status = 1;
        }
        
        return status;
    }
    
    public final class SeverityComparator{
        
        private SeverityComparator() {
            
        }
        
        public static int compareSeverity(EventModel eventModel,ChangeEventEntity other) {
            
            int status =-1;
            
            SeverityScale severityScale1 = ((Scale)eventModel.getReactiveMetaData()).getSeverityScale();
            SeverityScale severityScale2 = ((Scale)other.getChangedModel().getReactiveMetaData()).getSeverityScale();
            
            if(severityScale1 != null && severityScale2 != null) {
                
                Severity severity1          = severityScale1.getSeverity();
                double scale1               = severityScale1.getValueOf(severity1);
                Severity severity2  = severityScale2.getSeverity();
                double scale2       = severityScale2.getValueOf(severity2);
                
                if(severity1 != null && severity2 != null) {
                
                    switch(severity1) {
                                        
                        case CRITICAL:{
                            
                            status = compareCriticalSeverity(severity2, scale1, scale2);                        
                            break;
                        }case HIGH:{
                            
                            status = compareHighSeverity(severity2, scale1, scale2);                        
                            break;
                        }case LOW:{
                            
                            status = compareLowSeveirty(severity2, scale1, scale2);
                            break;
                        }case MEDIUM:{
                            
                            status = compareMediumSeverity(scale1, severity2, scale2);
                            break;
                        }default:{
                            break;
                        }
                    }
                }
                
            }
            
            return status;
        }
        
        private static int compareMediumSeverity(double scale1, Severity severity2, double scale2) {
           
            int status;
            
            if(severity2 == Severity.LOW) {
                
                status = 1;
            }else if(severity2 == Severity.MEDIUM) {
                
                status = compareValue(scale1, scale2);
            }else {
                status = -1;
            }        
            
            return status;
        }
        
        private static int compareLowSeveirty(Severity severity, double scale1, double scale2) {
            
            int status;
            
            if(severity == Severity.LOW) {
                
                status = compareValue(scale1, scale2);
            }else {
                status = -1;
            }
            return status;
        }
        private static int compareHighSeverity(Severity severity, double scale1,  double scale2) {
            
            int status;
            
            if(severity == Severity.LOW || severity == Severity.MEDIUM) {
                
                status = 1;                                     
            }else if(severity == Severity.HIGH) {
                
                status = compareValue(scale1, scale2);
            }else {
                status = -1;
            }
            return status;
        }
        private static int compareCriticalSeverity(Severity severity, double scale1,  double scale2) {
            
            int status;
            
            if(severity == Severity.LOW || severity == Severity.MEDIUM || severity == Severity.HIGH) {
                status = 1;
            }else if(severity == Severity.CRITICAL) {
                
                status = compareValue(scale1, scale2);
            }else {
                status = -1;
            }
            
            return status;
        }

    }
    
    public final class PriorityComparator{
        
        private PriorityComparator() {
            
        }
        
        public static int comparePriority(EventModel eventModel,ChangeEventEntity other) {
            
            int status =-1;
            
            PriorityScale priorityScale1 = ((Scale)eventModel.getReactiveMetaData()).getPriorityScale();
            PriorityScale priorityScale2 = ((Scale)other.getChangedModel().getReactiveMetaData()).getPriorityScale();

            if(priorityScale1 != null && priorityScale2 != null) {
                
                Priority priority1  = priorityScale1.getPriority();
                double scale1       = priorityScale1.getValueOf(priority1);
                
                Priority priority2  = priorityScale2.getPriority();
                double scale2       = priorityScale2.getValueOf(priority2);
                                                    
                if(priority1 != null && priority2 != null) {
                
                    switch(priority1) {
                        
                        case CRITICAL:{
                            
                            status = compareCriticalPriority(priority2, scale1, scale2);
                            break;
                        }case HIGH:{
                            
                            status = compareHighPriority(priority2, scale1, scale2);
                            
                            break;
                        }case LOW:{
                            
                            status = compareLowPriority(priority2, scale1, scale2);
                            break;
                        }case MEDIUM:{
                            
                            status = compareMediumPriority(priority2, scale1, scale2);
                            break;
                        }default:{
                            break;
                        }
                    }
                }
            }
            return status;
        }
        private static int compareMediumPriority(Priority priority, double scale1, double scale2) {
            int status;
            if(priority == Priority.LOW) {
                
                status = 1;
            }else if(priority == Priority.MEDIUM) {
                
                status = compareValue(scale1, scale2);
            }else {
                status = -1;
            }
            return status;
        }
        private static int compareLowPriority(Priority priority, double scale1, double scale2) {
            int status;
            if(priority == Priority.LOW) {
                
                status = compareValue(scale1, scale2);
            }else {
                status = -1;
            }
            return status;
        }
        private static int compareHighPriority(Priority priority, double scale1, double scale2) {
            int status;
            if(priority == Priority.LOW || priority == Priority.MEDIUM) {
                
                status = 1;                                     
            }else if(priority == Priority.HIGH) {
                
                status = compareValue(scale1, scale2);
            }else {
                status = -1;
            }
            return status;
        }
        private static int compareCriticalPriority(Priority priority, double scale1, double scale2) {
            int status;
            if(priority == Priority.LOW || priority == Priority.MEDIUM || priority == Priority.HIGH) {
                status = 1;
            }else if(priority == Priority.CRITICAL) {
                
                status = compareValue(scale1, scale2);                                                  
            }else {
                status = -1;
            }
            return status;
        }
    }
}
