package org.flowr.framework.core.model;


/**
 * Defines Entity model w.r.t Relationship
 * @author Chandra Shekhar Pandey
 * Copyright © 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
public interface EntityModel extends Model {

	public void setEntityModel(EntityModel entityModel);
	
	public void setEntityModelType(ModelType entityModelType);
	
	public ModelType getEntityModelType();
	
	public EntityModel getEntityModel();
}
