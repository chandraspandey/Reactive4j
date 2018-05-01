package org.flowr.framework.core.service;

import org.flowr.framework.core.service.dependency.Dependency;
import org.flowr.framework.core.service.dependency.DependencyLoop;

public interface ServiceFrameworkComponent extends Service, Dependency, DependencyLoop{

	public void setServiceFramework(ServiceFramework<?,?> serviceFramework);
}
