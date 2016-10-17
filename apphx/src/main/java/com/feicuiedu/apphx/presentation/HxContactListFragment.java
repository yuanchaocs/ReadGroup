package com.feicuiedu.apphx.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hyphenate.easeui.ui.EaseContactListFragment;

import java.util.List;

/**
 * 作者：yuanchao on 2016/10/17 0017 11:59
 * 邮箱：yuanchao@feicuiedu.com
 */

public class HxContactListFragment extends EaseContactListFragment implements HxContactListView{
    private HxContactListPresenter presenter;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 创建你的协调人
        presenter = new HxContactListPresenter();
        presenter.onCreate();
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presenter.attachView(this);
        // 执行业务
        presenter.loadContacts();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    // start - 视图接口
    @Override public void setContacts(List<String> contacts) {

    }

    @Override public void refreshContacts() {

    }

    @Override public void showDeleteContactFail(String msg) {

    }
    // end - 视图接口
}
