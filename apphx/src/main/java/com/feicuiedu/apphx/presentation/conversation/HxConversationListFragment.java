package com.feicuiedu.apphx.presentation.conversation;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.feicuiedu.apphx.R;
import com.feicuiedu.apphx.presentation.chat.HxChatActivity;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

/**
 * 环信会话列表页面
 */
public class HxConversationListFragment extends EaseConversationListFragment implements HxConversationListView {


    private HxConversationListPresenter presenter;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 此方法要在 onActivityCreated 之前调用才有效
        setConversationListItemClickListener(new EaseConversationListItemClickListener() {
            @Override public void onListItemClicked(EMConversation conversation) {
                HxChatActivity.open(getContext(),conversation.getUserName());
            }
        });

        presenter = new HxConversationListPresenter();
        presenter.onCreate();

    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        customUi();
        presenter.attachView(this);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    // 上下文菜单, 删除会话
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v == conversationListView) {
            getActivity().getMenuInflater().inflate(R.menu.fragment_hx_conversation_list, menu);
        }
    }

    @Override public boolean onContextItemSelected(MenuItem item) {

        boolean deleteMessage;

        if (item.getItemId() == R.id.menu_delete_conversation) {
            deleteMessage = false;
        } else if (item.getItemId() == R.id.menu_delete_conversation_and_message) {
            deleteMessage = true;
        } else {
            throw new RuntimeException("Wrong branch!");
        }

        int position = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;
        EMConversation conversation = conversationListView.getItem(position);
        // 删除会话
        presenter.deleteConversation(conversation, deleteMessage);

        return true;
    }

    @Override public void refreshConversations() {
        super.refresh();
    }

    @SuppressWarnings({"deprecation", "ConstantConditions"})
    private void customUi(){
        hideTitleBar();
        // 注册上下文菜单
        registerForContextMenu(conversationListView);

        clearSearch.setImageResource(R.drawable.hx_btn_clear_search); // 按钮: 清除搜索内容

        Drawable searchIcon = getResources().getDrawable(R.drawable.hx_ic_search_accent);
        searchIcon.setBounds(0, 0, searchIcon.getIntrinsicWidth(), searchIcon.getIntrinsicHeight());
        query.setCompoundDrawables(searchIcon, null, null, null); // 设置查询编辑框左侧的图标
    }


}
