package org.flowr.framework.core.config;



import static org.flowr.framework.core.constants.ExceptionConstants.ERR_CONFIG;
import static org.flowr.framework.core.constants.ExceptionMessages.MSG_CONFIG;

import java.util.HashMap;

import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.notification.NotificationServiceAdapter;
import org.flowr.framework.core.notification.NotificationTask;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.subscription.NotificationSubscription;

/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

public class ClientConfig<REQUEST, RESPONSE> {

	private String CLIENT_CONFIG_PATH;
	private String SERVER_CONFIG_PATH;
	private HashMap<NotificationProtocolType,NotificationSubscription> clientSubscriptionList;
	private NotificationServiceAdapter clientNotificationServiceAdapter;
	private NotificationTask clientNotificationTask;
	private HashMap<NotificationProtocolType,NotificationSubscription> serverSubscriptionList;
	private NotificationServiceAdapter serverNotificationServiceAdapter;
	private NotificationTask serverNotificationTask;
	private REQUEST REQ; 
	private RESPONSE RES;
	
	public ClientConfig(	
		String CLIENT_CONFIG_PATH,
		String SERVER_CONFIG_PATH,
		HashMap<NotificationProtocolType,NotificationSubscription> clientSubscriptionList,
		NotificationServiceAdapter clientNotificationServiceAdapter,
		NotificationTask clientNotificationTask,
		HashMap<NotificationProtocolType,NotificationSubscription> serverSubscriptionList,
		NotificationServiceAdapter serverNotificationServiceAdapter,
		NotificationTask serverNotificationTask,
		REQUEST REQ, 
		RESPONSE RES
	) throws ConfigurationException {
					
			this.CLIENT_CONFIG_PATH 				= CLIENT_CONFIG_PATH;
			this.SERVER_CONFIG_PATH 				= SERVER_CONFIG_PATH;
			this.clientSubscriptionList				= clientSubscriptionList;
			this.clientNotificationServiceAdapter	= clientNotificationServiceAdapter;
			this.clientNotificationTask				= clientNotificationTask;
			this.serverSubscriptionList 			= serverSubscriptionList;	
			this.serverNotificationServiceAdapter  	= serverNotificationServiceAdapter;
			this.serverNotificationTask				= serverNotificationTask;
			this.REQ								= REQ;
			this.RES								= RES;	

			if( 
				CLIENT_CONFIG_PATH != null  && clientSubscriptionList != null && !clientSubscriptionList.isEmpty() && 
				clientNotificationServiceAdapter != null && clientNotificationTask != null && 
				SERVER_CONFIG_PATH != null && serverSubscriptionList != null && !serverSubscriptionList.isEmpty() && 
				serverNotificationServiceAdapter != null && serverNotificationTask != null && 
				REQ != null && RES != null
			){	
				
			}else{
				
				throw new ConfigurationException(
						ERR_CONFIG,
						MSG_CONFIG, 
						"Mandatory Client configuration not provided for execution."+this);
			}
			
			//System.out.println("ClientConfig : CLIENT_CONFIG_PATH = "+CLIENT_CONFIG_PATH);	
	}

	public String getCLIENT_CONFIG_PATH() {
		return CLIENT_CONFIG_PATH;
	}

	public String getSERVER_CONFIG_PATH() {
		return SERVER_CONFIG_PATH;
	}

	public HashMap<NotificationProtocolType, NotificationSubscription> getClientSubscriptionList() {
		return clientSubscriptionList;
	}

	public NotificationServiceAdapter getClientNotificationServiceAdapter() {
		return clientNotificationServiceAdapter;
	}

	public NotificationTask getClientNotificationTask() {
		return clientNotificationTask;
	}

	public HashMap<NotificationProtocolType, NotificationSubscription> getServerSubscriptionList() {
		return serverSubscriptionList;
	}

	public NotificationServiceAdapter getServerNotificationServiceAdapter() {
		return serverNotificationServiceAdapter;
	}

	public NotificationTask getServerNotificationTask() {
		return serverNotificationTask;
	}

	public REQUEST getREQ() {
		return REQ;
	}

	public RESPONSE getRES() {
		return RES;
	}
	
	public String toString(){
		
		return "\n ClientConfig{"+
				" CLIENT_CONFIG_PATH : "+CLIENT_CONFIG_PATH+"\n"+	
				" SERVER_CONFIG_PATH : "+SERVER_CONFIG_PATH+"\n"+	
				" clientSubscriptionList : "+clientSubscriptionList+"\n"+	
				" clientNotificationAdapter : "+clientNotificationServiceAdapter+"\n"+	
				" clientNotificationTask : "+clientNotificationTask+"\n"+	
				" serverSubscriptionList : "+serverSubscriptionList+"\n"+	
				" serverNotificationAdapter : "+serverNotificationServiceAdapter+"\n"+	
				" serverNotificationTask : "+serverNotificationTask+"\n"+	
				" REQ : "+REQ+"\n"+	
				" RES : "+RES+"\n"+	
				"}\n";
	}
}
