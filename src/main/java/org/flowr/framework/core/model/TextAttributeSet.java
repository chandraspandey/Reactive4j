package org.flowr.framework.core.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.flowr.framework.core.model.Attribute;
import org.flowr.framework.core.model.AttributeSet;

/**
 * A collection wrapper for TextAttribute that can form a trend.
 * @author Chandra Pandey
 *
 */

public class TextAttributeSet implements AttributeSet{

	private List<Attribute> attributeList = new ArrayList<Attribute>();
	
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
	
	public String getValue(String keyAttribute){
		
		String val = null;
		
		if(!attributeList.isEmpty()){
			
			Iterator<Attribute> attributeIterator = attributeList.iterator();
			
			while(attributeIterator.hasNext()){
				
				TextAttribute attrib = (TextAttribute)attributeIterator.next();
				
				if(attrib.isKeyPresent(keyAttribute) ){
					
					val = attrib.getValue();
					break;
				}
			}
		}
		
		return val;
	}
	
	public boolean isKeyPresent(String keyAttribute){
		
		boolean isPresent = false;
		
		if(!attributeList.isEmpty()){
			
			Iterator<Attribute> attributeIterator = attributeList.iterator();
			
			while(attributeIterator.hasNext()){
				
				TextAttribute attrib = (TextAttribute)attributeIterator.next();
				
				if(attrib.isKeyPresent(keyAttribute) ){
					
					 isPresent = true;
					 break;
				}
			}
		}
		
		return isPresent;
	}
	
	
	public boolean isKeyPresent(TextAttribute textAttribute){
				
		boolean isPresent = false;
		
		if(!attributeList.isEmpty()){
			
			Iterator<Attribute> attributeIterator = attributeList.iterator();
			
			while(attributeIterator.hasNext()){
				
				TextAttribute attrib = (TextAttribute)attributeIterator.next();
				
				if(attrib.isKeyPresent(textAttribute) ){
					
					 isPresent = true;
					 break;
				}
			}
		}
		
		return isPresent;
	}
	
	public boolean isValuePresent(String valueAttribute){
		
		boolean isPresent = false;
		
		if(!attributeList.isEmpty()){
			
			Iterator<Attribute> attributeIterator = attributeList.iterator();
			
			while(attributeIterator.hasNext()){
				
				TextAttribute attrib = (TextAttribute)attributeIterator.next();
				
				if(attrib.isValuePresent(valueAttribute) ){
					
					 isPresent = true;
					 break;
				}
			}
		}
		
		return isPresent;
	}

	public boolean isValuePresent(TextAttribute textAttribute){
		
		boolean isPresent = false;
		
		if(!attributeList.isEmpty()){
			
			Iterator<Attribute> attributeIterator = attributeList.iterator();
			
			while(attributeIterator.hasNext()){
				
				TextAttribute attrib = (TextAttribute)attributeIterator.next();
				
				if(attrib.isValuePresent(textAttribute) ){
					
					 isPresent = true;
					 break;
				}
			}
		}
		
		return isPresent;
	}
	
	
	/*
	 * Provides a wrapper functionality around pattern matching functionality
	 * of String for region matching in the entire attribute set.
	 */
	public boolean isPatternMatch(int toffset, String other,
            int ooffset, int len){
		
		boolean isPatternMatch = false;
		
		if(!attributeList.isEmpty()){
			
			Iterator<Attribute> attributeIterator = attributeList.iterator();
			
			while(attributeIterator.hasNext()){
				
				TextAttribute attrib = (TextAttribute)attributeIterator.next();
				
				if(attrib.isPatternMatch(toffset, other, ooffset, len)){
					
					isPatternMatch = true;
					 break;
				}
			}
		}
		
		return isPatternMatch;
		
	}
	
	public String toString(){
		
		return "TextAttributeSet{"+
				" attributeList : "+attributeList+	
				"}\n";
	}

	@Override
	public boolean isEmpty() {
		
		if(attributeList != null){
			return attributeList.isEmpty();
		}else{
			return true;
		}
		
		
	}
	
}
