package com.example.update.component;

import android.content.Context;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.example.base.CP;
import com.example.base.utils.UiSwitch;
import com.example.update.ui.UpgradeActivity;

public class Cp implements IComponent {

    @Override
    public String getName() {
        return CP.CP_UPDATE;
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();
        Context context = cc.getContext();
        switch (actionName) {
            case CP.ACTION_UPDATE:


                UiSwitch.single(context, UpgradeActivity.class);


                break;

        }
        CC.sendCCResult(cc.getCallId(), CCResult.error("has not support for action:" + cc.getActionName()));
        return false;
    }

}
