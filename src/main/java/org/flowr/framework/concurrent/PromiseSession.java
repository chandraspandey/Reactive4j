
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.concurrent;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import org.flowr.framework.core.promise.Timeline;
import org.flowr.framework.core.security.ClientIdentity;

public final class PromiseSession {

    private Timestamp startTime;
    private Timestamp endTime;
    private ClientIdentity clientIdentity;
    private Optional<Timeline> sessionTimeline = Optional.empty(); 
  
    public PromiseSession(ClientIdentity clientIdentity){
        
        this.clientIdentity = clientIdentity;
    }
    
    public void start() {
        
        this.startTime = new Timestamp(Instant.now().toEpochMilli());
    }
    
    public void end(Optional<Timeline> sessionTimeline) {
        
        this.endTime          = new Timestamp(Instant.now().toEpochMilli());
        this.sessionTimeline    = sessionTimeline;
    }
    
    public Duration getDuration(){
        
        Duration duration = null;
        
        if(startTime != null) {
            
            if(endTime != null) {
                
                duration =  Duration.ofMillis(endTime.getTime()-startTime.getTime());
            }else {
                duration =  Duration.ofMillis(Instant.now().toEpochMilli()-startTime.getTime());
            }
        }
        
        return duration;
    }
    
    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public ClientIdentity getClientIdentity() {
        return clientIdentity;
    }

    public Optional<Timeline> getSessionTimeline() {
        return sessionTimeline;
    }
    
    public String toString(){
        
        StringBuilder sb = new StringBuilder();
        sb.append("\n PromiseSession[");
        sb.append(" | App Name  : "+clientIdentity.getClientApplicationName());
        
        if(clientIdentity.getIdentityData().getIdentifier() != null) {
            sb.append(" | Identifier  : "+clientIdentity.getIdentityData().getIdentifier());
        }
        
        Duration duration = getDuration();
        
        if(duration != null) {
            sb.append(" | Duration(Seconds)   : "+duration.getSeconds());
        }
        sb.append(" | Start  : "+startTime);
        sb.append(" | End    : "+endTime);
        sb.append(" | sessionTimeline   : "+sessionTimeline);
        sb.append("]\n");
        
        return sb.toString();
    }


}
