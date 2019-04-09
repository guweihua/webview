package com.example.choosepicture.component;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.example.base.CP;
import com.example.choosepicture.SelectPicActivity;

public class Cp implements IComponent {

    @Override
    public String getName() {
        return CP.CP_CHOOSE_PICTURE;
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();
        Context context = cc.getContext();
        switch (actionName) {
            case CP.ACTION_CHOOSE_PICTURE:

//                String isCrop = cc.getParamItem("isCrop");

                Log.e("Afas", "ACTION_CHOOSE_PICTURE");


//                UiSwitch.bundle(cc.getContext(), SelectPicActivity.class,new BUN().putString("call_id",cc.getCallId()).ok());

                Intent intent = new Intent();
                intent.setClass(context, SelectPicActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Bundle bundle = new Bundle();
                bundle.putString("call_id", cc.getCallId());

                intent.putExtras(bundle);
                context.startActivity(intent);

                return true;


        }
        CC.sendCCResult(cc.getCallId(), CCResult.error("has not support for action:" + cc.getActionName()));
        return false;
    }

}
