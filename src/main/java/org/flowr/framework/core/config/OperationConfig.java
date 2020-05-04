
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.config;

import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.model.Model.RequestModel;
import org.flowr.framework.core.model.Model.ResponseModel;

public class OperationConfig {

    private  NotificationConfig clientNotificationConfig;
    private NotificationConfig serverNotificationConfig;
    private RequestModel requestModel; 
    private ResponseModel responseModel;
    
    private boolean isValidConfig;
    
    public OperationConfig( 
        NotificationConfig clientNotificationConfig,
        NotificationConfig serverNotificationConfig,
        RequestModel requestModel, 
        ResponseModel responseModel
    ) throws ConfigurationException {

            this.clientNotificationConfig   = clientNotificationConfig;
            this.serverNotificationConfig   = serverNotificationConfig;
            this.requestModel               = requestModel;
            this.responseModel              = responseModel;    

            if( 
                clientNotificationConfig.isValidConfig() && 
                serverNotificationConfig.isValidConfig() && 
                requestModel != null && responseModel != null
            ){  
                
                isValidConfig = true;
            }else{
                
                throw new ConfigurationException(
                        ErrorMap.ERR_CONFIG,"Mandatory Client configuration not provided for execution."+this);
            }       
    }

    public NotificationConfig getClientNotificationConfig() {
        return clientNotificationConfig;
    }

    public NotificationConfig getServerNotificationConfig() {
        return serverNotificationConfig;
    }

    public RequestModel getRequestModel() {
        return requestModel;
    }

    public ResponseModel getResponseModel() {
        return responseModel;
    }   
    
    public boolean isValidConfig() {
        return isValidConfig;
    }
    
    public String toString(){
        
        return "\n OperationConfig{"+
                "\n | isValidConfig             : "+isValidConfig+ 
                "\n | requestModel              : "+requestModel+   
                "\n | responseModel             : "+responseModel+
                "\n | clientNotificationConfig  : "+clientNotificationConfig+   
                "\n | serverNotificationConfig  : "+serverNotificationConfig+ 
                "\n}\n";
    }
}
