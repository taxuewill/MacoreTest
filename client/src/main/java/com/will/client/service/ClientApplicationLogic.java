package com.will.client.service;


import macore.MaApplication;
import macore.multiprocess.BaseApplicationLogic;

/**
 * Created by will on 18-5-7.
 */

public class ClientApplicationLogic extends BaseApplicationLogic {
    private static MaApplication maApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        maApplication = mApplication;
    }

    public static MaApplication getMaApplication(){
        return maApplication;
    }
}
