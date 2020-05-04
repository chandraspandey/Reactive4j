
/**
 * Provides composite model for time boxing progress in scaleunits alongwith 
 * intervals.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.promise;

import java.sql.Timestamp;

import org.flowr.framework.core.model.Model;

public class ListenerTimeUnit implements Model{

    private double progress;

    private Timestamp occurenceTimestamp;
    
    public double getProgress() {
        return progress;
    }
    public void setProgress(double progress) {
        this.progress = progress;
    }

    public Timestamp getOccurenceTimestamp() {
        return occurenceTimestamp;
    }
    public void setOccurenceTimestamp(Timestamp occurenceTimestamp) {
        this.occurenceTimestamp = occurenceTimestamp;
    }
    
    
    public String toString(){
        
        return "ListenerTimeUnit{"+
                "progress : "+progress+
                " | occurenceTimestamp : "+occurenceTimestamp+      
                "}";
    }
}
