
/**
 * TextAttribute represents a model of user defined type where there is 1:1 
 * relationship exists between key & value as a Text expression.
 * @author Chandra Pandey
 *
 */

package org.flowr.framework.core.model;

public class TextAttribute implements Attribute{

    private String key;
    private String value;
    
    // Attribute defined for marking the length to be read
    private int charOffset;
    private int charLength;
    
    
    public void set(String key, String value){
        this.key = key;
        this.value = value;
    }
    
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    
    public int getCharOffset() {
        return charOffset;
    }

    public void setCharOffset(int charOffset) {
        this.charOffset = charOffset;
    }

    public int getCharLength() {
        return charLength;
    }

    public void setCharLength(int charLength) {
        this.charLength = charLength;
    }
    
    public boolean isKeyPresent(String keyAttribute){
        
        boolean isPresent = false;
        
        if(key.equals(keyAttribute)){
            
            isPresent =  true;
        }
        
        return isPresent;
    }
    
    
    public boolean isKeyPresent(TextAttribute textAttribute){
        
        boolean isPresent = false;
        
        if(key.equals(textAttribute.getKey())){
            
            isPresent =  true;
        }
        
        return isPresent;
    }
    
    public boolean isValuePresent(String valueAttribute){
        
        boolean isPresent = false;
        
        if(value.equals(valueAttribute)){
            
            isPresent =  true;
        }
        
        return isPresent;
    }
    
    public boolean isValuePresent(TextAttribute textAttribute){
        
        boolean isPresent = false;
        
        if(value.equals(textAttribute.getValue())){
            
            isPresent =  true;
        }
        
        return isPresent;
    }
    
    
    /*
     * Provides a wrapper functionality around pattern matching functionality
     * of String for region matching.
     */
    public boolean isPatternMatch(int toffset, String other,
            int ooffset, int len){
        
        boolean isPresent = false; 
        
        String tempVal = null;
        
        if(value != null){
            
            if(value.endsWith(".*")){
            
                tempVal = value.substring(toffset, value.indexOf(".*"));
                isPresent = tempVal.regionMatches(toffset, other, ooffset, tempVal.length());           
            }else{
                isPresent = value.regionMatches(toffset, other, ooffset, len);      
            }
        }
        
        return isPresent;
    }
    
    
    public String toString(){
        
        return "TextAttribute{"+
                " key : "+key+
                " | value : "+value+
                " | charOffset : "+charOffset+
                " | charLength : "+charLength+
                "}\n";
    }


}
