package com.feicuiedu.apphx.model.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hyphenate.easeui.domain.EaseUser;

import java.util.List;

/**
 * 本地用户信息仓库接口
 * 本地用户信息仓库接口
 * <p/>
 * 为了简化，本模块内将使用{@link android.content.SharedPreferences}来实现一个默认的本地仓库
 *
 * 作者：yuanchao on 2016/10/18 0018 10:52
 * 邮箱：yuanchao@feicuiedu.com
 */
public interface ILocalUsersRepo {

    /** 保存用户列表*/
    void saveAll(@NonNull List<EaseUser> userList);

    /** 保存用户*/
    void save(@NonNull EaseUser easeUser);

    /** 获取指定用户*/
    @Nullable EaseUser getUser(String hxId);
}
