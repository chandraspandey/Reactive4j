
/**
 * Defines Model which encloses PromiseState, PromiseStatus, Timeline alongwith
 * DeferredResponse. The client API can use DeferredResponse accordingly with 
 * ProgressScale & handle raw response.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.promise;

import java.util.Collection;
import java.util.concurrent.Delayed;

import org.flowr.framework.core.model.Model.ResponseModel;

public class PromiseResponse {
    
    private Timeline timeline;
    private ResponseModel response;
    private Scale scale;    
    
    /* Is populated only when target invocation is successfull & returned as 
     * part ProgressScale & also as part of final response. Use for bi 
     * directional traceability between client & response.
     * 
     */
    private String acknowledgmentIdentifier;
    
    // Maps to HTTP Status
    private int status; 
    
    // Maps to Error Message that can be mapped to error states
    private String errorMessage;
    
    // Used only for stream & chunked messages of phased promise
    private Collection<Delayed> streamValues;
    
    public Timeline getTimeline() {
        return timeline;
    }
    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }
    public ResponseModel getResponse() {
        return response;
    }
    public void setResponse(ResponseModel response) {
        this.response = response;
    }
    public Scale getProgressScale() {
        return scale;
    }
    public void setProgressScale(Scale progressScale) {
        this.scale = progressScale;
    }
    
    public String getAcknowledgmentIdentifier() {
        return acknowledgmentIdentifier;
    }

    public void setAcknowledgmentIdentifier(String acknowledgmentIdentifier) {
        this.acknowledgmentIdentifier = acknowledgmentIdentifier;
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public Collection<Delayed> getStreamValues() {
        return streamValues;
    }
    public void setStreamValues(Collection<Delayed> streamValues) {
        this.streamValues = streamValues;
    }
    
    public String toString(){
        
        return "\n PromiseResponse{"+
                "\n acknowledgmentIdentifier : "+acknowledgmentIdentifier+
                " | status : "+status+
                " | timeline : "+timeline+              
                " | streamValues : "+streamValues+
                " | errorMessage : "+errorMessage+
                " | \n response : "+response+
                " | scale : "+scale+
                "}\n";
    }



    
}
