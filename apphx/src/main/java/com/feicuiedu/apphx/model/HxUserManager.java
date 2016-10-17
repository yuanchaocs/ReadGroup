package com.feicuiedu.apphx.model;

import android.support.annotation.NonNull;

import com.feicuiedu.apphx.model.event.HxErrorEvent;
import com.feicuiedu.apphx.model.event.HxEventType;
import com.feicuiedu.apphx.model.event.HxSimpleEvent;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import timber.log.Timber;

/**
 * Model层, 环信用户基本功能管理，登录、注册、登出
 * <p>
 * 作者：yuanchao on 2016/10/14 0014 15:39
 * 邮箱：yuanchao@feicuiedu.com
 */
public class HxUserManager {

    private static HxUserManager sInstance;

    public static HxUserManager getInstance() {
        if (sInstance == null) {
            sInstance = new HxUserManager();
        }
        return sInstance;
    }

    private final EMClient emClient;
    private final ExecutorService executorService; // 用于当前业务处理内，业务操作的线程池
    private final EventBus eventBus;

    private HxUserManager() {
        emClient = EMClient.getInstance();
        executorService = Executors.newSingleThreadExecutor();
        eventBus = EventBus.getDefault();
    }

    /**
     * 环信异步注册(用于测试,后期将通过自己应用服务进行注册)
     */
    public void asyncRegister(@NonNull final String hxId, @NonNull final String password) {
        Runnable runnable = new Runnable() {
            @Override public void run() {
                try {
                    emClient.createAccount(hxId, password);
                    Timber.d("%s RegisterHx success", hxId);
                    // 成功了
                    eventBus.post(new HxSimpleEvent(HxEventType.REGISTER));
                } catch (HyphenateException e) {
                    Timber.d("RegisterHx fail");
                    // 失败了
                    eventBus.post(new HxErrorEvent(HxEventType.REGISTER, e));
                }
            }
        };
        // 提交，线程池处理此Runnable
        executorService.submit(runnable);
    }

    /**
     * 环信异步登录
     */
    public void asyncLogin(@NonNull final String hxId, @NonNull final String password) {
        emClient.login(hxId, password, new EMCallBack() {
            @Override public void onSuccess() {
                Timber.d("%s LoginHx success", hxId);
                eventBus.post(new HxSimpleEvent(HxEventType.LOGIN));
            }

            @Override public void onError(int code, String message) {
                Timber.d("%s LoginHx error, code is %s.", hxId, code);
                eventBus.post(new HxErrorEvent(HxEventType.LOGIN, code, message));
            }

            @Override public void onProgress(int i, String s) {
            }
        });
    }


}
