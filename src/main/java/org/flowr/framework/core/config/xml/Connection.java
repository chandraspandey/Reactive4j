
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.config.xml;

import java.math.BigInteger;

import org.flowr.framework.core.config.ConnectionConfiguration;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Connection", propOrder = {
    "dialect",
    "driver",
    "userName",
    "cred",
    "db",
    "url",
    "poolSize"
})
public class Connection {

    @XmlElement(name = "dialect", required = true)
    protected String dialect;
    @XmlElement(name = "driver", required = true)
    protected String driver;
    @XmlElement(name = "userName", required = true)
    protected String userName;
    @XmlElement(name = "cred", required = true)
    protected String cred;
    @XmlElement(name = "db", required = true)
    protected String db;
    @XmlElement(name = "url", required = true)
    protected String url;
    @XmlElement(name = "poolSize", required = true)
    protected BigInteger poolSize;
    
    public ConnectionConfiguration toConnectionConfiguration() {
        
        ConnectionConfiguration config = new ConnectionConfiguration();
        config.setConnectionDriverClass(driver);
        config.setConnectionPassword(cred);
        config.setConnectionPoolSize(poolSize.intValue());
        config.setConnectionURL(url);
        config.setConnectionUserName(userName);
        config.setDialect(dialect);
        config.setDbName(db);
        
        return config;
    }
    public String getDialect() {
        return dialect;
    }

    public void setDialect(String value) {
        this.dialect = value;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String value) {
        this.driver = value;
    }

    public String getUser() {
        return userName;
    }

    public void setUser(String value) {
        this.userName = value;
    }

    public String getCred() {
        return cred;
    }

    public void setCred(String value) {
        this.cred = value;
    }

    public String getDB() {
        return db;
    }

    public void setDB(String value) {
        this.db = value;
    }

    public String getURL() {
        return url;
    }

    public void setURL(String value) {
        this.url = value;
    }

    public BigInteger getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(BigInteger value) {
        this.poolSize = value;
    }

    @Override
    public String toString() {
        return "\n  Connection\n  [ "+
                "   \n   dialect  : "+dialect + 
                " , \n   driver   : "+driver + 
                " , \n   userName : "+userName + 
                " , \n   cred     : "+cred + 
                " , \n   db       : "+db + 
                " , \n   url      : "+url + 
                " , \n   poolSize : "+poolSize + 
                "  \n  ] \n";
    }

}
