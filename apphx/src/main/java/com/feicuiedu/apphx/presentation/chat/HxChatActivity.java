package com.feicuiedu.apphx.presentation.chat;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.feicuiedu.apphx.R;
import com.hyphenate.easeui.EaseConstant;

public class HxChatActivity extends AppCompatActivity {

    /** 启动当前Activity*/
    public static void open(Context context, String chatId){
        Intent intent = new Intent(context, HxChatActivity.class);
        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE,EaseConstant.CHATTYPE_SINGLE); // 单聊
        intent.putExtra(EaseConstant.EXTRA_USER_ID, chatId); // 对方id
        context.startActivity(intent);
    }

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hx_chat);
    }

    @Override public void onContentChanged() {
        super.onContentChanged();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int chatType = getIntent().getIntExtra(EaseConstant.EXTRA_CHAT_TYPE, 0);
        String chatId = getIntent().getStringExtra(EaseConstant.EXTRA_USER_ID);

        HxChatFragment hxChatFragment = HxChatFragment.getInstance(chatType, chatId);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout_container, hxChatFragment)
                .commit();

    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
