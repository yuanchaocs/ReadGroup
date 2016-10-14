package com.feicuiedu.apphx.basemvp;


import android.support.annotation.NonNull;

import org.greenrobot.eventbus.EventBus;

/**
 * Presenter是View(视图)和Model(模型)的协调人。
 * <p/>
 * 它是通过视图接口 ，来向视图汇报工作的(控制视图行为的)
 * <p/>
 * 作者：yuanchao on 2016/10/13 0013 16:56
 * 邮箱：yuanchao@feicuiedu.com
 */
public abstract class MvpPresenter<V extends MvpView> {

    private V view;

    /**
     * Presenter的创建
     * <p/>
     * 在Activity或Fragment的onCreate()方法中调用
     */
    public final void onCreate() {
        EventBus.getDefault().register(this);
    }

    /**
     * Presenter和视图关联
     * <p/>
     * 在Activity的onCreate()中调用
     * <p/>
     * 在Fragment的onViewCreated()或onActivityCreated()中调用
     */
    public final void attachView(V view) {
        this.view = view;
    }

    /**
     * Presenter和视图解除关联
     * <p/>
     * 在Activity的onDestroy()中调用
     * <p/>
     * 在Fragment的onViewDestroyed()中调用
     */
    public final void detachView() {
        // 使用Null Object Pattern,避免使用view时，频繁的检测null值情况
        this.view = getNullObject();
    }

    /**
     * Presenter的销毁
     * <p/>
     * 在Activity或Fragment的onDestroy()方法中调用
     */
    public final void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    protected final V getView() {
        return view;
    }

    protected abstract @NonNull V getNullObject();
}