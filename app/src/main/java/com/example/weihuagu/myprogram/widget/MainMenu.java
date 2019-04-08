package com.example.weihuagu.myprogram.widget;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.base.CP;
import com.example.base.utils.RxBus;
import com.example.weihuagu.myprogram.MainActivity;
import com.example.weihuagu.myprogram.R;
import com.example.weihuagu.myprogram.ui.fragment.IndexFragment;
import com.example.weihuagu.myprogram.ui.fragment.SettingFragment;

public class MainMenu extends LinearLayout implements View.OnTouchListener {
    private ViewGroup menuOne;
    private ImageView imgMenuOne;
    private TextView tvMenuOne;

    private ViewGroup menuTwo;
    private ImageView imgMenuTwo;
    private TextView tvMenuTwo;

    private ViewGroup menuThree;
    private ImageView imgMenuThree;
    private TextView tvMenuThree;

    private ViewGroup menuFour;
    private ImageView imgMenuFour;
    private TextView tvMenuFour;


    private Context _context;
    private Fragment[] fragments;
    private TextView imageCount;
    private TextView tv_unreadmsg_num;
    private LinearLayout linearLayout;

    private Delegate delegate;
    private int preTab = 0;
    private int curTab = 0;

    public MainMenu(Context context) {
        super(context);
        init(context, null, 0);
    }

    public MainMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public MainMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this._context = context;
        initFragments();
        inflate(context, R.layout.widget_menu, this);
        setOrientation(VERTICAL);
        linearLayout = findViewById(R.id.linear);
        menuOne = (FrameLayout) findViewById(R.id.menu_one);
        imgMenuOne = findViewById(R.id.img_menu_one);
        tvMenuOne = findViewById(R.id.tv_menu_one);

        menuTwo = (FrameLayout) findViewById(R.id.menu_two);
        imgMenuTwo = findViewById(R.id.img_menu_two);
        tvMenuTwo = findViewById(R.id.tv_menu_two);

        menuThree = (FrameLayout) findViewById(R.id.menu_three);
        imgMenuThree = findViewById(R.id.img_menu_three);
        tvMenuThree = findViewById(R.id.tv_menu_three);

        menuFour = (FrameLayout) findViewById(R.id.menu_four);
        imgMenuFour = findViewById(R.id.img_menu_four);
        tvMenuFour = findViewById(R.id.tv_menu_four);

        imageCount = findViewById(R.id.image_count);
        setMenu();
    }


    private void setMenu() {

        GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                RxBus.getDefault().post(CP.Event.EVENT_FIND_NEXT_UNREAD_MSG);

                return super.onDoubleTap(e);
            }
        });


        menuOne.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
        //首页
        menuOne.setOnClickListener(view12 -> {
            tvMenuOne.setTextColor(getResources().getColor(R.color.colorAccent));
            tvMenuTwo.setTextColor(getResources().getColor(R.color.tvBlack));
            tvMenuThree.setTextColor(getResources().getColor(R.color.tvBlack));
            tvMenuFour.setTextColor(getResources().getColor(R.color.tvBlack));
            imgMenuOne.setImageDrawable(getResources().getDrawable(R.drawable.ic_index_focus));
            imgMenuTwo.setImageDrawable(getResources().getDrawable(R.drawable.ic_setting));
            imgMenuThree.setImageDrawable(getResources().getDrawable(R.drawable.ic_index_focus));
            imgMenuFour.setImageDrawable(getResources().getDrawable(R.drawable.ic_index_focus));
            switchContent(0);
        });


        //发现
        menuTwo.setOnClickListener(view13 -> {
            tvMenuOne.setTextColor(getResources().getColor(R.color.tvBlack));
            tvMenuTwo.setTextColor(getResources().getColor(R.color.colorAccent));
            tvMenuThree.setTextColor(getResources().getColor(R.color.tvBlack));
            tvMenuFour.setTextColor(getResources().getColor(R.color.tvBlack));

            imgMenuOne.setImageDrawable(getResources().getDrawable(R.drawable.ic_index_focus));
            imgMenuTwo.setImageDrawable(getResources().getDrawable(R.drawable.ic_setting));
            imgMenuThree.setImageDrawable(getResources().getDrawable(R.drawable.ic_index_focus));
            imgMenuFour.setImageDrawable(getResources().getDrawable(R.drawable.ic_index_focus));

//
            switchContent(1);
        });

        //动态
        menuThree.setOnClickListener(view13 -> {

            tvMenuOne.setTextColor(getResources().getColor(R.color.tvBlack));
            imgMenuOne.setImageDrawable(getResources().getDrawable(R.drawable.ic_index_focus));

            tvMenuTwo.setTextColor(getResources().getColor(R.color.tvBlack));
            imgMenuTwo.setImageDrawable(getResources().getDrawable(R.drawable.ic_index_focus));

            tvMenuThree.setTextColor(getResources().getColor(R.color.tvPrimary));
            imgMenuThree.setImageDrawable(getResources().getDrawable(R.drawable.ic_index_focus));

            tvMenuFour.setTextColor(getResources().getColor(R.color.tvBlack));
            imgMenuFour.setImageDrawable(getResources().getDrawable(R.drawable.ic_index_focus));


            switchContent(2);

        });

        //我的
        menuFour.setOnClickListener(view1 -> {

            tvMenuOne.setTextColor(getResources().getColor(R.color.tvBlack));
            imgMenuOne.setImageDrawable(getResources().getDrawable(R.drawable.ic_index_focus));

            tvMenuTwo.setTextColor(getResources().getColor(R.color.tvBlack));
            imgMenuTwo.setImageDrawable(getResources().getDrawable(R.drawable.ic_index_focus));

            tvMenuThree.setTextColor(getResources().getColor(R.color.tvBlack));
            imgMenuThree.setImageDrawable(getResources().getDrawable(R.drawable.ic_index_focus));

            tvMenuFour.setTextColor(getResources().getColor(R.color.tvPrimary));
            imgMenuFour.setImageDrawable(getResources().getDrawable(R.drawable.ic_index_focus));
            switchContent(3);
        });

    }

    private void initFragments() {
        IndexFragment oneFragment = new IndexFragment();
        SettingFragment twoFragment = new SettingFragment();

        fragments = new Fragment[]{
                oneFragment,
                twoFragment,
        };
    }



    private void switchContent(int index) {
        preTab = curTab;
        curTab = index;
        FragmentManager supportFragmentManager = ((MainActivity) _context).getSupportFragmentManager();
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        Fragment fragment = supportFragmentManager.findFragmentByTag("tab_" + index);

        if (fragment == null) {
            fragment = fragments[index];
            if (fragment != null) {
                transaction.add(R.id.container, fragment, "tab_" + index);
            }
        } else {
            transaction.show(fragment);
        }

        for (int i = 0; i < fragments.length; i++) {
            fragment = supportFragmentManager.findFragmentByTag("tab_" + i);
            if (fragment == null || index == i) {
                continue;
            }
            transaction.hide(fragment);
        }


        transaction.commitAllowingStateLoss();
    }


    public void switchPreTab() {
        switchTab(preTab);
    }


    public void switchTab(int index) {
        switch (index) {
            case 0://首页
                menuOne.performClick();
                break;
            case 1://发现
                menuTwo.performClick();
                break;
            case 2://动态
                menuThree.performClick();
                break;
            case 3://我的
                menuFour.performClick();
                break;
        }
    }

    /**
     * 进入首页
     */
    public void enterHomeFragment() {
        initFragments();

//        FragmentTransaction localFragmentTransaction = ((HomeActivity) _context).getSupportFragmentManager().beginTransaction();
//        for (int i = 0; i < fragments.length; i++) {
//            Fragment fragment = fragments[i];
//            if (fragment != null) {
//                localFragmentTransaction.add(R.id.container, fragment, "tab_" + i);
//            }
//        }
//
//        localFragmentTransaction.commitAllowingStateLoss();
        switchTab(0);
    }


    public void setClick(boolean b) {
        if (!b) {
            linearLayout.setOnClickListener(view -> {

            });
        } else {
            linearLayout.setOnClickListener(null);
        }
    }


    public TextView getImageCount() {
        return imageCount;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {

        /**
         * 发生确定的单击时执行
         * @param e
         * @return
         */
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {//单击事件

            return super.onSingleTapConfirmed(e);
        }

        /**
         * 双击发生时的通知
         * @param e
         * @return
         */
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            //双击事件
            return super.onDoubleTap(e);
        }

        /**
         * 双击手势过程中发生的事件，包括按下、移动和抬起事件
         * @param e
         * @return
         */
        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return super.onDoubleTapEvent(e);
        }
    });

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }


    public interface Delegate {
        void switchTab(int tab);
    }

    public void setOnListener(Delegate delegate) {
        this.delegate = delegate;
    }





    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        RxBus..unregister(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        RxBus.get().register(this);

    }

    public Fragment[] getFragments() {
        return fragments;
    }
}
