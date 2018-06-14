package com.will.multiprocess.macoretest.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import macore.tools.Logger;

/**
 * Created by will on 18-6-11.
 */

public abstract class BaseActivity extends AppCompatActivity{
    protected final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Logger.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        initPresenter();
        setWindowFeature();
        setContentLayout();
        initView();
    }

    @Override
    protected void onStart() {
        Logger.i(TAG, "onStart");
        super.onStart();

    }

    @Override
    protected void onRestart() {
        Logger.i(TAG, "onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {


        Logger.i(TAG, "onResume");
        super.onResume();
//        if (mBasicPresenter != null) {
//            mBasicPresenter.resume();
//        }
    }

    @Override
    protected void onPause() {
//        if (BaseSharePreference.readBoolean(SettingsInfo.SETTING_INFO_DEBUG, false)) {
//            //Bugtags.onPause(this);
//        }
        Logger.i(TAG, "onPause");
//        if (mBasicPresenter != null) {
//            mBasicPresenter.pause();
//        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        Logger.i(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Logger.i(TAG, "onDestroy");
//        if (mBasicPresenter != null) {
//            mBasicPresenter.destroy();
//        }
        super.onDestroy();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
//        if (BaseSharePreference.readBoolean(SettingsInfo.SETTING_INFO_DEBUG, false)) {
//            //Bugtags.onDispatchTouchEvent(this, event);
//        }
        return super.dispatchTouchEvent(event);
    }

    protected abstract void initView();

    protected abstract void setListener();

    protected abstract void setContentLayout();

    protected abstract void initPresenter();

    protected void setWindowFeature() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
