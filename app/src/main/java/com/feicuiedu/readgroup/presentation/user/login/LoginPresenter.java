package com.feicuiedu.readgroup.presentation.user.login;

import android.support.annotation.NonNull;

import com.feicuiedu.apphx.model.HxUserManager;
import com.feicuiedu.apphx.basemvp.MvpPresenter;
import com.feicuiedu.apphx.model.event.HxErrorEvent;
import com.feicuiedu.apphx.model.event.HxEventType;
import com.feicuiedu.apphx.model.event.HxSimpleEvent;

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
    public void onEvent(HxSimpleEvent event){
        // 判断是否是登录成功事件
        if (event.type != HxEventType.LOGIN) return;
        // 协调视图那边的变化
        getView().hideLoading();
        getView().navigateToHome();
    }

    // 业务人员返结果<EventBus>
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HxErrorEvent event) {
        // 判断是否是登录失败事件
        if (event.type != HxEventType.LOGIN) return;
        getView().hideLoading();
        getView().showMessage(event.toString());
    }

    @NonNull @Override protected LoginView getNullObject() {
        return LoginView.NULL_VIEW;
    }
}
