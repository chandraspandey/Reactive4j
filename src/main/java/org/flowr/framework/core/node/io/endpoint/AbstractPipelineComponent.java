
/**
 * 
 * @author Chandra Shekhar Pandey
 *
 */

package org.flowr.framework.core.node.io.endpoint;

import org.flowr.framework.core.event.Event.EventType;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineType;
import org.flowr.framework.core.node.NodeManager.NetworkIntegrationConfig;
import org.flowr.framework.core.node.io.flow.IOFlow.IOFlowType;
import org.flowr.framework.core.node.io.flow.protocol.NetworkProcessor;
import org.flowr.framework.core.node.io.network.NodeChannel;
import org.flowr.framework.core.node.io.pipeline.NetworkPipeline;
import org.flowr.framework.core.security.LogicaIdentity;


public abstract class AbstractPipelineComponent extends LogicaIdentity implements NodeChannel{

    private NetworkProcessor serverPublisher;
    private NodeEndPointConfiguration nodeEndPointConfiguration; 
    private NodeConfig nodeConfig;    
    private NetworkPipeline in;    
    private NetworkPipeline out;
    
    public AbstractPipelineComponent(NodeEndPointConfiguration nodeEndPointConfiguration) {
        
        super();
        this.nodeEndPointConfiguration = nodeEndPointConfiguration;
        
        this.in     = new NetworkPipeline();
        this.out    = new NetworkPipeline();
        
        setInboundPipelineAttributes(in, nodeEndPointConfiguration);
        setOutboundPipelineAttributes(out,nodeEndPointConfiguration);
   
        NetworkIntegrationConfig networkIntegrationConfig = new NetworkIntegrationConfig(
                                                                nodeEndPointConfiguration.getPipelineHandler(),
                                                                nodeEndPointConfiguration.getProtocolPublishType(), 
                                                                nodeEndPointConfiguration.getHandleFor(),
                                                                nodeEndPointConfiguration.getHandleAs()
                                                            );

        
        this.nodeConfig = new NodeConfig(
                            in,
                            out,
                            nodeEndPointConfiguration.getChannelName(),
                            nodeEndPointConfiguration.getNetworkGroupType(),
                            true,
                            nodeEndPointConfiguration.getPipelineMetricSubscriber(),
                            networkIntegrationConfig
        );
        
        serverPublisher = new NetworkProcessor(nodeConfig);
    }

    private static void setOutboundPipelineAttributes(NetworkPipeline out, NodeEndPointConfiguration configuration) {
        out.setPipelineName(configuration.getOutboundPipelineName());
        out.setEventType(EventType.NETWORK);
        out.setPipelineFunctionType(PipelineFunctionType.PIPELINE_NETWORK_SERVER);
        out.setIOFlowType(IOFlowType.OUTBOUND);
        out.setPipelineType(PipelineType.BUS);
        out.setHostName(configuration.getOutboundPipelineHostName());
        out.setPortNumber(configuration.getOutboundPipelinePortNumber());
    }

    private static void setInboundPipelineAttributes(NetworkPipeline in,NodeEndPointConfiguration configuration) {
        
        in.setPipelineName(configuration.getInboundPipelineName());
        in.setEventType(EventType.NETWORK);
        in.setPipelineFunctionType(PipelineFunctionType.PIPELINE_NETWORK_SERVER);
        in.setIOFlowType(IOFlowType.INBOUND);
        in.setPipelineType(PipelineType.BUS);
        in.setHostName(configuration.getInboundPipelineHostName());
        in.setPortNumber(configuration.getInboundPipelinePortNumber());
    }

 
    
    @Override
    public boolean equals(Object other){
        
        return super.equals(other);
    }
    
    @Override
    public int hashCode() {
        
        return super.hashCode();
    }
    
    @Override
    public String getChannelName() {
        return this.nodeConfig.getChannelName();
    }

    @Override
    public NodeConfig getNodeConfig() {
        return nodeConfig;
    }

    public NodeEndPointConfiguration getNodeEndPointConfiguration() {
        return nodeEndPointConfiguration;
    }    

    public NetworkPipeline getIn() {
        return in;
    }

    public NetworkPipeline getOut() {
        return out;
    }
    
    public NetworkProcessor getServerPublisher() {
        return serverPublisher;
    }
    
    @Override
    public String toString(){
        
        StringBuilder sb = new StringBuilder();
        sb.append("\n AbstractPipelineServer{");
        sb.append(" \n| serverPublisher : "+serverPublisher);
        sb.append(" \n| in : "+in.getPipelineName());
        sb.append(" \n| out : "+out.getPipelineName());
        sb.append(" \n| "+super.toString());  
        sb.append("\n}\n");
        
        return sb.toString();
    }



}
