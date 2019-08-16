package com.wlm.wlm.ui;

import in.srain.cube.views.ptr.PtrUIHandlerHook;

/**
 * Created by LG on 2019/8/8.
 */
public class CustomerPtrUIHandlerHook extends PtrUIHandlerHook {

    public interface OnPtrUIHandlerHookCallback{
        void onPtrUIHandlerHookStart();
    }

    private OnPtrUIHandlerHookCallback handlerHookCallback;

    public CustomerPtrUIHandlerHook(OnPtrUIHandlerHookCallback handlerHookCallback) {
        this.handlerHookCallback = handlerHookCallback;
    }

    @Override
    public void run() {
        if (null != handlerHookCallback) {
            handlerHookCallback.onPtrUIHandlerHookStart();
        }
    }
}
