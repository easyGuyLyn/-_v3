package com.dawoo.gamebox.view.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dawoo.coretool.util.activity.DensityUtil;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.payInfo.PayItemBean;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rain on 18-4-1.
 */

public class SelectBankDialog<T> extends Dialog {
    TextView cancel, confirm;
    LoopView loopView;
    private List<String> mArrayString = new ArrayList<>();
    private int mIndex;

    public SelectBankDialog(Context context) {
        super(context, R.style.CommonHintDialog);
        initView();
    }

    void initView() {
        setContentView(R.layout.dialog_select_bank);
        cancel = findViewById(R.id.cancel);
        confirm = findViewById(R.id.confirm);
        loopView = findViewById(R.id.lp_select_bank);
        setCancelable(true);
        setCanceledOnTouchOutside(false);

        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        loopView.setTextSize(20);
        loopView.setBackgroundColor(getContext().getResources().getColor(R.color.bgColor));
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        loopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                mIndex = index;
            }
        });
    }

    public void setData(List<T> datas, int selected) {
        this.mIndex = selected;
        for (T item : datas) {
            if (item instanceof PayItemBean) {
                mArrayString.add(((PayItemBean) item).getPayName());
            } else if (item instanceof String) {
                mArrayString.add((String) item);
            }
        }
        loopView.setItems(mArrayString);
        loopView.setInitPosition(mIndex);

    }

    public void setSureClicked(View.OnClickListener onClickListener) {
        confirm.setOnClickListener(onClickListener);
    }

    public int getIndex() {
        return mIndex;
    }
    public void clearDatas(){
        mArrayString.clear();
        mIndex=0;
    }
    public boolean isEmpty(){
        return mArrayString.isEmpty();
    }
}
