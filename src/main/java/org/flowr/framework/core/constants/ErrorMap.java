
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.constants;

public enum ErrorMap {

    ERR_TIMEOUT(101,"Operation Time Out."),
    ERR_UNREACHABLE(102,"Operation End Point Not Reachable."),
    ERR_NO_RESPONSE(103,"Operation did not recieve any Response for the request."),
    ERR_CONFIG(104,"Configuration loading failed."),
    ERR_CONFIG_INVALID_FORMAT(105,"Invalid configuration file provided for service execution."),
    ERR_SECURITY_ACCESS_DOMIAN(110,"Invalid Domain for network lookup."),
    ERR_SECURITY_ACCESS_PROTOCOL(111,"Protocol not defined for operation.."),
    ERR_ROUTE_MAPPING_NOT_FOUND(121,"Configuration for Route Mapping not found."),
    ERR_REQUEST_TYPE_NOT_FOUND(122,"Request Type not found."),
    ERR_REQUEST_INVALID(123,"Invalid Request."),
    ERR_PROCESS_INTERUPPTED(124,"Processing Interrupted."),
    ERR_CLIENT_INVALID_CONFIG(125,"Invalid Client configuration."),
    ERR_SERVER_PROCESSING(126,"Processing Failed."),
    ERR_NOTIFICATION_SUBSCRIPTION(127,"Notification Subscription Not defined."),
    ERR_NOTIFICATION_PROTOCOL(128,"Notification Protocol not defined."),
    ERR_TYPE(129,"Invalid Type."),
    ERR_MANDATORY(130,"Mandatory Field."),
    ERR_IO(150,"Unable to read input ."),
    ERR_IO_COMMAND(151,"Invalid command."),
    ERR_IO_REQUEST(152,"Invalid Request for operation."),
    ERR_IO_RESPONSE(153,"Invalid Response for operation."),
    ERR_IO_FILE_NOT_FOUND(154,"Configured file not found for the input parameter."),
    ERR_IO_INVALID_INPUT(155,"Invalid Input to framework."),
    ERR_IO_INVALID_OUTPUT(156,"Invalid Output."),
    ERR_IO_INVALID_FORMAT(157,"Invalid format fr processing."),
    ERR_SERVICE(201,"Service Error."),
    ERR_SERVICE_ALREADY_EXISTS(202,"Service with same ServiceType & name already exists."),   
    ;


    private int errorCode;
    private String errorMessage;
    
    ErrorMap(int errorCode,String errorMessage){
        
        this.errorCode      = errorCode;
        this.errorMessage   = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
