package org.flowr.framework.core.security.policy;

import java.util.Calendar;

/**
 * Defines a decision with encapsulated DecisionModel as contextual data. 
 * Additionaly provides manifestation of user actors involved, Version & 
 * Timestamp for traceability.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public class PolicyDecision implements Decision{

	private DecisionModel decisionModel;
	private Calendar decisionTime;

	
	// Identity
	private String policyActorIdentifier; 
	
	// Identity
	private String userActorIdentifier; 
	

	@Override
	public void setDecisionModel(DecisionModel decisionModel) {
		this.decisionModel = decisionModel;
	}

	@Override
	public DecisionModel getDecisionModel() {
		return this.decisionModel;
	}
	

	public Calendar getDecisionTime() {
		return decisionTime;
	}

	public void setDecisionTime(Calendar decisionTime) {
		this.decisionTime = decisionTime;
	}

	public String getPolicyActorIdentifier() {
		return policyActorIdentifier;
	}

	public void setPolicyActorIdentifier(String policyActorIdentifier) {
		this.policyActorIdentifier = policyActorIdentifier;
	}

	public String getUserActorIdentifier() {
		return userActorIdentifier;
	}

	public void setUserActorIdentifier(String userActorIdentifier) {
		this.userActorIdentifier = userActorIdentifier;
	}	
	
	
	public String toString(){
		
		return "PolicyDecision{"+
				"\n decisionModel : "+decisionModel+	
				"\n decisionTime : "+decisionTime+
				"\n policyActorIdentifier : "+policyActorIdentifier+
				"\n userActorIdentifier : "+userActorIdentifier+	
				"\n}\n";
	}
	
}
