
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
package org.flowr.framework.core.security.policy;

import org.flowr.framework.core.security.policy.Qualifier.QualifierOption;
import org.flowr.framework.core.security.policy.Qualifier.QualifierResult;
import org.flowr.framework.core.security.policy.Qualifier.QualifierType;

public interface PolicyCompliance {

    QualifierResult qualify();
    
    void setQualifierType(QualifierType qualifierType);
    
    QualifierType getQualifierType();
    
    QualifierOption getQualifierOption();
    
    void setQualifierOption(QualifierOption qualifierOption);

    
}
