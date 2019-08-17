package example.flowr.promise.client;

import static org.flowr.framework.core.constants.ExceptionConstants.ERR_CONFIG;
import static org.flowr.framework.core.constants.ExceptionMessages.MSG_CONFIG;

import java.util.Optional;
import java.util.Properties;

import org.flowr.framework.core.config.OperationConfig;
import org.flowr.framework.core.config.Configuration.ConfigurationType;
import org.flowr.framework.core.config.ServiceConfiguration;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.exception.PromiseException;
import org.flowr.framework.core.process.ProcessBuilder;
import org.flowr.framework.core.promise.PromiseRequest;
import org.flowr.framework.core.promise.PromiseResponse;
import org.flowr.framework.core.promise.PromiseTypeClient;
import org.flowr.framework.core.promise.PromiseTypeServer;
import org.flowr.framework.core.security.ClientIdentity;
import org.flowr.framework.core.service.Service.ServiceStatus;
import org.flowr.framework.core.service.ServiceFramework;
import org.flowr.framework.core.service.ServiceProvider;
import org.flowr.framework.core.service.ServiceRequest.RequestType;

import example.flowr.promise.server.DeferredPromiseServer;

public class DeferredPromiseClient<REQUEST, RESPONSE> implements PromiseTypeClient<REQUEST, RESPONSE> {

	private boolean isConfigured;
	private RequestType requestType 						= RequestType.PROMISE_DEFFERED;
	private ProcessBuilder<REQUEST, RESPONSE> builder 		= new ProcessBuilder<REQUEST, RESPONSE>();
	private OperationConfig<REQUEST, RESPONSE> clientConfig;
	private ServiceConfiguration adapterServiceConfiguration= null;
	
	@Override
	public ClientIdentity buildClientIdentity() throws ConfigurationException{
		
		ClientIdentity clientIdentity = new ClientIdentity("PromiseClient",ClientType.PERSISTENT);
		
		return clientIdentity;
	}
	
	@Override
	public PromiseRequest<REQUEST>  buildPromiseRequest() throws ConfigurationException{
		
		PromiseRequest<REQUEST> promiseRequest = new PromiseRequest<REQUEST>();
		
		promiseRequest.setRequestType(requestType);
		
		promiseRequest.setTimelineRequired(true);
		promiseRequest.setClientIdentity(buildClientIdentity());
		return promiseRequest;	
	}
	
	@Override
	public PromiseTypeServer<REQUEST, RESPONSE> buildPromiseTypeServer() throws ConfigurationException{
		
		@SuppressWarnings("unchecked")
		PromiseTypeServer<REQUEST, RESPONSE> integrationServer = 
			(PromiseTypeServer<REQUEST, RESPONSE>) new DeferredPromiseServer();
		
		return integrationServer;
	}
	
	@Override
	public void configure(OperationConfig<REQUEST, RESPONSE> clientConfig) throws ConfigurationException{
				
		if( clientConfig!= null ){
			
			adapterServiceConfiguration = ServiceFramework.getInstance().getConfigurationService().getServiceConfiguration(
					ConfigurationType.CLIENT);
			
			if(adapterServiceConfiguration != null){
				this.clientConfig 	= clientConfig;
				this.isConfigured	= true;
			}
		}
		
	}
	
	@Override
	public REQUEST getRequest() {

		return clientConfig.getREQ();
	}

	@Override
	public void run() {
		
		RESPONSE response = null;
		
		try {
		
			if(isConfigured){
				
				startupAdapter(Optional.of(adapterServiceConfiguration.getConfigAsProperties()));
			
				PromiseRequest<REQUEST> promiseRequest 		= buildPromiseRequest();
				
				PromiseTypeServer<REQUEST, RESPONSE> integrationServer 	= buildPromiseTypeServer();
				
				ServiceProvider<REQUEST, RESPONSE> processProvider;
				
					processProvider = builder
							.withProvider(clientConfig.getREQ(), clientConfig.getRES())
							.withServerConfigurationAs(clientConfig.getServerSubscriptionList(), clientConfig.getServerNotificationTask(), clientConfig.getServerNotificationServiceAdapter())
							.andClientConfigurationAs(clientConfig.getClientSubscriptionList(), clientConfig.getClientNotificationTask(), clientConfig.getClientNotificationServiceAdapter())
							.forPromiseRequestAndResponseServerAs(promiseRequest, clientConfig.getREQ(),integrationServer)
							.build();
				
				
				PromiseResponse<RESPONSE> promiseResponse = processProvider.service(promiseRequest);
				
				response = promiseResponse.getResponse();
				
				System.out.println("PromiseClient : Response = "+response);	
			
				
				shutdownAdapter(Optional.of(adapterServiceConfiguration.getConfigAsProperties()));
		
			}else{
				throw new ConfigurationException(
						ERR_CONFIG,
						MSG_CONFIG, 
						"Client not configured for execution.");
				
			}
			
		} catch (ConfigurationException | PromiseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public ServiceStatus startupAdapter(Optional<Properties> configProperties) throws ConfigurationException{
		
		return clientConfig.getClientNotificationServiceAdapter().startup(configProperties);
	}
	
	@Override
	public ServiceStatus shutdownAdapter(Optional<Properties> configProperties) throws ConfigurationException{
		
		return clientConfig.getClientNotificationServiceAdapter().shutdown(configProperties);
	}


}
