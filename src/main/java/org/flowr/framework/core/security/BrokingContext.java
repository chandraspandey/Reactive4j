
/**
 * Defines the Context for security broking. 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.security;

import java.util.Calendar;

public class BrokingContext {

    private Identity identity; 
    private Calendar lastServerSyncTime;

    
    public Identity getIdentity() {
        return identity;
    }
    public void setIdentity(Identity identity) {
        this.identity = identity;
    }
    public Calendar getLastServerSyncTime() {
        return lastServerSyncTime;
    }
    public void setLastServerSyncTime(Calendar lastServerSyncTime) {
        this.lastServerSyncTime = lastServerSyncTime;
    }
    
    public String toString(){
        
        return "BrokingContext {"+
                "\n identity : "+identity+
                "\n lastServerSyncTime : "+lastServerSyncTime+
                "\n}\n";
    }
}
