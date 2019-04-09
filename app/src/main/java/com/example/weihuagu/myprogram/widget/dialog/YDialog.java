package com.example.weihuagu.myprogram.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.weihuagu.myprogram.R;


/**
 * Created by xianguangjin on 16/5/18.
 */
public abstract class YDialog implements DialogInter {

    private Context _context;
    public int style = BOTTOM;
    public Dialog _Dialog;
    public View _Layout;
    public final static int BOTTOM = 1;
    public final static int CENTER = 2;
    public final static int TOP = 3;
    public final static int TOP_RIGHT = 4;

    public YDialog(Context context) {
        this._context = context;
    }

    public YDialog init() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        _Layout = layoutInflater.inflate(provideLayoutId(), null);
        _Dialog = new Dialog(_context, getStypeRes());
        _Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        _Dialog.setContentView(_Layout);

        Window win = _Dialog.getWindow();
        win.getDecorView().setPadding(getPaddingLeft(), getPaddingTop(), getPaddingLeft(), getPaddingBotoom());
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        lp.gravity = getGravity();
        lp.windowAnimations = getAnimation();
        win.setAttributes(lp);
        _Dialog.setCanceledOnTouchOutside(true);
        initView(_Layout);
        return this;
    }

    protected int getPaddingBotoom() {
        return 0;
    }

    protected int getPaddingTop() {
        return 0;
    }

    /**
     * 获取左右边距
     *
     * @return
     */
    protected int getPaddingLeft() {
        return 20;
    }

    /**
     * 初始化View,需要在构造函数里调用
     *
     * @param view
     */
    protected abstract void initView(View view);


    /**
     * 位置
     *
     * @return
     */
    private int getGravity() {
        switch (style) {
            case BOTTOM:
                return Gravity.BOTTOM;
            case CENTER:
                return Gravity.CENTER;
            case TOP:
                return Gravity.TOP;
            case TOP_RIGHT:
                return Gravity.RIGHT;
        }
        return Gravity.BOTTOM;
    }

    /**
     * 获取动画
     *
     * @return
     */
    private int getAnimation() {
        switch (style) {
            case BOTTOM:
                return R.style.dialogAnim;
            case CENTER:
                return R.style.dialogAnimCenter;
            case TOP:
                return R.style.dialogAnimTop;
        }

        return R.style.dialogAnim;
    }

    /**
     * 获取类型
     *
     * @return
     */
    private int getStypeRes() {

        switch (style) {
            case BOTTOM:
                return R.style.dialog;
            case CENTER:
                return R.style.dialog_progress;
            case TOP:
                return R.style.dialog_top;
        }

        return R.style.dialog;
    }

    /**
     * 提供布局
     *
     * @return
     */
    public abstract int provideLayoutId();


    /**
     * 展示
     */
    @Override
    public DialogInter show() {
        if (_Dialog == null) {
            init();
        }
        _Dialog.show();


        return this;
    }


    /**
     * 消失
     */
    @Override
    public void dismiss() {
        if (_Dialog != null) {
            _Dialog.dismiss();
        }
    }

    public Context getContext() {
        return _context;
    }

    public Dialog getDialog() {
        return _Dialog;
    }


    public YDialog setStyle(int style) {
        this.style = style;
        return this;
    }

}
