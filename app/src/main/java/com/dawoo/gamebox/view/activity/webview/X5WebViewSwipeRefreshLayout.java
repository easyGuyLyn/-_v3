package com.dawoo.gamebox.view.activity.webview;

/**
 * Created by benson on 18-3-15.
 */

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.tencent.smtt.sdk.WebView;

/**
 *        兼容QQ X5WebView
 */
public class X5WebViewSwipeRefreshLayout extends SwipeRefreshLayout {
    public X5WebViewSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean canChildScrollUp() {
        View child = getChildAt(0);
        if (child != null && child instanceof WebView) {
            View scrollView = ((WebView) child).getChildAt(0);
            if (scrollView != null)
                return scrollView.getScrollY() != 0;
        }
        return super.canChildScrollUp();
    }
}
