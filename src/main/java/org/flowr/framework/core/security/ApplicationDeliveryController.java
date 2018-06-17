package org.flowr.framework.core.security;

import org.flowr.action.Action;
import org.flowr.framework.core.model.AttributeSet;
import org.flowr.framework.core.model.Model;
import org.flowr.framework.core.security.policy.Qualifier;


/**
 * Defines cetralized control function for non disruptive business continuity.
 * Implements Strategy to provide a layered adjustment to support automatic 
 * continuity for identified & known changes in the external environment. As a
 * control objective automation implementation the decision are driven by 
 * DecisionTree and Qualification of change parameters in the model provides 
 * right selection based on AdjustmentContext required for transition from
 *  AS-IS to TO-BE.  
 *    
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class ApplicationDeliveryController implements Qualifier, ControlStrategy{

	@Override
	public QualifierResult qualify(Model model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Action applyDecision(AttributeSet attributeSet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setQualifierType(QualifierType qualifierType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public QualifierType getQualifierType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttributeSet getSatisfiedQualification() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSatisfiedQualification(AttributeSet satisfiedQualification) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AttributeSet getUnsatisfiedQualification() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUnsatisfiedQualification(AttributeSet unsatisfiedQualification) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setQualifierResult(QualifierResult qualifierResult) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public QualifierResult getQualifierResult() {
		// TODO Auto-generated method stub
		return null;
	}

}
