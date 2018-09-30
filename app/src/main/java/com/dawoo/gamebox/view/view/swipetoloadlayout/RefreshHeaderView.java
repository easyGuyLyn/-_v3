package com.dawoo.gamebox.view.view.swipetoloadlayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;
import com.dawoo.gamebox.R;

/**
 * Created by benson on 17-12-24.
 */

public class RefreshHeaderView extends android.support.v7.widget.AppCompatTextView implements SwipeRefreshTrigger, SwipeTrigger {


    public RefreshHeaderView(Context context) {
        super(context);
    }

    public RefreshHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onRefresh() {
        setText(getResources().getString(R.string.REFRESHING));
    }

    @Override
    public void onPrepare() {
        setText("");
    }

    @Override
    public void onMove(int yScrolled, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            if (yScrolled >= getHeight()) {
                setText(getResources().getString(R.string.RELEASE_TO_REFRESH));
            } else {
                setText(getResources().getString(R.string.SWIPE_TO_REFRESH));
            }
        } else {
            setText(getResources().getString(R.string.REFRESH_RETURNING));
        }
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onComplete() {
        setText(getResources().getString(R.string.REFRESH_COMPLETE));
    }

    @Override
    public void onReset() {

    }
}
