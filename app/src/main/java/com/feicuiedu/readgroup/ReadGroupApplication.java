package com.feicuiedu.readgroup;

import android.content.Intent;

import com.feicuiedu.apphx.HxBaseApplication;
import com.feicuiedu.readgroup.presentation.SplashActivity;

public class ReadGroupApplication extends HxBaseApplication {

    @Override protected void exit() {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
