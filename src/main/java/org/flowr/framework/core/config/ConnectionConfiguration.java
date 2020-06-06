
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
    private String dialect;
    private String dbName;
    
    public boolean isValid() {
        
        return (isValidConnectionConfiguration() && isValidCredential() && isValidDatabaseDialect() );
    }

   public boolean isValidConnectionConfiguration() {
        
        return (connectionDriverClass != null && connectionPoolSize > 0 );
    }
    
    public boolean isValidCredential() {
        
        return (connectionUserName != null && connectionPassword != null && connectionURL != null );
    }
    
    public boolean isValidDatabaseDialect() {
        
        return (dbName != null && dialect != null );
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
    
    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }
    
    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
    
    public String toString(){
        
        return "\n ConnectionConfiguration{\n"+
                " | connectionDriverClass : "+connectionDriverClass+    
                " | connectionUserName : "+connectionUserName+  
                " | connectionURL : "+connectionURL+
                " | connectionPoolSize : "+connectionPoolSize+    
                " | dialect : "+dialect+ 
                " | dbName : "+dbName+ 
                "}\n";
    }
}
