package com.will.client;

import android.util.Log;

import com.will.client.service.ClientApplicationLogic;
import com.will.client.service.ClientLocalRouterConnectService;
import com.will.common.config.ClientConfig;
import com.will.common.config.MainConfig;

import macore.MaApplication;
import macore.router.WideRouter;
import macore.router.WideRouterConnectService;

/**
 * Created by will on 18-5-7.
 */

public class ClientApplication extends MaApplication {

    private static final String TAG = "ClientApplication";
    @Override
    public void initializeAllProcessRouter() {
        WideRouter.registerLocalRouter(ClientConfig.processName, ClientLocalRouterConnectService.class);
    }

    @Override
    protected void initializeLogic() {
        Log.i(TAG,"initializeLogic");
        registerApplicationLogic(ClientConfig.processName,999,ClientApplicationLogic.class);
    }

    @Override
    public boolean needMultipleProcess() {
        configWideRouter(MainConfig.processName, WideRouterConnectService.class.getName());
        return false;
    }
}
