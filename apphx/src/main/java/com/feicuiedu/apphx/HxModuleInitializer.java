package com.feicuiedu.apphx;

import com.feicuiedu.apphx.model.HxContactManager;
import com.feicuiedu.apphx.model.repository.ILocalUsersRepo;
import com.feicuiedu.apphx.model.repository.IRemoteUserRepo;

/**
 * AppHx模块初始化工具
 *
 * 作者：yuanchao on 2016/10/18 0018 11:22
 * 邮箱：yuanchao@feicuiedu.com
 */

public class HxModuleInitializer {

    private static HxModuleInitializer sInstance;

    public static HxModuleInitializer getInstance() {
        if (sInstance == null) {
            sInstance = new HxModuleInitializer();
        }
        return sInstance;
    }

    public void init(
            IRemoteUserRepo remoteUsersRepo,
            ILocalUsersRepo localUsersRepo){

        // 初始化联系操作内的本地及远程用户仓库
        HxContactManager.getInstance()
                .initLocalUsersRepo(localUsersRepo)
                .initRemoteUserRepo(remoteUsersRepo);

    }
}
