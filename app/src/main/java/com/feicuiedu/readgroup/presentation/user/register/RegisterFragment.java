package com.feicuiedu.readgroup.presentation.user.register;


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

import com.feicuiedu.readgroup.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RegisterFragment extends DialogFragment implements RegisterView {

    private RegisterPresenter registerPresenter;

    private Unbinder unbinder;

    @BindView(R.id.edit_username) protected EditText etUsername;
    @BindView(R.id.edit_password) protected EditText etPassword;
    @BindView(R.id.button_confirm) Button btnConfirm;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerPresenter = new RegisterPresenter();
        registerPresenter.onCreate();
    }


    @Nullable @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        registerPresenter.attachView(this);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        registerPresenter.detachView();
        unbinder.unbind();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        registerPresenter.onDestroy();
    }

    // start-interface: RegisterView
    @Override public void showLoading() {
        btnConfirm.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        setCancelable(false);
    }

    @Override public void hideLoading() {
        btnConfirm.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        setCancelable(true);
    }

    @Override public void showRegisterFail(String msg) {
        String info = getString(R.string.user_error_register_fail, msg);
        Toast.makeText(getContext(), info, Toast.LENGTH_SHORT).show();
    }

    @Override public void showRegisterSuccess() {
        Toast.makeText(getContext(), R.string.user_register_success, Toast.LENGTH_SHORT).show();
    }

    @Override public void close() {
        dismiss();
    } // end-interface: RegisterView

    @OnClick(R.id.button_confirm)
    public void register() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            String info = getString(R.string.user_error_not_null);
            Toast.makeText(getContext(), info, Toast.LENGTH_SHORT).show();
            return;
        }

        registerPresenter.register(username, password);
    }
}
