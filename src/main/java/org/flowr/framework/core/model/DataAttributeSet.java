
/**
 * A collection wrapper for DataAttribute that can form a trend.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataAttributeSet implements AttributeSet{

    private List<Attribute> attributeList = new ArrayList<>();
    
    @Override
    public List<Attribute> getAttributeList() {
        return this.attributeList;
    }

    @Override
    public void setAttributeList(List<Attribute> attributeList) {
        this.attributeList=attributeList;       
    }
    
    @Override
    public void addAttribute(Attribute dataAttribute) {
        attributeList.add(dataAttribute);
    }

    public boolean isKeyPresent(DataAttribute dataAttribute){
        
        boolean isPresent = false;
        
        if(!attributeList.isEmpty()){
            
            Iterator<Attribute> attributeIterator = attributeList.iterator();
            
            while(attributeIterator.hasNext()){
                
                DataAttribute attrib = (DataAttribute)attributeIterator.next();
                
                if(attrib.isKeyPresent(dataAttribute) ){
                    
                     isPresent = true;
                     break;
                }
            }
        }
        
        return isPresent;
    }

    public boolean isValuePresent(DataAttribute dataAttribute){
        
        boolean isPresent = false;
        
        if(!attributeList.isEmpty()){
            
            Iterator<Attribute> attributeIterator = attributeList.iterator();
            
            while(attributeIterator.hasNext()){
                
                DataAttribute attrib = (DataAttribute)attributeIterator.next();
                
                if(attrib.isValuePresent(dataAttribute) ){
                    
                     isPresent = true;
                     break;
                }
            }
        }
        
        return isPresent;
    }   
    
    @Override
    public boolean isEmpty() {
        
        if(attributeList != null){
            return attributeList.isEmpty();
        }else{
            return true;
        }
        
        
    }
    
    
    public String toString(){
        
        return "DataAttributeSet{"+
                "\n attributeList : "+attributeList+    
                "\n}\n";
    }
    
}
