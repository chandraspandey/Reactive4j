
/**
 * Defines Version as an identifiable set of information of version, time stamp
 * & restore point identifier.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
package org.flowr.framework.core.security.policy;

import java.sql.Timestamp;

import org.flowr.framework.core.model.DataAttribute;

public class Version {

    private int versionNumber;
    private Timestamp lastUpdated;
    private DataAttribute restorePointIdentifier;
    
    public int getVersion() {
        return versionNumber;
    }
    public void setVersion(int versionNumber) {
        this.versionNumber = versionNumber;
    }
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }
    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
    public DataAttribute getRestorePointIdentifier() {
        return restorePointIdentifier;
    }
    public void setRestorePointIdentifier(DataAttribute restorePointIdentifier) {
        this.restorePointIdentifier = restorePointIdentifier;
    }   
    
    public String toString(){
        
        return "Version{"+
                "\n versionNumber : "+versionNumber+
                "\n lastUpdated : "+lastUpdated+
                "\n restorePointIdentifier : "+restorePointIdentifier+
                "\n}\n";
    }

}
