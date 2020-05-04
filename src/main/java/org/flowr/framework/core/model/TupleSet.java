
/**
 *  
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TupleSet<K,V> {

    private List<Tuple<K,V>> tupleList = new ArrayList<>();
    
    public List<Tuple<K, V>> getTupleList() {
        return tupleList;
    }

    public void setTupleList(List<Tuple<K, V>> tupleList) {
        this.tupleList = tupleList;
    }   
    
    public void add(Tuple<K,V> tuple){
        tupleList.add(tuple);
    }
    
    
    public boolean isPresent(Tuple<K,V> tuple){
        
        boolean isPresent = false;
        
        if(!tupleList.isEmpty()){
            
            Iterator<Tuple<K,V>> tupleIterator = tupleList.iterator();
            
            while(tupleIterator.hasNext()){
                
                if(tupleIterator.next().equals(tuple)){
                    isPresent = true;
                    break;
                }
            }
            
        }
        
        return isPresent;
    }
    
    
    public String toString(){
        
        return "TupleSet{"+
                " tupleList : "+tupleList+  
                "}\n";
    }
}
