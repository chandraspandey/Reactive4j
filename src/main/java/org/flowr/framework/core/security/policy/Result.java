package org.flowr.framework.core.security.policy;

import org.flowr.framework.core.model.DataAttribute;
import org.flowr.framework.core.model.DataAttributeSet;
import org.flowr.framework.core.security.policy.Policy.PolicyError;
import org.flowr.framework.core.security.policy.Policy.PolicyStatus;

/**
 *  Provides Result as a List of rule id & status as a pair. Provides linkage 
 *  for the calling program to look up Rule & associated policy for end user 
 *  display. It is maintained as a list for 
 *  RuleSetErrorPolicy.CONTINUE_ON_ERROR option.   
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public interface Result {

	public DataAttributeSet getResultSet();
	
	public enum SessionResult {

		APPLIED,
		FAILED,
		NOT_APPLIED,
		ERROR
	}
	
	
	/**
	 * Concrete implementation of Result for Policy application.
	 * PolicyError is populated when the result is not available.
	 * @author Chandra Pandey
	 *
	 */

	public class PolicyResult implements Result{
		
		private DataAttributeSet resultList = new DataAttributeSet(); 
		
		private PolicyError policyError;

		@Override
		public DataAttributeSet getResultSet() {

			return resultList;
		} 
		
		
		public void addToPolicyResult(Long sequence,PolicyStatus policyStatus){
			
			resultList.addAttribute(new DataAttribute());
			
			/*Pair<Long,PolicyStatus> pair = new Pair<Long,PolicyStatus>();
			pair.setKey(sequence);
			pair.setValue(policyStatus);
			
			resultList.add(pair);*/
		}
		
		public PolicyError getPolicyError() {
			return policyError;
		}


		public void setPolicyError(PolicyError policyError) {
			this.policyError = policyError;
		}
		
		public String toString(){
			
			return "PolicyResult{"+
					"\n resultList : "+resultList+	
					"\n policyError : "+policyError+		
					"\n}\n";
		}

	}
}
