package com.dawoo.gamebox.view.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.dawoo.gamebox.R;

/**
 * Created by alex on 18-3-30.
 * @author alex
 */

public class PasswordEditText extends android.support.v7.widget.AppCompatEditText {

    private Drawable mDrawableRight;
    /**
     * 判断当前密码打开状态，默认关闭状态
     */
    private boolean isOpen = false;


    public PasswordEditText(Context context) {
        this(context, null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mDrawableRight = getCompoundDrawables()[2];
        if (mDrawableRight == null) {

            setmDrawableRight(isOpen);
        }
    }


    public void setmDrawableRight(boolean isOpen) {
        int drawableId;

        if (!isOpen) {
            drawableId = R.mipmap.eye;
        } else {
            drawableId = R.mipmap.eyeclose;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mDrawableRight = getResources().getDrawable(drawableId, null);
        } else {
            mDrawableRight = getResources().getDrawable(drawableId);
        }
        mDrawableRight.setBounds(0, 0, mDrawableRight.getIntrinsicWidth(), mDrawableRight.getIntrinsicHeight());

        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], mDrawableRight, getCompoundDrawables()[3]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                Drawable drawable = getCompoundDrawables()[2];
                boolean isTouchRight = event.getX() > (getWidth() - getTotalPaddingRight()) && (event.getX() < ((getWidth() - getPaddingRight())));
                if (drawable != null && isTouchRight) {
                    isOpen = !isOpen;
                    setmDrawableRight(isOpen);
                    if (isOpen) {
                        setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    } else {
                        setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                    }
                    setSelection(getText().length());

                }
                break;
            default:
        }
        return super.onTouchEvent(event);
    }


}
