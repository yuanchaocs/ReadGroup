package com.feicuiedu.apphx.model.event;

import com.hyphenate.exceptions.HyphenateException;

/**
 * 事件：环信注册结果
 * <p>
 * 作者：yuanchao on 2016/10/14 0014 16:09
 * 邮箱：yuanchao@feicuiedu.com
 */

public class HxRegisterEvent {
    // 是否成功
    private final boolean success;
    // 错误
    private HyphenateException exception;

    public HxRegisterEvent() {
        success = true;
    }

    public HxRegisterEvent(HyphenateException exception) {
        this.exception = exception;
        success = false;
    }

    public boolean isSuccess() {
        return success;
    }

    public HyphenateException getException() {
        return exception;
    }
}
