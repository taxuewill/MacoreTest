package com.will.client.router;

import com.linked.annotion.Provider;
import com.will.common.config.ClientConfig;

import macore.MaProvider;

/**
 * Created by will on 18-5-7.
 */
@Provider(processName = ClientConfig.processName)
public class ClientProvider extends MaProvider {
    @Override
    protected String getName() {
        return ClientConfig.providerName;
    }
}
