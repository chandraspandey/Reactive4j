
/**
 * Consumes the ReactiveMetaData as input for processing for deriving Entity model & associated attribute.
 * Composite Model represents both entity & attribute model in a 1:N  relationship. The attributes can be dynamically
 * associated with the entity as a collection and may or may not have direct relationship. EventModel may be consumed 
 * by AI systems to determine strong or weak link between EntityModel & DataAttribute for inference or conclusion. 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.model;

import org.flowr.framework.core.context.Context;
import org.flowr.framework.core.event.ReactiveMetaData;

public class EventModel implements Model{

    private Context eventContext;
    private EntityModel entityModel;    
    private DataAttributeSet dataAttributeSet;
    
    private ReactiveMetaData reactiveMetaData;
    
    public EntityModel getEntityModel() {
        return entityModel;
    }
    public void setEntityModel(EntityModel entityModel) {
        this.entityModel = entityModel;
    }

    public DataAttributeSet getDataAttributeSet() {
        return dataAttributeSet;
    }
    
    public void addDataAttribute(DataAttribute dataAttribute) {
        this.dataAttributeSet.addAttribute(dataAttribute); 
    }
    
    
    public void setDataAttributeSet(AttributeSet dataAttributeSet) {
        this.dataAttributeSet.getAttributeList().addAll(dataAttributeSet.getAttributeList());
    }
    
    public ReactiveMetaData getReactiveMetaData() {
        return reactiveMetaData;
    }

    public void setReactiveMetaData(ReactiveMetaData reactiveMetaData) {
        this.reactiveMetaData = reactiveMetaData;
    }

    public Context getContext() {
        return eventContext;
    }
    public void setContext(Context eventContext) {
        this.eventContext = eventContext;
    }
    
    public String toString(){
        
        return "EventModel{"+
                " eventContext : "+eventContext+
                " | reactiveMetaData : "+reactiveMetaData+
                " | entityModel : "+entityModel+
                " | dataAttributeSet : "+dataAttributeSet+          
                "}";
    }


}
