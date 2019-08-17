package example.flowr.node;

import org.flowr.framework.api.Node;
import org.flowr.framework.core.config.Configuration;
import org.flowr.framework.core.config.Configuration.ConfigurationType;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.node.EndPoint.EndPointStatus;
import org.flowr.framework.core.process.management.NodeProcessHandler;
import org.flowr.framework.core.service.ServiceEndPoint;
import org.flowr.framework.core.service.ServiceFramework;

public class NodeProvider implements Node{
		
	@Override
	public NodeProcessHandler getNodeProcessHandler() {
		
		return ServiceFramework.getInstance().getNodeService().getNodeProcessHandler();
	}
	
	@Override
	public EndPointStatus addServiceEndpoint(ConfigurationType configurationType, ServiceEndPoint serviceEndPoint) throws ConfigurationException {
		
		return ServiceFramework.getInstance().getHighAvailabilityService().addServiceEndpoint(configurationType, serviceEndPoint);

	}
	
	@Override
	public EndPointStatus removeServiceEndpoint(ConfigurationType configurationType, ServiceEndPoint serviceEndPoint) throws ConfigurationException {
		
		return ServiceFramework.getInstance().getHighAvailabilityService().removeServiceEndpoint(configurationType, serviceEndPoint);
	}

	@Override
	public Configuration loadQueueProviderConfig() throws ConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
