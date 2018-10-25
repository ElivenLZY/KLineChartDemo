package com.lzy.klinechartdemo.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.klinechartdemo.R;

import java.util.List;

public class KLineMenuView extends LinearLayout {

    private View tabView;

    private LayoutParams tabParams;

    private int selIndex = -1;

    public KLineMenuView(Context context) {
        this(context, null);
    }

    public KLineMenuView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KLineMenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOrientation(HORIZONTAL);
    }

    public void setData(List<String> dataList) {
        for (int i = 0; i < dataList.size(); i++) {
            String text = dataList.get(i);
            addTab(text, i);
        }
        updateTabSelection(0);
    }

    private void addTab(String text, int index) {
        tabView = View.inflate(getContext(), R.layout.content_k_line_menu_item, null);
        TextView tv_content = tabView.findViewById(R.id.tv_content);
        tv_content.setText(text);
        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = indexOfChild(v);
                updateTabSelection(index);
            }
        });

        tabParams = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);
        this.addView(tabView, index, tabParams);
    }

    private void updateTabSelection(int index) {
        if (index == selIndex) return;
        this.selIndex = index;
        int mTabCount = getChildCount();
        for (int i = 0; i < mTabCount; i++) {
            View tabView = getChildAt(i);
            View indicatorView = tabView.findViewById(R.id.indicator);
            final boolean isSelect = i == index;
            indicatorView.setVisibility(isSelect ? VISIBLE : INVISIBLE);
        }
        if (mOnTabSelectListener != null) mOnTabSelectListener.onTabClick(index);
    }

    private OnTabSelectListener mOnTabSelectListener;

    public void setOnTabSelectListener(OnTabSelectListener onTabSelectListener) {
        this.mOnTabSelectListener = onTabSelectListener;
    }

    public interface OnTabSelectListener {
        void onTabClick(int position);
    }

}
