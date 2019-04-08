package com.example.base.widget.baseview;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.base.R;
import com.example.base.common.BaseView;


public class TitleBar extends RelativeLayout  {

    private BaseView view;
    private DataSource dataSource;
    private ImageView imgLeft;
    private TextView tvTitle;
    private TextView tvRight;
    private ImageView imgRight;

    private ImageView imgRightLeft;
    private TextView tvLeft;
    public TextView tv_title_bottom;


    public TitleBar(Context context, DataSource dataSource) {
        super(context);
        this.dataSource = dataSource;
        init(context, null, 0);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.widget_title_bar, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        int style = typedArray.getInt(R.styleable.TitleBar_title_bar_style, 0);
        String title = typedArray.getString(R.styleable.TitleBar_title_bar_title);
        String rightText = typedArray.getString(R.styleable.TitleBar_title_bar_right_text);
        String leftText = typedArray.getString(R.styleable.TitleBar_title_bar_left_text);
        Drawable rightDrawable = typedArray.getDrawable(R.styleable.TitleBar_title_bar_right_img);
        Drawable leftDrawable = typedArray.getDrawable(R.styleable.TitleBar_title_bar_left_img);
        Drawable rightLeftDrawable = typedArray.getDrawable(R.styleable.TitleBar_title_bar_right_left_img);
        boolean leftImgEnabled = typedArray.getBoolean(R.styleable.TitleBar_title_bar_left_img_enabled, true);

        imgLeft = findViewById(R.id.img_left);
        tvTitle = findViewById(R.id.tv_title);
        tvRight = findViewById(R.id.tv_right);
        tvLeft = findViewById(R.id.tv_left);
        imgRight = findViewById(R.id.img_right);
        imgRightLeft = findViewById(R.id.img_right_left);
        tv_title_bottom = findViewById(R.id.tv_title_bottom);

        setLeftImgEnabled(leftImgEnabled);


        if (!TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(VISIBLE);
            setTitle(title);
        } else {
            if (dataSource != null) {
                tvTitle.setText(dataSource.getPageTitle());
            }
        }

        if (!TextUtils.isEmpty(rightText)) {
            setRightText(rightText);
        }

        if (!TextUtils.isEmpty(leftText)) {
            setLeftText(leftText);
        }

        if (rightDrawable != null) {
            setRitghtImg(rightDrawable);
        }

        if (leftDrawable != null) {
            setLeftImg(leftDrawable);
        }
        if (rightLeftDrawable != null) {
            setRitghtLeftImg(rightLeftDrawable);
        }

        imgLeft.setOnClickListener(v -> {
            if (getContext() instanceof Activity) {
                if (getContext() != null) {
                    ((Activity) getContext()).finish();
                }
            }
        });

        typedArray.recycle();

    }

    /**
     * 设置右边的图片，并显示出来
     *
     * @param drawable
     */
    public void setRitghtImg(Drawable drawable) {
        imgRight.setVisibility(VISIBLE);
        imgRight.setImageDrawable(drawable);
    }


    /**
     * 隐藏右边图片
     *
     * @param
     */
    public void setRitghtImgHide() {
        imgRight.setVisibility(GONE);
    }

    /**
     * 设置左边的图片，并显示出来
     *
     * @param drawable
     */
    public void setLeftImg(Drawable drawable) {
        imgLeft.setVisibility(VISIBLE);
        imgLeft.setImageDrawable(drawable);
    }

    /**
     * 设置右边靠左的图片，并显示出来
     *
     * @param drawable
     */
    public void setRitghtLeftImg(Drawable drawable) {
        imgRightLeft.setVisibility(VISIBLE);
        imgRightLeft.setImageDrawable(drawable);
    }

    /**
     * 设置右边textview的文字，并显示出来
     *
     * @param rightText
     */
    public void setRightText(String rightText) {
        tvRight.setVisibility(VISIBLE);
        tvRight.setText(rightText);
    }

    /**
     * 设置左边textview的文字，并显示出来
     *
     * @param leftText
     */
    public void setLeftText(String leftText) {
        tvLeft.setVisibility(VISIBLE);
        tvLeft.setText(leftText);
    }


    /**
     * 显示未读jiaobiao
     *
     * @param
     */

    /**
     * 隐藏未读jiaobiao
     *
     * @param
     */


    public TextView getTvRight() {
        return tvRight;
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setTitle(int ResId) {
        tvTitle.setText(getContext().getString(ResId));
    }

    public ImageView getImgLeft() {
        return imgLeft;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public ImageView getImgRight() {
        return imgRight;
    }

    public ImageView getImgRightLeft() {
        return imgRightLeft;
    }

    public void setView(BaseView view) {
        this.view = view;
    }

    /**
     * 左边的图片是否显示
     *
     * @param isEnabled
     */
    public void setLeftImgEnabled(boolean isEnabled) {
//        if (!dataSource.isNeedBack()) {
//            this.imgLeft.setVisibility(GONE);
//        }else {
//            this.imgLeft.setVisibility(isEnabled ? VISIBLE : GONE);
//        }

    }



    public interface DataSource {
        String getPageTitle();

        boolean isNeedBack();
    }

}
