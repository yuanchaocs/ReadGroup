package com.feicuiedu.readgroup.presentation.user.login;

import android.support.annotation.NonNull;

import com.feicuiedu.apphx.model.event.HxLoginEvent;
import com.feicuiedu.apphx.model.HxUserManager;
import com.feicuiedu.apphx.basemvp.MvpPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Login页面协调人, 实现业务层接口,持有视图层接口,实现两者间的"协调"
 * 作者：yuanchao on 2016/10/14 0014 16:23
 * 邮箱：yuanchao@feicuiedu.com
 */

public class LoginPresenter extends MvpPresenter<LoginView>{

    /** 登录,协调人要做的主要工作*/
    public void login(@NonNull final String hxId, @NonNull final String password){
        // 协调视图那边的变化
        getView().showLoading();
        // 安排业务人员去做事,(等业务人员返结果<EventBus>)
        HxUserManager.getInstance().asyncLogin(hxId,password);
    }

    // 业务人员返结果<EventBus>
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HxLoginEvent event){
        // 协调视图那边的变化
        getView().hideLoading();
        // 看业务数据结果
        if(event.isSuccess()){
            // 协调视图那边的变化
            getView().navigateToHome();
        }else{
            // 协调视图那边的变化
            String msg = String.format("失败原因: %s",event.getErrorMessage());
            getView().showMessage(msg);
        }
    }

    @NonNull @Override protected LoginView getNullObject() {
        return LoginView.NULL_VIEW;
    }
}
