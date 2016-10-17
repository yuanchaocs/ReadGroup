package com.feicuiedu.readgroup.presentation.user.register;

import com.feicuiedu.apphx.basemvp.MvpView;

public interface RegisterView extends MvpView {

    // 开始加载
    void showLoading();

    // 停止加载
    void hideLoading();

    // 显示注册失败提示
    void showRegisterFail(String msg);

    // 显示注册成功
    void showRegisterSuccess();

    // 关闭注册对话框
    void close();

    RegisterView NULL = new RegisterView() {
        @Override public void showLoading() {

        }

        @Override public void hideLoading() {

        }

        @Override public void showRegisterFail(String msg) {

        }

        @Override public void showRegisterSuccess() {

        }

        @Override public void close() {

        }
    };
}
