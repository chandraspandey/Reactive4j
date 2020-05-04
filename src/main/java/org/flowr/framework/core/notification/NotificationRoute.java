
/**
 * Tuple represents a model of user defined type<K,V> where there is 1:N relationship between K & V. Notification & 
 * Service Configuration is instrumented so that concrete adapter classes can provide delegated implementation based
 * on Notification Characteristics. 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.notification;

import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.dispatcher.NotificationServiceAdapter;

public final class NotificationRoute{

    private NotificationServiceAdapter key;
    private NotificationProtocolType values;
    
    public void set(NotificationServiceAdapter key,NotificationProtocolType values){
        this.key = key;
        this.values = values;
    }
    
    public NotificationServiceAdapter getKey() {
        return key;
    }
    public void setKey(NotificationServiceAdapter key) {
        this.key = key;
    }
    public NotificationProtocolType getValues() {
        return values;
    }
    public void setValues(NotificationProtocolType values) {
        this.values = values;
    }
    
    @Override
    public int hashCode() {
       
        return key.hashCode();
    }
    
    /**
     * Provides concrete implementation  for equals.
     * @param other
     * @return
     */
    public boolean equals(Object other){
        
        boolean isEqual = false;        
        
        if( other != null && 
                other.getClass() == this.getClass() &&
            ((NotificationRoute)other).key.getClass().isAssignableFrom(this.key.getClass()) &&
            ((NotificationRoute)other).values.getClass().isAssignableFrom(this.values.getClass()) &&
            key.equals(((NotificationRoute)other).getKey()) && 
            values.equals(((NotificationRoute)other).getValues())
        ){
            
            isEqual = true;
        }
        
        return isEqual;
    }
    
    public boolean isKeyPresent(NotificationServiceAdapter notificationServiceAdapterInstance){
        
        boolean isPresent = false;
        
        if(key == notificationServiceAdapterInstance){
            
            isPresent =  true;
        }
        
        return isPresent;
    }
    
    public boolean isValuePresent(NotificationProtocolType notificationProtocolType){
        
        boolean isPresent = false;
        
        if(values.equals(notificationProtocolType)){            
            isPresent =  true;
        }
        
        return isPresent;
    }
    
    public String toString(){
        
        return "NotificationRoute{"+
            " | K : "+key+
            " | V : "+values+
            " | }\n";
    }
}
