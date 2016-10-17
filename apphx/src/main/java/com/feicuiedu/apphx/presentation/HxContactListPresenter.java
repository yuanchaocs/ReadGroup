package com.feicuiedu.apphx.presentation;

import android.support.annotation.NonNull;

import com.feicuiedu.apphx.basemvp.MvpPresenter;
import com.feicuiedu.apphx.model.HxContactManager;
import com.feicuiedu.apphx.model.event.HxErrorEvent;
import com.feicuiedu.apphx.model.event.HxEventType;
import com.feicuiedu.apphx.model.event.HxRefreshContactEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 协调人
 * 作者：yuanchao on 2016/10/17 0017 12:02
 * 邮箱：yuanchao@feicuiedu.com
 */

public class HxContactListPresenter extends MvpPresenter<HxContactListView>{

    @NonNull @Override protected HxContactListView getNullObject() {
        return HxContactListView.NULL;
    }

    // 执行业务
    public void loadContacts(){
        HxContactManager.getInstance().getContacts();
    }

    // 执行业务
    public void deleteContact(String hxId) {
        HxContactManager.getInstance().deleteContact(hxId);
    }

    // 接收model层刷新联系人事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HxRefreshContactEvent event) {
        // 是否有更新
        if(event.changed){
            // 设置到视图
            getView().setContacts(event.contacts);
        }
        getView().refreshContacts();
    }

    // 接收model层错误事件
    public void onEvent(HxErrorEvent event){
        // 不是删除联系人的错误事件，不做处理
        if(event.type != HxEventType.DELETE_CONTACT)return;
        getView().showDeleteContactFail(event.toString());
    }
}
