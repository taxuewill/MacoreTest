package com.will.multiprocess.macoretest.router.action;

import android.content.Context;
import android.util.Log;

import com.linked.annotion.Action;

import com.will.common.config.MainConfig;

import macore.MaAction;
import macore.router.MaActionResult;
import macore.router.RouterRequest;

/**
 * Created by will on 18-5-7.
 */
@Action(processName = MainConfig.processName, providerName = MainConfig.providerName)
public class RpcAction implements MaAction {

    private static final String TAG = "RpcAction";
    @Override
    public boolean isAsync(Context context, RouterRequest routerRequest) {
        return true;
    }

    @Override
    public MaActionResult invoke(Context context, RouterRequest routerRequest) {
        Log.d(TAG, "invoke: " + routerRequest.toJsonString());

        MaActionResult result = new MaActionResult.Builder().SuccessResult();
        return result;
    }

    @Override
    public String getName() {
        return MainConfig.rpcActionName;
    }
}
