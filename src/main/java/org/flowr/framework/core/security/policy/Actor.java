package org.flowr.framework.core.security.policy;

import org.flowr.framework.core.model.MetaData;
import org.flowr.framework.core.security.Identity;

/**
 * Defines end user as Actor in the context of user interaction programming. 
 * Based on the context of real or workflow or virtual interaction the calling
 * program can can associate a Role for the identity for execution.
 * ResourceMetaData is instrumentation capability which can be used as part
 * of discovery where Identity can not be established or in virtual or Threat
 * actor is the representation. ResourceMetaData is optional attribution.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public interface Actor extends Identity{

	public enum ActorInRole{
		GUEST,
		UNKNOWN,
		USER,
		ADVISOR,
		ADMINISTRATOR
	}
	
	public void setActorInRole(ActorInRole actorInRole);
	
	public ActorInRole getActorInRole();
	
	public void setResourceMetaData(MetaData resourceMetaData);
	
	public MetaData getResourceMetaData();
	
	
	/**
	 * Defines concrete representation of Actor in context of policy decision 
	 * resolution.SecurityMetaData is integrated for cumulative identification for
	 * traceability.
	 * @author Chandra Pandey
	 *
	 */

	public class PolicyActor implements Actor {

		private ActorInRole actorInRole = ActorInRole.ADVISOR;
		private MetaData securityMetaData;
		
		
		@Override
		public void setActorInRole(ActorInRole actorInRole) {
			this.actorInRole = actorInRole;
		}

		@Override
		public ActorInRole getActorInRole() {
			return this.actorInRole;
		}

		@Override
		public void setResourceMetaData(MetaData resourceMetaData) {
			this.securityMetaData = resourceMetaData;
		}

		@Override
		public MetaData getResourceMetaData() {

			return this.securityMetaData;
		}

		public String toString(){
			
			return "PolicyActor{"+
					"\n actorInRole : "+actorInRole+
					"\n securityMetaData : "+securityMetaData+
					"\n}\n";
		}


	}
}
