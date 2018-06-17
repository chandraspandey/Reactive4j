package org.flowr.framework.core.security.policy;

import java.sql.Timestamp;

import org.flowr.framework.core.model.DataAttribute;

/**
 * Defines Version as an identifiable set of information of version, timestamp
 * & restore point identifier.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public class Version {

	private int version;
	private Timestamp lastUpdated;
	private DataAttribute restorePointIdentifier;
	
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
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
				"\n version : "+version+
				"\n lastUpdated : "+lastUpdated+
				"\n restorePointIdentifier : "+restorePointIdentifier+
				"\n}\n";
	}

}
