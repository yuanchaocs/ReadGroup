package com.feicuiedu.readgroup.presentation.user.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.feicuiedu.readgroup.presentation.HomeActivity;
import com.feicuiedu.readgroup.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Login页面 (DialogFragment),采用MVP结构
 * <p>
 * 作者：yuanchao on 2016/10/14 0014 16:17
 * 邮箱：yuanchao@feicuiedu.com
 */
public class LoginFragment extends DialogFragment implements LoginView {
    private Unbinder unbinder;
    private LoginPresenter loginPresenter;

    @BindView(R.id.edit_username) EditText etUsername;
    @BindView(R.id.edit_password) EditText etPassword;
    @BindView(R.id.button_confirm) Button btnConfirm;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginPresenter = new LoginPresenter();
        loginPresenter.onCreate();
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        loginPresenter.attachView(this);
    }

    // 按下登录按钮，协调人开始干活
    @OnClick(R.id.button_confirm)
    public void login() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            String info = getString(R.string.user_error_not_null);
            Toast.makeText(getContext(), info, Toast.LENGTH_SHORT).show();
            return;
        }
        loginPresenter.login(username, password);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        loginPresenter.detachView();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        loginPresenter.onDestroy();
    }

    // start View Interface -------------------------------------
    @Override public void showLoading() {
        setCancelable(false);
        btnConfirm.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override public void hideLoading() {
        setCancelable(true);
        btnConfirm.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override public void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override public void navigateToHome() {
        Intent intent = new Intent(getContext(), HomeActivity.class);
        startActivity(intent);
        getActivity().finish();
    }// end View Interface -------------------------------------
}
