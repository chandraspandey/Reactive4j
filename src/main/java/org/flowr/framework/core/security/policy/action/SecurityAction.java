package org.flowr.framework.core.security.policy.action;

import org.flowr.framework.action.Action;

/**
 * Extends Action Interface to provide Security specific instrumentation
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public interface SecurityAction extends Action{
	
	public enum CauseType {
		OUTAGE,
		REMEDIATION,
		THREAT
	}
	
	public enum ActionType {	
		FIREWALL,
		MASKING,
		OBFUSCATION,
		REMOVAL
	}
	
	public void setPolicySubject(PolicySubject policySubject);
	
	public PolicySubject getPolicySubject();
	
	public void setPolicyTarget(PolicyTarget PolicyTarget);
	
	public PolicyTarget getPolicyTarget();
	
	public void setCauseType(CauseType causeType);
	
	public CauseType getCauseType();

	public void setActionType(ActionType securityActionType);
	
	public ActionType getActionType();

}
