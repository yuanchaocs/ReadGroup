package com.feicuiedu.apphx.model.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.List;

/**
 * 默认本地用户信息仓库
 * <p>
 * 作者：yuanchao on 2016/10/18 0018 11:06
 * 邮箱：yuanchao@feicuiedu.com
 */
public class DefaultLocalUsersRepo implements ILocalUsersRepo {
    private static final String PREF_HX_USERS_FILE_NAME = "PREF_HX_USERS_FILE_NAME";

    private final SharedPreferences preferences;
    private final Gson gson;

    private static DefaultLocalUsersRepo sInstance;

    public static DefaultLocalUsersRepo getInstance(Context context) {
        if(sInstance == null){
            sInstance = new DefaultLocalUsersRepo(context.getApplicationContext());
        }
        return sInstance;
    }

    private DefaultLocalUsersRepo(Context context) {
        preferences = context.getSharedPreferences(PREF_HX_USERS_FILE_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    @Override public void saveAll(@NonNull List<EaseUser> userList) {
        SharedPreferences.Editor editor = preferences.edit();
        for (EaseUser user : userList) {
            editor.putString(user.getUsername(), gson.toJson(user));
        }
        editor.apply();
    }

    @Override public void save(@NonNull EaseUser easeUser) {
        // 当用户信息有更新时，重新向里面存就可以了,使用的都是hxid来做为key的,是会覆盖的
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(easeUser.getUsername(), gson.toJson(easeUser));
        editor.apply();
    }

    @Nullable @Override public EaseUser getUser(String hxId) {
        String userJsonStr = preferences.getString(hxId, null);
        if (userJsonStr == null) return null;
        return gson.fromJson(userJsonStr, EaseUser.class);
    }
}