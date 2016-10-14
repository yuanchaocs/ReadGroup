package com.feicuiedu.apphx.model.event;

/**
 * 事件：环信登录结果
 * <p>
 * 作者：yuanchao on 2016/10/14 0014 16:05
 * 邮箱：yuanchao@feicuiedu.com
 */

public class HxLoginEvent {
    // 是否成功
    private final boolean success;
    // 错误码
    private int errorCode;
    // 错误描述
    private String errorMessage;

    public HxLoginEvent(){
        this.success = true;
    }

    public HxLoginEvent(int errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.success = false;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
