
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.node.io.flow.protocol;

import java.util.Optional;
import java.util.concurrent.Flow.Processor;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

import org.flowr.framework.core.node.io.endpoint.NodeConfig;
import org.flowr.framework.core.node.io.flow.metric.ChannelMetric;
import org.flowr.framework.core.node.io.flow.metric.MetricContext;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric.MetricType;
import org.flowr.framework.core.node.io.flow.metric.NetworkMetric.ProtocolPublishType;

public class NetworkProcessor implements Processor<ChannelMetric,ProtocolPublishType>{

    private NodeConfig nodeConfig;
    private NetworkMetric pipelineMetricSubscriber;
    private Subscription subscription;
    
    public NetworkProcessor(NodeConfig nodeConfig) {
            
        this.nodeConfig                 = nodeConfig;
        this.pipelineMetricSubscriber   = nodeConfig.getMetricHandler();    
        
    }
    
    
    @Override
    public void onComplete() {
                                
        pipelineMetricSubscriber.handleMetricType(
                new MetricContext(
                    MetricType.COMPLETION,
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    this.nodeConfig.getProtocolPublishType(),
                    this.nodeConfig.getHandleFor(),
                    this.nodeConfig.getHandleAs()
                ));

    }

    @Override
    public void onError(Throwable throwable) {
        
        pipelineMetricSubscriber.handleMetricType(
                new MetricContext(
                    MetricType.TRANSFER,                        
                    Optional.empty(),
                    Optional.of(throwable),
                    Optional.empty(),
                    this.nodeConfig.getProtocolPublishType(),
                    this.nodeConfig.getHandleFor(),
                    this.nodeConfig.getHandleAs()
        ));
    }

    @Override
    public void onNext(ChannelMetric channelMetric) {

        pipelineMetricSubscriber.handleMetricType(
                new MetricContext(
                    MetricType.TRANSFER,
                    Optional.of(channelMetric),
                    Optional.empty(),
                    Optional.empty(),
                    this.nodeConfig.getProtocolPublishType(),
                    this.nodeConfig.getHandleFor(),
                    this.nodeConfig.getHandleAs()
        ));
        
    }

    @Override
    public void onSubscribe(Subscription subscription) {

        this.subscription = subscription;
        pipelineMetricSubscriber.handleMetricType(
                new MetricContext(
                    MetricType.SUBSCRIPTION,
                    Optional.empty(),
                    Optional.empty(),
                    Optional.of(subscription),
                    this.nodeConfig.getProtocolPublishType(),
                    this.nodeConfig.getHandleFor(),
                    this.nodeConfig.getHandleAs()
        ));
        
    }

    @Override
    public void subscribe(Subscriber<? super ProtocolPublishType> subscriber) {

        this.pipelineMetricSubscriber.setNetworkMetricSubscriber((Subscriber<ProtocolPublishType>) subscriber); 
        
    }

    
    public void onSeek(ChannelMetric channelMetric) {
        
        pipelineMetricSubscriber.handleMetricType(
                new MetricContext(
                    MetricType.SEEK,
                    Optional.of(channelMetric),
                    Optional.empty(),
                    Optional.empty(),
                    this.nodeConfig.getProtocolPublishType(),
                    this.nodeConfig.getHandleFor(),
                    this.nodeConfig.getHandleAs()
        ));
    }
    
    public void onCancel(ChannelMetric channelMetric) {
        
        pipelineMetricSubscriber.handleMetricType(
                new MetricContext(
                    MetricType.CANCEL,
                    Optional.of(channelMetric),
                    Optional.empty(),
                    Optional.empty(),
                    this.nodeConfig.getProtocolPublishType(),
                    this.nodeConfig.getHandleFor(),
                    this.nodeConfig.getHandleAs()
        ));
    }
    
    public Subscription getSubscription() {
        
        return this.subscription;
    }
}
