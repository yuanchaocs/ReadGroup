package com.feicuiedu.apphx.model.repository;

import com.hyphenate.easeui.domain.EaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * 假的远程仓库数据
 *
 * 作者：yuanchao on 2016/10/18 0018 11:03
 * 邮箱：yuanchao@feicuiedu.com
 */

public class MockRemoteUsersRepo implements IRemoteUserRepo{

    @Override public List<EaseUser> queryByName(String username) throws Exception {
        Thread.sleep(3000);

        ArrayList<EaseUser> easeUsers = new ArrayList<>();
        easeUsers.add(new EaseUser("test01"));
        easeUsers.add(new EaseUser("test02"));
        easeUsers.add(new EaseUser("test03"));

        return easeUsers;
    }

    @Override public List<EaseUser> getUsers(List<String> ids) throws Exception {
        return null;
    }
}
