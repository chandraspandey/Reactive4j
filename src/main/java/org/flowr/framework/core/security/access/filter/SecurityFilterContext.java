
/**
 * Extends FilterContext for security specific filtering instrumentation. 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */
package org.flowr.framework.core.security.access.filter;

import org.flowr.framework.core.context.Context.FilterContext;

public class SecurityFilterContext extends FilterContext {

    private int startOffset;
    private String uri;
    private int endOffset;
    private int length; 
    
    public int getStartOffset() {
        return startOffset;
    }
    public void setStartOffset(int startOffset) {
        this.startOffset = startOffset;
    }
    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }
    public int getEndOffset() {
        return endOffset;
    }
    public void setEndOffset(int endOffset) {
        this.endOffset = endOffset;
    }
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString(){
        
        return "SecurityFilterContext{"+
                "\n startOffset : "+startOffset+
                "\n endOffset : "+endOffset+
                "\n uri : "+uri+
                "\n length : "+length+
                "\n}\n";
    }
    
}
