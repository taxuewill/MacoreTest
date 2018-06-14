package macore;

import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import com.provider.ProviderInit;

import macore.multiprocess.BaseApplicationLogic;
import macore.multiprocess.PriorityLogicWrapper;
import macore.router.LocalRouter;
import macore.router.WideRouter;
import macore.router.WideRouterApplicationLogic;
import macore.router.WideRouterConnectService;
import macore.tools.Logger;
import macore.tools.ProcessUtil;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by wanglei on 2016/11/25.
 */

public abstract class MaApplication extends Application {
    private static final String TAG = "MaApplication";
    private static MaApplication sInstance;
    private ArrayList<PriorityLogicWrapper> mLogicList;
    private HashMap<String, ArrayList<PriorityLogicWrapper>> mLogicClassMap;
    private HashMap<String, ArrayList<MaProvider>> mProviderMap;
    private HashMap<String, ArrayList<MaAction>> mActionMap;
    private String wideRouterPackageName;
    private String wideRouterServiceName;


    @CallSuper
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Logger.d(TAG, "Application onCreate start: " + System.currentTimeMillis());
        init();
        startWideRouter();
        initializeLogic();
        dispatchLogic();
        instantiateLogic();

        // Traverse the application logic.
        if (null != mLogicList && mLogicList.size() > 0) {
            for (PriorityLogicWrapper priorityLogicWrapper : mLogicList) {
                if (null != priorityLogicWrapper && null != priorityLogicWrapper.instance) {
                    priorityLogicWrapper.instance.onCreate();
                    String precessName = ProcessUtil.getProcessName(this, ProcessUtil.getMyProcessId());
                    Logger.d(TAG, precessName);
                    if(mProviderMap.get(precessName)!= null){
                        for (MaProvider maProvider : mProviderMap.get(precessName)) {
                            LocalRouter.getInstance(this).registerProvider(maProvider.getName(), maProvider);
                            for (MaAction maAction : mActionMap.get(precessName + "_" + maProvider.getName())) {
                                maProvider.registerAction(maAction.getName(), maAction);
                            }
                        }
                    }
                }
            }
        }
        Logger.d(TAG, "Application onCreate end: " + System.currentTimeMillis());
    }

    private void init() {
        LocalRouter.getInstance(this);
        mLogicClassMap = new HashMap<>();
        mProviderMap = new HashMap<>();
        mActionMap = new HashMap<>();
        ProviderInit.init(mProviderMap, mActionMap);
    }

    protected void startWideRouter() {
        if (needMultipleProcess()) {
            registerApplicationLogic(WideRouter.PROCESS_NAME, 1000, WideRouterApplicationLogic.class);
            Intent intent = new Intent(this, WideRouterConnectService.class);
            startService(intent);
        }
    }

    protected void configWideRouter(String wideRouterPackageName, String wideRouterServiceName){
        this.wideRouterPackageName = wideRouterPackageName;
        this.wideRouterServiceName = wideRouterServiceName;
    }

    public String getWideRouterPackageName(){
        return this.wideRouterPackageName;
    }

    public String getWideRouterServiceName(){
        return this.wideRouterServiceName;
    }

    public abstract void initializeAllProcessRouter();

    protected abstract void initializeLogic();

    public abstract boolean needMultipleProcess();

    protected boolean registerApplicationLogic(String processName, int priority, @NonNull Class<? extends BaseApplicationLogic> logicClass) {
        boolean result = false;
        if (null != mLogicClassMap) {
            ArrayList<PriorityLogicWrapper> tempList = mLogicClassMap.get(processName);
            if (null == tempList) {
                tempList = new ArrayList<>();
                mLogicClassMap.put(processName, tempList);
            }
            if (tempList.size() > 0) {
                for (PriorityLogicWrapper priorityLogicWrapper : tempList) {
                    if (logicClass.getName().equals(priorityLogicWrapper.logicClass.getName())) {
                        throw new RuntimeException(logicClass.getName() + " has registered.");
                    }
                }
            }
            PriorityLogicWrapper priorityLogicWrapper = new PriorityLogicWrapper(priority, logicClass);
            tempList.add(priorityLogicWrapper);
        }
        return result;
    }

    private void dispatchLogic() {
        if (null != mLogicClassMap) {
            mLogicList = mLogicClassMap.get(ProcessUtil.getProcessName(this, ProcessUtil.getMyProcessId()));
        }
    }

    private void instantiateLogic() {
        if (null != mLogicList && mLogicList.size() > 0) {
            if (null != mLogicList && mLogicList.size() > 0) {
                Collections.sort(mLogicList);
                for (PriorityLogicWrapper priorityLogicWrapper : mLogicList) {
                    if (null != priorityLogicWrapper) {
                        try {
                            priorityLogicWrapper.instance = priorityLogicWrapper.logicClass.newInstance();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        if (null != priorityLogicWrapper.instance) {
                            priorityLogicWrapper.instance.setApplication(this);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (null != mLogicList && mLogicList.size() > 0) {
            for (PriorityLogicWrapper priorityLogicWrapper : mLogicList) {
                if (null != priorityLogicWrapper && null != priorityLogicWrapper.instance) {
                    priorityLogicWrapper.instance.onTerminate();
                }
            }
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (null != mLogicList && mLogicList.size() > 0) {
            for (PriorityLogicWrapper priorityLogicWrapper : mLogicList) {
                if (null != priorityLogicWrapper && null != priorityLogicWrapper.instance) {
                    priorityLogicWrapper.instance.onLowMemory();
                }
            }
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (null != mLogicList && mLogicList.size() > 0) {
            for (PriorityLogicWrapper priorityLogicWrapper : mLogicList) {
                if (null != priorityLogicWrapper && null != priorityLogicWrapper.instance) {
                    priorityLogicWrapper.instance.onTrimMemory(level);
                }
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (null != mLogicList && mLogicList.size() > 0) {
            for (PriorityLogicWrapper priorityLogicWrapper : mLogicList) {
                if (null != priorityLogicWrapper && null != priorityLogicWrapper.instance) {
                    priorityLogicWrapper.instance.onConfigurationChanged(newConfig);
                }
            }
        }
    }

    public static MaApplication getMaApplication() {
        return sInstance;
    }
}
