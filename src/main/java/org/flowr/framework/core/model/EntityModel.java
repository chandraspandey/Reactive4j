
/**
 * Defines Entity model w.r.t Relationship
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.model;

public interface EntityModel extends Model {
    
    /**
     * Defines composite Relationship for a DataModel. IS_A is a must existence for
     * domain type objects and concrete implementation must qualify it with 
     * instanceOf function. HAS_A is an optional relationship that may or may not 
     * exist or be used for persistence of composite change in terms of data models.
     * @author Chandra Shekhar Pandey
     *
     */
    public enum ModelType {
        IS_A,
        HAS_A
    }

    void setEntityModel(EntityModel entityModel);
    
    void setEntityModelType(ModelType entityModelType);
    
    ModelType getEntityModelType();
    
    EntityModel getEntityModel();
}
