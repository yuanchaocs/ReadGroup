package com.feicuiedu.readgroup.presentation.user.login;

import com.feicuiedu.apphx.basemvp.MvpView;

/**
 * 登录页面视图接口
 * 作者：yuanchao on 2016/10/14 0014 16:21
 * 邮箱：yuanchao@feicuiedu.com
 */

public interface LoginView extends MvpView {

    /**
     * 当开始加载数据时，将来调用的视图方法
     */
    void showLoading();

    /**
     * 当结束加载数据后，将来调用的视图方法
     */
    void hideLoading();
    /**
     * 当业务执行过程中,出现错误,将来调用的视图方法
     */
    void showMessage(String msg);

    /** 登录成功后，视图要切换到Home页面*/
    void navigateToHome();

    LoginView NULL_VIEW = new LoginView() {
        @Override public void showLoading() {
        }

        @Override public void hideLoading() {
        }

        @Override public void showMessage(String msg) {
        }

        @Override public void navigateToHome() {
        }
    };
}
