package com.example.track_hailing;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 叶泽锐 on 2017/12/8.
 */

public class TopBar extends RelativeLayout {

    private Button leftButton, rightButton;
    private OnLeftAndRightClickListener listener;//监听点击事件


    //设置监听器
    public void setOnLeftAndRightClickListener(OnLeftAndRightClickListener listener) {
        this.listener = listener;
    }

    //按钮点击接口
    public interface OnLeftAndRightClickListener {
        void OnLeftButtonClick();

        void OnRightButtonClick();
    }
    //设置左边按钮的可见性
    public void setLeftButtonVisibility(boolean flag){
        if (flag)
            leftButton.setVisibility(View.VISIBLE);
        else
            leftButton.setVisibility(View.GONE);
    }

    //设置右边按钮的可见性
    public void setRightButtonVisibility(boolean flag){
        if (flag)
            rightButton.setVisibility(View.VISIBLE);
        else
            rightButton.setVisibility(View.GONE);
    }
    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_topbar, this);

        leftButton = (Button) findViewById(R.id.leftButton);
        rightButton = (Button) findViewById(R.id.rightButton);

        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.OnLeftButtonClick();//点击回调
                }
            }
        });
        rightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.OnRightButtonClick();//点击回调
                }
            }
        });



        //获得自定义属性并赋值
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.TopBar);
        int leftBtnBackground = typeArray.getResourceId(R.styleable.TopBar_leftBackground, 0);
        int rightBtnBackground = typeArray.getResourceId(R.styleable.TopBar_rightBackground, 0);
        //释放资源
        typeArray.recycle();
        leftButton.setBackgroundResource(leftBtnBackground);
        rightButton.setBackgroundResource(rightBtnBackground);
    }
}
