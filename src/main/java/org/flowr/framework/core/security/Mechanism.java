package org.flowr.framework.core.security;

/**
 * High level marker definition for a mechanism which can be used to represent
 * closed internal or external process. The concrete implementation would be
 * a representation of ProxyPattern. For internal module or library the mechanism
 * would provide wrapper with identifiable API for invocation. For external 
 * service manifestation it will provide wrapper mechanism for encapsulating
 * security of published end point & augmented parameters for invocation as
 * deemed necessary.
 * In addition internally the concrete implementation can use Adapters as necessary
 * for to & fro translation of data types as deemed necessary.
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 *
 */

public interface Mechanism {


}
