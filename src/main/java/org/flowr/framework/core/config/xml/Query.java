
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.config.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Query", propOrder = {
    "show",
    "format",
    "queryCache"
})
class Query {

    @XmlElement(name = "show", required = true)
    protected String show;
    @XmlElement(name = "format", required = true)
    protected String format;
    @XmlElement(name = "queryCache", required = true)
    protected QueryCache queryCache;

    public String getShow() {
        return show;
    }

    public void setShow(String value) {
        this.show = value;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String value) {
        this.format = value;
    }

    public QueryCache getQueryCache() {
        return queryCache;
    }

    public void setQueryCache(QueryCache value) {
        this.queryCache = value;
    }
    
    @Override
    public String toString() {
        return "\n  Query\n  [ "+
                "   \n    show   : "+show + 
                ", \n    format  : "+format + 
                ", "+queryCache + 
                "  ] \n";
    }

}
