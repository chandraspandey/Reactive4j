
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package org.flowr.framework.core.service;

import java.util.ArrayList;
import java.util.List;

import org.flowr.framework.core.service.Catalog.AbstractFrameworkCatalog;
import org.flowr.framework.core.service.extension.DefferedPromiseService;
import org.flowr.framework.core.service.extension.MapPromiseService;
import org.flowr.framework.core.service.extension.PhasedPromiseService;
import org.flowr.framework.core.service.extension.PromiseService;
import org.flowr.framework.core.service.extension.ScheduledPromiseService;
import org.flowr.framework.core.service.extension.StagePromiseService;
import org.flowr.framework.core.service.extension.StreamPromiseService;

public final class ServiceCatalog extends AbstractFrameworkCatalog{

    private PromiseService promiseService                   = PromiseService.getInstance();
    private DefferedPromiseService defferedPromiseService   = DefferedPromiseService.getInstance();
    private PhasedPromiseService phasedPromiseService       = PhasedPromiseService.getInstance();
    private ScheduledPromiseService scheduledPromiseService = ScheduledPromiseService.getInstance();
    private StagePromiseService stagePromiseService         = StagePromiseService.getInstance();
    private StreamPromiseService streamPromiseService       = StreamPromiseService.getInstance();
    private MapPromiseService mapPromiseService             = MapPromiseService.getInstance();
    
    private List<ServiceFrameworkComponent> serviceList     = new ArrayList<>();  
    
    public ServiceCatalog() {
        
        super();
        this.serviceList.add(promiseService);
        this.serviceList.add(defferedPromiseService);
        this.serviceList.add(phasedPromiseService);
        this.serviceList.add(scheduledPromiseService);
        this.serviceList.add(stagePromiseService);
        this.serviceList.add(streamPromiseService);
        this.serviceList.add(mapPromiseService);
    }
    
   @Override
   public ServiceCatalog postProcess(ServiceFramework serviceFramework) {
        
       super.postProcess(serviceFramework);
        
        this.serviceList.forEach(
               
            (ServiceFrameworkComponent s) ->  s.setServiceFramework(serviceFramework)
                 
        );
        
        return this;
    }
   
   @Override
   public PromiseService getPromiseService() {
       return promiseService;
   }
   
   @Override
   public DefferedPromiseService getDefferedPromiseService() {
       return defferedPromiseService;
   }

   @Override
   public PhasedPromiseService getPhasedPromiseService() {
       return phasedPromiseService;
   }

   @Override
   public ScheduledPromiseService getScheduledPromiseService() {
       return scheduledPromiseService;
   }

   @Override
   public StagePromiseService getStagedPromiseService() {
       return stagePromiseService;
   }
   
   @Override
   public StreamPromiseService getStreamPromiseService() {
       return streamPromiseService;
   }


   @Override
   public MapPromiseService getMapPromiseService() {
       return mapPromiseService;
   }

 
   @Override
   public List<ServiceFrameworkComponent> getServiceList(){
       
       return this.serviceList;
   }
   
   @Override
   public String toString(){
       
       StringBuilder builder = new StringBuilder();
       
       serviceList.forEach(
              
            ( ServiceFrameworkComponent s) -> builder.append("\n"+s.getServiceConfig().getServiceName())   
       );
       
        return "ServiceCatalog{"+
                "\n serviceList : "+serviceList.size()+builder.toString()+
                "\n "+super.toString()+
                "\n}\n";
    }
}
