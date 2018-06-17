package org.flowr.framework.core.security.token;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import org.flowr.framework.core.security.identity.IdentityData;

/**
 * Concrete Tokenized implementation for time bound access. 
 * @author Chandra Pandey
 *
 */

/** Renew Token automatically for continued access in the background without
 * external renewal.  
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class ExecutionToken implements Token, TokenHandler{

	private IdentityData identityData;
	private long initializationTime;
	private long timeToLive = 0; // Default to 0/Expired
	private TokenType tokenType;
	
	
	public String normalizeWithoutToken(String text, String delim){
		
		StringBuffer buffer = new StringBuffer();
		
		StringTokenizer tokenizer = new StringTokenizer(text,delim);
		
		while (tokenizer.hasMoreTokens()) {
			
			buffer.append(tokenizer.nextToken());
	     }
		
		return buffer.toString();
	}
	
	public String annotateWithToken(String text, String delim,String append){
		
		StringBuffer buffer = new StringBuffer();
		
		StringTokenizer tokenizer = new StringTokenizer(text,delim);
		
		while (tokenizer.hasMoreTokens()) {
			
			buffer.append(tokenizer.nextToken()).append(append);
	     }
		
		return buffer.toString();
	}
	
	public List<String> tokenAsList(String text, String delim){
		
		ArrayList<String> list = new ArrayList<String>();
		
		StringTokenizer tokenizer = new StringTokenizer(text,delim);
		
		while (tokenizer.hasMoreTokens()) {
			
			list.add(tokenizer.nextToken());
	     }
		
		return list;
	}
	
	public static void main(String args[]){
		
		ExecutionToken handler = new ExecutionToken();
		
		String para = "This is first line. Topics. A. B. C. This is last line.";
		
		System.out.println("para : "+para);
		
		String changed = handler.annotateWithToken(para, ".", ".\n");

		System.out.println("changed : \n"+changed);
		
		String changedBack = handler.normalizeWithoutToken(changed, "\n");
		
		System.out.println("changedBack : "+changedBack);
		
		System.out.println("list : "+ handler.tokenAsList(changedBack,"."));
	}
	
	
	public ExecutionToken(){
		initializationTime = Calendar.getInstance().getTimeInMillis();
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
		
		long currentTime = Calendar.getInstance().getTimeInMillis();
		
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

	
	public String toString(){
		
		return "ExecutionToken{"+
				"\n identityData : "+identityData+
				"\n tokenType : "+tokenType+
				"\n timeToLive : "+timeToLive+
				"\n isValid : "+isValid()+
				"\n}\n";
	}




}
