package com.feicuiedu.apphx.model;

import com.feicuiedu.apphx.model.event.HxErrorEvent;
import com.feicuiedu.apphx.model.event.HxEventType;
import com.feicuiedu.apphx.model.event.HxRefreshContactEvent;
import com.feicuiedu.apphx.model.event.HxSearchContactEvent;
import com.feicuiedu.apphx.model.repository.ILocalUsersRepo;
import com.feicuiedu.apphx.model.repository.IRemoteUserRepo;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMContactManager;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.exceptions.HyphenateException;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import timber.log.Timber;

/**
 * 环信联系人管理
 * <p/>
 * MVP的model:主要负责业务处理,且将结果通过EventBus发送到Presenter中去处理
 * <p/>
 * 作者：yuanchao on 2016/10/17 0017 10:03
 * 邮箱：yuanchao@feicuiedu.com
 */
public class HxContactManager implements EMContactListener, EMConnectionListener {

    private static HxContactManager sInstance;

    public static HxContactManager getInstance() {
        if (sInstance == null) {
            sInstance = new HxContactManager();
        }
        return sInstance;
    }

    private List<String> contacts; // 联系人列表集合
    private String currentUserId;

    private final EMContactManager emContactManager;
    private final EventBus eventBus;
    private final ExecutorService executorService;

    private HxContactManager() {
        // EventBus
        eventBus = EventBus.getDefault();
        // 线程池
        executorService = Executors.newSingleThreadExecutor();
        // 环信连接监听
        EMClient.getInstance().addConnectionListener(this);
        // 环信联系人相关操作SDK
        emContactManager = EMClient.getInstance().contactManager();
        emContactManager.setContactListener(this);
    }

    /**
     * 获取联系人
     */
    public void getContacts() {
        // 已获取过联系人(不用重复去获取)
        if (contacts != null) {
            notifyContactsRefresh();
        }
        // 还未获取过联系人
        else {
            asyncGetContactsFromServer();
        }
    }

    // 远程仓库(一般指的主是应用服务器)
    private IRemoteUserRepo remoteUsersRepo;
    private ILocalUsersRepo localUsersRepo;

    // 初始远程仓库
    public HxContactManager initRemoteUserRepo(IRemoteUserRepo remoteUsersRepo){
        this.remoteUsersRepo = remoteUsersRepo;
        return this;
    }

    public HxContactManager initLocalUsersRepo(ILocalUsersRepo localUsersRepo){
        this.localUsersRepo = localUsersRepo;
        return this;
    }

    /** 搜索用户，业务流程
     * <p/>
     * 环信服务器不提供搜索功能，搜索完全由App和应用服务器实现*/
    public void asyncSearchContacts(final String username){
        Runnable runnable = new Runnable() {
            @Override public void run() {
                try {
                    // 上应用服务器获取数据 , 还没有应用服务器  (到远程用户仓库去查询数据)
                    List<EaseUser> user = remoteUsersRepo.queryByName(username); // test01 test02 test03
                    // 缓存到本地 , 只知道缓存 (只要是你操作过，用过，见过的用户EaseUser，都应该要缓存起来)
                    localUsersRepo.saveAll(user);
                    // 将结果发送到presenter
                    eventBus.post(new HxSearchContactEvent(user));
                } catch (Exception e) {
                    Timber.e(e,"asyncSearchContacts");
                    eventBus.post(new HxSearchContactEvent(e.getMessage()));
                }
            }
        };
        executorService.submit(runnable);
    }

    private void asyncGetContactsFromServer() {
        Runnable runnable = new Runnable() {
            @Override public void run() {
                try {
                    contacts = emContactManager.getAllContactsFromServer();
                    notifyContactsRefresh();
                } catch (HyphenateException e) {
                    Timber.e(e, "asyncGetContactsFromServer");
                }
            }
        };
        executorService.submit(runnable);
    }

    private void notifyContactsRefresh() {
        List<String> currentContacts;
        if (contacts == null) {
            currentContacts = Collections.emptyList();
        } else {
            currentContacts = new ArrayList<>(contacts);
        }
        eventBus.post(new HxRefreshContactEvent(currentContacts));
    }

    /**
     * 删除联系人,如果删除成功，会自己触发{@link #onContactDeleted(String)}
     * <p/>
     * 注意：A将B删除了, B客户端的{@link #onContactDeleted(String)}也会触发
     *
     * @param hxId 对方的环信id
     */
    public void deleteContact(final String hxId) {
        Runnable runnable = new Runnable() {
            @Override public void run() {
                try {
                    emContactManager.deleteContact(hxId);
                } catch (HyphenateException e) {
                    Timber.e(e, "deleteContact");
                    // 删除失败
                    eventBus.post(new HxErrorEvent(HxEventType.DELETE_CONTACT, e));
                }
            }
        };
        executorService.submit(runnable);
    }

    public void setCurrentUser(String hxId) {
        this.currentUserId = hxId;
    }

    public void reset() {
        contacts = null;
        currentUserId = null;
    }

    // start-interface: EMConnectionListener
    @Override public void onConnected() {
        if (contacts == null) {
            asyncGetContactsFromServer();
        }
    }

    @Override public void onDisconnected(int i) {
    }
    // end-interface: EMConnectionListener

    // start contact ContactListener -------------------------
    // 添加联系人
    @Override public void onContactAdded(String hxId) {
        Timber.d("onContactAdded %s", hxId);
        if (contacts == null) {
            asyncGetContactsFromServer();
        } else {
            contacts.add(hxId);
            notifyContactsRefresh();
        }
    }

    // 删除联系人
    @Override public void onContactDeleted(String hxId) {
        Timber.d("onContactDeleted %s", hxId);
        if (contacts == null) {
            asyncGetContactsFromServer();
        } else {
            contacts.remove(hxId);
            notifyContactsRefresh();
        }
    }

    // 收到好友邀请
    @Override public void onContactInvited(String hxId, String reason) {
        Timber.d("onContactInvited %s, reason: %s", hxId, reason);
    }

    // 好友请求被同意
    @Override public void onContactAgreed(String hxId) {
        Timber.d("onContactAgreed %s", hxId);

    }

    // 好友请求被拒绝
    @Override public void onContactRefused(String hxId) {
        Timber.d("onContactRefused %s", hxId);
    }

    // end contact ContactListener -------------------------
}
