
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.node.io.pipeline;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.flowr.framework.core.constants.Constant.ServerConstants;
import org.flowr.framework.core.constants.ErrorMap;
import org.flowr.framework.core.context.Context.PersistenceContext;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineFunctionType;
import org.flowr.framework.core.event.pipeline.Pipeline.PipelineType;
import org.flowr.framework.core.exception.ConfigurationException;
import org.flowr.framework.core.model.PersistenceProvider.PeristenceType;
import org.flowr.framework.core.model.Tuple;
import org.flowr.framework.core.node.io.flow.handler.IntegrationPipelineHandler;
import org.flowr.framework.core.node.io.flow.handler.IntegrationPipelineHandler.HandlerType;
import org.flowr.framework.core.node.io.flow.handler.IntegrationPipelineHandlerContext;
import org.flowr.framework.core.node.io.network.NetworkGroup;
import org.flowr.framework.core.node.io.network.NetworkGroup.NetworkGroupStatus;
import org.flowr.framework.core.service.ServiceFramework;

public class NetworkPipelineBus implements NetworkBus {

    private static HashMap<NetworkGroup, 
        SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler>> networkMap     = new HashMap<>();
    private RegistryType registryType                           = RegistryType.LOCAL;
    private PipelineType pipelineType                           = PipelineType.BUS; 
    private String networkBusName                               = ServerConstants.SERVER_NETWORK_BUS_NAME;
    
    @Override
    public Map<String,IntegrationPipelineHandler> asHandlerMap(){
        
        Map<String,IntegrationPipelineHandler> handlerMap = new HashMap<>();
        
        networkMap.values().forEach(
                
            (SimpleEntry<IntegrationPipelineHandler, IntegrationPipelineHandler> h) -> {
                
                handlerMap.put(h.getKey().getHandlerName(), h.getKey());
                handlerMap.put(h.getValue().getHandlerName(), h.getValue());                
            }
        );  
        
        return handlerMap;
    }
    
    @Override
    public NetworkGroupStatus addNetworkGroup(NetworkGroup networkGroup, 
        SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler> handlerSet) {
        
        NetworkGroupStatus networkGroupStatus = null;
        
        if(networkMap.containsKey(networkGroup)) {
        
            networkGroupStatus = NetworkGroupStatus.IGNORED;
        }else {
            bind(networkGroup, handlerSet);
            networkGroupStatus = NetworkGroupStatus.ASSOCIATED;
        }
        
        return networkGroupStatus;
    }
    

    @Override
    public NetworkGroupStatus removeNetworkGroup(NetworkGroup networkGroup, 
        SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler> handlerSet) {       
        
        NetworkGroupStatus networkGroupStatus = null;
        
        if(networkMap.containsKey(networkGroup)) {
        
            unbind(networkGroup, handlerSet);
            networkGroupStatus = NetworkGroupStatus.DISASSOCIATED;
        }else {         

            networkGroupStatus = NetworkGroupStatus.INVALID;
        }
        
        return networkGroupStatus;
    }

    @Override
    public List<NetworkPipeline> lookup(PipelineType pipelineType){
        
        List<NetworkPipeline> networkPipelineList = new ArrayList<>();
        
        networkMap.keySet().forEach(
                
            (NetworkGroup g) -> {
                
                if(g.getIn().getPipelineType() == pipelineType) {
                    networkPipelineList.add(g.getIn());
                }
                
                if(g.getOut().getPipelineType() == pipelineType) {
                    networkPipelineList.add(g.getOut());
                }
            }
        );      
        
        return networkPipelineList;
    }
    
    @Override
    public void execute(){
        
        networkMap.entrySet().forEach(
              
                
                
            (Entry<NetworkGroup, SimpleEntry<IntegrationPipelineHandler, IntegrationPipelineHandler>> entry) -> {
                
                IntegrationPipelineHandlerContext handlerContext = new IntegrationPipelineHandlerContext(
                        HandlerType.IO,entry.getKey().getIn().asNetworkBufferQueue());
                
                entry.getValue().getKey().handle(handlerContext);
            }
        );
        
    }
    
    @Override
    public List<NetworkPipeline> lookup(PipelineFunctionType pipelineFunctionType){
        
        List<NetworkPipeline> networkPipelineList = new ArrayList<>();
        
        networkMap.keySet().forEach(
                
            (NetworkGroup g) -> {
                
                if(g.getIn().getPipelineFunctionType() == pipelineFunctionType) {
                    networkPipelineList.add(g.getIn());
                }
                
                if(g.getOut().getPipelineFunctionType() == pipelineFunctionType) {
                    networkPipelineList.add(g.getOut());
                }
            }
        );
        
        return networkPipelineList;
    }
    
    @Override
    public NetworkPipeline lookup(String pipelineName,PipelineType pipelineType, PipelineFunctionType 
        pipelineFunctionType){
        
        NetworkPipeline networkPipeline =null;
                                
        Iterator<NetworkGroup> iter = networkMap.keySet().iterator();
        
        while(iter.hasNext()) {
            
            NetworkGroup g = iter.next();
            
            Optional<NetworkPipeline> inPipelineOption = isInPipeline(g,pipelineName,
                    pipelineType,pipelineFunctionType);
            
            Optional<NetworkPipeline> outPipelineOption = isOutPipeline(g,pipelineName,
                    pipelineType,pipelineFunctionType);
            
            if(inPipelineOption.isPresent() ) {
                networkPipeline = inPipelineOption.get();
            }
            
            if(outPipelineOption.isPresent()){
                
                networkPipeline = outPipelineOption.get();
            }   
            
            if(networkPipeline != null ) {

                break;
            }
        }       
        Logger.getRootLogger().info("NetworkPipelineBus : networkPipeline : "+networkPipeline);
        
        return networkPipeline;
    }
    
    private static Optional<NetworkPipeline>  isInPipeline(NetworkGroup g,String pipelineName,
            PipelineType pipelineType, PipelineFunctionType pipelineFunctionType){
        
        Optional<NetworkPipeline> networkPipelineOption = Optional.empty();
        
        if(
                g.getIn().getPipelineName().equals(pipelineName) &&
                g.getIn().getPipelineType() == pipelineType &&
                g.getIn().getPipelineFunctionType() == pipelineFunctionType
        ) {
            networkPipelineOption = Optional.of(g.getIn());
        }
        
        return networkPipelineOption;
    }
    
    private static Optional<NetworkPipeline>  isOutPipeline(NetworkGroup g,String pipelineName,
            PipelineType pipelineType, PipelineFunctionType pipelineFunctionType){
            
            Optional<NetworkPipeline> networkPipelineOption = Optional.empty();
            
            if(
                    g.getOut().getPipelineName().equals(pipelineName) &&
                    g.getOut().getPipelineType() == pipelineType &&
                    g.getOut().getPipelineFunctionType() == pipelineFunctionType
            ) {
                networkPipelineOption = Optional.of(g.getOut());
            }
            
            return networkPipelineOption;
        }

    @Override
    public PipelineType getEventBusType() {

        return this.pipelineType;
    }


    @Override
    public String getNetworkBusName() {
        return this.networkBusName;
    }


    @Override
    public List<NetworkPipeline> getAllPipelines() {
        
        List<NetworkPipeline> networkPipelineList   = new ArrayList<>();
        
        networkMap.keySet().forEach(
                
            (NetworkGroup g) -> {
                
                    networkPipelineList.add(g.getIn());
                    networkPipelineList.add(g.getOut());
            }
        );
        
        return networkPipelineList;
    }
    
    @Override
    public void bind(NetworkGroup networkGroup, 
            SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler> handlerSet) {
        networkMap.put(networkGroup,handlerSet);
    }

    @Override
    public void unbind(NetworkGroup networkGroup, 
            SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler> handlerSet) {
        networkMap.remove(networkGroup);
    }

    @Override
    public void rebind(NetworkGroup networkGroup, 
            SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler> handlerSet) {
        unbind(networkGroup, handlerSet);
        bind(networkGroup, handlerSet);
    }

    @Override
    public Collection<SimpleEntry<IntegrationPipelineHandler, IntegrationPipelineHandler>> list() {
        return  networkMap.values();
    }

    @Override
    public SimpleEntry<IntegrationPipelineHandler, IntegrationPipelineHandler> lookup(NetworkGroup networkGroup) {
                
        return networkMap.get(networkGroup);
    }

    @Override
    public void persist() throws ConfigurationException{
        
        PersistenceContext<String,HashMap<NetworkGroup, 
            SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler>>> context 
            = new PersistenceContext<>(
                    this.getClass().getCanonicalName(),
                PeristenceType.TUPLE
            );
        
        Tuple<String,HashMap<NetworkGroup, SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler>>> tuple = 
                new Tuple<>(this.getClass().getCanonicalName(),networkMap);
        
        Optional<Tuple<String,HashMap<NetworkGroup, 
            SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler>>>> tupleOption = Optional.of(tuple);
        context.setTupleOption(tupleOption);
        
        ServiceFramework.getInstance().getCatalog().getNodeService().getPersistenceHandler().persist(context);
    }
    
    @Override
    public void restore() throws ConfigurationException{

        PersistenceContext<String,HashMap<NetworkGroup, 
            SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler>>> context = 
            ServiceFramework.getInstance().getCatalog().getNodeService().getPersistenceHandler().restore(
                    this.getClass().getCanonicalName());
        
        Optional<Tuple<String,HashMap<NetworkGroup, 
            SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler>>>> tupleOption = 
            context.getTupleOption();
        
        if( tupleOption.isPresent() && tupleOption.get().getKey().equals(this.getClass().getCanonicalName())) {
                        
            setHandlerMap(tupleOption.get().getValues());
        }else {
            
            throw new ConfigurationException(
                    ErrorMap.ERR_CONFIG,
                    "Restore Operation failed for Persistence configuration in : "+this.getClass().getCanonicalName());
        }

    }
    
    private static void setHandlerMap(HashMap<NetworkGroup, 
            SimpleEntry<IntegrationPipelineHandler,IntegrationPipelineHandler>> map) {
        networkMap = map;
    }

    @Override
    public void clear() {
        networkMap.clear();
    }



    @Override
    public RegistryType getRegistryType() {
        return this.registryType;
    }

    @Override
    public void setRegistryType(RegistryType registryType) {
        this.registryType = registryType;
    }

    public String toString() {
        
        return "NetworkPipelineBus { "+networkBusName+" | "+pipelineType+" | "+registryType+" | "+networkMap+" } ";
    }


}
