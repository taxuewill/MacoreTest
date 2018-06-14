package com.will.multiprocess.macoretest;

import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.will.common.config.ClientConfig;
import com.will.common.config.MainConfig;
import com.will.multiprocess.macoretest.service.MainApplicationLogic;
import com.will.multiprocess.macoretest.service.MainService;

import macore.MaApplication;
import macore.router.WideRouter;

/**
 * Created by will on 18-5-7.
 */

public class MainApplication extends MaApplication {

    private static final String TAG = "MainApplication";

    @Override
    public void initializeAllProcessRouter() {
        Log.i(TAG,"initializeAllProcessRouter");
        WideRouter.registerLocalRouter(MainConfig.processName, MainService.class);
        WideRouter.registerRemoteRouter(ClientConfig.processName,"com.will.client","com.will.client.service.ClientLocalRouterConnectService");
    }

    @Override
    protected void initializeLogic() {
        registerApplicationLogic(MainConfig.processName,999, MainApplicationLogic.class);
        if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化

    }

    @Override
    public boolean needMultipleProcess() {
        return true;
    }

    private boolean isDebug(){
        return true;
    }
}
