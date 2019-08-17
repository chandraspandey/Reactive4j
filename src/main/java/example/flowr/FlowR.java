package example.flowr;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.flowr.framework.core.config.Configuration.ConfigurationType;
import org.flowr.framework.core.config.NodeServiceConfiguration;
import org.flowr.framework.core.config.OperationConfig;
import org.flowr.framework.core.constants.FrameworkConstants;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.exception.PromiseException;
import org.flowr.framework.core.node.NodePipelineManager;
import org.flowr.framework.core.node.io.endpoint.NodePipelineClient;
import org.flowr.framework.core.node.io.endpoint.NodePipelineServer;
import org.flowr.framework.core.node.io.flow.IOFlow.IOFlowType;
import org.flowr.framework.core.node.io.flow.handler.IntegrationBridge;
import org.flowr.framework.core.node.io.flow.handler.IntegrationPipelineHandler;
import org.flowr.framework.core.node.io.flow.handler.IntegrationPipelineHandler.HandlerType;
import org.flowr.framework.core.node.io.flow.handler.IntegrationPipelineHandlerContext;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessRequest;
import org.flowr.framework.core.node.io.flow.handler.IntegrationProcessResponse;
import org.flowr.framework.core.node.io.network.NetworkGroup.NetworkGroupType;
import org.flowr.framework.core.node.io.network.ServiceMesh.ServiceTopologyMode;
import org.flowr.framework.core.notification.Notification.ClientNotificationProtocolType;
import org.flowr.framework.core.notification.Notification.NotificationDeliveryType;
import org.flowr.framework.core.notification.Notification.NotificationFormatType;
import org.flowr.framework.core.notification.Notification.NotificationFrequency;
import org.flowr.framework.core.notification.Notification.NotificationProtocolType;
import org.flowr.framework.core.notification.Notification.NotificationType;
import org.flowr.framework.core.notification.Notification.ServerNotificationProtocolType;
import org.flowr.framework.core.notification.NotificationServiceAdapter.NotificationServiceAdapterType;
import org.flowr.framework.core.notification.NotificationTask;
import org.flowr.framework.core.notification.subscription.EventNotificationSubscription;
import org.flowr.framework.core.notification.subscription.NotificationSubscription;
import org.flowr.framework.core.notification.subscription.NotificationSubscription.SubscriptionOption;
import org.flowr.framework.core.notification.subscription.NotificationSubscription.SubscriptionType;
import org.flowr.framework.core.service.Service.ServiceStatus;
import org.flowr.framework.core.service.ServiceFramework;
import org.flowr.framework.core.service.internal.ConfigurationService;

import example.flowr.mock.Mock.PHASED_PROMISE_MOCK_REQUEST;
import example.flowr.mock.Mock.PHASED_PROMISE_MOCK_RESPONSE;
import example.flowr.mock.Mock.PROMISE_MOCK_REQUEST;
import example.flowr.mock.Mock.PROMISE_MOCK_RESPONSE;
import example.flowr.mock.Mock.SCHEDULED_PROMISE_MOCK_REQUEST;
import example.flowr.mock.Mock.SCHEDULED_PROMISE_MOCK_RESPONSE;
import example.flowr.promise.client.PhasedPromiseClient;
import example.flowr.promise.client.PromiseClient;
import example.flowr.promise.client.ScheduledPromiseClient;
import example.flowr.promise.client.notification.ClientNotificationAdapter;
import example.flowr.promise.client.notification.ClientNotificationAdapterTask;
import example.flowr.promise.client.notification.DefferedPromiseClient;
import example.flowr.promise.server.notification.ServerNotificationAdapter;
import example.flowr.promise.server.notification.ServerNotificationAdapterTask;

public class FlowR extends NodePipelineManager{

	private Map<NodePipelineServer,List<NodePipelineClient>> serverClientMap = 
			new HashMap<NodePipelineServer,List<NodePipelineClient>>();
	private List<SimpleEntry<Thread, Thread>> networkThreadPool = new ArrayList<SimpleEntry<Thread, Thread>>();

	
	@SuppressWarnings("unused")
	private ServiceStatus startStatus;
	private ClientNotificationAdapter clientNotificationServiceAdapter = new ClientNotificationAdapter();
	
	private ServerNotificationAdapter serverNotificationServiceAdapter = new ServerNotificationAdapter();
	
	private NotificationTask clientNotificationTask = new ClientNotificationAdapterTask();
	private ServerNotificationAdapterTask serverNotificationTask = new ServerNotificationAdapterTask();
	private ExecutorService executorService = Executors.newCachedThreadPool();
	private HashMap<NotificationProtocolType,NotificationSubscription> clientSubscriptionList(){

		HashMap<NotificationProtocolType,NotificationSubscription> notificationSubscriptionMap = 
				new HashMap<NotificationProtocolType,NotificationSubscription>();
		
		NotificationSubscription notificationSubscription = new EventNotificationSubscription();
		
		notificationSubscription.setSubscriptionType(SubscriptionType.CLIENT);
		notificationSubscription.setNotificationFormatType(NotificationFormatType.TEXT);
		
		notificationSubscription.setNotificationFrequency(NotificationFrequency.ALL);

		notificationSubscription.setNotificationProtocolType(ClientNotificationProtocolType.CLIENT_EMAIL);
		
		notificationSubscription.setNotificationType(NotificationType.AMQP);
		notificationSubscription.setNotificationDeliveryType(NotificationDeliveryType.EXTERNAL);
		notificationSubscription.setSubscriptionOption(SubscriptionOption.AUTOMATIC);
		
		notificationSubscriptionMap.put(ClientNotificationProtocolType.CLIENT_EMAIL, notificationSubscription);
		
		return notificationSubscriptionMap;	
	}
	
	private HashMap<NotificationProtocolType,NotificationSubscription> serverSubscriptionList(){

		HashMap<NotificationProtocolType,NotificationSubscription> notificationSubscriptionMap = 
				new HashMap<NotificationProtocolType,NotificationSubscription>();
		
		NotificationSubscription notificationSubscription = new EventNotificationSubscription();
		
		notificationSubscription.setSubscriptionType(SubscriptionType.SERVER);
		notificationSubscription.setNotificationFormatType(NotificationFormatType.TEXT);
		
		notificationSubscription.setNotificationFrequency(NotificationFrequency.ALL);
		
		notificationSubscription.setNotificationProtocolType(ServerNotificationProtocolType.SERVER_ALL);
		
		notificationSubscription.setNotificationType(NotificationType.AMQP);
		notificationSubscription.setNotificationDeliveryType(NotificationDeliveryType.INTERNAL);
		notificationSubscription.setSubscriptionOption(SubscriptionOption.AUTOMATIC);
		
		notificationSubscriptionMap.put(ServerNotificationProtocolType.SERVER_ALL, notificationSubscription);
				
		return notificationSubscriptionMap;
		
	}
	

	public ServiceStatus startup() throws ConfigurationException{
		
		//  -D<property-name>=<value> i.e. -DFRAMEWORK_CONFIG_PATH=<Install_HOME>/../../framework.properties
		//System.out.println(FrameworkConstants.FRAMEWORK_CONFIG_PATH+ " : "+ System.getProperty(FrameworkConstants.FRAMEWORK_CONFIG_PATH));

		String configurationFilePath = System.getProperty(FrameworkConstants.FRAMEWORK_CONFIG_PATH);
		System.out.println(FrameworkConstants.FRAMEWORK_CONFIG_PATH+ " : "+ configurationFilePath);
		
		ServiceFramework.getInstance().configure(configurationFilePath);
		
		ConfigurationService configurationService = ServiceFramework.getInstance().getConfigurationService();
		
		System.out.println("FlowR : configurationService : "+configurationService);		
				
		List<NodeServiceConfiguration> nodeInboundConfiguration = configurationService.getIntegrationServiceConfiguration(ConfigurationType.INTEGRATION_INBOUND);
		
		List<NodeServiceConfiguration> nodeOutboundConfiguration = configurationService.getIntegrationServiceConfiguration(ConfigurationType.INTEGRATION_OUTBOUND);
		
		System.out.println(nodeInboundConfiguration);
		
		System.out.println(nodeOutboundConfiguration);
	
		
		ServiceStatus serviceStatus = ServiceFramework.getInstance().startup(
				Optional.of(configurationService.getServiceConfiguration(ConfigurationType.SERVER).getConfigAsProperties()));
		
		initNetworkBusExecutor(Optional.empty());
		//System.exit(0);
		
		
		
		return serviceStatus;
	}
	@Override	
	public void configure() throws IOException, ConfigurationException {
			
		List<NodeServiceConfiguration> nodeInboundConfiguration = 
				ServiceFramework.getInstance().getConfigurationService().getIntegrationServiceConfiguration(
						ConfigurationType.INTEGRATION_INBOUND);
		
		List<NodeServiceConfiguration> nodeOutboundConfiguration = 
				ServiceFramework.getInstance().getConfigurationService().getIntegrationServiceConfiguration(
						ConfigurationType.INTEGRATION_OUTBOUND);
		
		
		NodeServiceConfiguration inboundConfiguration = nodeInboundConfiguration.get(0);		
		NodeServiceConfiguration outboundConfiguration = nodeOutboundConfiguration.get(0);
		
		
		
		IntegrationBridge inboundServerPipelineHandlerBridge  = new IntegrationBridge() {
			@Override
			public void handle(IntegrationPipelineHandlerContext handlerContext) {
				
				System.out.println("FlowR : "+handlerContext);				
			}			
		};
		
		IntegrationBridge outboundServerPipelineHandlerBridge  = new IntegrationBridge() {
			@Override
			public void handle(IntegrationPipelineHandlerContext handlerContext) {
				
				System.out.println("FlowR : "+handlerContext);				
			}			
		};
		
		IntegrationPipelineHandler inboundServerPipelineHandler =  
				new IntegrationPipelineHandler.DefaultIntegrationPipelineHandler(
						inboundConfiguration.getConfigName()+"-"+ConfigurationType.SERVER.name(), 
						HandlerType.IO,
						IOFlowType.INBOUND, 
						inboundServerPipelineHandlerBridge);
				
				
		IntegrationPipelineHandler outboundServerPipelineHandler = 
				new IntegrationPipelineHandler.DefaultIntegrationPipelineHandler(
						inboundConfiguration.getConfigName()+"-"+ConfigurationType.CLIENT.name(),
						HandlerType.IO,
						IOFlowType.OUTBOUND, 
						outboundServerPipelineHandlerBridge);	
		
		SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler> serverPipelineHandler = 
				new SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler>
					(inboundServerPipelineHandler,outboundServerPipelineHandler);
		
		ProtocolPublishType inboundProtocolPublishType 			= ProtocolPublishType.INTERNAL;
		Optional<IntegrationProcessRequest> inboundHandleFor  	= Optional.of(
				new IntegrationProcessRequest.DefaultIntegrationProcessRequest());

		inboundHandleFor.get().getProperties().put("id", "server-101");
		inboundHandleFor.get().setResponseMinMaxLimit(Optional.of(new SimpleEntry<Integer,Integer>(1,1)));
		inboundHandleFor.get().addMetricType(MetricType.TRANSFER);
		
		Optional<IntegrationProcessResponse> inboundHandleAs	= Optional.of(
				new IntegrationProcessResponse.DefaultIntegrationProcessResponse());
		
		//System.out.println("NodePipelineManagerTest : inboundHandleFor : "+inboundHandleFor);
		//System.out.println("NodePipelineManagerTest : inboundHandleAs : "+inboundHandleAs);
		
		NodePipelineServer nodeServer = createInboundNetworkPipeline(
											inboundConfiguration,
											outboundConfiguration, 
											NetworkGroupType.LOCAL,
											this,
											true,
											serverPipelineHandler,
											inboundProtocolPublishType,
											inboundHandleFor,
											inboundHandleAs);
		
		
		IntegrationBridge inboundClientPipelineHandlerBridge  = new IntegrationBridge() {
			@Override
			public void handle(IntegrationPipelineHandlerContext handlerContext) {
				
				System.out.println("FlowR : "+handlerContext);				
			}			
		};
		
		IntegrationBridge outboundClientPipelineHandlerBridge  = new IntegrationBridge() {
			@Override
			public void handle(IntegrationPipelineHandlerContext handlerContext) {
				
				System.out.println("FlowR : "+handlerContext);				
			}			
		};
		
		
		IntegrationPipelineHandler inboundClientPipelineHandler = 
				new IntegrationPipelineHandler.DefaultIntegrationPipelineHandler(
					outboundConfiguration.getConfigName()+"-"+ConfigurationType.SERVER.name(),
					HandlerType.IO,
					IOFlowType.OUTBOUND, 
					inboundClientPipelineHandlerBridge);					
				
		IntegrationPipelineHandler outboundClientPipelineHandler = 
				new IntegrationPipelineHandler.DefaultIntegrationPipelineHandler(
					outboundConfiguration.getConfigName()+"-"+ConfigurationType.CLIENT.name(),
					HandlerType.IO,
					IOFlowType.OUTBOUND, 
					outboundClientPipelineHandlerBridge);	
		
		
		SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler> clientPipelineHandler = 
				new SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler>
					(inboundClientPipelineHandler,outboundClientPipelineHandler);
		
		ProtocolPublishType outboundProtocolPublishType 		= ProtocolPublishType.INTERNAL;
		Optional<IntegrationProcessRequest> outboundHandleFor  	= Optional.of(
				new IntegrationProcessRequest.DefaultIntegrationProcessRequest());
		
		outboundHandleFor.get().getProperties().put("id", "client-101");
		outboundHandleFor.get().setResponseMinMaxLimit(Optional.of(new SimpleEntry<Integer,Integer>(1,1)));
		outboundHandleFor.get().addMetricType(MetricType.TRANSFER);
		
		Optional<IntegrationProcessResponse> outboundHandleAs	= Optional.of(
				new IntegrationProcessResponse.DefaultIntegrationProcessResponse());
		
		//System.out.println("NodePipelineManagerTest : outboundHandleFor : "+outboundHandleFor);
		//System.out.println("NodePipelineManagerTest : outboundHandleAs : "+outboundHandleAs);
		
		List<NodePipelineClient> nodePipelineClientList = createOutboundNetworkPipeline(
															inboundConfiguration,
															outboundConfiguration,	
															NetworkGroupType.LOCAL,
															this,
															true,
															clientPipelineHandler,
															outboundProtocolPublishType,
															outboundHandleFor,
															outboundHandleAs);
		
		serverClientMap.put(nodeServer, nodePipelineClientList);
		
		SimpleEntry<Thread, Thread> serverThreadHooks = startInboundNetworkPipeline(
															nodeServer, nodePipelineClientList.get(0));
		
		clientToServer("ECHO".getBytes(),nodePipelineClientList.get(0),nodeServer);
		
		networkThreadPool.add(serverThreadHooks);
		
		SimpleEntry<Thread, Thread> clientThreadHooks = startOutboundNetworkPipeline(
															nodePipelineClientList.get(0),nodeServer );
		
		serverToClient("ECHO".getBytes(),nodeServer,nodePipelineClientList.get(0));
		
		networkThreadPool.add(clientThreadHooks);
		
		System.out.println("FlowR : handlerMap : "+super.getNetworkBus().asHandlerMap());

	}


	public ServiceStatus shutdown() throws ConfigurationException{
		
		ConfigurationService configurationService = ServiceFramework.getInstance().getConfigurationService();
		
		System.out.println("configurationService : "+configurationService);
		
		serverClientMap.entrySet().forEach(
				
				(entry) -> {
					
					entry.getKey().close();
					entry.getValue().forEach(
						
						(client) -> {
							client.close();
						}
					);
				}
		);
		
		// See if NodeThread with KeepRunning would be better alternative
		networkThreadPool.forEach(
			
			(t) -> {
				try {
					t.getKey().join();
					t.getValue().join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		);
		
		this.closeNetworkBusExecutor(Optional.empty());
		
		ServiceStatus serviceStatus = ServiceFramework.getInstance().shutdown(
				Optional.of(configurationService.getServiceConfiguration(ConfigurationType.SERVER).getConfigAsProperties()));

		executorService.shutdown();
		
		return serviceStatus;
	}
	
	public Runnable promiseClient() throws ConfigurationException{
		
		PROMISE_MOCK_REQUEST REQ = new PROMISE_MOCK_REQUEST();
		
		PROMISE_MOCK_RESPONSE RES = new PROMISE_MOCK_RESPONSE();
		
		PromiseClient<PROMISE_MOCK_REQUEST, PROMISE_MOCK_RESPONSE> promiseClient = new PromiseClient<PROMISE_MOCK_REQUEST, PROMISE_MOCK_RESPONSE>();	
		
		clientNotificationServiceAdapter.setNotificationServiceAdapterName("CLIENT_NOTIFICATION_ADAPTER");
		clientNotificationServiceAdapter.setNotificationServiceAdapterType(NotificationServiceAdapterType.CLIENT);
		clientNotificationTask.setServiceMode(ServiceTopologyMode.DISTRIBUTED);
		clientNotificationServiceAdapter.configure(clientNotificationTask);
		
		serverNotificationServiceAdapter.setNotificationServiceAdapterName("SERVER_NOTIFICATION_ADAPTER");
		serverNotificationServiceAdapter.setNotificationServiceAdapterType(NotificationServiceAdapterType.SERVER);
		serverNotificationTask.setServiceMode(ServiceTopologyMode.DISTRIBUTED);
		serverNotificationServiceAdapter.configure(serverNotificationTask);
	
		OperationConfig<PROMISE_MOCK_REQUEST, PROMISE_MOCK_RESPONSE> operationConfig = new OperationConfig<PROMISE_MOCK_REQUEST, PROMISE_MOCK_RESPONSE>(
				clientSubscriptionList(), 
				clientNotificationServiceAdapter, 				
				clientNotificationTask, 
				serverSubscriptionList(), 
				serverNotificationServiceAdapter, 
				serverNotificationTask, 
				REQ, 
				RES
		);
		
		promiseClient.configure(operationConfig);
		return promiseClient;
	}
	
	public Runnable defferedPromiseClient() throws ConfigurationException{
		
		PROMISE_MOCK_REQUEST REQ = new PROMISE_MOCK_REQUEST();
		
		PROMISE_MOCK_RESPONSE RES = new PROMISE_MOCK_RESPONSE();
		
		DefferedPromiseClient<PROMISE_MOCK_REQUEST, PROMISE_MOCK_RESPONSE> promiseClient = new DefferedPromiseClient<PROMISE_MOCK_REQUEST, PROMISE_MOCK_RESPONSE>();	
		
		clientNotificationServiceAdapter.setNotificationServiceAdapterName("CLIENT_NOTIFICATION_ADAPTER");
		clientNotificationServiceAdapter.setNotificationServiceAdapterType(NotificationServiceAdapterType.CLIENT);
		clientNotificationTask.setServiceMode(ServiceTopologyMode.DISTRIBUTED);
		clientNotificationServiceAdapter.configure(clientNotificationTask);
		
		serverNotificationServiceAdapter.setNotificationServiceAdapterName("SERVER_NOTIFICATION_ADAPTER");
		serverNotificationServiceAdapter.setNotificationServiceAdapterType(NotificationServiceAdapterType.SERVER);
		serverNotificationTask.setServiceMode(ServiceTopologyMode.DISTRIBUTED);
		serverNotificationServiceAdapter.configure(serverNotificationTask);
	
		OperationConfig<PROMISE_MOCK_REQUEST, PROMISE_MOCK_RESPONSE> operationConfig = new OperationConfig<PROMISE_MOCK_REQUEST, PROMISE_MOCK_RESPONSE>(
				clientSubscriptionList(), 
				clientNotificationServiceAdapter, 				
				clientNotificationTask, 
				serverSubscriptionList(), 
				serverNotificationServiceAdapter, 
				serverNotificationTask, 
				REQ, 
				RES
		);
		
		promiseClient.configure(operationConfig);
		return promiseClient;
	}
	
	public Runnable phasedPromiseClient() throws ConfigurationException{
			
		PHASED_PROMISE_MOCK_REQUEST REQ = new PHASED_PROMISE_MOCK_REQUEST();
		
		PHASED_PROMISE_MOCK_RESPONSE RES = new PHASED_PROMISE_MOCK_RESPONSE();		
		
		PhasedPromiseClient<PHASED_PROMISE_MOCK_REQUEST, PHASED_PROMISE_MOCK_RESPONSE> phasedPromiseClient = 
				new PhasedPromiseClient<PHASED_PROMISE_MOCK_REQUEST, PHASED_PROMISE_MOCK_RESPONSE>();
	
		clientNotificationServiceAdapter.setNotificationServiceAdapterName("PHASED_CLIENT_NOTIFICATION_ADAPTER");
		clientNotificationServiceAdapter.setNotificationServiceAdapterType(NotificationServiceAdapterType.CLIENT);
		clientNotificationTask.setServiceMode(ServiceTopologyMode.DISTRIBUTED);
		clientNotificationServiceAdapter.configure(clientNotificationTask);

		serverNotificationServiceAdapter.setNotificationServiceAdapterName("PHASED_SERVER_NOTIFICATION_ADAPTER");
		serverNotificationServiceAdapter.setNotificationServiceAdapterType(NotificationServiceAdapterType.SERVER);
		serverNotificationTask.setServiceMode(ServiceTopologyMode.DISTRIBUTED);
		serverNotificationServiceAdapter.configure(serverNotificationTask);
		
		OperationConfig<PHASED_PROMISE_MOCK_REQUEST, PHASED_PROMISE_MOCK_RESPONSE> operationConfig = 
				new OperationConfig<PHASED_PROMISE_MOCK_REQUEST, PHASED_PROMISE_MOCK_RESPONSE>(
				clientSubscriptionList(), 
				clientNotificationServiceAdapter, 
				clientNotificationTask, 
				serverSubscriptionList(), 
				serverNotificationServiceAdapter, 
				serverNotificationTask, 
				REQ, 
				RES
		);
		
		phasedPromiseClient.configure(operationConfig);

		return phasedPromiseClient;
	}
	
	public Runnable scheduledPromiseClient() throws ConfigurationException{
		
		SCHEDULED_PROMISE_MOCK_REQUEST REQ = new SCHEDULED_PROMISE_MOCK_REQUEST();
		
		SCHEDULED_PROMISE_MOCK_RESPONSE RES = new SCHEDULED_PROMISE_MOCK_RESPONSE();
		
		ScheduledPromiseClient<SCHEDULED_PROMISE_MOCK_REQUEST, SCHEDULED_PROMISE_MOCK_RESPONSE> scheduledPromiseClient = 
				new ScheduledPromiseClient<SCHEDULED_PROMISE_MOCK_REQUEST, SCHEDULED_PROMISE_MOCK_RESPONSE>();
	
		clientNotificationServiceAdapter.setNotificationServiceAdapterName("SCHEDULED_CLIENT_NOTIFICATION_ADAPTER");
		clientNotificationServiceAdapter.setNotificationServiceAdapterType(NotificationServiceAdapterType.CLIENT);
		clientNotificationTask.setServiceMode(ServiceTopologyMode.DISTRIBUTED);
		clientNotificationServiceAdapter.configure(clientNotificationTask);
		
		serverNotificationServiceAdapter.setNotificationServiceAdapterName("SCHEDULED_SERVER_NOTIFICATION_ADAPTER");
		serverNotificationServiceAdapter.setNotificationServiceAdapterType(NotificationServiceAdapterType.SERVER);
		serverNotificationTask.setServiceMode(ServiceTopologyMode.DISTRIBUTED);
		serverNotificationServiceAdapter.configure(serverNotificationTask);
		
		OperationConfig<SCHEDULED_PROMISE_MOCK_REQUEST, SCHEDULED_PROMISE_MOCK_RESPONSE> operationConfig = 
				new OperationConfig<SCHEDULED_PROMISE_MOCK_REQUEST, SCHEDULED_PROMISE_MOCK_RESPONSE>(
				clientSubscriptionList(), 
				clientNotificationServiceAdapter, 
				clientNotificationTask, 
				serverSubscriptionList(), 
				serverNotificationServiceAdapter, 
				serverNotificationTask, 
				REQ, 
				RES
		);
		
		scheduledPromiseClient.configure(operationConfig);		

		return scheduledPromiseClient;

	}
	
	public void testNodeOperation() throws ConfigurationException, PromiseException, InterruptedException, ExecutionException, IOException{
		
		configure();
	}
	
	public void test() throws ConfigurationException, PromiseException, InterruptedException, ExecutionException{
				
		new Thread(promiseClient()).start();		
		
		//new Thread(defferedPromiseClient()).start();	
		
		//new Thread(scheduledPromiseClient()).start();
		
		//new Thread(phasedPromiseClient()).start();
		
	}	
	
	public static void main(String[] args) throws ConfigurationException, PromiseException, InterruptedException, 
		ExecutionException, IOException{
		
		System.out.println("USAGE : Reguires FRAMEWORK_CONFIG_PATH as key for loading framework.properties in classpath settings for running.");
		System.out.println("USAGE : It can be provided as part of VM of program arguments for execution.");		
		System.out.println("USAGE : -D<property-name>=<value> i.e. -DFRAMEWORK_CONFIG_PATH=<Install_HOME>/../../framework.properties");
		
		FlowR flowr = new FlowR();
		
		flowr.registerMetricMapperHandler();
		
		flowr.startup();
				
		flowr.test();	
		
		//flowr.testNodeOperation();

		flowr.shutdown();
	}
	

}
