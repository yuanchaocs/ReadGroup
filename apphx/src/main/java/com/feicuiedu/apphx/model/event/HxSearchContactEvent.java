package com.feicuiedu.apphx.model.event;

import com.hyphenate.easeui.domain.EaseUser;

import java.util.List;

/**
 * 搜索联系人事件。
 * 作者：yuanchao on 2016/10/18 0018 10:57
 * 邮箱：yuanchao@feicuiedu.com
 */

public class HxSearchContactEvent {

    public final List<EaseUser> contacts;
    public final boolean isSuccess;
    public final String errorMessage;

    public HxSearchContactEvent(List<EaseUser> contacts){
        this.contacts = contacts;
        this.isSuccess = true;
        this.errorMessage = null;
    }

    public HxSearchContactEvent(String errorMessage){
        this.contacts = null;
        this.isSuccess = false;
        this.errorMessage = errorMessage;
    }

}
