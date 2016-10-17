package com.feicuiedu.apphx.model;

import com.feicuiedu.apphx.model.event.HxNewMsgEvent;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class HxMessageManager implements EMMessageListener {

    private static HxMessageManager sInstance;

    public static HxMessageManager getInstance() {
        if (sInstance == null) {
            sInstance = new HxMessageManager();
        }
        return sInstance;
    }

    private final EventBus eventBus;
    private final EMChatManager emChatManager;

    private HxMessageManager() {
        eventBus = EventBus.getDefault();
        emChatManager = EMClient.getInstance().chatManager();
        emChatManager.addMessageListener(this);
    }

    // 删除会话
    public void deleteConversation(String hxId, boolean deleteMessage) {
        emChatManager.deleteConversation(hxId, deleteMessage);
    }

    // start-interface: EMMessageListener
    // 接受消息接口，在接受到文本消息，图片，视频，语音，地理位置，文件这些消息体的时候，会通过此接口通知用户。
    @Override public void onMessageReceived(List<EMMessage> list) {
        eventBus.post(new HxNewMsgEvent(list));
    }

    // 这个接口只包含命令的消息体(透传消息)，包含命令的消息体通常不对用户展示。
    @Override public void onCmdMessageReceived(List<EMMessage> list) {

    }

    /* no-op */
    @Override public void onMessageReadAckReceived(List<EMMessage> list) {
        // 接受到消息体的已读回执, 消息的接收方已经阅读此消息。
    }

    /* no-op */
    @Override public void onMessageDeliveryAckReceived(List<EMMessage> list) {
        // 收到消息体的发送回执，消息体已经成功发送到对方设备。
    }

    /* no-op */
    @Override public void onMessageChanged(EMMessage emMessage, Object o) {
        // 接受消息发生改变的通知，包括消息ID的改变。消息是改变后的消息。
    } // end-interface: EMMessageListener

}
