package com.example.camera.component;

import android.content.Context;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.IComponent;
import com.example.base.CP;
import com.example.base.utils.BUN;
import com.example.base.utils.UiSwitch;
import com.example.camera.cameralibrary.JcamerActivity;

public class Cp implements IComponent {

    @Override
    public String getName() {
        return CP.CP_CAMERA;
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();
        Context context = cc.getContext();
        switch (actionName) {
            case CP.ACTION_CAMERA:
                UiSwitch.bundle(cc.getContext(),JcamerActivity.class,new BUN().putString("call_id",cc.getCallId()).ok());
                return true;

        }
        return false;
    }

}
