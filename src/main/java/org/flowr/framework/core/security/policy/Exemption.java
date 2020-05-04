
/**
 * Defines optional Exemption mechanism from sequential policy/ruleset execution. 
 * Implementation class provides concrete implementation of short circuit 
 * mechanism. During execution if preceeding rule provides a Deterministic 
 * Result due to which succeeding rule validation is rendered redundant and 
 * need not be performed.
 * It models the flow instrumentation of IF THEN WHAT execution policy.
 * Exemption is optionally bounded with ScheduleCalendar as a data type where
 * expiry of exemption is necessary such as Security.  
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
package org.flowr.framework.core.security.policy;

import org.flowr.framework.core.model.AttributeSet;
import org.flowr.framework.core.model.Model;

public interface Exemption extends Model{
    
    public enum ExemptionType {
        NONE,// Default
        POLICY_EXEMPTION,
        ROLE_EXEMPTION,
        IDENTITY_EXEMPTION,
        MARKED_COMPLETE_ON_SUCCESS 
    }
    
    /**
     * Defines the Mode of network connection for which the exemption should be 
     * made applicable. The use cases may involve the usage with ScheduleCalendar:
     * SERVER_TO_SERVER : If the communication channel is already covered as
     * part of SecurityGroup, admin functions can be made exempt.
     * READ, WRITE are the mode defined for Identity or APP lead access.   
     */
    public enum ExemptionMode {
        IDENTITY_READ,
        IDENTITY_WRITE,
        APP_READ,
        APP_WRITE
    }

    void setExemptionMode(AttributeSet exemptionMode);
    
    AttributeSet getExemptionMode();
    
    void setExemptionType(ExemptionType exemptionType);
    
    ExemptionType getExemptionType();
    
    
    
    /**
     * Defines Bounded exemption for process use cases for maintenance, upgrade  
     * & patch processes where interception should be considered redundant for
     * admin function.
     * It may be applicable for usecases where the business user needs to 
     * upload/download reports etc. as part of usual business function with
     * enterprise app and interception should be deemed as unnecessary. At the
     * discretion & risk sensitivity SecurityPolicy may exempt Identity, Role
     * from the processing overhead.     
     * @param scheduleCalendar
     */
    void setExemptionCalendar(ScheduleCalendar scheduleCalendar);
    
    ScheduleCalendar getExemptionCalendar();
}
