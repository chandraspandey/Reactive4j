
/**
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

package org.flowr.framework.core.exception;

import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.constants.Constant.FrameworkConstants;

public class AccessSecurityException extends Exception{
   
    private static final long serialVersionUID = FrameworkConstants.FRAMEWORK_VERSION_ID;

    private final int errorCode;
    private final String warningMessage;
    private final String securityMessage;

    public AccessSecurityException(ErrorMap errorMap,String warningMessage){
        
        super(errorMap.getErrorMessage());
        this.errorCode          = errorMap.getErrorCode();
        this.securityMessage    = errorMap.getErrorMessage();
        this.warningMessage     = warningMessage;
    }
    
    
    public String getWarningMessage() {
        return warningMessage;
    }

    public String getSecurityMessage() {
        return securityMessage;
    }
    
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getLocalizedMessage(){        
        
        return super.toString()+" : "+warningMessage+" : "+securityMessage;     
    }


}
