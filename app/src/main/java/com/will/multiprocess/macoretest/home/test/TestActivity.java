package com.will.multiprocess.macoretest.home.test;

import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.will.multiprocess.macoretest.R;
import com.will.multiprocess.macoretest.views.BaseActivity;

/**
 * Created by will on 18-6-11.
 */
@Route(path = "/home/testactivity")
public class TestActivity extends BaseActivity {
    Button mTestBtn;
    @Autowired
    public String name;



    private void animaiton1(){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mTestBtn,"translationX",0,300);
        objectAnimator.setDuration(1000);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(mTestBtn,"alpha",0.1f,1f);
        objectAnimator1.setDuration(2000);
        ValueAnimator animator = ValueAnimator.ofInt(0,100);
        animator.setDuration(2000);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(objectAnimator).before(animator);
        animatorSet.play(objectAnimator1).after(animator);
        //animatorSet.setInterpolator(new BounceInterpolator());
        animatorSet.start();

    }

    private void animation2(){
        PropertyValuesHolder xHolder = PropertyValuesHolder.ofFloat("translationX", 0, 600);
        PropertyValuesHolder yHolder = PropertyValuesHolder.ofFloat("translationY", 0, 600);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(mTestBtn,xHolder,yHolder);
        animator.setDuration(2000);
        animator.start();
    }

    private void animation3(){
        Keyframe scaleFrame1 = Keyframe.ofFloat(0f, 0);
        Keyframe scaleFrame2 = Keyframe.ofFloat(0.5f, 400);
        Keyframe scaleFrame3 = Keyframe.ofFloat(1.0f, 600);
        PropertyValuesHolder xHolder = PropertyValuesHolder.ofKeyframe("translationX", scaleFrame1, scaleFrame2, scaleFrame3);
        PropertyValuesHolder yHolder = PropertyValuesHolder.ofKeyframe("translationY", scaleFrame1, scaleFrame2, scaleFrame3);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(mTestBtn, xHolder, yHolder);
        animator.setDuration(2000);
        animator.start();
    }
    @Override
    protected void initView() {
        mTestBtn = findViewById(R.id.test_btn);
        //animation3();
        mTestBtn.setText(name);

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_test);
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void initPresenter() {

    }
}
