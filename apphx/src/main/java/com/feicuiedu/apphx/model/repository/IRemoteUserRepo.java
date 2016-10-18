package com.feicuiedu.apphx.model.repository;

import com.hyphenate.easeui.domain.EaseUser;

import java.util.List;

/**
 * 远程用户仓库的接口, 代表到应用服务器获取数据的相关操作
 * <p/>
 * 前期为了测试，本模块内将提供一个假实现
 *
 * 作者：yuanchao on 2016/10/18 0018 10:43
 * 邮箱：yuanchao@feicuiedu.com
 */
public interface IRemoteUserRepo {

    /** 通过用户名查询用户*/
    List<EaseUser> queryByName(String username) throws Exception;

    /** 通过环信id查询用户信息*/
    List<EaseUser> getUsers(List<String> ids) throws Exception;
}
