package org.flowr.framework.core.service.extension;

import org.flowr.framework.core.process.management.ManagedProcessHandler;
import org.flowr.framework.core.service.ServiceFrameworkComponent;

public interface ManagedService extends ServiceFrameworkComponent{

	public static ManagedProcessHandler getDefaultProcessHandler() {
		
		return DefaultManagedService.getDefaultProcessHandler();
	}
	
	public static ManagedService getInstance() {
		
		return DefaultManagedService.getInstance();
	}
	
	public class DefaultManagedService{
		
		private static ManagedService managedService 				= null;
		private static ManagedProcessHandler defaultProcessHandler 	= null;
		

		public static ManagedService getInstance() {
			
			if(managedService == null) {
				managedService = new ManagedServiceImpl();
			}
			
			return managedService;
		}
		
		public static ManagedProcessHandler getDefaultProcessHandler() {
			
			if(defaultProcessHandler == null) {
				defaultProcessHandler 	= new ManagedProcessHandler();
			}
			
			return defaultProcessHandler;
		}
		
	}
}
