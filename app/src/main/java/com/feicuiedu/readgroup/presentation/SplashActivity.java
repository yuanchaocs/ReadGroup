package com.feicuiedu.readgroup.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.feicuiedu.readgroup.R;
import com.feicuiedu.readgroup.presentation.user.login.LoginFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {

    private LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
    }

    // 按下登录进入登录DialogFragment
    @OnClick(R.id.button_login)
    public void showLoginDialog(){
        if (loginFragment == null) {
            loginFragment = new LoginFragment();
        }
        loginFragment.show(getSupportFragmentManager(), null);
    }

}