
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package example.flowr.node;

import java.util.AbstractMap.SimpleEntry;

import org.flowr.framework.core.model.DataSourceProvider;

public class NodeDataSourceHandler implements DataSourceProvider{

    private SimpleEntry<ProviderType,String> entry = new SimpleEntry<>(
                                                        ProviderType.DATASOURCE,
                                                        "example.flowr.node.NodeDataSourceHandler"
                                                     );

    @Override
    public SimpleEntry<ProviderType, String> getProviderConfiguration() {
        return this.entry;
    }

}
