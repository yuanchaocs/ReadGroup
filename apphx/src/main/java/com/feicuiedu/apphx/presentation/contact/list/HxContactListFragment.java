package com.feicuiedu.apphx.presentation.contact.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.feicuiedu.apphx.R;
import com.feicuiedu.apphx.presentation.chat.HxChatActivity;
import com.feicuiedu.apphx.presentation.contact.search.HxSearchContactActivity;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;

import java.util.HashMap;
import java.util.List;

/**
 * 环信联系人列表页面
 * <p/>
 * MVP的View实现:主要负责view层视图实现，触发presenter层的执行
 * <p/>
 * 作者：yuanchao on 2016/10/17 0017 11:59
 * 邮箱：yuanchao@feicuiedu.com
 */
public class HxContactListFragment extends EaseContactListFragment implements HxContactListView {
    // 协调人
    private HxContactListPresenter presenter;
    private String selectedHxId;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 此方法要在 onActivityCreated 之前调用才有效
        setContactListItemClickListener(new EaseContactListItemClickListener() {
            @Override public void onListItemClicked(EaseUser user) {
                HxChatActivity.open(getContext(), user.getUsername());
            }
        });

        presenter = new HxContactListPresenter();// 创建你的协调人
        presenter.onCreate();
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        customUi();
        presenter.attachView(this);
        presenter.loadContacts(); // 执行业务
    }

    @Override public void onCreateContextMenu(ContextMenu menu, View v,
                                              ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // 创建listview的context menu
        if (v == listView) {
            int position = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
            EaseUser easeUser = (EaseUser) listView.getItemAtPosition(position);
            if (easeUser == null) return; // 长按HeaderView的情况
            selectedHxId = easeUser.getUsername();
            getActivity().getMenuInflater().inflate(R.menu.fragment_hx_contact_list, menu);
        }
    }

    @Override public boolean onContextItemSelected(MenuItem item) {
        // 删除好友
        if (item.getItemId() == R.id.menu_delete_contact) {
            presenter.deleteContact(selectedHxId);
            return true;
        }
        return super.onContextItemSelected(item);
    }

    // 自定制UI
    private void customUi() {
        // 隐藏EaseUI的标题栏
        super.hideTitleBar();
        // 注册上下文菜单(也就是长按listview将弹出menu)
        registerForContextMenu(super.listView);

        // 设置super.listView的Header
        View headerView = LayoutInflater.from(getContext())
                .inflate(R.layout.partial_hx_contact_list_header, super.listView, false);

        // 添加新朋友
        View addContacts = headerView.findViewById(R.id.layout_add_contacts);
        addContacts.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(getContext(), HxSearchContactActivity.class);
                startActivity(intent);
            }
        });
        // 邀请和通知
        View notifications = headerView.findViewById(R.id.layout_notifications);
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                // TODO: 2016/10/17 0017  邀请和通知
            }
        });
        super.listView.addHeaderView(headerView);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    // start-interface: HxContactListView视图接口
    @Override public void setContacts(List<String> contacts) {
        // easeui这边,认EaseUser
        HashMap<String, EaseUser> contactsMap = new HashMap<>();
        contactsMap.clear();
        for (String contact : contacts) {
            contactsMap.put(contact, new EaseUser(contact));
        }
        super.setContactsMap(contactsMap);
    }

    @Override public void refreshContacts() {
        super.refresh();
    }

    @Override public void showDeleteContactFail(String msg) {
        String info = getString(R.string.hx_contact_error_delete_contact, msg);
        Toast.makeText(getContext(), info, Toast.LENGTH_SHORT).show();
    }
    // end-interface: HxContactListView视图接口
}
