
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.config;

public class ConnectionConfiguration implements Configuration{

    private String connectionDriverClass;
    private String connectionUserName;
    private String connectionPassword;
    private String connectionURL;
    private int connectionPoolSize;

   public boolean isValidConnectionConfiguration() {
        
        return (connectionDriverClass != null && connectionPoolSize > 0 );
    }
    
    public boolean isValidCredential() {
        
        return (connectionUserName != null && connectionPassword != null && connectionURL != null );
    }
    
    public String getConnectionDriverClass() {
        return connectionDriverClass;
    }

    public void setConnectionDriverClass(String connectionDriverClass) {
        this.connectionDriverClass = connectionDriverClass;
    }
    
 
    public String getConnectionUserName() {
        return connectionUserName;
    }

    public void setConnectionUserName(String connectionUserName) {
        this.connectionUserName = connectionUserName;
    }

    public String getConnectionPassword() {
        return connectionPassword;
    }

    public void setConnectionPassword(String connectionPassword) {
        this.connectionPassword = connectionPassword;
    }

    public String getConnectionURL() {
        return connectionURL;
    }

    public void setConnectionURL(String connectionURL) {
        this.connectionURL = connectionURL;
    }


    public int getConnectionPoolSize() {
        return connectionPoolSize;
    }

    public void setConnectionPoolSize(int connectionPoolSize) {
        this.connectionPoolSize = connectionPoolSize;
    }
    
    public String toString(){
        
        return "\n ConnectionConfiguration{\n"+
                " | connectionDriverClass : "+connectionDriverClass+    
                " | connectionUserName : "+connectionUserName+  
                " | connectionURL : "+connectionURL+
                " | connectionPoolSize : "+connectionPoolSize+    
                "}\n";
    }
}
