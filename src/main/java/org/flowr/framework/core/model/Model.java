
/**
 * 
 * Marker interface for object models. 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.model;

import java.util.Optional;

public interface Model {

    
    /**
     * Marker interface for Request object models. 
     * @author Chandra Shekhar Pandey
     * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
     *
     */
    public interface RequestModel extends Model{
        
    }
    
    /**
     * Marker interface for Response object models. 
     * @author Chandra Shekhar Pandey
     * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
     *
     */
    public interface ResponseModel extends Model{
        
    }
    
    /**
     * Marker interface for persistence object models. 
     * @author Chandra Shekhar Pandey
     * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
     *
     */
    public interface ModelORM extends Model{
        
        
        Optional<Model> toModel(Model model);
        
        Optional<ModelORM> fromModel(Model model);
    }
}
