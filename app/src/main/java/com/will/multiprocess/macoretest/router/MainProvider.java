package com.will.multiprocess.macoretest.router;

import com.linked.annotion.Provider;
import com.will.common.config.MainConfig;

import macore.MaProvider;

/**
 * Created by will on 18-5-7.
 */
@Provider(processName = MainConfig.processName)
public class MainProvider extends MaProvider {
    @Override
    protected String getName() {
        return MainConfig.providerName;
    }
}
