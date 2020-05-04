
/** Renew Token automatically for continued access in the background without
 * external renewal.  
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.security.token;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.flowr.framework.core.security.identity.IdentityData;

public class ExecutionToken implements Token{

    private IdentityData identityData;
    private long initializationTime;
    private long timeToLive; 
    private TokenType tokenType;
    
    public ExecutionToken(){
        initializationTime = Instant.now().toEpochMilli();
    }
        
    public String normalizeWithoutToken(String text, String delim){
        
        StringBuilder buffer = new StringBuilder();
        
        StringTokenizer tokenizer = new StringTokenizer(text,delim);
        
        while (tokenizer.hasMoreTokens()) {
            
            buffer.append(tokenizer.nextToken());
         }
        
        return buffer.toString();
    }
    
    public String annotateWithToken(String text, String delim,String append){
        
        StringBuilder buffer = new StringBuilder();
        
        StringTokenizer tokenizer = new StringTokenizer(text,delim);
        
        while (tokenizer.hasMoreTokens()) {
            
            buffer.append(tokenizer.nextToken()).append(append);
         }
        
        return buffer.toString();
    }
    
    public List<String> tokenAsList(String text, String delim){
        
        ArrayList<String> list = new ArrayList<>();
        
        StringTokenizer tokenizer = new StringTokenizer(text,delim);
        
        while (tokenizer.hasMoreTokens()) {
            
            list.add(tokenizer.nextToken());
         }
        
        return list;
    }
       
    @Override
    public IdentityData getAccessGranted() {

        return this.identityData;
    }

    @Override
    public void grantAccess(IdentityData identityData) {
        this.identityData=identityData;
    }

    @Override
    public void setTimeToLive(long timeToLive) {
        this.timeToLive=timeToLive;
    }

    @Override
    public long getTimeToLive() {
        return this.timeToLive;
    }

    @Override
    public boolean isValid() {
        
        boolean isValid = false;
        
        long currentTime = Instant.now().toEpochMilli();
        
        long lifespanTime = initializationTime+(initializationTime*1000);
        
        if(lifespanTime > currentTime){
            isValid = true;
        }
        
        return isValid;
    }

    @Override
    public void setTokenType(TokenType tokenType) {
        this.tokenType=tokenType;
    }

    @Override
    public TokenType getTokenType() {
        return this.tokenType;
    }

   public static void main(String[] args){
        
        ExecutionToken handler = new ExecutionToken();
        
        String para = "This is first line. Topics. A. B. C. This is last line.";
        
        Logger.getRootLogger().info("para : "+para);
        
        String changed = handler.annotateWithToken(para, ".", ".\n");

        Logger.getRootLogger().info("changed : \n"+changed);
        
        String changedBack = handler.normalizeWithoutToken(changed, "\n");
        
        Logger.getRootLogger().info("changedBack : "+changedBack);
        
        Logger.getRootLogger().info("list : "+ handler.tokenAsList(changedBack,"."));
    }
    
    public String toString(){
        
        return "ExecutionToken{"+
                "\n identityData : "+identityData+
                "\n tokenType : "+tokenType+
                "\n timeToLive : "+timeToLive+
                "\n isValid : "+isValid()+
                "\n}\n";
    }
}
