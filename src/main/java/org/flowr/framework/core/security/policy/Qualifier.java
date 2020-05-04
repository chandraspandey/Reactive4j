
/**
 * Defines Qualifier as a type. The implementation classes provide concrete
 * behaviour for qualification based on use case.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
package org.flowr.framework.core.security.policy;

import org.flowr.framework.core.model.AttributeSet;
import org.flowr.framework.core.model.Model;

public interface Qualifier {

    QualifierResult qualify(Model model);
    
    void setQualifierType(QualifierType qualifierType);
    
    QualifierType getQualifierType();
    
    AttributeSet getSatisfiedQualification();

    void setSatisfiedQualification(
            AttributeSet satisfiedQualification);
    
    AttributeSet getUnsatisfiedQualification();

    void setUnsatisfiedQualification(
            AttributeSet unsatisfiedQualification);
    
    /*
     * Defines qualification as distinct types
     */
    public enum QualifierType {
        ELIGIBILITY,
        REMEDIATION
    }
    
    /*
     * Defines attribution to be considered for evaluating the data as OPTIONAL
     * or MANDATORY in qualification
     */
    public enum QualifierOption {
        OPTIONAL,
        MANDATORY       
    }
    
    public enum QualifierResult {
        DEFAULT,
        QUALIFIED,
        QUALIFIED_TILL_LIMIT,
        NOT_QUALIFIED,
        EXPIRED_FOR_QUALIFICATION,
        UNQUALIFIED_FOR_GOLIVE,
        DELEGATED_FOR_QUALIFICATION,
        EXEMPTED
    }
    
    void setQualifierResult(QualifierResult qualifierResult);
    
    QualifierResult getQualifierResult();

}
