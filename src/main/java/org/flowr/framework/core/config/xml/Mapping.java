
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.config.xml;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.flowr.framework.core.model.Model;
import org.flowr.framework.core.model.Model.ModelORM;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Mapping", propOrder = {
    "entity"
})
public class Mapping {

    @XmlElement(name = "entity")
    protected List<String> entity;

    public List<String> getEntity() {
        return entity;
    }

    public void setEntity(List<String> value) {
        this.entity = value;
    }
    
    @SuppressWarnings("unchecked")
    public List<Class<? extends Model.ModelORM>> classes(){
        
        List<Class<? extends Model.ModelORM>> list = new ArrayList<>();
        
        if(entity != null) {
            
            entity.forEach(
                    
                (String e) -> {
                    
                    try {
                        list.add(
                            (Class<? extends ModelORM>) Class.forName(e));
                    } catch (ClassNotFoundException ex) {
                        Logger.getRootLogger().error(ex);
                    }
                }
            );
        }
        
        
        return list;
    }

    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        sb.append("\n  Mapping\n  {  "); 

        
        entity.forEach(
            
                p -> sb.append("\n   "+p) 
        );
        
        sb.append("\n  }\n "); 
        return sb.toString();
    }
    
}
