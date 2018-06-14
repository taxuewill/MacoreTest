package com.will.multiprocess.macoretest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.will.common.config.ClientConfig;
import com.will.multiprocess.macoretest.home.test.TestActivity;
import com.will.multiprocess.macoretest.service.MainApplicationLogic;

import macore.router.LocalRouter;
import macore.router.RouterRequestUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Intent intent = new Intent(this,TestActivity.class);
//        startActivity(intent);
        ARouter.getInstance()
                .build("/home/testactivity")
                .withString("name","will")
                .navigation();
        finish();
//        try {
//            LocalRouter.getInstance(MainApplicationLogic.getMaApplication()).rxRoute(MainApplication.getMaApplication(), RouterRequestUtil.obtain(MainApplication.getMaApplication())
//                    .domain(ClientConfig.processName)
//                    .provider(ClientConfig.providerName)
//                    .action(ClientConfig.rpcActionName));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
