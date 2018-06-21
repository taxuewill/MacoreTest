package com.will.client.router;

import android.content.Context;
import android.util.Log;

import com.linked.annotion.Action;
import com.will.common.config.ClientConfig;

import java.util.Map;

import macore.MaAction;
import macore.router.MaActionResult;
import macore.router.RouterRequest;

/**
 * Created by will on 18-5-7.
 */
@Action(processName = ClientConfig.processName,providerName = ClientConfig.providerName)
public class RpcAction implements MaAction {

    private static final String TAG = "ClientRpcAction";
    @Override
    public boolean isAsync(Context context, RouterRequest routerRequest) {
        return true;
    }

    @Override
    public MaActionResult invoke(Context context, RouterRequest routerRequest) {
        Log.i(TAG,"invoke " +routerRequest);
        Map<String,String> params = routerRequest.getData();
        for(String key : params.keySet()){
            Log.i(TAG,"key["+key+"],value is "+params.get(key));
        }
        return null;
    }

    @Override
    public String getName() {
        return ClientConfig.rpcActionName;
    }
}
