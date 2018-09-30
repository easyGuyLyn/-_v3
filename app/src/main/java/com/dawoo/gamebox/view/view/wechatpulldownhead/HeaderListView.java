package com.dawoo.gamebox.view.view.wechatpulldownhead;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;


/**
 * @author Created by  on on 2018/2/23
 */

public class HeaderListView extends ListView {

    private onTouchUpListener mOnTouchUpListener;

    public HeaderListView(Context context) {
        super(context);
    }

    public HeaderListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnTouchUpListener(onTouchUpListener onTouchUpListener) {
        mOnTouchUpListener = onTouchUpListener;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL) {
            if (mOnTouchUpListener != null) {
                mOnTouchUpListener.onActionUp();
            }
        }
        return super.onTouchEvent(ev);
    }

    public interface onTouchUpListener {
        void onActionUp();
    }
}
